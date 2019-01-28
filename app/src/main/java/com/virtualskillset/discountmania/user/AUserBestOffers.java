package com.virtualskillset.discountmania.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.GPSTracker;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.merchant.MerDetailsActivity;
import com.virtualskillset.discountmania.user.model.ModelUserBestOffer;
import com.virtualskillset.discountmania.user.userHelper.GridSpacingItemDecoration;
import com.virtualskillset.discountmania.user.userHelper.RevAdapter_Usr_BestOffer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AUserBestOffers extends AppCompatActivity implements RevAdapter_Usr_BestOffer.ItemClickListener {

    private RecyclerView recyclerView;
    private RevAdapter_Usr_BestOffer adapter;
    private List<ModelUserBestOffer> MerDataa;
    ApiUserInter apiInterface;
    private ProgressDialog progressDialog;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_best_offer);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}
        emptyView=(TextView)findViewById(R.id.empty_view);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        getMerDetail();
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
    private void getMerDetail() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUserInter.class);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<List<ModelUserBestOffer>> call = apiInterface.getBestOffer();
        call.enqueue(new Callback<List<ModelUserBestOffer>>() {
            @Override
            public void onResponse(Call<List<ModelUserBestOffer>> call, retrofit2.Response<List<ModelUserBestOffer>> response) {
                progressDialog.dismiss();
                MerDataa=response.body();
                if (MerDataa.contains(null) || MerDataa.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,15, false));
                    recyclerView.setLayoutManager(new GridLayoutManager(AUserBestOffers.this, 1));
                    adapter = new RevAdapter_Usr_BestOffer(AUserBestOffers.this,MerDataa);
                    adapter.setClickListener(AUserBestOffers.this);
                    recyclerView.setAdapter(adapter);

                }


            }

            @Override
            public void onFailure(Call<List<ModelUserBestOffer>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AUserBestOffers.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        /*Intent pr=new Intent(getApplicationContext(),FullImageActivity.class);
        pr.putExtra("imagepath","http://discountmania.org/images/merchant/offer/"+MerDataa.get(position).getImage());
        startActivity(pr);*/
        startActivity(new Intent(AUserBestOffers.this, MerDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("mid", MerDataa.get(position).getMid()));
    }
}
