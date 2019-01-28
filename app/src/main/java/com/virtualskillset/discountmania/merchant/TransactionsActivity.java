package com.virtualskillset.discountmania.merchant;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_transcations;
import com.virtualskillset.discountmania.merchant.retro.ModelMerTrans;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionsActivity extends AppCompatActivity implements RevAdapter_transcations.AdapterListener {
    private View header;
    ApiInterfaceMer apiIfc;
    private ProgressDialog progressDialog;
    private ModelMerTrans model;
    private SearchView searchView;
    private User user;
    private TextView empty_view;
    private RecyclerView recyclerView;
    private RevAdapter_transcations mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}
        /*lv=(ExpandableHeightListView) findViewById(R.id.lstt);*/
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        empty_view=(TextView)findViewById(R.id.empty_view);
        user = SharedPrefManager.getInstance(TransactionsActivity.this).getUser();
        LayoutInflater inflater = getLayoutInflater();
            if(user.getRole().equals("0"))
            {
                try{getSupportActionBar().setTitle("Used Service Benefits");}catch (Exception e){}
            }
            /*else
            {
                header = inflater.inflate(R.layout.cell_mer_happy_cus_header,null);
            }*/
        getverify();

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

    private void getverify() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiIfc = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);


        progressDialog = new ProgressDialog(TransactionsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Call<ModelMerTrans> call;
        if(user.getRole().equals("0"))
        {
        call = apiIfc.usrTrans(user.getId());}
        else { call = apiIfc.merTrans(user.getId());}
        call.enqueue(new Callback<ModelMerTrans>() {
            @Override
            public void onResponse(Call<ModelMerTrans> call, retrofit2.Response<ModelMerTrans> response) {
                progressDialog.dismiss();
                model=response.body();
                if(response.body()!=null) {
                    if (model.getError()) {
                        recyclerView.setVisibility(View.GONE);
                        empty_view.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        empty_view.setVisibility(View.GONE);
                        mAdapter = new RevAdapter_transcations(getApplicationContext(), model.getResponse(),user.getRole(), TransactionsActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                        recyclerView.setAdapter(mAdapter);
                    }
                }


            }

            @Override
            public void onFailure(Call<ModelMerTrans> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    @Override
    public void onContactSelected(ModelMerTrans.Response contact) {

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
                mAdapter = new RevAdapter_transcations(getApplicationContext(),model.getResponse(),user.getRole(),TransactionsActivity.this);
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
}
