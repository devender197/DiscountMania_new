package com.virtualskillset.discountmania.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.merchant.retro.ModelSubC;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.merchant.retro.SubCat;
import com.virtualskillset.discountmania.user.model.ModelDistricts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AUserFilters extends AppCompatActivity {

    private Button btn,clear;
    private ProgressDialog progressDialog;
    ApiUserInter uploadAPIService;
    private List<ModelDistricts> modelData;
    private List<SubCat> lst=new ArrayList<>();
    private Spinner spn_subC,spn_dis,spn_state;
    private ArrayAdapter<String> spnAdp_subC,spnAdp_state,spnAdp_dis;
    private ArrayList<String>arr_state=new ArrayList<>(),arr_dis=new ArrayList<>(),arr_sub=new ArrayList<>();
    private String state="",dis="",subC="",cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_filters);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}

        btn = (Button) findViewById(R.id.btn);
        clear=(Button) findViewById(R.id.clear);
        spn_subC=(Spinner)findViewById(R.id.subSpinner);
        spn_dis=(Spinner)findViewById(R.id.disSpinner);
        spn_state=(Spinner)findViewById(R.id.stateSpinner);

        progressDialog = new ProgressDialog(AUserFilters.this);
        progressDialog.setCancelable(false);
        if(getIntent().getStringExtra("cat")!=null)
        {cat= getIntent().getStringExtra("cat");}
        getdata();
        //getSubCat();

       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this,CheckedActivity.class);
                startActivity(intent);*/
                Intent returnIntent = new Intent();
                returnIntent.putExtra("state",state);
                returnIntent.putExtra("dist",dis);
                returnIntent.putExtra("subc",subC);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

       clear.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               recreate();
           }
       });

        /////////////////////category spinner//////////////
        spnAdp_state = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                arr_state);
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn_state.setAdapter(spnAdp_state);
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = arr_state.get(position);
                arr_dis.clear();
               for(int i=0;i<modelData.get(position).getDistricts().size();i++)
               {
                   arr_dis.add(modelData.get(position).getDistricts().get(i).getDistrictname());
               }
               spnAdp_dis.notifyDataSetChanged();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////////////category spinner//////////////
        spnAdp_dis = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                arr_dis);
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn_dis.setAdapter(spnAdp_dis);
        spn_dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dis=arr_dis.get(position);

                // getSubCat();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /////////////////////category spinner//////////////
        spnAdp_subC = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                arr_sub);
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn_subC.setAdapter(spnAdp_subC);
        spn_subC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subC=arr_sub.get(position);

                // getSubCat();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    private void getdata() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUserInter.class);

        if(progressDialog.isShowing())
        {
            progressDialog.setMessage("Please wait...");}
        else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        //User user = SharedPrefManager.getInstance(AUserFilters.this).getUser();

        Call<List<ModelDistricts>> call = uploadAPIService.getStateDistricts();
        call.enqueue(new Callback<List<ModelDistricts>>() {
            @Override
            public void onResponse(Call<List<ModelDistricts>> call, retrofit2.Response<List<ModelDistricts>> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {
                    modelData = response.body();
                        for (int i = 0; i < modelData.size(); i++) {
                            arr_state.add(modelData.get(i).getState());
                        }
                        spnAdp_state.notifyDataSetChanged();

                        getSubCat(cat);

                }
                else {
                    Toast.makeText(AUserFilters.this, response.message() + " Error Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelDistricts>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AUserFilters.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    private void getSubCat(String cat) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder() .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUserInter.class);

       /* if(progressDialog.isShowing())
        {
            progressDialog.setMessage("Preparing data...");
        }
        else {
            progressDialog.setMessage("Preparing data..."); progressDialog.show();
        }*/


        Call<ModelSubC> call = uploadAPIService.getSubCatt(cat);
        call.enqueue(new Callback<ModelSubC>() {
            @Override
            public void onResponse(Call<ModelSubC> call, retrofit2.Response<ModelSubC> response) {
                progressDialog.dismiss();
                ModelSubC loginResponse = response.body();

                if (loginResponse != null) {
                    arr_sub.add("All");
                    for(int i=0;i<loginResponse.getSubCat().size();i++)
                    {
                        arr_sub.add(loginResponse.getSubCat().get(i).getSubCat());
                    }
                    spnAdp_subC.notifyDataSetChanged();
                    //lst=loginResponse.getSubCat();
                    /*if(modelData.getBSubCat()!=null) {
                        int subCatPos = subCatListValue(modelData.getBSubCat());
                        spn2.setSelection(subCatPos);
                    }*/
                }
                else {
                    Toast.makeText(AUserFilters.this, response.message()+" Error Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelSubC> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AUserFilters.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


}
