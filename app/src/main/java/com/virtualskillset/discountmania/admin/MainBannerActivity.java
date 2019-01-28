package com.virtualskillset.discountmania.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.helper.adapter.Adapter_add_mainbanner;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.retro.ModelMerBanner;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainBannerActivity extends AppCompatActivity {
    private APIService uploadAPIService;
    private ProgressDialog progressDialog;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap lastBitmap = null;
    //private Uri filePath;
    private ImageView pic;
    private Button uplBtn;
    private List<ModelMerBanner> model=new ArrayList<>();
    private TextView emptyView;
    private Adapter_add_mainbanner adapterViewAndroid;
    ////grid///
    GridView androidGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_banner);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}

        pic=(ImageView)findViewById(R.id.pic);
        uplBtn=(Button)findViewById(R.id.sub);
        emptyView=(TextView)findViewById(R.id.empty_view);
        androidGridView=(GridView)findViewById(R.id.grid);
        progressDialog = new ProgressDialog(MainBannerActivity.this);
        getOffer();

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        uplBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastBitmap==null)
                { Toast.makeText(MainBannerActivity.this,"Please Select An Image",Toast.LENGTH_LONG).show(); }else { uploadMultiFile();}
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void uploadMultiFile() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build();

        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);

        progressDialog.setMessage("Uploading...");
        progressDialog.show();


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("id", "1");


        if(lastBitmap!=null) {
            File f = new File(MainBannerActivity.this.getCacheDir(), System.currentTimeMillis() + ".jpg");

//Convert bitmap to byte array
            Bitmap bitmap = lastBitmap;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20  /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            try {
                f.createNewFile();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (Exception e) {
            }

            //File file = onCaptureImageResult(lastBitmap);;
            builder.addFormDataPart("image", f.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), f));
        }

        MultipartBody requestBody = builder.build();
        Call<ModelResponse> call = uploadAPIService.addMerchantBanner(requestBody);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                // Log.d(TAG, "Response " + "Success " + response.body());
                //String resp=response.get;
                progressDialog.dismiss();
                try {
                    String resp=response.body().ResponseMessage;
                    Toast.makeText(MainBannerActivity.this, resp, Toast.LENGTH_LONG).show();
                    if(!response.body().getError())
                    {
                        getOffer();
                    }
                }catch (Exception e){ Toast.makeText(MainBannerActivity.this, "Success " + response.message(), Toast.LENGTH_LONG).show();}

                // Toast.makeText(MainBannerActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainBannerActivity.this,t.getMessage(),1).show();
                //Log.d(TAG, "Error " + t.getMessage());
            }
        });

    }

    private void showFileChooser() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageIntent.setType("image/*");
        pickImageIntent.putExtra("aspectX", 1);
        pickImageIntent.putExtra("aspectY", 1);
        pickImageIntent.putExtra("scale", true);
        // pickImageIntent.putExtra("outputFormat",
        //    Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK  && data != null && data.getData() != null) {

            // Get selected gallery image
            Uri selectedPicture = data.getData();
            // Get and resize profile image
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = MainBannerActivity.this.getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);

            ExifInterface exif = null;
            try {
                File pictureFile = new File(picturePath);
                exif = new ExifInterface(pictureFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            int orientation = ExifInterface.ORIENTATION_NORMAL;

            if (exif != null)
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    loadedBitmap = rotateBitmap(loadedBitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    loadedBitmap = rotateBitmap(loadedBitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    loadedBitmap = rotateBitmap(loadedBitmap, 270);
                    break;
            }

            lastBitmap=loadedBitmap;
            pic.setImageBitmap(lastBitmap);
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void getOffer() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<List<ModelMerBanner>> call = uploadAPIService.getMerBanner(1);
        call.enqueue(new Callback<List<ModelMerBanner>>() {
            @Override
            public void onResponse(Call<List<ModelMerBanner>> call, retrofit2.Response<List<ModelMerBanner>> response) {
                progressDialog.dismiss();
                model=response.body();
                if (model.contains(null) || model.isEmpty()) {
                    androidGridView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    androidGridView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    adapterViewAndroid = new Adapter_add_mainbanner(MainBannerActivity.this, model);
                    androidGridView.setAdapter(adapterViewAndroid);
                    /*androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int i, long id) {
                            // Toast.makeText(getApplicationContext(), "GridView Item: " + kmdeat[+i], Toast.LENGTH_LONG).show();
                        }
                    });*/
                    adapterViewAndroid.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<List<ModelMerBanner>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainBannerActivity.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


    public void delProduct(int idd) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(35, TimeUnit.SECONDS)
                .writeTimeout(35, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);

        // User user = SharedPrefManager.getInstance(MainBannerActivity.this).getUser();
        Call<ModelResponse> call = uploadAPIService.MerDeleteBanner(idd+"","DELETE");
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                //getOffer();
                if(response.isSuccessful())
                {
                    Toast.makeText(MainBannerActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    getOffer();
                }
                else
                    Toast.makeText(MainBannerActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainBannerActivity.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
}
