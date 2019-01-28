package com.virtualskillset.discountmania.merchant;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_mer_dash;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_mer_review;
import com.virtualskillset.discountmania.merchant.retro.ModelMerDash;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.user.userHelper.GridSpacingItemDecoration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerDashFragment extends Fragment {
    ApiInterfaceMer apiInterface;
    private ProgressDialog progressDialog;
    private ModelMerDash model;
    private ProgressBar _progressBar1;
    private TextView tv1,tv2,tv3;
    private TextView emptyView;
    private Context mContext;
    private RecyclerView recyclerView;
    private RevAdapter_mer_dash adapter;
    private RatingBar rb;


    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public MerDashFragment() {
        // Required empty public constructor
    }
    public static MerDashFragment newInstance() {
        MerDashFragment fragment = new MerDashFragment();
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        /*state.putString("mInitialTitle", mInitialTitle);
        state.putString("mTitle", mTitle);*/
        super.onSaveInstanceState(state);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_mer_dashboard, container, false);
        try{
            getActivity().setTitle("Dashboard");}catch (NullPointerException e){};
        tv1=(TextView)v.findViewById(R.id.txtReq);
        tv2=(TextView)v.findViewById(R.id.c2val);
        tv3=(TextView)v.findViewById(R.id.ratv);
        rb=(RatingBar)v.findViewById(R.id.ratingBar);
        emptyView=(TextView)v.findViewById(R.id.empty_view);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);

        _progressBar1 = (ProgressBar)v.findViewById (R.id.r2progressBar);
        getDash();

        return v;

    }

    private void getDash() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        Call<ModelMerDash> call = apiInterface.getMerDash(user.getId()+"");
        call.enqueue(new Callback<ModelMerDash>() {
            @Override
            public void onResponse(Call<ModelMerDash> call, retrofit2.Response<ModelMerDash> response) {
                progressDialog.dismiss();
                model=response.body();
               try {
                    if (model.getReviews().contains(null) || model.getReviews().size()==0 /*|| model.getReviews().contains("")*/) {
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    } else {

                            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 2, false));
                            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
                            adapter = new RevAdapter_mer_dash(mContext, model.getReviews());
                            recyclerView.setAdapter(adapter);

                    }
                }catch (Exception e){ recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);}
                if(model.getServed()==null)
                {
                    _progressBar1.setProgress(0);tv2.setText("0");}else {_progressBar1.setProgress(model.getServed());tv2.setText(model.getServed()+"");}

                int total=0;
                double avg=0.0;
                for(int i=0;i<model.getReviews().size();i++)
                {
                    total=total+1;
                    avg=avg+model.getReviews().get(i).getRating();
                }

                avg=avg/total;
                rb.setRating((float) avg);
               tv3.setText(new DecimalFormat("##.#").format(avg));


            }

            @Override
            public void onFailure(Call<ModelMerDash> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

}
