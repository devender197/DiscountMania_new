package com.virtualskillset.discountmania.user;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.user.model.ModelUserData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
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

public class AUserProfile extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    ApiUserInter apiInterface;
    private ProgressDialog progressDialog;
    private ModelUserData MerData;
    private User user;
    private EditText date,inputName,inputFatherName,inputProfession,inputEmail,inputPAN,inputAadhar,inputNumber,inputPin,inputAddress;
    private ImageView pic;
    private Button send,loc;
    int PLACE_PICKER_REQUEST = 1;
    private double lat,lng;
    private LatLng goglLatLng;
    private int PICK_IMAGE_REQUEST = 2;
    private Bitmap lastBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}
        user=SharedPrefManager.getInstance(AUserProfile.this).getUser();
        inputName=(EditText)findViewById(R.id.editText);
        inputFatherName=(EditText)findViewById(R.id.editText4);
        inputProfession=(EditText)findViewById(R.id.editText5);
        inputEmail=(EditText)findViewById(R.id.editText7);
        inputPAN=(EditText)findViewById(R.id.editText9);
        inputAadhar=(EditText)findViewById(R.id.editText11);
        inputNumber=(EditText)findViewById(R.id.editText13);
        inputPin=(EditText)findViewById(R.id.editText14);
        inputAddress=(EditText)findViewById(R.id.editText15);
        send=(Button)findViewById(R.id.button3);
        loc=(Button)findViewById(R.id.button2);
        pic=(ImageView)findViewById(R.id.prfimg);

        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.date);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AUserProfile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        getUsrDetail();

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(AUserProfile.this), PLACE_PICKER_REQUEST);
                }catch (Exception e){}
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMultiFile();
            }
        });

    }
    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                goglLatLng=place.getLatLng();
                lat=goglLatLng.latitude;
                lng=goglLatLng.longitude;
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                goglLatLng=place.getLatLng();
                lat=goglLatLng.latitude;
                lng=goglLatLng.longitude;
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK  && data != null && data.getData() != null) {

            // Get selected gallery image
            Uri selectedPicture = data.getData();
            // Get and resize profile image
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = AUserProfile.this.getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
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

    private void uploadMultiFile() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUserInter.class);
        progressDialog = new ProgressDialog(AUserProfile.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("name", inputName.getText().toString());
        builder.addFormDataPart("father",inputFatherName.getText().toString());
        builder.addFormDataPart("dob",date.getText().toString());
        builder.addFormDataPart("profession", inputProfession.getText().toString());
        builder.addFormDataPart("pan",inputPAN.getText().toString());
        builder.addFormDataPart("aadhar", inputAadhar.getText().toString());
        builder.addFormDataPart("email", inputEmail.getText().toString());
        builder.addFormDataPart("lat", lat+"");
        builder.addFormDataPart("long",lng+"");
        builder.addFormDataPart("pincode",inputPin.getText().toString());
        builder.addFormDataPart("address",inputAddress.getText().toString());
        builder.addFormDataPart("_method","PUT");



        if(lastBitmap!=null) {
            File f = new File(AUserProfile.this.getCacheDir(), System.currentTimeMillis() + ".jpg");

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
        Call<ModelResponse> call = apiInterface.updateUserDetails(user.getId(),requestBody);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                // Log.d(TAG, "Response " + "Success " + response.body());
                //String resp=response.get;
                progressDialog.dismiss();
                try {
                    String resp=response.body().ResponseMessage;
                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){ Toast.makeText(getApplicationContext(), "Success " + response.message(), Toast.LENGTH_LONG).show();}

                // Toast.makeText(mContext, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
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

    private void getUsrDetail() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUserInter.class);


        progressDialog = new ProgressDialog(AUserProfile.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<ModelUserData> call = apiInterface.getUserDetails(user.getId());
        call.enqueue(new Callback<ModelUserData>() {
            @Override
            public void onResponse(Call<ModelUserData> call, retrofit2.Response<ModelUserData> response) {
                progressDialog.dismiss();
                if (response != null) {
                    MerData = response.body();
                    inputName.setText(MerData.getName());
                    inputNumber.setText(MerData.getNumber());
                    inputFatherName.setText(MerData.getFather());
                    inputAadhar.setText(MerData.getAadhar());
                    inputAddress.setText(MerData.getAddress());
                    inputPAN.setText(MerData.getPan());
                    inputProfession.setText(MerData.getProfession());
                    inputEmail.setText(MerData.getEmail());
                    date.setText(MerData.getDob());
                    try {
                        if (MerData.getPincode() != 0) {
                            inputPin.setText(MerData.getPincode() + "");
                        }
                    } catch (Exception e) {
                    }
                   // int loader = R.drawable.ic_admin;
                    String image_url = "http://discountmania.org/images/client/" + MerData.getImage();

                    // ImageLoader class instance
                   /* ImageLoader imgLoader = new ImageLoader(AUserProfile.this);
                    imgLoader.DisplayImage(image_url, loader, pic);*/
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.drawable.ic_admin)
                            .error(R.drawable.ic_admin);
                    //.centerCrop()
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.priority(Priority.HIGH);
                    Glide.with(AUserProfile.this)
                            .load(image_url)
                            .apply(options)
                            //   .placeholder(R.drawable.noim)
                            .into(pic);
                }
            }

            @Override
            public void onFailure(Call<ModelUserData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
}
