package com.virtualskillset.discountmania.merchant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_mer_view_offer;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_mer_view_product;
import com.virtualskillset.discountmania.merchant.retro.ModelMerOffer;
import com.virtualskillset.discountmania.user.userHelper.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MerViewOfferFragment extends Fragment implements RevAdapter_mer_view_offer.ItemClickListener {

    private RecyclerView recyclerView;
    private RevAdapter_mer_view_offer adapter;
    private List<ModelMerOffer>  MerDataa;
    ApiInterfaceMer apiInterface;
   // private ProgressDialog progressDialog;
    private TextView emptyView;

    public MerViewOfferFragment() {
        // Required empty public constructor
    }

    private Context mContext;

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (RelativeLayout) inflater.inflate(R.layout.fragment_mer_view_offers, container, false);
        emptyView=(TextView)view.findViewById(R.id.empty_view);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        getMerDetail();
        return view;
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
                .create(ApiInterfaceMer.class);


       /* progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();*/

        Call<List<ModelMerOffer>> call = apiInterface.getMerOffer(MerDetailsActivity.m_id);
        call.enqueue(new Callback<List<ModelMerOffer>>() {
            @Override
            public void onResponse(Call<List<ModelMerOffer>> call, retrofit2.Response<List<ModelMerOffer>> response) {
             //   progressDialog.dismiss();
                MerDataa=response.body();
                if (MerDataa.contains(null) || MerDataa.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,1, false));
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
                    adapter = new RevAdapter_mer_view_offer(mContext,MerDataa);
                    adapter.setClickListener(MerViewOfferFragment.this);
                    recyclerView.setAdapter(adapter);

                }


            }

            @Override
            public void onFailure(Call<List<ModelMerOffer>> call, Throwable t) {
            //    progressDialog.dismiss();
                Toast.makeText(mContext, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent pr=new Intent(mContext,FullImageActivity.class);
        pr.putExtra("imagepath","http://discountmania.org/images/merchant/offer/"+MerDataa.get(position).getImage());
        startActivity(pr);
    }
}