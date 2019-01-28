package com.virtualskillset.discountmania.merchant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.model.ModelResponse;

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

public class MerchantImagesAddActivity extends AppCompatActivity {
    private ImageView up1,up2,up3,up4,up5,up6,up7,up8,up9,up10,snd1,snd2,snd3,snd4,snd5,snd6,snd7,snd8,snd9,snd10;
    private EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9,ed10;
    Bitmap lastBitmap,lastBitmap1,lastBitmap2,lastBitmap3,lastBitmap4,lastBitmap5,lastBitmap6,lastBitmap7,lastBitmap8,lastBitmap9,lastBitmap10,lastBitmap11,lastBitmap12,lastBitmap13,lastBitmap14,lastBitmap15 = null;
   // Uri filePath;
    int btnID=0,banID=0;
    // Camera activity request codes
    private int PICK_IMAGE_REQUEST = 1;
    private ApiInterfaceMer uploadAPIService;
    private ProgressDialog progressDialog;
    private String mid;
    private Button ban1,ban2,ban3,ban4,ban5,upload;
    private Context ctx=MerchantImagesAddActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_images_add);
        if(getIntent().getStringExtra("id")!=null)
        {
            mid=getIntent().getStringExtra("id");
        }
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){}
        up1=(ImageView)findViewById(R.id.upl1);
        up2=(ImageView)findViewById(R.id.upl2);
        up3=(ImageView)findViewById(R.id.upl3);
        up4=(ImageView)findViewById(R.id.upl4);
        up5=(ImageView)findViewById(R.id.upl5);
        up6=(ImageView)findViewById(R.id.upl6);
        up7=(ImageView)findViewById(R.id.upl7);
        up8=(ImageView)findViewById(R.id.upl8);
        up9=(ImageView)findViewById(R.id.upl9);
        up10=(ImageView)findViewById(R.id.upl10);
        snd1=(ImageView)findViewById(R.id.snd1);
        snd2=(ImageView)findViewById(R.id.snd2);
        snd3=(ImageView)findViewById(R.id.snd3);
        snd4=(ImageView)findViewById(R.id.snd4);
        snd5=(ImageView)findViewById(R.id.snd5);
        snd6=(ImageView)findViewById(R.id.snd6);
        snd7=(ImageView)findViewById(R.id.snd7);
        snd8=(ImageView)findViewById(R.id.snd8);
        snd9=(ImageView)findViewById(R.id.snd9);
        snd10=(ImageView)findViewById(R.id.snd10);

        ed1=(EditText)findViewById(R.id.ed1);
        ed2=(EditText)findViewById(R.id.ed2);
        ed3=(EditText)findViewById(R.id.ed3);
        ed4=(EditText)findViewById(R.id.ed4);
        ed5=(EditText)findViewById(R.id.ed5);
        ed6=(EditText)findViewById(R.id.ed6);
        ed7=(EditText)findViewById(R.id.ed7);
        ed8=(EditText)findViewById(R.id.ed8);
        ed9=(EditText)findViewById(R.id.ed9);
        ed10=(EditText)findViewById(R.id.ed10);
        ban1=(Button)findViewById(R.id.ban1);
        ban2=(Button)findViewById(R.id.ban2);
        ban3=(Button)findViewById(R.id.ban3);
        ban4=(Button)findViewById(R.id.ban4);
        ban5=(Button)findViewById(R.id.ban5);
        upload=(Button)findViewById(R.id.button2);
        init();

        up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(1);

            }
        });
        up2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(2);
            }
        });
        up3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(3);
            }
        });
        up4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(4);
            }
        });
        up5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(5);
            }
        });
        up6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(6);
            }
        });
        up7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(7);
            }
        });
        up8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(8);
            }
        });
        up9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(9);
            }
        });
        up10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(10);
            }
        });

        snd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed1.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(1);
                }
                else
                {
                    ed1.setError("Enter Product Details");
                }
            }
        });
        snd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed2.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(2);
                }
                else
                {
                    ed2.setError("Enter Product Details");
                }
            }
        });
        snd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed3.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(3);
                }
                else
                {
                    ed3.setError("Enter Product Details");
                }
            }
        });
        snd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed4.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(4);
                }
                else
                {
                    ed4.setError("Enter Product Details");
                }
            }
        });
        snd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed5.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(5);
                }
                else
                {
                    ed5.setError("Enter Product Details");
                }
            }
        });
        snd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed6.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(6);
                }
                else
                {
                    ed6.setError("Enter Product Details");
                }
            }
        });
        snd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed7.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(7);
                }
                else
                {
                    ed7.setError("Enter Product Details");
                }
            }
        });
        snd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed8.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(8);
                }
                else
                {
                    ed8.setError("Enter Product Details");
                }
            }
        });
        snd9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed9.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(9);
                }
                else
                {
                    ed9.setError("Enter Product Details");
                }
            }
        });
        snd10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed10.getText().toString().trim().isEmpty())
                {
                    uploadMultiFile(10);
                }
                else
                {
                    ed10.setError("Enter Product Details");
                }
            }
        });

        ban1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(11);
                banID=11;
            }
        });
        ban2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(12);
                banID=12;
            }
        });
        ban3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(13);
                banID=13;
            }
        });
        ban4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(14);
                banID=14;
            }
        });
        ban5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(15);
                banID=15;
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(banID!=0) {
                    uploadBanner(banID);
                }else {
                    Toast.makeText(ctx,"Select A banner",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    private void showFileChooser(int btnid) {
        btnID=btnid;
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

 /*   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), filePath);
                if(btnID==1)
                {
                    lastBitmap1 = bitmap;
                    up1.setImageBitmap(lastBitmap1);
                }
                else if(btnID==2)
                {
                    lastBitmap2 = bitmap;
                    up2.setImageBitmap(lastBitmap2);
                }
                else if(btnID==3)
                {
                    lastBitmap3 = bitmap;
                    up3.setImageBitmap(lastBitmap3);
                }
                else if(btnID==4)
                {
                    lastBitmap4 = bitmap;
                    up4.setImageBitmap(lastBitmap4);
                }
                else if(btnID==5)
                {
                    lastBitmap5 = bitmap;
                    up5.setImageBitmap(lastBitmap5);
                }
                else if(btnID==11)
                {
                    lastBitmap11 = bitmap;
                    ban1.setBackground(new BitmapDrawable(getResources(),lastBitmap11));
                }
                else if(btnID==12)
                {
                    lastBitmap12 = bitmap;
                    ban2.setBackground(new BitmapDrawable(getResources(),lastBitmap12));
                }
                else if(btnID==13)
                {
                    lastBitmap13 = bitmap;
                    ban3.setBackground(new BitmapDrawable(getResources(),lastBitmap13));
                }
                //encoding image to string
                /// image = getStringImage(lastBitmap);
                // btnCapturePicture.setImageBitmap(lastBitmap);
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
            Cursor cursor = MerchantImagesAddActivity.this.getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
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
            if(btnID==1)
            {
                lastBitmap1 = lastBitmap;
                up1.setImageBitmap(lastBitmap1);
            }
            else if(btnID==2)
            {
                lastBitmap2 = lastBitmap;
                up2.setImageBitmap(lastBitmap2);
            }
            else if(btnID==3)
            {
                lastBitmap3 = lastBitmap;
                up3.setImageBitmap(lastBitmap3);
            }
            else if(btnID==4)
            {
                lastBitmap4 = lastBitmap;
                up4.setImageBitmap(lastBitmap4);
            }
            else if(btnID==5)
            {
                lastBitmap5 = lastBitmap;
                up5.setImageBitmap(lastBitmap5);
            }
            else if(btnID==6)
            {
                lastBitmap6 = lastBitmap;
                up6.setImageBitmap(lastBitmap6);
            }
            else if(btnID==7)
            {
                lastBitmap7 = lastBitmap;
                up7.setImageBitmap(lastBitmap7);
            }
            else if(btnID==8)
            {
                lastBitmap8 = lastBitmap;
                up8.setImageBitmap(lastBitmap8);
            }
            else if(btnID==9)
            {
                lastBitmap9 = lastBitmap;
                up9.setImageBitmap(lastBitmap9);
            }
            else if(btnID==10)
            {
                lastBitmap10 = lastBitmap;
                up10.setImageBitmap(lastBitmap10);
            }
            else if(btnID==11)
            {
                lastBitmap11 = lastBitmap;
                ban1.setBackground(new BitmapDrawable(getResources(),lastBitmap11));
            }
            else if(btnID==12)
            {
                lastBitmap12 = lastBitmap;
                ban2.setBackground(new BitmapDrawable(getResources(),lastBitmap12));
            }
            else if(btnID==13)
            {
                lastBitmap13 = lastBitmap;
                ban3.setBackground(new BitmapDrawable(getResources(),lastBitmap13));
            }
            else if(btnID==14)
            {
                lastBitmap14 = lastBitmap;
                ban4.setBackground(new BitmapDrawable(getResources(),lastBitmap14));
            }
            else if(btnID==15)
            {
                lastBitmap15 = lastBitmap;
                ban5.setBackground(new BitmapDrawable(getResources(),lastBitmap15));
            }
           // pic.setImageBitmap(lastBitmap);
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
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

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Uploading...");
    }

    private void uploadMultiFile(int btnid) {
        progressDialog.show();
        String details="",pic="",des="";

        if(btnid==1)
        {
            lastBitmap=lastBitmap1;
            details=ed1.getText().toString();
            pic="p_pic1";
            des="p_desc1";
        }
        else if(btnid==2)
        {
            lastBitmap=lastBitmap2;
            details=ed2.getText().toString();
            pic="p_pic2";
            des="p_desc2";
        }
        else if(btnid==3)
        {
            lastBitmap=lastBitmap3;
            details=ed3.getText().toString();
            pic="p_pic3";
            des="p_desc3";
        }
        else if(btnid==4)
        {
            lastBitmap=lastBitmap4;
            details=ed4.getText().toString();
            pic="p_pic4";
            des="p_desc4";
        }
        else if(btnid==5)
        {
            lastBitmap=lastBitmap5;
            details=ed5.getText().toString();
            pic="p_pic5";
            des="p_desc5";
        }
        else if(btnid==6)
        {
            lastBitmap=lastBitmap6;
            details=ed6.getText().toString();
        }
        else if(btnid==7)
        {
            lastBitmap=lastBitmap7;
            details=ed7.getText().toString();
        }
        else if(btnid==8)
        {
            lastBitmap=lastBitmap8;
            details=ed8.getText().toString();
        }
        else if(btnid==9)
        {
            lastBitmap=lastBitmap9;
            details=ed9.getText().toString();
        }
        else if(btnid==10)
        {
            lastBitmap=lastBitmap10;
            details=ed10.getText().toString();
        }

        //Local SD card path
     /*   String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/pic/";
        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(targetPath+"/a.jpg");
        filePaths.add(targetPath+"/b.jpg");
*/


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("id", mid).addFormDataPart("desc",details);




        File file = onConvertImage(lastBitmap);
        builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));


        MultipartBody requestBody = builder.build();
        Call<ModelResponse> call = uploadAPIService.uploadMerchantProducts(requestBody);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                // Log.d(TAG, "Response " + "Success " + response.body());
                //String resp=response.get;
                try {
                    String resp=response.body().ResponseMessage;
                    Toast.makeText(ctx, resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){ Toast.makeText(ctx, "Success " + response.message(), Toast.LENGTH_LONG).show();}

                // Toast.makeText(getApplicationContext(), "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ctx,t.getMessage(),1).show();
                //Log.d(TAG, "Error " + t.getMessage());
            }
        });

    }

   /* private File onCaptureImageResult(Bitmap bitmap) {
        File imgFile;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/DiscountMania");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
        imgFile = new File(*//*Environment.getExternalStorageDirectory()*//*myDir,
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            imgFile.createNewFile();
            fo = new FileOutputStream(imgFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgFile;
    }*/

    private File onConvertImage(Bitmap bmp)
    {
        File f = new File(getApplicationContext().getCacheDir(), System.currentTimeMillis() + ".jpg");
//Convert bitmap to byte array
        bmp = lastBitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,10  /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }



    private void uploadBanner(int btnid) {
        progressDialog.show();
        String pic="";

        if(btnid==11)
        {
            lastBitmap=lastBitmap11;
            pic="banner1";
            // des="p_desc1";
        }
        else if(btnid==12)
        {
            lastBitmap=lastBitmap12;
            pic="banner2";
        }
        else if(btnid==13)
        {
            lastBitmap=lastBitmap13;
            pic="banner3";
        }
        else if(btnid==14)
        {
            lastBitmap=lastBitmap14;
            pic="banner4";
        }
        else if(btnid==15)
        {
            lastBitmap=lastBitmap15;
            pic="banner5";
        }
        //Local SD card path
     /*   String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/pic/";
        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(targetPath+"/a.jpg");
        filePaths.add(targetPath+"/b.jpg");
*/


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        // builder.addFormDataPart(des,details)
        builder.addFormDataPart("_method", "PUT");


        File file = onConvertImage(lastBitmap);
        builder.addFormDataPart(pic, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));


        MultipartBody requestBody = builder.build();
        Call<ModelResponse> call = uploadAPIService.uploadMerchantBanner(mid,requestBody);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                // Log.d(TAG, "Response " + "Success " + response.body());
                //String resp=response.get;
                progressDialog.dismiss();
                if (response.body()!=null) {
                    try {
                        String resp = response.body().ResponseMessage;
                        Toast.makeText(ctx, resp, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ctx, "Success " + response.message(), Toast.LENGTH_LONG).show();
                    }

                    // Toast.makeText(getApplicationContext(), "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ctx,t.getMessage(),1).show();
                //Log.d(TAG, "Error " + t.getMessage());
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
}

