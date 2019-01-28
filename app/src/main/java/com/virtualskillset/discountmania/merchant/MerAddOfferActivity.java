package com.virtualskillset.discountmania.merchant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.merAdpter.Adapter_add_offer;
import com.virtualskillset.discountmania.merchant.retro.ModelMerOffer;
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

public class MerAddOfferActivity extends AppCompatActivity {
    private EditText tag,cat,exp,rep,disc;
    private ApiInterfaceMer uploadAPIService;
    private ApiInterfaceMer apiIfc;
    private ProgressDialog progressDialog;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap lastBitmap = null;
    //private Uri filePath;
    private ImageView pic;
    private Button uplBtn;
    private List<ModelMerOffer> model=new ArrayList<>();
    private TextView emptyView;
    private Adapter_add_offer adapterViewAndroid;



    ///file related activity_cus_shp_list.xml , gridtwo.xml ,adapter_shp_list.java

    ////grid///
    GridView androidGridView;
/*
    String[] kmdeat = {
            "500₹", "400₹", "200₹", "350₹", "10,000₹", "15,000₹","800₹"

    } ;

    String[] dis = {
            "5%", "10%", "20%", "20%", "20%", "20%","20%"

    } ;

    String[] time = {
            "1 Jan 19", "1 Jan 19", "1 Jan 19", "1 Jan 19", "1 Jan 19", "1 Jan 19","1 Jan 19"

    } ;

    int[] gridViewImageId = {
            R.drawable.ofr, R.drawable.ofr, R.drawable.ofr, R.drawable.ofr, R.drawable.ofr, R.drawable.ofr, R.drawable.ofr};

    int[] cal = {
            R.drawable.dlt, R.drawable.dlt, R.drawable.dlt, R.drawable.dlt, R.drawable.dlt, R.drawable.dlt, R.drawable.dlt};

    int[] mod = {
            R.drawable.mod, R.drawable.mod, R.drawable.mod, R.drawable.mod, R.drawable.mod, R.drawable.mod, R.drawable.mod};


    String[] detailstr = {
            "Mobile", "Kitchen Product", "Mobile", "Business Product", "Mobile",
            "Business Product", "Business Product"

    } ;
    String[] headname = {
            "Diwali offer", "New year offer", "Regular offer", "Regular offer", "Regular offer",
            "Regular offer", "Regular offer"

    } ;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_add_offer);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){}
        pic=(ImageView)findViewById(R.id.pic);
        //tag,cat,exp,rep,disc;
        tag=(EditText)findViewById(R.id.tag);
        cat=(EditText)findViewById(R.id.cat);
        exp=(EditText)findViewById(R.id.mob);
        rep=(EditText)findViewById(R.id.dis);
        disc=(EditText)findViewById(R.id.time);
        uplBtn=(Button)findViewById(R.id.sub);
        emptyView=(TextView)findViewById(R.id.empty_view);
        androidGridView=(GridView)findViewById(R.id.grid);
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
                uploadMultiFile();
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

    private void init(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build();

        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);

        progressDialog = new ProgressDialog(MerAddOfferActivity.this);
        progressDialog.setMessage("Uploading...");
    }


    private void uploadMultiFile() {
        init();
        progressDialog.show();

        //Local SD card path
     /*   String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/pic/";
        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(targetPath+"/a.jpg");
        filePaths.add(targetPath+"/b.jpg");
*/
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("id", user.getId()+"")
                .addFormDataPart("tag", tag.getText().toString())
                .addFormDataPart("cat",cat.getText().toString())
                .addFormDataPart("expire", exp.getText().toString())
                .addFormDataPart("price",rep.getText().toString())
                .addFormDataPart("disc",disc.getText().toString());

        if(lastBitmap!=null) {
            File f = new File(getApplicationContext().getCacheDir(), System.currentTimeMillis() + ".jpg");

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
        Call<ModelResponse> call = uploadAPIService.uploadMerchantOffer(requestBody);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                // Log.d(TAG, "Response " + "Success " + response.body());
                //String resp=response.get;
                progressDialog.dismiss();
                try {
                    String resp=response.body().ResponseMessage;
                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
                    if(!response.body().getError())
                    {
                        getOffer();
                    }
                }catch (Exception e){ Toast.makeText(getApplicationContext(), "Success " + response.message(), Toast.LENGTH_LONG).show();}

                // Toast.makeText(getApplicationContext(), "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),1).show();
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
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                lastBitmap = rotateBitmap(filePath.getPath(),bitmap);
                //encoding image to string
                /// image = getStringImage(lastBitmap);
                //btnCapturePicture.setImageBitmap(lastBitmap);
                pic.setImageBitmap(lastBitmap);
                // Log.d("image",image);
                //passing the image to volley


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK  && data != null && data.getData() != null) {

            // Get selected gallery image
            Uri selectedPicture = data.getData();
            // Get and resize profile image
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = MerAddOfferActivity.this.getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
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
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiIfc = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);


        progressDialog = new ProgressDialog(MerAddOfferActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        User user = SharedPrefManager.getInstance(MerAddOfferActivity.this).getUser();
        Call<List<ModelMerOffer>> call = apiIfc.getMerOffer(user.getId());
        call.enqueue(new Callback<List<ModelMerOffer>>() {
            @Override
            public void onResponse(Call<List<ModelMerOffer>> call, retrofit2.Response<List<ModelMerOffer>> response) {
                progressDialog.dismiss();
                model=response.body();
                if (model.contains(null) || model.isEmpty()) {
                    androidGridView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    androidGridView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    adapterViewAndroid = new Adapter_add_offer(MerAddOfferActivity.this, model);
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
            public void onFailure(Call<List<ModelMerOffer>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MerAddOfferActivity.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


    public void delOffer(int id) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiIfc = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);

       // User user = SharedPrefManager.getInstance(MerAddOfferActivity.this).getUser();
        Call<ModelResponse> call = apiIfc.MerDeleteOffer(id+"","DELETE");
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                //getOffer();
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    getOffer();
                }
                else
                    Toast.makeText(getApplicationContext(), "null"+response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


}


