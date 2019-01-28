package com.virtualskillset.discountmania.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.GPSTracker;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.merchant.MerDetailsActivity;
import com.virtualskillset.discountmania.user.model.ModelUserMerList;
import com.virtualskillset.discountmania.user.model.ModelUsrMerLst;
import com.virtualskillset.discountmania.user.userHelper.RevAdapter_shp_list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AUserMerList extends AppCompatActivity implements RevAdapter_shp_list.AdapterListener {
    private String catMn="";
    ApiUserInter apiInterface;
    private ProgressDialog progressDialog;
    private ModelUserMerList rs;
    private List<ModelUsrMerLst>allList=new ArrayList<>();
    private RecyclerView recyclerView;
    private RevAdapter_shp_list mAdapter;
    private SearchView searchView;
    private TextView empty_view;
    private String state="",dist="",subc="";
    private double dlat=0.0,dlon=0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usr_mer_list);
        if (getIntent().getStringExtra("cat") != null) {
            catMn = getIntent().getStringExtra("cat");
        }
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(catMn);
            }
        } catch (Exception e) {
        }
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        empty_view=(TextView)findViewById(R.id.empty_view);
        GPSTracker gpsTracker = new GPSTracker(AUserMerList.this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            dlat=gpsTracker.getLatitude();
            dlon=gpsTracker.getLongitude();
        }

        getMerchants();



    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/


    private void getMerchants() {
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
                .create(ApiUserInter.class);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<ModelUserMerList> call = apiInterface.getMerchantListUsr(catMn);
        call.enqueue(new Callback<ModelUserMerList>() {
            @Override
            public void onResponse(Call<ModelUserMerList> call, retrofit2.Response<ModelUserMerList> response) {
                progressDialog.dismiss();
                rs=response.body();
                if(rs!=null) {
                    List<ModelUsrMerLst> temp=new ArrayList<>();
                    for (int i = 0; i < rs.getPayd().size(); i++) {
                        double dkm=distance(dlat,dlon,rs.getPayd().get(i).getLati(),rs.getPayd().get(i).getLongi());
                        ModelUsrMerLst Paper = new ModelUsrMerLst(rs.getPayd().get(i).getId(), rs.getPayd().get(i).getNumber(), rs.getPayd().get(i).getBCat(), rs.getPayd().get(i).getBSubCat(), rs.getPayd().get(i).getBName(), rs.getPayd().get(i).getLocality(), rs.getPayd().get(i).getImage(), rs.getPayd().get(i).getStreet(), rs.getPayd().get(i).getPin(), rs.getPayd().get(i).getDistrict(), rs.getPayd().get(i).getState(), rs.getPayd().get(i).getBDesc(),/*rs.getPayd().get(i).getLati(),rs.getPayd().get(i).getLongi()*/dkm);
                        allList.add(Paper);
                    }
                    for (int i = 0; i < rs.getOthers().size(); i++) {
                        double dkm=distance(dlat,dlon,rs.getOthers().get(i).getLati(),rs.getOthers().get(i).getLongi());
                        ModelUsrMerLst Paper = new ModelUsrMerLst(rs.getOthers().get(i).getId(), rs.getOthers().get(i).getNumber(), rs.getOthers().get(i).getBCat(), rs.getOthers().get(i).getBSubCat(), rs.getOthers().get(i).getBName(), rs.getOthers().get(i).getLocality(), rs.getOthers().get(i).getImage(), rs.getOthers().get(i).getStreet(), rs.getOthers().get(i).getPin(), rs.getOthers().get(i).getDistrict(), rs.getOthers().get(i).getState(), rs.getOthers().get(i).getBDesc(),dkm/*rs.getOthers().get(i).getLati(),rs.getOthers().get(i).getLongi()*/);
                        temp.add(Paper);
                    }
                    Collections.sort(temp, new Comparator< ModelUsrMerLst >() {
                        @Override public int compare(ModelUsrMerLst p1, ModelUsrMerLst p2) {
                            return Double.compare(p1.getDistt(), p2.getDistt()); // Ascending
                        }
                    });
                    allList.addAll(temp);

                    if (allList == null || allList.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        empty_view.setVisibility(View.VISIBLE);
                    } else {
                        mAdapter = new RevAdapter_shp_list(getApplicationContext(), allList, AUserMerList.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                        recyclerView.setAdapter(mAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelUserMerList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    private void getMerchantsFil() {
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
                .create(ApiUserInter.class);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<ModelUserMerList> call = apiInterface.getMerchantListUsrFilter(catMn,subc,state,dist);
        call.enqueue(new Callback<ModelUserMerList>() {
            @Override
            public void onResponse(Call<ModelUserMerList> call, retrofit2.Response<ModelUserMerList> response) {
                progressDialog.dismiss();
                rs=response.body();
                allList.clear();
                for (int i=0;i<rs.getPayd().size();i++)
                {
                    ModelUsrMerLst Paper = new ModelUsrMerLst(rs.getPayd().get(i).getId(),rs.getPayd().get(i).getNumber(),rs.getPayd().get(i).getBCat(),rs.getPayd().get(i).getBSubCat(),rs.getPayd().get(i).getBName(),rs.getPayd().get(i).getLocality(),rs.getPayd().get(i).getImage(),rs.getPayd().get(i).getStreet(),rs.getPayd().get(i).getPin(),rs.getPayd().get(i).getDistrict(),rs.getPayd().get(i).getState(),rs.getPayd().get(i).getBDesc(),/*rs.getPayd().get(i).getLati(),rs.getPayd().get(i).getLongi()*/0.0);
                    allList.add(Paper);
                }
                for (int i=0;i<rs.getOthers().size();i++)
                {
                    ModelUsrMerLst Paper = new ModelUsrMerLst(rs.getOthers().get(i).getId(),rs.getOthers().get(i).getNumber(),rs.getOthers().get(i).getBCat(),rs.getOthers().get(i).getBSubCat(),rs.getOthers().get(i).getBName(),rs.getOthers().get(i).getLocality(),rs.getOthers().get(i).getImage(),rs.getOthers().get(i).getStreet(),rs.getOthers().get(i).getPin(),rs.getOthers().get(i).getDistrict(),rs.getOthers().get(i).getState(),rs.getOthers().get(i).getBDesc(),/*rs.getOthers().get(i).getLati(),rs.getOthers().get(i).getLongi()*/0.0);
                    allList.add(Paper);
                }
                if(allList==null || allList.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    empty_view.setVisibility(View.GONE);
                    mAdapter = new RevAdapter_shp_list(getApplicationContext(), allList, AUserMerList.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onFailure(Call<ModelUserMerList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
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
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usr_filters, menu);

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
                mAdapter = new RevAdapter_shp_list(getApplicationContext(),allList,AUserMerList.this);
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
        if (id == R.id.action_filter) {
           // startActivity(new Intent(AUserMerList.this,AUserFilters.class).putExtra("cat",catMn));
           // Toast.makeText(AUserMerList.this,"This Feature will avaliable soon!",Toast.LENGTH_LONG).show();

            Intent i = new Intent(this, AUserFilters.class);
            i.putExtra("cat",catMn);
            startActivityForResult(i, 11);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 11) {
            if(resultCode == Activity.RESULT_OK){
                state=data.getStringExtra("state");
                dist=data.getStringExtra("dist");
                subc=data.getStringExtra("subc");
                if(subc.equals("All"))
                {
                    subc="";
                }
                getMerchantsFil();
               // Toast.makeText(getApplicationContext(),state+" "+dist+" "+subc,1).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult


    @Override
    public void onContactSelected(ModelUsrMerLst contact) {
        startActivity(new Intent(AUserMerList.this, MerDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("mid", contact.getId()));

    }
    private double distance(double lat1, double lon1, String latt2, String lonn2) {
        double lat2=0.0,lon2=0.0,dist=0.0;
        try{
            lat2=Double.parseDouble(latt2);
            lon2=Double.parseDouble(lonn2);
            double theta = lon1 - lon2;
            dist = Math.sin(deg2rad(lat1))
                    * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1))
                    * Math.cos(deg2rad(lat2))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
        }catch (Exception e){
            dist=0.0;
        }
        return (dist*2);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}