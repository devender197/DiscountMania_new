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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_mer_view_product;
import com.virtualskillset.discountmania.merchant.retro.ModelMerProduct;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.user.userHelper.GridSpacingItemDecoration;
import com.virtualskillset.discountmania.user.userHelper.RevAdapter_category_home;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MerViewProductsFragment extends Fragment implements RevAdapter_mer_view_product.ItemClickListener {

    public MerViewProductsFragment() {
        // Required empty public constructor
    }
   // private GridView androidGridView;
    //private Adapter_mer_view adapterViewAndroid;
    private List<ModelMerProduct> MerDataa;
    private ApiInterfaceMer apiInterface;
   // private ProgressDialog progressDialog;
    private Context mContext;
    private TextView emptyView;
    private RecyclerView recyclerView;
    private RevAdapter_mer_view_product adapter;

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
        // Inflate the layout for this fragment
        View view = (RelativeLayout) inflater.inflate(R.layout.fragment_mer_view_products, container, false);
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
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .build()
                .create(ApiInterfaceMer.class);

/*
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();*/

        Call<List<ModelMerProduct>>  call = apiInterface.getMerProduct(MerDetailsActivity.m_id);
        call.enqueue(new Callback<List<ModelMerProduct>>() {
            @Override
            public void onResponse(Call<List<ModelMerProduct>> call, retrofit2.Response<List<ModelMerProduct>> response) {
             //   progressDialog.dismiss();
                if (response.body() != null) {
                    MerDataa = response.body();
                    try {
                        if (MerDataa.size() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 1, false));
                            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
                            adapter = new RevAdapter_mer_view_product(mContext, MerDataa);
                            adapter.setClickListener(MerViewProductsFragment.this);
                            recyclerView.setAdapter(adapter);

                        }
                    }catch (Exception e){
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<ModelMerProduct>> call, Throwable t) {
           //     progressDialog.dismiss();
                Toast.makeText(getContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent pr=new Intent(mContext,FullImageActivity.class);
        pr.putExtra("imagepath","http://discountmania.org/images/merchant/product/"+MerDataa.get(position).getImage());
        startActivity(pr);
    }
}
