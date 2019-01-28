package com.virtualskillset.discountmania.admin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.admin.helper.adapter.RevAdapter_request_merchant;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.helper.model.ModelResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminReqMerchantsActivity extends AppCompatActivity {
    APIService apiInterface;
    private ProgressDialog progressDialog;
    List<ModelMerchantList> reqList =new ArrayList<>();
    private RecyclerView recyclerView;
    private RevAdapter_request_merchant mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_req_merchants);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}
            getMerchants(0);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

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

    private void getMerchants(final int type) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);


        progressDialog = new ProgressDialog(AdminReqMerchantsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<List<ModelMerchantList>> call = apiInterface.getMerchantList();
        call.enqueue(new Callback<List<ModelMerchantList>>() {
            @Override
            public void onResponse(Call<List<ModelMerchantList>> call, retrofit2.Response<List<ModelMerchantList>> response) {
                progressDialog.dismiss();
                if (response != null) {
                    List<ModelMerchantList> rs = response.body();
                    for (int i = rs.size() - 1; i >= 0; i--) {
                        if (rs.get(i).getApproved() == 0) {
                            reqList.add(rs.get(i));

                        }
                    }


                    mAdapter = new RevAdapter_request_merchant(AdminReqMerchantsActivity.this, reqList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    if (type == 0) {
                        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                    }
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ModelMerchantList>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public void aprvReq(int mid) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);


        progressDialog = new ProgressDialog(AdminReqMerchantsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<ModelResponse> call = apiInterface.merAprove(1,mid);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {
                    ModelResponse rs = response.body();
                    Toast.makeText(getApplicationContext(), rs.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    if (!rs.getError()) {
                        reqList.clear();
                        getMerchants(1);
                    }
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
