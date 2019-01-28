package com.virtualskillset.discountmania.admin;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.admin.helper.adapter.RevAdapter_admin_mer_list;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.merchant.MerDetailsActivity;
import com.virtualskillset.discountmania.user.userHelper.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewMerListAvtivity extends AppCompatActivity implements RevAdapter_admin_mer_list.ItemClickListener{
    private APIService apiInterface;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private RevAdapter_admin_mer_list adapter;
    private SearchView searchView;
    private List<ModelMerchantList> rs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mer_list_avtivity);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        getMerchants();
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
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter = new RevAdapter_admin_mer_list(ViewMerListAvtivity.this,rs );
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
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
    private void getMerchants() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);


        progressDialog = new ProgressDialog(ViewMerListAvtivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<List<ModelMerchantList>> call = apiInterface.getMerchantList();
        call.enqueue(new Callback<List<ModelMerchantList>>() {
            @Override
            public void onResponse(Call<List<ModelMerchantList>> call, retrofit2.Response<List<ModelMerchantList>> response) {
                progressDialog.dismiss();
                List<ModelMerchantList> lstt=response.body();
                for(int i=lstt.size()-1;i>=0;i--)
                {
                    rs.add(lstt.get(i));
                }
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,2, false));
                recyclerView.setLayoutManager(new GridLayoutManager(ViewMerListAvtivity.this, 1));
                adapter = new RevAdapter_admin_mer_list(ViewMerListAvtivity.this,rs );
                adapter.setClickListener(ViewMerListAvtivity.this);
                recyclerView.setAdapter(adapter);

                // idAll.add(rs.get(i).getId());
            }

            @Override
            public void onFailure(Call<List<ModelMerchantList>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewMerListAvtivity.this, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent in=new Intent(ViewMerListAvtivity.this,MerDetailsActivity.class);
        in.putExtra("mid",position);
        startActivity(in);
    }
}
