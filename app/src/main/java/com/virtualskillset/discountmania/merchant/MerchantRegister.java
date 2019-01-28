package com.virtualskillset.discountmania.merchant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.virtualskillset.discountmania.LoginActivity;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.merchant.retro.ModelMerchant;
import com.virtualskillset.discountmania.merchant.retro.ModelSubC;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.merchant.retro.SubCat;

import java.io.BufferedReader;
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


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MerchantRegister extends AppCompatActivity {
    private Spinner spn1,spn2;
    private ProgressDialog pDialog;
    private String Category,SubCatageory,verotp,oldmob;
    private Button btn_register,btn_otp;
    private EditText inputShopName,inputOwnerName,inputMobile,inputEmail,inputPassword,inputConPassword,inputOTP;
    private ArrayList<String> subcat=new ArrayList<>();
    ArrayAdapter<String> subadp;
    private ApiInterfaceMer uploadAPIService;
    private ApiInterfaceMer apiInterface;
    private ProgressDialog progressDialog;
    private Button btnLinkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_register);
        spn1=(Spinner)findViewById(R.id.spinner1);
        spn2=(Spinner)findViewById(R.id.spinner2);
        btn_register=(Button)findViewById(R.id.btnRegister);
        btn_otp=(Button)findViewById(R.id.btnOtp);
        inputShopName=(EditText)findViewById(R.id.sh_name);
        inputOwnerName=(EditText)findViewById(R.id.own_name);
        inputMobile=(EditText)findViewById(R.id.mob);
        inputEmail=(EditText)findViewById(R.id.mail);
        inputPassword=(EditText)findViewById(R.id.password);
        inputConPassword=(EditText)findViewById(R.id.conpass);
        inputOTP=(EditText)findViewById(R.id.otp);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        subcat.add("Default");
        init();

        /////////////////////category spinner//////////////
        ArrayAdapter<String> main = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                getResources().getStringArray(R.array.category));
      //  myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn1.setAdapter(main);
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
        //myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ShopName = inputShopName.getText().toString().trim();
                String OwnerName = inputOwnerName.getText().toString().trim();
                String mobile = inputMobile.getText().toString().trim();
                String password= inputPassword.getText().toString().trim();
                String cnpassword = inputConPassword.getText().toString().trim();
                String otp= inputOTP.getText().toString().trim();

                if (!ShopName.isEmpty() &&!OwnerName.isEmpty() &&!otp.isEmpty() && !mobile.isEmpty() && !password.isEmpty() && !cnpassword.isEmpty()) {
                    /*if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        inputEmail.setError("Enter a valid email");
                        inputEmail.requestFocus();
                    }*/


                    if(mobile.length()!=10 ){
                        inputMobile.setError("Please enter valid mobile no");
                        inputMobile.requestFocus();}
                    else {
                        if(password.equals(cnpassword)) {
                            if(password.length()>5) {
                                if(otp.equals(verotp)&& mobile.equals(oldmob))
                                {
                                    uploadForm();
                                    //merreg(Category,SubCatageory,ShopName,OwnerName,mobile,inputEmail.getText().toString(),password,inputBusinessDes.getText().toString(),inputWorkDays.getText().toString(),inputWorkTime.getText().toString(),inputStreetAdd.getText().toString(),inputArea.getText().toString(),district,state,inputPincode.getText().toString(),inputPAN.getText().toString(),inputGST.getText().toString(),inputTIN.getText().toString(),inputCIN.getText().toString(),inputWhatsNO.getText().toString(),inputFBlink.getText().toString(),inputInstaLink.getText().toString(),inputTwitterLink.getText().toString(),inputYouTubeLink.getText().toString(),inputPaymentMethod.getText().toString(),lat,lng,user.getNumber());
                                }
                                else if(!otp.equals(verotp)) {
                                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Mobile No not matched with OTP",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                inputPassword.setError("Password must be at least 6 character long");
                                inputPassword.requestFocus();
                            }
                        }
                        else {
                            inputConPassword.setError("Password not match");
                            inputConPassword.requestFocus();
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(ShopName)) {
                        inputShopName.setError("Please enter Shop name");
                        inputShopName.requestFocus();
                    }else  if (TextUtils.isEmpty(OwnerName)) {
                        inputOwnerName.setError("Please enter Owner name");
                        inputOwnerName.requestFocus();
                    }
                    /*else if (TextUtils.isEmpty(email)) {
                        inputEmail.setError("Please enter your email");
                        inputEmail.requestFocus();
                    }*/ else if (TextUtils.isEmpty(mobile)) {
                        inputMobile.setError("Please enter your mobile no");
                        inputMobile.requestFocus();
                    } else if (TextUtils.isEmpty(password)) {
                        inputPassword.setError("Enter password");
                        inputPassword.requestFocus();
                    } else if (TextUtils.isEmpty(cnpassword)) {
                        inputConPassword.setError("Enter confirm password");
                        inputConPassword.requestFocus();
                    } else if (TextUtils.isEmpty(otp)) {
                        inputOTP.setError("Please enter OTP");
                        inputOTP.requestFocus();
                    }
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

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btn_otp.setEnabled(true);
                                btn_otp.setText("Resend OTP");
                                btn_otp.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }
                        });
                    }
                }, 20000);
               /* if (mob_first.equals("")) {
                    Toast.makeText(getApplicationContext(), "Mobile no cant be blank", Toast.LENGTH_LONG).show();
                } else {*/
                // progressBar.setVisibility(View.VISIBLE);
                // showDialog();
                //inputMobile.setFocusable(false);
                if (!inputMobile.getText().toString().trim().isEmpty()) {
                        oldmob=inputMobile.getText().toString();

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                String authkey = "238200AVXPs2p1HK5ba0bfa2"; //Your authentication key

                                String mobiles = "91" + inputMobile.getText().toString().trim(); //Multiple mobiles numbers separated by comma

                                String senderId = "DMania"; //Sender ID,While using route4 sender id should be 6 characters long.
                                Random random = new Random();
                                verotp = String.format("%04d", random.nextInt(10000));

                                String message = "Welcome Merchant! Your otp is : ";//Your message to send, Add URL encoding here.
                                message = message + verotp;

                                String route = "4";//define route

                                URLConnection myURLConnection = null;
                                URL myURL = null;
                                BufferedReader reader = null;
                                String encoded_message = URLEncoder.encode(message);//encoding message

                                String mainUrl = "http://msg.yantraainfotech.in/api/sendhttp.php?";//Send SMS API
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
    }

    private void init(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
         OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                 .writeTimeout(30, TimeUnit.SECONDS)
                 .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();

       /* OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build();*/
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
    }

    private void uploadForm() {
        progressDialog.show();


        String ShopName = inputShopName.getText().toString().trim();
        String OwnerName = inputOwnerName.getText().toString().trim();
        String mobile = inputMobile.getText().toString().trim();
        String password= inputPassword.getText().toString().trim();
        //MultipartBody requestBody = builder.build();

        final ModelMerchant mer = new ModelMerchant(OwnerName,ShopName,mobile,password,inputEmail.getText().toString(),Category,SubCatageory,mobile);
        Call<ModelMerchant> call = uploadAPIService.createMerchant(mer);
        call.enqueue(new Callback<ModelMerchant>() {
            @Override
            public void onResponse(Call<ModelMerchant> call, retrofit2.Response<ModelMerchant> response) {
                progressDialog.dismiss();

                if (response.body()!= null) {
                    ModelMerchant loginResponse = response.body();

                    String responseCode = loginResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(getApplicationContext(), "Invalid Details \n Please try again", Toast.LENGTH_SHORT).show();
                    } else {
                        String resp=response.body().getResponseMessage();
                        Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelMerchant> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


    private void getSubCat() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);

       /* OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build();*/
        /*uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Preparing Category...");
        /*uploadAPIsubCat = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                // -----add here-------
                .addConverterFactory(new NullOnEmptyConverterFactory())
                //---------------------
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);*/
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
       // ModelSubC mod= new ModelSubC(Category);
        Call<ModelSubC> call = apiInterface.getSubCatt(Category);
        call.enqueue(new Callback<ModelSubC>() {
            @Override
            public void onResponse(Call<ModelSubC> call, retrofit2.Response<ModelSubC> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    ModelSubC loginResponse = response.body();

                    List<SubCat> lst=loginResponse.getSubCat();
                   /* String responseCode = loginResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(getApplicationContext(), "Invalid Details \n Please try again", Toast.LENGTH_SHORT).show();
                    } else {*/
                        //String resp=response.body().getResponseMessage();

                        subcat.clear();
                        subcat.add("Default");
                     //   List<String> rs = response.body().getSubCatResults();
                    for (int i=0;i<lst.size();i++)
                    {
                        subcat.add(lst.get(i).getSubCat());
                    }
                       // subcat.addAll(loginResponse.get(0).getSubcatt().toString());
                        subadp.notifyDataSetChanged();

                        // Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                    //}
                }
                else {
                    Toast.makeText(getApplicationContext(), response.message()+" ", Toast.LENGTH_SHORT).show();
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
}
