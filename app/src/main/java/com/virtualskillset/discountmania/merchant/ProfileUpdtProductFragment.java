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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.AdminAddMerImgActivity;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.merAdpter.Adapter_add_product;
import com.virtualskillset.discountmania.merchant.retro.ModelMerProduct;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileUpdtProductFragment extends Fragment  {
    private EditText des;
    private ApiInterfaceMer uploadAPIService;
    private ApiInterfaceMer apiIfc;
    private ProgressDialog progressDialog;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap lastBitmap = null;
    //private Uri filePath;
    private ImageView pic;
    private Button uplBtn;
    private List<ModelMerProduct> model=new ArrayList<>();
    private TextView emptyView;
    private Adapter_add_product adapterViewAndroid;
    private Context mContext;



    ///file related activity_cus_shp_list.xml , gridtwo.xml ,adapter_shp_list.java

    ////grid///
    GridView androidGridView;


    public ProfileUpdtProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile_updt_product, container, false);
        pic=(ImageView)v.findViewById(R.id.pic);
        //tag,cat,exp,rep,disc;
        des=(EditText)v.findViewById(R.id.tagg);
        uplBtn=(Button)v.findViewById(R.id.sub);
        emptyView=(TextView)v.findViewById(R.id.empty_view);
        androidGridView=(GridView)v.findViewById(R.id.grid);
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
                if(des.getText().toString().trim().isEmpty()) { des.setError("Enter Description");des.requestFocus(); }else { uploadMultiFile();}
            }
        });
        return v;
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

        progressDialog = new ProgressDialog(mContext);
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
        User user = SharedPrefManager.getInstance(mContext).getUser();
        int mid=0;
        if(!user.getRole().equals("1"))
        {

            mid=MerchantUpdatePfActivity.m_id;
        }
        else {
            mid=user.getId();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("id", mid+"")
                .addFormDataPart("desc", des.getText().toString());


        if(lastBitmap!=null) {
            File f = new File(mContext.getCacheDir(), System.currentTimeMillis() + ".jpg");

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

        final MultipartBody requestBody = builder.build();
        Call<ModelResponse> call = uploadAPIService.addMerchantProduct(requestBody);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                // Log.d(TAG, "Response " + "Success " + response.body());
                //String resp=response.get;
                progressDialog.dismiss();
                if (response.body() != null) {
                    try {
                        String resp = response.body().ResponseMessage;
                        Toast.makeText(mContext, resp, Toast.LENGTH_LONG).show();
                        if (!response.body().getError()) {
                            getOffer();
                        }
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Success " + response.message(), Toast.LENGTH_LONG).show();
                    }

                    // Toast.makeText(mContext, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext,t.getMessage(),1).show();
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
            Cursor cursor = mContext.getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
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


        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        User user = SharedPrefManager.getInstance(mContext).getUser();
        int mid=0;
        if(!user.getRole().equals("1"))
        {
            mid=MerchantUpdatePfActivity.m_id;
        }
        else {
            mid=user.getId();
        }
        Call<List<ModelMerProduct>> call = apiIfc.getMerProduct(mid);
        call.enqueue(new Callback<List<ModelMerProduct>>() {
            @Override
            public void onResponse(Call<List<ModelMerProduct>> call, retrofit2.Response<List<ModelMerProduct>> response) {
                progressDialog.dismiss();
                if (response.body()==null) {
                    androidGridView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    model=response.body();
                    androidGridView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    adapterViewAndroid = new Adapter_add_product(mContext, model,ProfileUpdtProductFragment.this);
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
            public void onFailure(Call<List<ModelMerProduct>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


    public void delProduct(int idd) {
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

        // User user = SharedPrefManager.getInstance(mContext).getUser();
        Call<ModelResponse> call = apiIfc.MerDeleteProduct(idd+"","DELETE");
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                //getOffer();
                if(response.body()!=null)
                {
                    Toast.makeText(mContext, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    getOffer();
                }
                else
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(mContext, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
