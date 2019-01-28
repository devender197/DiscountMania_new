package com.virtualskillset.discountmania.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.virtualskillset.discountmania.LoginActivity;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.user.model.ModelUserResponse;

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

public class AUserRegister extends AppCompatActivity {

    private Button btnRegister,otp;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    //private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputConPassword,inputOtp;
    private EditText inputMobile;
    private String id;
    private ApiUserInter apiInterface;
    private ProgressDialog progressDialog;
    private String name,mobile,password,oldmob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        inputFullName = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        inputConPassword = (EditText) findViewById(R.id.conpass);
        inputMobile = (EditText) findViewById(R.id.mob);
        inputOtp = (EditText) findViewById(R.id.otp);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        otp=(Button)findViewById(R.id.btnOtp);
       // progressBar = (ProgressBar) findViewById(R.id.progressBar);


       /* if(i==0)
        {
            mob_first=inputMobile.getText().toString().trim();
        }
*/
       /* LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter("otp"));*/




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
               /* if (mob_first.equals("")) {
                    Toast.makeText(getApplicationContext(), "Mobile no cant be blank", Toast.LENGTH_LONG).show();
                } else {*/
                // progressBar.setVisibility(View.VISIBLE);
                // showDialog();
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
                // hideDialog();
                //progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    inputMobile.setError("Enter Mobile No");
                    inputMobile.requestFocus();
                }
            }
            // }


        });




        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                name = inputFullName.getText().toString().trim();
                //String email = inputEmail.getText().toString().trim();
                mobile = inputMobile.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                String cnpassword = inputConPassword.getText().toString().trim();
                String otp = inputOtp.getText().toString().trim();

                if (!name.isEmpty() && !otp.isEmpty() && !mobile.isEmpty() && !password.isEmpty() && !cnpassword.isEmpty()) {
                    /*if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        inputEmail.setError("Enter a valid email");
                        inputEmail.requestFocus();
                    }*/


                    if(mobile.length()!=10 || mobile.equals("0123456789") || mobile.equals("1234567890") || mobile.equals("9876543210")){
                        inputMobile.setError("Please enter valid mobile no");
                        inputMobile.requestFocus();}
                    else {
                        if(password.equals(cnpassword)) {
                            if(password.length()>5) {
                                if(otp.equals(id)&& mobile.equals(oldmob))
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
                    if (TextUtils.isEmpty(name)) {
                        inputFullName.setError("Please enter your name");
                        inputFullName.requestFocus();
                    } /*else if (TextUtils.isEmpty(email)) {
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

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void regUser() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUserInter.class);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<ModelUserResponse> call = apiInterface.getRegResponse(name,mobile,password);
        call.enqueue(new Callback<ModelUserResponse>() {
            @Override
            public void onResponse(Call<ModelUserResponse> call, retrofit2.Response<ModelUserResponse> response) {
                progressDialog.dismiss();
               ModelUserResponse loginResponse = response.body();
               if(loginResponse!=null) {
                   String resp = response.body().getResponseMessage();

                   if (response.body() != null) {

                       //   List<SubCat> lst=loginResponse.getSubCat();

                       Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                   }
               }
               else {
                   Toast.makeText(getApplicationContext(), response.message() + " Error Null", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<ModelUserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
   /* private void register(final String name, final String mobile,
                          final String password) {

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_User_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  progressBar.setVisibility(View.GONE);
                        hideDialog();



                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();

                                //getting the user from the response
                                *//* JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                       // userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("email"),
                                        userJson.getString("mobile")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);*//*

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        if(error.getMessage()==null)
                        {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "Timeout Please try again", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String ,String> getParams() {//throws AuthFailureError
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                // params.put("email", email);
                params.put("number", mobile);
                params.put("password", password);
                params.put("role", "0");
                params.put("status","1");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        //  Volley.newRequestQueue(this).add(stringRequest);

    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/

    /*private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");

                inputOtp.setText(message);
            }
        }
    };*/


    @Override
    public void onResume() {
        /*LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter("otp"));*/
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       /* LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);*/
    }
}
