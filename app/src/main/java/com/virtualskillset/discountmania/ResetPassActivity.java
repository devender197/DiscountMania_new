package com.virtualskillset.discountmania;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.model.ModelResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPassActivity extends AppCompatActivity {
    private Button btnRegister,otp;
    private Button btnLinkToLogin;
    private EditText inputPassword;
    private EditText inputConPassword,inputOtp;
    private EditText inputMobile;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private String mobile,password,id,oldmob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputPassword = (EditText) findViewById(R.id.password);
        inputConPassword = (EditText) findViewById(R.id.conpass);
        inputMobile = (EditText) findViewById(R.id.mob);
        inputOtp = (EditText) findViewById(R.id.otp);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        otp=(Button)findViewById(R.id.btnOtp);

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp.setEnabled(false);
                otp.setTextColor(getResources().getColor(R.color.darkGrey));

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                otp.setEnabled(true);
                                otp.setText("Resend OTP");
                                otp.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }
                        });
                    }
                }, 20000);

               // inputMobile.setFocusable(false);
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

                            String mobiles = "91"+inputMobile.getText().toString().trim();

//Sender ID,While using route4 sender id should be 6 characters long.
                            String senderId = "DMania";
                            Random random = new Random();
                            id = String.format("%04d", random.nextInt(10000));
//Your message to send, Add URL encoding here.
                            String message = "Your otp is : ";
                            message = message + id;
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




        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mobile = inputMobile.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                String cnpassword = inputConPassword.getText().toString().trim();
                String otp = inputOtp.getText().toString().trim();

                if (!otp.isEmpty() && !mobile.isEmpty() && !password.isEmpty() && !cnpassword.isEmpty()) {
                    if(mobile.length()!=10 || mobile.equals("0123456789") || mobile.equals("1234567890") || mobile.equals("9876543210")){
                        inputMobile.setError("Please enter valid mobile no");
                        inputMobile.requestFocus();}
                    else {
                        if(password.equals(cnpassword)) {
                            if(password.length()>5) {
                                if(otp.equals(id) && mobile.equals(oldmob))
                                {
                                    regUser();
                                    // register(name, mobile, password);
                                }
                                else if(!otp.equals(id)) {
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
                    if (TextUtils.isEmpty(mobile)) {
                        inputMobile.setError("Please enter your mobile no");
                        inputMobile.requestFocus();
                    } else if (TextUtils.isEmpty(password)) {
                        inputPassword.setError("Enter password");
                        inputPassword.requestFocus();
                    } else if (TextUtils.isEmpty(cnpassword)) {
                        inputConPassword.setError("Enter confirm password");
                        inputConPassword.requestFocus();
                    } else if (TextUtils.isEmpty(otp)) {
                        inputOtp.setError("Please enter OTP");
                        inputOtp.requestFocus();
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

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void regUser() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<ModelResponse> call = apiInterface.getResetResp(mobile,password);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                progressDialog.dismiss();
                // UserResponse loginResponse = response.body();
                String resp=response.body().getResponseMessage();

                if (response.body() != null) {

                    //   List<SubCat> lst=loginResponse.getSubCat();

                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), response.message()+" Error Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
}
