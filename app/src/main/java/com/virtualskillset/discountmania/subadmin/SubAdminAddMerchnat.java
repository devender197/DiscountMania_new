package com.virtualskillset.discountmania.subadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.retro.ModelMerchantAdd;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.merchant.MerchantImagesAddActivity;
import com.virtualskillset.discountmania.merchant.retro.ModelSubC;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.merchant.retro.SubCat;
import com.virtualskillset.discountmania.user.model.ModelDistricts;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubAdminAddMerchnat extends AppCompatActivity {
    int PLACE_PICKER_REQUEST = 1;
    private Spinner spn1,spn2,spn_dis,spn_state;
    private String Category,SubCatageory,district,state,verotp,oldmob;
    private Button btn_register,btn_otp,btn_loc;
    private EditText inputShopName,inputOwnerName,inputMobile,inputEmail,inputPassword,inputConPassword,inputBusinessDes,inputWorkDays,inputWorkTime,inputStreetAdd,inputArea,inputPincode,inputPAN,inputGST,inputTIN,inputCIN,inputWhatsNO,inputFBlink,inputInstaLink,inputTwitterLink,inputYouTubeLink,inputOTP;
    private double lat,lng;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private ArrayList<String> subcat=new ArrayList<>();
    ArrayAdapter<String> subadp;
    private APIsubAdmin uploadAPIService;
    private ProgressDialog progressDialog;
    private Timer buttonTimer;
    private boolean timm=false;
    private CheckBox tnc;
    private EditText disMin,disMax;
    private TextView tncc;
    private LatLng goglLatLng;
    private ImageView icon,agreement;
    Context ctx=SubAdminAddMerchnat.this;
    private int resm=0;
    private int PICK_IMAGE_REQUEST = 2;
    private Bitmap lastBitmap1=null,lastBitmap2=null;
    private List<ModelDistricts>  modelData;
    private ArrayAdapter<String> spnAdp_state,spnAdp_dis;
    private ArrayList<String>arr_state=new ArrayList<>(),arr_dis=new ArrayList<>();
    int btnID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin_add_merchnat);

        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}
        spn1=(Spinner)findViewById(R.id.spinner1);
        spn2=(Spinner)findViewById(R.id.spinner2);
        spn_dis=(Spinner)findViewById(R.id.spinner4);
        spn_state=(Spinner)findViewById(R.id.spinner3);
        btn_register=(Button)findViewById(R.id.btnRegister);
        btn_loc=(Button)findViewById(R.id.loc);
        btn_otp=(Button)findViewById(R.id.btnOtp);
        inputShopName=(EditText)findViewById(R.id.sh_name);
        inputOwnerName=(EditText)findViewById(R.id.own_name);
        inputMobile=(EditText)findViewById(R.id.mob);
        inputEmail=(EditText)findViewById(R.id.mail);
        inputPassword=(EditText)findViewById(R.id.password);
        inputConPassword=(EditText)findViewById(R.id.conpass);
        inputBusinessDes=(EditText)findViewById(R.id.business_des);
        inputWorkDays=(EditText)findViewById(R.id.open);
        inputWorkTime=(EditText)findViewById(R.id.close);
        inputStreetAdd=(EditText)findViewById(R.id.street);
        inputArea=(EditText)findViewById(R.id.area);
        inputPincode=(EditText)findViewById(R.id.pincode);
        inputPAN=(EditText)findViewById(R.id.pan);
        inputGST=(EditText)findViewById(R.id.gst);
        inputTIN=(EditText)findViewById(R.id.tin);
        inputCIN=(EditText)findViewById(R.id.cin);
        inputWhatsNO=(EditText)findViewById(R.id.whtnm);
        inputFBlink=(EditText)findViewById(R.id.face);
        inputInstaLink=(EditText)findViewById(R.id.instagram);
        inputTwitterLink=(EditText)findViewById(R.id.twitter);
        inputYouTubeLink=(EditText)findViewById(R.id.youtube);
        // inputPaymentMethod=(EditText)findViewById(R.id.mth);
        inputOTP=(EditText)findViewById(R.id.otp);
        tnc=(CheckBox)findViewById(R.id.checkBox);
        disMax=(EditText)findViewById(R.id.disMax);
        disMin=(EditText)findViewById(R.id.disMin);
        tncc=(TextView)findViewById(R.id.tncc);
        icon=(ImageView)findViewById(R.id.busIcn);
        agreement=(ImageView)findViewById(R.id.agrIcn);


        progressDialog = new ProgressDialog(SubAdminAddMerchnat.this);
        progressDialog.setCancelable(false);
        subcat.add("Default");
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(1);
            }
        });
        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(2);
            }
        });

        /////////////////////category spinner//////////////
        ArrayAdapter<String> mainCat = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                getResources().getStringArray(R.array.category));
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn1.setAdapter(mainCat);
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category=getResources().getStringArray(R.array.category)[position];
                //GetSubCat(Category);
                getSubCat();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////////////sub cat spinner///////
        subadp = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item, subcat);
        //myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spn2.setAdapter(subadp);
        spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    SubCatageory=subcat.get(position);
                }
                else {
                    SubCatageory="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////////////category state//////////////
        spnAdp_state = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                arr_state);
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn_state.setAdapter(spnAdp_state);
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = arr_state.get(position);
                arr_dis.clear();
                for (int i = 0; i < modelData.get(position).getDistricts().size(); i++) {
                    arr_dis.add(modelData.get(position).getDistricts().get(i).getDistrictname());
                }

                spnAdp_dis.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////distict Spinner////////////
        spnAdp_dis = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                arr_dis);
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn_dis.setAdapter(spnAdp_dis);
        spn_dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district=arr_dis.get(position);

                // getSubCat();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    /*    /////////////distict Spinner////////////
        ArrayAdapter<String> disadp = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                getResources().getStringArray(R.array.district));
        //myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dis.setAdapter(disadp);
        spn_dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district=getResources().getStringArray(R.array.district)[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////State Spinner////////////
        ArrayAdapter<String> steadp = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                getResources().getStringArray(R.array.state));
        //myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_state.setAdapter(steadp);
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state=getResources().getStringArray(R.array.state)[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        tncc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Light_Dialog);
                } else {
                    builder = new AlertDialog.Builder(ctx);
                }
                builder.setTitle("Terms & Conditions")
                        .setMessage("*I confirm that i will provide the offers, discounts to the App users in case i refuse to give such schemes then Discount Mania Team will take legal action on me.")
                        .setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.dismiss();
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ShopName = inputShopName.getText().toString().trim();
                String OwnerName = inputOwnerName.getText().toString().trim();
                String mobile = inputMobile.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String cnpassword = inputConPassword.getText().toString().trim();
                String otp = inputOTP.getText().toString().trim();
                String disMN=disMin.getText().toString().trim();

                if (!ShopName.isEmpty() && !OwnerName.isEmpty() && !otp.isEmpty() && !mobile.isEmpty() && !password.isEmpty() && !cnpassword.isEmpty() &&!disMN.isEmpty()) {
                    /*if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        inputEmail.setError("Enter a valid email");
                        inputEmail.requestFocus();
                    }*/


                    if (mobile.length() != 10) {
                        inputMobile.setError("Please enter valid mobile no");
                        inputMobile.requestFocus();
                    } else {
                        if (password.equals(cnpassword)) {
                            if (password.length() > 5) {
                                if (otp.equals(verotp) && mobile.equals(oldmob))
                                // if(mobile.length()==10)
                                {
                                    // smsTnC();
                                    if (timm) {
                                        buttonTimer.cancel();
                                    }
                                    if (tnc.isChecked()) { uploadForm(); }else { Toast.makeText(getApplicationContext(),"Accept terms & conditions",Toast.LENGTH_LONG).show(); }

                                } else if (!otp.equals(verotp) && mobile.equals(oldmob)) {
                                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Mobile No & OTP not matched ", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                inputPassword.setError("Password must be at least 6 character long");inputPassword.requestFocus();
                            }
                        } else {
                            inputConPassword.setError("Password not match");inputConPassword.requestFocus();
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(ShopName)) {
                        inputShopName.setError("Please enter Shop name");inputShopName.requestFocus();
                    } else if (TextUtils.isEmpty(OwnerName)) {
                        inputOwnerName.setError("Please enter Owner name");inputOwnerName.requestFocus();
                    }
                    /*else if (TextUtils.isEmpty(email)) {
                        inputEmail.setError("Please enter your email");
                        inputEmail.requestFocus();
                    }*/
                    else if (TextUtils.isEmpty(mobile)) {
                        inputMobile.setError("Please enter your mobile no");inputMobile.requestFocus();
                    } else if (TextUtils.isEmpty(password)) {
                        inputPassword.setError("Enter password");inputPassword.requestFocus();
                    } else if (TextUtils.isEmpty(cnpassword)) {
                        inputConPassword.setError("Enter confirm password");inputConPassword.requestFocus();
                    } else if (TextUtils.isEmpty(otp)) {
                        inputOTP.setError("Please enter OTP");inputOTP.requestFocus();
                    }
                    else if (TextUtils.isEmpty(disMN)) {
                        disMin.setError("Please enter Min Discount");disMin.requestFocus();
                    }
                    ///////////////////discount Max optional////////////
                    /*else if (TextUtils.isEmpty(disMX)) {
                        disMax.setError("Please enter Max Discount");
                        disMax.requestFocus();
                    }*/
                }
            }

             /*   if (!name.isEmpty() && !email.isEmpty()&& !mobile.isEmpty() && !password.isEmpty()) {
                    registerUser(name, email, mobile, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }*/
        });


        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_otp.setEnabled(false);
                btn_otp.setTextColor(getResources().getColor(R.color.darkGrey));
                timm=true;

                buttonTimer = new Timer();
                try {
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    btn_otp.setEnabled(true);
                                    btn_otp.setText("Resend OTP");
                                    timm=false;
                                    btn_otp.setTextColor(getResources().getColor(R.color.colorPrimary));
                                }
                            });
                        }
                    }, 20000);
                }catch (Exception e){}

                if (!inputMobile.getText().toString().trim().isEmpty()) {
                    oldmob=inputMobile.getText().toString();

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                //Your code goes here

//Your authentication key
                                String authkey = "238200AVXPs2p1HK5ba0bfa2";
//Multiple mobiles numbers separated by comma

                                String mobiles = "91" + inputMobile.getText().toString().trim();

//Sender ID,While using route4 sender id should be 6 characters long.
                                String senderId = "DMania";
                                Random random = new Random();
                                verotp = String.format("%04d", random.nextInt(10000));
//Your message to send, Add URL encoding here.
                                String message = "Welcome Merchant! Your otp is : ";
                                message = message + verotp;
//define route
                                String route = "4";

                                URLConnection myURLConnection = null;
                                URL myURL = null;
                                BufferedReader reader = null;

//encoding message
                                String encoded_message = URLEncoder.encode(message);

//Send SMS API
                                String mainUrl = "http://msg.yantraainfotech.in/api/sendhttp.php?";

//Prepare parameter string
                                StringBuilder sbPostData = new StringBuilder(mainUrl);
                                sbPostData.append("authkey=" + authkey);
                                sbPostData.append("&mobiles=" + mobiles);
                                sbPostData.append("&message=" + encoded_message);
                                sbPostData.append("&route=" + route);
                                sbPostData.append("&sender=" + senderId);

//final string
                                mainUrl = sbPostData.toString();
                                try {
                                    //prepare connection
                                    myURL = new URL(mainUrl);
                                    myURLConnection = myURL.openConnection();
                                    myURLConnection.connect();
                                    reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

                                    //reading response
                                    String response;
                                    while ((response = reader.readLine()) != null)
                                        //print response
                                        Log.d("RESPONSE", "" + response);

                                    //finally close connection
                                    reader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
                else {
                    inputMobile.setError("Enter Mobile No");
                    inputMobile.requestFocus();
                }
            }



        });

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if GPS enabled

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    try {
                        startActivityForResult(builder.build(SubAdminAddMerchnat.this), PLACE_PICKER_REQUEST);
                    }catch (Exception e){}

            }
        });


        getdata();

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        else if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK  && data != null && data.getData() != null) {

            // Get selected gallery image
            Uri selectedPicture = data.getData();
            // Get and resize profile image
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
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

           /* lastBitmap=loadedBitmap;
            icon.setImageBitmap(lastBitmap);*/
            if(btnID==1)
            {
                lastBitmap1 = loadedBitmap;
                icon.setImageBitmap(lastBitmap1);
            }
            else if(btnID==2)
            {
                lastBitmap2 = loadedBitmap;
                agreement.setImageBitmap(lastBitmap2);
            }

            // pic.setImageBitmap(lastBitmap);
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private File onConvertImage(Bitmap bmp,int s)
    {
        File f = new File(getCacheDir(), System.currentTimeMillis() + ".jpg");
//Convert bitmap to byte array
        /*bmp = lastBitmap;*/
        if(s==0)
        {
            bmp = lastBitmap1;
        }else {
            bmp = lastBitmap2;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,7  /*ignored for PNG*/, bos);
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


    private void uploadForm() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //  OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

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
                .create(APIsubAdmin.class);
        if(progressDialog.isShowing())
        {
            progressDialog.setMessage("Registering...");}
        else {
            progressDialog.setMessage("Registering...");
            progressDialog.show();
        }


        String ShopName = inputShopName.getText().toString().trim();
        String OwnerName = inputOwnerName.getText().toString().trim();
        String mobile = inputMobile.getText().toString().trim();
        String password= inputPassword.getText().toString().trim();
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        //MultipartBody requestBody = builder.build();

       /* final ModelMerchantAdd mer = new ModelMerchantAdd(OwnerName,ShopName,mobile,password,inputStreetAdd.getText().toString(),inputArea.getText().toString(),inputPincode.getText().toString(),district,state, inputEmail.getText().toString(),inputWhatsNO.getText().toString(),Category,SubCatageory,inputPAN.getText().toString(),inputTIN.getText().toString(), inputCIN.getText().toString(),inputGST.getText().toString(),inputBusinessDes.getText().toString(),inputFBlink.getText().toString(),inputTwitterLink.getText().toString(),inputInstaLink.getText().toString(),inputYouTubeLink.getText().toString(),inputWorkDays.getText().toString(),inputWorkTime.getText().toString(),String.valueOf(lat),String.valueOf(lng),user.getNumber(),disMin.getText().toString(),disMax.getText().toString());
        Call<ModelMerchantAdd> call = uploadAPIService.createMerchant(mer);*/

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("name",OwnerName)
                .addFormDataPart("b_name",ShopName)
                .addFormDataPart("number",mobile)
                .addFormDataPart("password",password)
                .addFormDataPart("street",inputStreetAdd.getText().toString())
                .addFormDataPart("locality",inputArea.getText().toString())
                .addFormDataPart("pin",inputPincode.getText().toString())
                .addFormDataPart("district",district)
                .addFormDataPart("state",state)
                .addFormDataPart("email",inputEmail.getText().toString())
                .addFormDataPart("whattsapp",inputWhatsNO.getText().toString())
                .addFormDataPart("b_cat",Category)
                .addFormDataPart("b_subCat",SubCatageory)
                .addFormDataPart("pan",inputPAN.getText().toString())
                .addFormDataPart("tin",inputTIN.getText().toString())
                .addFormDataPart("cin",inputCIN.getText().toString())
                .addFormDataPart("gst",inputGST.getText().toString())
                .addFormDataPart("b_desc",inputBusinessDes.getText().toString())
                .addFormDataPart("facebook",inputFBlink.getText().toString())
                .addFormDataPart("twitter",inputTwitterLink.getText().toString())
                .addFormDataPart("insta",inputInstaLink.getText().toString())
                .addFormDataPart("youtube",inputYouTubeLink.getText().toString())
                .addFormDataPart("day",inputWorkDays.getText().toString())
                .addFormDataPart("time",inputWorkTime.getText().toString())
                .addFormDataPart("lati",String.valueOf(lat))
                .addFormDataPart("longi",String.valueOf(lng))
                .addFormDataPart("created_by",user.getNumber())
                .addFormDataPart("d_min",disMin.getText().toString())
                .addFormDataPart("d_max",disMax.getText().toString());

        // final ModelMerchantAdd mer = new ModelMerchantAdd(OwnerName,ShopName,mobile,password,inputStreetAdd.getText().toString(),inputArea.getText().toString(),inputPincode.getText().toString(),district,state, inputEmail.getText().toString(),inputWhatsNO.getText().toString(),Category,SubCatageory,inputPAN.getText().toString(),inputTIN.getText().toString(), inputCIN.getText().toString(),inputGST.getText().toString(),inputBusinessDes.getText().toString(),inputFBlink.getText().toString(),inputTwitterLink.getText().toString(),inputInstaLink.getText().toString(),inputYouTubeLink.getText().toString(),inputWorkDays.getText().toString(),inputWorkTime.getText().toString(),String.valueOf(lat),String.valueOf(lng),user.getNumber(),disMin.getText().toString(),disMax.getText().toString());
        if(lastBitmap1!=null) {
            File file = onConvertImage(lastBitmap1,0);
            builder.addFormDataPart("pic", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        if(lastBitmap2!=null)
        {
            File file1 = onConvertImage(lastBitmap2,1);
            builder.addFormDataPart("agreement", file1.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file1));

        }

        MultipartBody requestBody = builder.build();
        Call<ModelMerchantAdd> call=uploadAPIService.createMerchantFile(requestBody);
        call.enqueue(new Callback<ModelMerchantAdd>() {
            @Override
            public void onResponse(Call<ModelMerchantAdd> call, retrofit2.Response<ModelMerchantAdd> response) {
                progressDialog.dismiss();

                if (response.body()!= null) {
                    final ModelMerchantAdd loginResponse = response.body();

                    boolean responseCode = loginResponse.getResponseCode();

                    String resp=response.body().getResponseMessage();
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Light_Dialog);
                    } else {
                        builder = new AlertDialog.Builder(ctx);
                    }

                    if(!responseCode) {
                        smsTnC();
                        builder.setTitle("Response")
                                .setMessage(resp)
                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Upload Images", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(getApplicationContext(), MerchantImagesAddActivity.class);
                                        i.putExtra("id", loginResponse.getMid());
                                        startActivity(i);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }else {
                        builder.setTitle("Response")
                                .setMessage(resp)
                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ModelMerchantAdd> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


    private void getSubCat() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder() .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIsubAdmin.class);

        if(progressDialog.isShowing())
        {
            progressDialog.setMessage("Please wait...");}
        else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        Call<ModelSubC> call = uploadAPIService.getSubCatt(Category);
        call.enqueue(new Callback<ModelSubC>() {
            @Override
            public void onResponse(Call<ModelSubC> call, retrofit2.Response<ModelSubC> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    ModelSubC loginResponse = response.body();

                    List<SubCat> lst=loginResponse.getSubCat();
                    subcat.clear();
                    subcat.add("Default");
                    for (int i=0;i<lst.size();i++)
                    {
                        subcat.add(lst.get(i).getSubCat());
                    }
                    subadp.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), response.message()+" Error Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelSubC> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
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

    public void smsTnC()
    {
        Thread thrd = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //Your code goes here

//Your authentication key
                    String authkey = "238200AVXPs2p1HK5ba0bfa2";
//Multiple mobiles numbers separated by comma

                    String mobiles = "91" + inputMobile.getText().toString().trim();

//Sender ID,While using route4 sender id should be 6 characters long.
                    String senderId = "DMania";
//Your message to send, Add URL encoding here.
                    String message = "Dear "+inputOwnerName.getText().toString()+", \nRegistration successful!.\n\n"+"You are confirming that you are agree with our TnC, as you will provide the offers, discounts to the App users in case you refuse to give such schemes then Discount Mania Team will take legal action on you.";
//define route
                    String route = "4";
                    URLConnection myURLConnection = null;
                    URL myURL = null;
                    BufferedReader reader = null;

//encoding message
                    String encoded_message = URLEncoder.encode(message);

//Send SMS API
                    String mainUrl = "http://msg.yantraainfotech.in/api/sendhttp.php?";

//Prepare parameter string
                    StringBuilder sbPostData = new StringBuilder(mainUrl);
                    sbPostData.append("authkey=" + authkey);
                    sbPostData.append("&mobiles=" + mobiles);
                    sbPostData.append("&message=" + encoded_message);
                    sbPostData.append("&route=" + route);
                    sbPostData.append("&sender=" + senderId);

//final string
                    mainUrl = sbPostData.toString();
                    try {
                        //prepare connection
                        myURL = new URL(mainUrl);
                        myURLConnection = myURL.openConnection();
                        myURLConnection.connect();
                        reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

                        //reading response
                        String response;
                        while ((response = reader.readLine()) != null)
                            //print response
                            Log.d("RESPONSE", "" + response);

                        //finally close connection
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thrd.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(resm==0) {
            resm=1;
        }
        else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Light_Dialog);
            } else {
                builder = new AlertDialog.Builder(ctx);
            }
            builder.setTitle("Reset")
                    .setMessage("Do you want to clear all fiels?")
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(SubAdminAddMerchnat.this, SubAdminAddMerchnat.class);  //your class
                            startActivity(i);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
    }

    private void getdata() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIsubAdmin.class);

        if(progressDialog.isShowing())
        {
            progressDialog.setMessage("Please wait...");}
        else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        //User user = SharedPrefManager.getInstance(AUserFilters.this).getUser();

        Call<List<ModelDistricts>> call = uploadAPIService.getStateDistricts();
        call.enqueue(new Callback<List<ModelDistricts>>() {
            @Override
            public void onResponse(Call<List<ModelDistricts>> call, retrofit2.Response<List<ModelDistricts>> response) {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                modelData = response.body();
                    for(int i=0;i<modelData.size();i++)
                    {
                        arr_state.add(modelData.get(i).getState());
                    }
                    spnAdp_state.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(SubAdminAddMerchnat.this, response.message() + " Error Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelDistricts>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SubAdminAddMerchnat.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
}

