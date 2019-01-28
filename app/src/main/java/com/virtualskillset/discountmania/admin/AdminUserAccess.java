package com.virtualskillset.discountmania.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.admin.helper.adapter.RevAdapter_User_Access;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.user.model.ModelUserData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminUserAccess extends AppCompatActivity implements RevAdapter_User_Access.AdapterListener{
    APIService apiInterface;
    private ProgressDialog progressDialog;
    List<ModelUserData> reqList =new ArrayList<>();
    RecyclerView recyclerView;
    RevAdapter_User_Access mAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_access);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        getMerchants(0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_srch, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager;
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mAdapter = new  RevAdapter_User_Access(AdminUserAccess.this,reqList,AdminUserAccess.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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


        progressDialog = new ProgressDialog(AdminUserAccess.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<List<ModelUserData>> call = apiInterface.getUserList();
        call.enqueue(new Callback<List<ModelUserData>>() {
            @Override
            public void onResponse(Call<List<ModelUserData>> call, retrofit2.Response<List<ModelUserData>> response) {
                progressDialog.dismiss();
                List<ModelUserData> lstt = response.body();
                for(int i=lstt.size()-1; i>=0;i--)
                {
                    reqList.add(lstt.get(i));
                }
                /*adapterViewAndroid = new Adpater_mer_blockpaid(AdminMerBlockPaidActivity.this,reqList);
                androidGridView.setAdapter(adapterViewAndroid);*/
                //mAdapter.notifyDataSetChanged();

                mAdapter = new  RevAdapter_User_Access(AdminUserAccess.this,reqList,AdminUserAccess.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                if(type==0)
                {
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));}
                recyclerView.setAdapter(mAdapter);




                // adapterViewAndroid.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ModelUserData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }


    public void paidMer(int val,int mid) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);


        progressDialog = new ProgressDialog(AdminUserAccess.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<ModelResponse> call = apiInterface.merPaid(val,mid);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                progressDialog.dismiss();
                ModelResponse rs = response.body();
                if(!rs.getError())
                {
                    reqList.clear();
                    getMerchants(1);
                    Toast.makeText(getApplicationContext(), rs.getResponseMessage(), Toast.LENGTH_SHORT).show();
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

    public void blockMer(int val,int mid) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);


        progressDialog = new ProgressDialog(AdminUserAccess.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<ModelResponse> call = apiInterface.merBlock(val,mid);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
                progressDialog.dismiss();
                ModelResponse rs = response.body();
                Toast.makeText(getApplicationContext(), rs.getResponseMessage(), Toast.LENGTH_SHORT).show();
                if(!rs.getError())
                {
                    reqList.clear();
                    getMerchants(1);
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

    @Override
    public void onContactSelected(final ModelUserData contact) {
        final AlertDialog.Builder ab = new AlertDialog.Builder(AdminUserAccess.this);
        View v = LayoutInflater.from(AdminUserAccess.this).inflate(R.layout.dialog_user_access,null);
        ab.setView(v);
        ab.setTitle(contact.getName());
        ab.setMessage(contact.getNumber()+"");
        TextView exp=(TextView)v.findViewById(R.id.exp);
        final Switch paid=(Switch) v.findViewById(R.id.mySwitchPaid);
        final Switch block=(Switch) v.findViewById(R.id.mySwitchBlock);
        exp.setText(contact.getExpiry());
        if(contact.getPaid()==1)
        {
            paid.setChecked(true);
        }
        if(contact.getStatus()==0)
        {
            block.setChecked(true);
        }

        paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int val=0;
                if (paid.isChecked()) {
                   val=1;}
                try {
                    paidMer(val,contact.getId());
                }catch (Exception e){}
            }
        });
        block.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int val=1;
                    if(isChecked) { val=0; }
                    try {
                        blockMer(val,contact.getId());
                    }catch (Exception e){}
            }
        });

      /*  ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AdminUserAccess.this, "ok button pressed", Toast.LENGTH_SHORT).show();
                        boolean tButton = paid.isChecked();
                        if (tButton) {
                            Toast.makeText(AdminUserAccess.this, "Button is off on a ", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                });*/
                ab.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ab.create().show();
            }


}

