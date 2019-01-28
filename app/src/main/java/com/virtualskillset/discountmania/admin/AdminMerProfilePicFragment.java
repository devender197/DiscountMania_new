package com.virtualskillset.discountmania.admin;


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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class AdminMerProfilePicFragment extends Fragment {
    private APIService uploadAPIService;
    APIService apiIfc;
    private ProgressDialog progressDialog;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap lastBitmap = null;
    //private Uri filePath;
    private ImageView pic;
    private Button uplBtn;
    private ModelMerchantList model;

    public AdminMerProfilePicFragment() {
        // Required empty public constructor
    }
    private Context mContext;

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_admin_mer_profile_pic, container, false);
        pic=(ImageView)v.findViewById(R.id.pic);
        uplBtn=(Button)v.findViewById(R.id.sub);
        getImage();


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
                { Toast.makeText(mContext,"Please Select An Image",Toast.LENGTH_LONG).show(); }else { uploadMultiFile();}
            }
        });

        return v;
    }

    private void init(){

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
        int mid=AdminAddMerImgActivity.m_id;


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("_method", "PUT");


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
            builder.addFormDataPart("pic", f.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), f));
        }

        MultipartBody requestBody = builder.build();
        Call<ModelResponse> call = uploadAPIService.updtMerchantImage(mid,requestBody);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                // Log.d(TAG, "Response " + "Success " + response.body());
                //String resp=response.get;
                progressDialog.dismiss();
                try {
                    String resp=response.body().ResponseMessage;
                    Toast.makeText(mContext, resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){ Toast.makeText(mContext, "Success " + response.message(), Toast.LENGTH_LONG).show();}

                // Toast.makeText(mContext, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
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

    private void getImage() {
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
                .create(APIService.class);


        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        User user = SharedPrefManager.getInstance(mContext).getUser();
        int mid=AdminAddMerImgActivity.m_id;

        Call<ModelMerchantList> call = apiIfc.getMerchantDetails(mid);
        call.enqueue(new Callback<ModelMerchantList>() {
            @Override
            public void onResponse(Call<ModelMerchantList> call, retrofit2.Response<ModelMerchantList> response) {
                progressDialog.dismiss();
                model=response.body();

               // int loader = R.drawable.admin;
                String image_url = "http://discountmania.org/images/merchant/profile/"+model.getImage();

              /*  // ImageLoader class instance
                ImageLoader imgLoader = new ImageLoader(mContext);
                imgLoader.DisplayImage(image_url, loader, pic);*/
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.admin)
                        .error(R.drawable.admin);
                //.centerCrop()
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                //.priority(Priority.HIGH);

                Glide.with(mContext)
                        .load(image_url)
                        .apply(options)
                        //   .placeholder(R.drawable.noim)
                        // .apply(RequestOptions.circleCropTransform())
                        .into(pic);
            }

            @Override
            public void onFailure(Call<ModelMerchantList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

}
