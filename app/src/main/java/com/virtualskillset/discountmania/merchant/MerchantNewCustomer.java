package com.virtualskillset.discountmania.merchant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.retro.ModelMerCusVerify;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

public class MerchantNewCustomer extends AppCompatActivity {
    private Button verify,submit,qrScan/*,btn_otp*/;
    private EditText inputMobile,inputDiscount,inputPrice/*,inputOTP*/;
    private TextView cusName,final_price;
    private ImageView iv;
    private ApiInterfaceMer apiIfc;
    private ProgressDialog progressDialog;
    ModelMerCusVerify model;
    //private String verotp,oldmob;
    private Timer buttonTimer;
    //private boolean timm=false;
    int final_prcc=0;
    boolean veri=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_new_customer);
        verify=(Button)findViewById(R.id.veri);
        submit=(Button)findViewById(R.id.sub);
        qrScan=(Button)findViewById(R.id.button8);
        inputMobile=(EditText) findViewById(R.id.mob);
        inputDiscount=(EditText) findViewById(R.id.dis);
        inputPrice=(EditText) findViewById(R.id.amount);
        cusName=(TextView) findViewById(R.id.cn);
        final_price=(TextView) findViewById(R.id.final_prc);
        iv=(ImageView) findViewById(R.id.cusiv);
        /*inputOTP=(EditText)findViewById(R.id.otp);
        btn_otp=(Button)findViewById(R.id.btnOtp);*/
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputMobile.getText().toString().trim().isEmpty())
                {
                    inputMobile.setError("Enter Customer Mobile");
                    inputMobile.requestFocus();
                }
                else
                getverify();
            }
        });
        inputDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun();
            }
        });

        qrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MerchantNewCustomer.this,BarCodeScanActivity.class),15);
            }
        });

        final_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cusName.getText().toString().equals(""))
                {
                   // Toast.makeText(getApplicationContext(),"Enter Valid Customer Mobile No",Toast.LENGTH_LONG).show();
                    inputMobile.setError("Enter Customer Mobile");
                    inputMobile.requestFocus();
                }
                else {
                    if (model.getId() != null) {
                        submitTrans();
                       /* String otp=inputOTP.getText().toString().trim();
                        String mobile = inputMobile.getText().toString().trim();
                        if(!inputDiscount.getText().toString().trim().isEmpty() && !inputPrice.getText().toString().trim().isEmpty() &&!otp.isEmpty())
                        {
                            if(otp.equals(verotp) && mobile.equals(oldmob)) {
                                if(timm)
                                {
                                    buttonTimer.cancel();
                                }
                                submitTrans();
                            }
                            else if (!otp.equals(verotp) && mobile.equals(oldmob)) {
                                Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Customer Mobile No & OTP not matched ", Toast.LENGTH_LONG).show();
                            }*/
                        }
                        else if(inputPrice.getText().toString().trim().isEmpty())
                        {
                            inputPrice.setError("Enter Price");
                            inputPrice.requestFocus();
                        }
                        else if(inputDiscount.getText().toString().trim().isEmpty())
                        {
                            inputDiscount.setError("Enter Discount");
                            inputDiscount.requestFocus();
                        }
                       /* else if(inputOTP.getText().toString().trim().isEmpty())
                        {
                            inputOTP.setError("Enter OTP");
                            inputOTP.requestFocus();
                        }}*/

                    }

            }
        });

       /* btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun();
                if (veri) {
                    btn_otp.setEnabled(false);
                    btn_otp.setTextColor(getResources().getColor(R.color.darkGrey));
                    timm = true;

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
                                        timm = false;
                                        btn_otp.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    }
                                });
                            }
                        }, 20000);
                    } catch (Exception e) {
                    }

                    if (!inputMobile.getText().toString().trim().isEmpty()) {
                        oldmob = inputMobile.getText().toString();

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
                                    String message = "Dear Customer! \nThank you for Shopping with us\nYour otp is -";
                                    message = message + verotp + " for confirming order. This OTP code is valid for 30 minutes. Discount Mania";
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
                    } else {
                        inputMobile.setError("Enter Mobile No");
                        inputMobile.requestFocus();
                    }
                }

            }

        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==15)
        {
            if(resultCode == Activity.RESULT_OK){
                try {
                    String result=data.getStringExtra("result");
                    inputMobile.setText(result);
                }catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Unable to detect QrCode",Toast.LENGTH_LONG).show();
                }

            }
        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }

        }
    }

    private void getverify() {
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


        progressDialog = new ProgressDialog(MerchantNewCustomer.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Verifying User...");
        progressDialog.show();

        User user = SharedPrefManager.getInstance(MerchantNewCustomer.this).getUser();
        Call<ModelMerCusVerify> call = apiIfc.veriCus(inputMobile.getText().toString());
        call.enqueue(new Callback<ModelMerCusVerify>() {
            @Override
            public void onResponse(Call<ModelMerCusVerify> call, retrofit2.Response<ModelMerCusVerify> response) {
                progressDialog.dismiss();
                model=response.body();

                if(model.getError())
                {
                    veri=false;
                    Toast.makeText(getApplicationContext(),"Customer not exist",Toast.LENGTH_LONG).show();
                }
                else {
                    veri=true;
                    if (model.getPaid() == 1 && model.getStatus() == 1) {
                        cusName.setText(model.getName());
                      //  int loader = R.drawable.admin;
                        String image_url = "http://discountmania.org/images/client/" + model.getImage();

                        // ImageLoader class instance
                       /* ImageLoader imgLoader = new ImageLoader(getApplicationContext());
                        imgLoader.DisplayImage(image_url, loader, iv);
*/
                        RequestOptions options = new RequestOptions()
                                .placeholder(R.drawable.admin)
                                .error(R.drawable.admin);
                        //.centerCrop()
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.priority(Priority.HIGH);
                        Glide.with(getApplicationContext())
                                .load(image_url)
                                .apply(options)
                                //   .placeholder(R.drawable.noim)
                                // .apply(RequestOptions.circleCropTransform())
                                .into(iv);
                    } else if (model.getStatus() == 0) {
                        Toast.makeText(getApplicationContext(), "Customer is Blocked",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "This is not Paid Customer",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelMerCusVerify> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MerchantNewCustomer.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
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

    private void submitTrans() {
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


        progressDialog = new ProgressDialog(MerchantNewCustomer.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

        User user = SharedPrefManager.getInstance(MerchantNewCustomer.this).getUser();
        Call<ModelResponse> call = apiIfc.submitTranscation(inputDiscount.getText().toString(),final_prcc+"",model.getId()+"",user.getId()+"",Integer.parseInt(inputPrice.getText().toString()));
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {
                    ModelResponse mdl = response.body();
                    Toast.makeText(MerchantNewCustomer.this, mdl.getResponseMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MerchantNewCustomer.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    public void fun()
    {
        int fp=0,dis=0;
        if(!inputPrice.getText().toString().trim().isEmpty() && !inputDiscount.getText().toString().trim().isEmpty()) {
            fp = Integer.parseInt(inputPrice.getText().toString());
            dis = Integer.parseInt(inputDiscount.getText().toString());
            final_prcc = fp - ((fp * dis) / 100);
            final_price.setText("Final Price ₹" + final_prcc);
        }
    }


}
