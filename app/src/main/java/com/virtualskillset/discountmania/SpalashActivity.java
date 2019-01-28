package com.virtualskillset.discountmania;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.helper.model.ModelVersion;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpalashActivity extends AppCompatActivity {
    ApiInterface apiInterr;
    private String oldvr ,newVr;
    private int id=0,vers=0,comp=0,oldv_code=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            oldvr= pInfo.versionName;
            oldv_code=pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        getDash();

        Thread thrd= new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    Thread.sleep(1600);
                    runOnUiThread(new  Runnable() {
                        public void run() {
                            try {

                                if (oldv_code>=vers && oldv_code>=comp) {
                                    Intent i = new Intent(SpalashActivity.this, LoginActivity.class);
                                    startActivity(i);

                                    // close this activity
                                    finish();
                                }
                                else if(oldv_code<vers && oldv_code>comp) {
                                    android.app.AlertDialog.Builder builder;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new android.app.AlertDialog.Builder(SpalashActivity.this, android.R.style.Theme_Material_Light_Dialog);
                                    } else {
                                        builder = new android.app.AlertDialog.Builder(SpalashActivity.this);
                                    }
                                    builder.setIcon(R.mipmap.ic_launcher)
                                            .setTitle("Update is available")
                                            .setMessage("Update the App to the Latest Version v" + newVr + " for seamless experience.")
                                            .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.virtualskillset.discountmania")));
                                                }

                                            })
                                            .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // dialog.cancel();
                                                    Intent i = new Intent(SpalashActivity.this, LoginActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            })
                                            .show();
                                }
                                else
                                 {
                                    android.app.AlertDialog.Builder builder;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new android.app.AlertDialog.Builder(SpalashActivity.this, android.R.style.Theme_Material_Light_Dialog);
                                    } else {
                                        builder = new android.app.AlertDialog.Builder(SpalashActivity.this);
                                    }
                                    builder.setIcon(R.mipmap.ic_launcher)
                                            .setTitle("Update is available")
                                            .setMessage("Update the App to the Latest Version v" + newVr + " for seamless experience.")
                                            .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.virtualskillset.discountmania")));
                                                }

                                            })
                                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // dialog.cancel();
                                                    finish();
                                                }
                                            })
                                            .show();
                                }
                            }catch (Exception e) {
                                if (isNetworkConnected()) {
                                    Intent i = new Intent(SpalashActivity.this, LoginActivity.class);
                                    startActivity(i);

                                    // close this activity
                                    finish();
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(findViewById(android.R.id.content), "Check Your Connection or update the App", Snackbar.LENGTH_INDEFINITE)
                                            .setAction("Update Now", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.virtualskillset.discountmania")));
                                                }
                                            }).setActionTextColor(getResources().getColor(R.color.colorPrimary));

                                    snackbar.show();
                                }
                            }
                        }
                    })	;

                }
                catch(Exception e)
                {

                }

            }
        });

        thrd.start();
    }


    private void getDash() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiInterr = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);


        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        final User user = SharedPrefManager.getInstance(this).getUser();
        id=user.getId();
        if(id==-1)
        {
            id=0;
        }
        Call<ModelVersion> call = apiInterr.getVersion(id);
        call.enqueue(new Callback<ModelVersion>() {
            @Override
            public void onResponse(Call<ModelVersion> call, retrofit2.Response<ModelVersion> response) {
                // progressDialog.dismiss();
                if (response.body()!= null) {
                    ModelVersion mdl = response.body();
                    try {
                        newVr = mdl.getVersion();
                        vers=mdl.getVers();
                        comp=mdl.getComp();
                        //int idd=user.getId();
                        if (id != 0 && id != -1) {
                            if (mdl.getStatus() == 0) {
                                SharedPrefManager.getInstance(getApplicationContext()).logoutt();
                            }
                        }
                    } catch (Exception e) {
                        newVr = oldvr;
                    }

               /*     Toast.makeText(getApplicationContext(), "Update The App to"+mdl.getVersion(), Toast.LENGTH_SHORT).show();
                }*/
                }
            }

            @Override
            public void onFailure(Call<ModelVersion> call, Throwable t) {
                //progressDialog.dismiss();
               // Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
