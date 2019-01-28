package com.virtualskillset.discountmania;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.virtualskillset.discountmania.admin.AdminMainActivity;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.merchant.MerchantMain;
import com.virtualskillset.discountmania.merchant.MerchantRegister;
import com.virtualskillset.discountmania.subadmin.SubAdminMain;
import com.virtualskillset.discountmania.user.AUserMain;
import com.virtualskillset.discountmania.user.AUserRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnLinkToRegister,btnforgot,btnSkip;
    private EditText inputMobile;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private ApiInterface uploadAPIService;
    private ProgressDialog progressDialog;
    private String mobile, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(this).getUser();
            Intent i;
            if(user.getRole().equals("5"))
            {
                    i = new Intent(this, AdminMainActivity.class);
            }
            else  if(user.getRole().equals("0"))
            {
                i = new Intent(this, AUserMain.class);
            }
            else  if(user.getRole().equals("1"))
            {
                i = new Intent(this, MerchantMain.class);
            }
            else {
                    i = new Intent(this, SubAdminMain.class);
            }

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            getApplicationContext().startActivity(i);
            finish();
        }

        inputMobile = (EditText) findViewById(R.id.mob);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.sign);
        btnforgot=(Button) findViewById(R.id.forgot);
        btnSkip=(Button) findViewById(R.id.skipSign);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }
        init();

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = 0;
                String num = "";
                String role = "0";
                String status = "1";
                String name="Guest";
                User user = new User(
                        //  userJson.getInt("id"),
                        id, num, role,status,name);
                //storing the user in shared preferences
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                Intent i;
                if (role.equals("5")) {
                    i = new Intent(getApplicationContext(), AdminMainActivity.class);
                } else if (role.equals("0")) {
                    i = new Intent(getApplicationContext(), AUserMain.class);
                } else if (role.equals("1")) {
                    i = new Intent(getApplicationContext(), MerchantMain.class);
                } else {
                    i = new Intent(getApplicationContext(), SubAdminMain.class);
                }
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Staring Login Activity
                getApplicationContext().startActivity(i);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                mobile = inputMobile.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                if (!mobile.isEmpty() && !password.isEmpty()) {
                    if (mobile.length()!=10 || mobile.equals("0123456789") || mobile.equals("1234567890") || mobile.equals("9876543200")) {
                        inputMobile.setError("Enter a valid mobile no");
                        inputMobile.requestFocus();
                    }
                    else {
                       // checkLogin(mobile, password);
                        uploadForm();
                    }
                }
                else {
                    if (TextUtils.isEmpty(mobile)) {
                        inputMobile.setError("Please enter your mobile");
                        inputMobile.requestFocus();
                    }
                    else if(TextUtils.isEmpty(password))  {
                        inputPassword.setError("Please enter your password");
                        inputPassword.requestFocus();
                    }
                }

//                // Check for empty data in the form
//                if (!email.isEmpty() && !password.isEmpty()) {
//                    // login user
//                    checkLogin(email, password);
//                } else {
//                    // Prompt user to enter credentials
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter the credentials!", Toast.LENGTH_LONG)
//                            .show();
//                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AlertDialog.Builder aler;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    aler = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                }else {
                    aler  = new AlertDialog.Builder(view.getContext());
                }
               // new AlertDialog.Builder(view.getContext())
                       aler.setIcon(R.mipmap.ic_launcher)
                        .setTitle("Register As")
                      //  .setMessage("Are you sure you want to close this application?")
                        .setPositiveButton("User", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getApplicationContext(),
                                        AUserRegister.class);
                                startActivity(i);
                                finish();
                            }

                        })
                        .setNegativeButton("Merchant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getApplicationContext(),
                                        MerchantRegister.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .show();

            }
        });
        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        ResetPassActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void init()
    {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();

       /* OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build();
*/
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in ...");
    }

    private void uploadForm() {
        progressDialog.show();


        // builder.addFormDataPart("pic", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));


        //MultipartBody requestBody = builder.build();
        final ModelLogin login = new ModelLogin(mobile,password);
        Call<ModelLogin> call = uploadAPIService.getLoginResponse(login);
        call.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, retrofit2.Response<ModelLogin> response) {
                progressDialog.dismiss();
                ModelLogin loginResponse = response.body();
                if (loginResponse != null) {
                        boolean err=response.body().isError();
                        String resp=response.body().getResponseMessage();
                        if(!err) {
                            int id = response.body().getId();
                            String num = response.body().getNumber();
                            String role = response.body().getRole();
                            String status = response.body().getStatus();
                            String name=response.body().getName();
                            User user = new User(
                                    //  userJson.getInt("id"),
                                    id, num, role,status,name);


                            /*obj.getInt("id"),
                            // userJson.getString("email"),
                            obj.getString("number"),
                            obj.getString("role")*/
                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                            Intent i;
                            if (role.equals("5")) {
                                i = new Intent(getApplicationContext(), AdminMainActivity.class);
                            } else if (role.equals("0")) {
                                i = new Intent(getApplicationContext(), AUserMain.class);
                            } else if (role.equals("1")) {
                                i = new Intent(getApplicationContext(), MerchantMain.class);
                            } else {
                                i = new Intent(getApplicationContext(), SubAdminMain.class);
                            }
                            // Closing all the Activities
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            // Staring Login Activity
                            getApplicationContext().startActivity(i);
                            finish();
                        }
                        else {

                            Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                        }

                }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
   /* private void checkLogin(final String mob, final String password) {

        pDialog.setMessage("Logging in ...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        hideDialog();

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                // JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        //  userJson.getInt("id"),
                                        obj.getInt("id"),
                                        // userJson.getString("email"),
                                        obj.getString("number"),
                                        obj.getString("role")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity

                               *//* if(obj.getString("role").equals("5"))
                                {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                                }
                                else {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                }*//*
                                    Intent i;
                                    if(obj.getString("role").equals("5"))
                                    {
                                        i = new Intent(getApplicationContext(), AdminMainActivity.class);
                                    }
                                    else  if(obj.getString("role").equals("0"))
                                    {
                                        i = new Intent(getApplicationContext(), UserMainActivity.class);
                                    }
                                    else  if(obj.getString("role").equals("1"))
                                    {
                                        i = new Intent(getApplicationContext(), MerchantMain.class);
                                    }
                                    else {
                                        i = new Intent(getApplicationContext(), SubAdminMain.class);
                                    }
                                    // Closing all the Activities
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    // Add new Flag to start new Activity
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    // Staring Login Activity
                                    getApplicationContext().startActivity(i);
                                    finish();


                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", mob);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/

   /* private void forgotpass(final String email, final View v) {

        pDialog.setMessage("Please Wait ...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_User_FORGOTPASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        hideDialog();
                        Snackbar.make(v, "Check password reset link in registered E-mail id", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    *//*    try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), "Password reset link sent in E-mail", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*//*
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.getMessage()==null)
                        {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "Timeout | You may received verification mail", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }*/


    private  boolean checkAndRequestPermissions() {
        int telephone = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        int storage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int storagewrite = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int call = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);

        int loc = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (telephone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (storagewrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (call != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}



