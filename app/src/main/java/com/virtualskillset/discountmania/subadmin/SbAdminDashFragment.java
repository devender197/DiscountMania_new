package com.virtualskillset.discountmania.subadmin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.subadmin.helperSubAdmin.ModelSubAdmin;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SbAdminDashFragment extends Fragment {
    private APIsubAdmin apiInterface;
    private ProgressDialog progressDialog;
    private ModelSubAdmin model;
    private ProgressBar _progressBar1,_progressBar2,_progressBar3,_progressBar4;
    private TextView tv1,tv2,tv3,tv4;

    public static SbAdminDashFragment newInstance() {
        SbAdminDashFragment fragment = new SbAdminDashFragment();
       /* Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_sb_admin_dash, container, false);

        try{
            getActivity().setTitle("Dashboard");}catch (NullPointerException e){};
        tv1=(TextView)v.findViewById(R.id.txtReq);
        tv2=(TextView)v.findViewById(R.id.texTtl);
        tv3=((TextView) v.findViewById(R.id.c2value));
        tv4= (TextView) v.findViewById(R.id.c3value);

        _progressBar1 = (ProgressBar)v.findViewById (R.id.r2progressBar);
        //_progressBar1.setProgress( 25 );
       // ((TextView) v.findViewById(R.id.r2textView1)).setText("25"); // merchant Request


        _progressBar2 = (ProgressBar)v.findViewById (R.id.progressBar);
        //_progressBar2.setProgress( 500 );
      //  ((TextView) v.findViewById(R.id.textView1)).setText("500");  //total merchant


        _progressBar3 = (ProgressBar)v.findViewById (R.id.c2progressBar);
       // _progressBar3.setProgress( 48 );
        // ((TextView) v.findViewById(R.id.c2textView1)).setText("48"); // active user


        _progressBar4 = (ProgressBar)v.findViewById (R.id.c3progressBar);
       // _progressBar4.setProgress( 6 );
        _progressBar4.setMax(10);
        //   ((TextView) v.findViewById(R.id.c3textView1)).setText("6"); // sub admin
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
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIsubAdmin.class);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        Call<ModelSubAdmin> call = apiInterface.getSbAdminDash(user.getNumber());
        call.enqueue(new Callback<ModelSubAdmin>() {
            @Override
            public void onResponse(Call<ModelSubAdmin> call, retrofit2.Response<ModelSubAdmin> response) {
                progressDialog.dismiss();
                if (response.body()!=null)
                {
                model=response.body();
                _progressBar1.setProgress(model.getMyMerchants());
//
                _progressBar2.setProgress(model.getMerchants());
                //            tv2.setText(model.getTotal());
                _progressBar3.setProgress(model.getUsers());
                _progressBar4.setProgress(model.getAdmins());
                tv1.setText(model.getMyMerchants()+"");
                tv2.setText(model.getMerchants()+"");
                tv3.setText(model.getUsers()+"");
                tv4.setText(model.getAdmins()+"");
                }
            }

            @Override
            public void onFailure(Call<ModelSubAdmin> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/


   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
