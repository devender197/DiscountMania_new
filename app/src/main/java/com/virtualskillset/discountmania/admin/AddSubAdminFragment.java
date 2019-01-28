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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.model.ModelResponse;

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
public class AddSubAdminFragment extends Fragment {
    // LogCat tag
    //private static final String TAG = AdminMainActivity.class.getSimpleName();

    private Bitmap lastBitmap = null;
    // Camera activity request codes
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView btnCapturePicture;
    private EditText inputName,inputMobile,inputPassword,inputConpas;
    private Button register;
    private RadioGroup radioGroup;
    private int radioId=2;
    private TextView txtPercentage;
    private ProgressBar progressBar;
    String Name,mobile,password;
    private APIService uploadAPIService;
    private ProgressDialog progressDialog;
    private Context mContext;
    //private ProgressDialog pDialog;
   // private Uri fileUri; // file url to store image/video
   // private RadioButton radioButton;


    public AddSubAdminFragment() {
        // Required empty public constructor
    }

    public static AddSubAdminFragment newInstance() {
        AddSubAdminFragment fragm = new AddSubAdminFragment();
        return fragm;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_sub_admin, container, false);
        try{
            getActivity().setTitle("Add Subadmin");}catch (NullPointerException e){};
        btnCapturePicture = (ImageView)v. findViewById(R.id.img_ad);
        register=(Button)v.findViewById(R.id.button);
        radioGroup = (RadioGroup)v. findViewById(R.id.radioGroup);
        inputName=(EditText)v.findViewById(R.id.name);
        inputMobile=(EditText)v.findViewById(R.id.mobile);
        inputPassword=(EditText)v.findViewById(R.id.enterpas);
        inputConpas=(EditText)v.findViewById(R.id.conpas);
        txtPercentage = (TextView)v. findViewById(R.id.txtPercentage);
        progressBar = (ProgressBar)v. findViewById(R.id.progressBar);

        init();

     ///////////////////////Sub Admin Category /////////////////////////////////////
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton1:
                        radioId=2;
                        // do operations specific to this selection
                        break;
                    case R.id.radioButton2:
                        radioId=3;
                        // do operations specific to this selection
                        break;
                    case R.id.radioButton3:
                        radioId=4;
                        // do operations specific to this selection
                        break;
                }
            }
        });

        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = inputName.getText().toString().trim();
                mobile = inputMobile.getText().toString().trim();
                password= inputPassword.getText().toString().trim();
                String cnpassword = inputConpas.getText().toString().trim();
                if (!Name.isEmpty() && !mobile.isEmpty() && !password.isEmpty() && !cnpassword.isEmpty()) {
                    if(mobile.length()!=10 ){
                        inputMobile.setError("Please enter valid mobile no");
                        inputMobile.requestFocus();}
                    else {
                        if(password.equals(cnpassword)) {
                            if(password.length()>5) {
                                //new UploadFileToServer().execute();
                                uploadMultiFile();
                            }
                            else
                            {
                                inputPassword.setError("Password must be at least 6 character long");
                                inputPassword.requestFocus();
                            }
                        }
                        else {
                            inputConpas.setError("Password not match");
                            inputConpas.requestFocus();
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(Name)) {
                        inputName.setError("Please enter name");
                        inputName.requestFocus();
                    }
                    else if (TextUtils.isEmpty(mobile)) {
                        inputMobile.setError("Please enter your mobile no");
                        inputMobile.requestFocus();
                    } else if (TextUtils.isEmpty(password)) {
                        inputPassword.setError("Enter password");
                        inputPassword.requestFocus();
                    } else if (TextUtils.isEmpty(cnpassword)) {
                        inputConpas.setError("Enter confirm password");
                        inputConpas.requestFocus();
                    }
                }
            }
        });

        return v;

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
                .create(APIService.class);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Uploading...");
    }

    private void uploadMultiFile() {
        progressDialog.show();

        //Local SD card path
     /*   String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath + "/pic/";
        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add(targetPath+"/a.jpg");
        filePaths.add(targetPath+"/b.jpg");
*/


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("name",inputName.getText().toString())
                .addFormDataPart("number", mobile)
                .addFormDataPart("password",password)
                .addFormDataPart("role", String.valueOf(radioId))
                .addFormDataPart("status", "1");



            if(lastBitmap!=null) {
                File file = onConvertImage(lastBitmap);
                ;
                builder.addFormDataPart("pic", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }

        MultipartBody requestBody = builder.build();
        Call<ModelResponse> call = uploadAPIService.uploadMultiFile(requestBody);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
               // Log.d(TAG, "Response " + "Success " + response.body());
                //String resp=response.get;
                try {
                    String resp=response.body().ResponseMessage;
                    Toast.makeText(getContext(), resp, Toast.LENGTH_LONG).show();
                }catch (Exception e){ Toast.makeText(getContext(), "Success " + response.message(), Toast.LENGTH_LONG).show();}

                // Toast.makeText(getApplicationContext(), "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),t.getMessage(),1).show();
                //Log.d(TAG, "Error " + t.getMessage());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK  && data != null && data.getData() != null) {

            // Get selected gallery image
            Uri selectedPicture = data.getData();
            // Get and resize profile image
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
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
            btnCapturePicture.setImageBitmap(lastBitmap);

            // pic.setImageBitmap(lastBitmap);
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private File onConvertImage(Bitmap bmp)
    {
        File f = new File(getActivity().getCacheDir(), System.currentTimeMillis() + ".jpg");
//Convert bitmap to byte array
        bmp = lastBitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,6  /*ignored for PNG*/, bos);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


}
