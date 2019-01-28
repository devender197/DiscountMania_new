package com.virtualskillset.discountmania.admin;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.retro.ModelAdminDashb;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.admin.helper.adapter.Adapter_top_sub_adm;


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
public class AdminDashboardFragment extends Fragment {
    GridView androidGridView;
    APIService apiInterface;
    private ProgressDialog progressDialog;
    private ModelAdminDashb model;
    private ProgressBar _progressBar1,_progressBar2,_progressBar3,_progressBar4;
    private Adapter_top_sub_adm adapterViewAndroid;
    private ArrayList<String> gridViewString=new ArrayList<>();
    private TextView tv1,tv2,tv3,tv4;
    ArrayList<String> numm=new ArrayList<>();


    public static AdminDashboardFragment newInstance() {
        AdminDashboardFragment fragment = new AdminDashboardFragment();
        return fragment;
    }

    public AdminDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        try{
            getActivity().setTitle("Dashboard");}catch (NullPointerException e){};
        tv1=(TextView)v.findViewById(R.id.txtReq);
        tv2=(TextView)v.findViewById(R.id.texTtl);

        _progressBar1 = (ProgressBar)v.findViewById (R.id.r2progressBar);
       // _progressBar1.setProgress( 25 );
       // tv1=(TextView) v.findViewById(R.id.r2textView1);//.setText("25"); // merchant Request


        _progressBar2 = (ProgressBar)v.findViewById (R.id.progressBar);
      //  _progressBar2.setProgress( 500 );
       // tv2=((TextView) v.findViewById(R.id.textView1));//.setText("500");  //total merchant


        _progressBar3 = (ProgressBar)v.findViewById (R.id.c2progressBar);
       // _progressBar3.setProgress( 48 );
        tv3=((TextView) v.findViewById(R.id.c2value));//.setText("48"); // active user


        _progressBar4 = (ProgressBar)v.findViewById (R.id.c3progressBar);
       // _progressBar4.setProgress( 6 );
        _progressBar4.setMax(10);
        tv4= (TextView) v.findViewById(R.id.c3value);//.setText("6"); // sub admin





        adapterViewAndroid = new Adapter_top_sub_adm(getContext(), gridViewString,numm);
        androidGridView=(GridView)v.findViewById(R.id.grid);
        androidGridView.setAdapter(adapterViewAndroid);
        getDash();
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext(), "Mobile "+model.getPerformers().get(i).getCreatedBy(), Toast.LENGTH_LONG).show();
            }
        });

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
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<ModelAdminDashb> call = apiInterface.getMAdminDash();
        call.enqueue(new Callback<ModelAdminDashb>() {
            @Override
            public void onResponse(Call<ModelAdminDashb> call, retrofit2.Response<ModelAdminDashb> response) {
                progressDialog.dismiss();
                model=response.body();
                for(int i=0;i<model.getPerformers().size();i++)
                {
                    gridViewString.add(model.getPerformers().get(i).getName());
                    numm.add(model.getPerformers().get(i).getCount());
                }
                adapterViewAndroid.notifyDataSetChanged();
                _progressBar1.setProgress(model.getRequests());
//
                _progressBar2.setProgress(model.getTotal());
    //            tv2.setText(model.getTotal());
                _progressBar3.setProgress(model.getUsers());
                _progressBar4.setProgress(model.getAdmins());
                tv1.setText(model.getRequests()+"");
                tv2.setText(model.getTotal()+"");
                tv3.setText(model.getUsers()+"");
                tv4.setText(model.getAdmins()+"");

            }

            @Override
            public void onFailure(Call<ModelAdminDashb> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

}
