package com.virtualskillset.discountmania.user;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.merchant.merAdpter.ViewPagerAdapter;
import com.virtualskillset.discountmania.merchant.retro.ModelMerBanner;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.user.userHelper.GridSpacingItemDecoration;
import com.virtualskillset.discountmania.user.userHelper.RevAdapter_category_home;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
public class FUserCategory extends Fragment implements RevAdapter_category_home.ItemClickListener {

    private ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    //private Adaptergrid adapterViewAndroid;
    private RevAdapter_category_home revAdp;
    RecyclerView rv;
    ApiUserInter apiInterface;
    ArrayList<String> images=new ArrayList<>();
    Timer timer;
    ViewPagerAdapter viewPagerAdapter;

    ////grid///
    //private GridView androidGridView;


    private Context mContext;

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }




    public FUserCategory() {
        // Required empty public constructor
    }

    public static FUserCategory newInstance() {
        FUserCategory fragment = new FUserCategory();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_user_category, container, false);
        try{
            getActivity().setTitle("Home");}catch (NullPointerException e){};
        rv=(RecyclerView)v.findViewById(R.id.recycler_view);
        int numberOfColumns = 3;
        int[] gridViewImageId = {
                R.drawable.restaurant, R.drawable.daily, R.drawable.movies, R.drawable.travel, R.drawable.grocery, R.drawable.ic_electro,  R.drawable.med,
                R.drawable.doctr, R.drawable.furniture, R.drawable.wed, R.drawable.cloth, R.drawable.education, R.drawable.repair,R.drawable.build,
                R.drawable.fitness, R.drawable.ic_hotels,  R.drawable.step, R.drawable.other
        };
        rv.addItemDecoration(new GridSpacingItemDecoration(3,2, false));
        rv.setLayoutManager(new GridLayoutManager(mContext, numberOfColumns));
        revAdp = new RevAdapter_category_home(mContext,mContext.getResources().getStringArray(R.array.category),gridViewImageId );
        revAdp.setClickListener(FUserCategory.this);
        rv.setAdapter(revAdp);

        viewPager = (ViewPager)v. findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout)v. findViewById(R.id.SliderDots);

      /*  ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);*/
      getBanner();
        return v;
    }

    public void runBnr()
    {
       // viewPager = (ViewPager) findViewById(R.id.viewPager);

      //  sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        /*Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        bmp_images.add(largeIcon);*/

        viewPagerAdapter = new ViewPagerAdapter(mContext,images);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];


        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        try{
            dots[0].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));}catch (Exception e){}

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                try {
                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.nonactive_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));
                }catch (Exception e){}
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent pr=new Intent(mContext,AUserMerList.class);
        pr.putExtra("cat",getResources().getStringArray(R.array.category)[position]);
        startActivity(pr);
    }


    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       /* if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else {
                            viewPager.setCurrentItem(0);
                        }*/
                        int bnr=images.size();
                        if(viewPager.getCurrentItem() == 0){
                            viewPager.setCurrentItem(1);
                        } else if(viewPager.getCurrentItem() == 1){
                            if(bnr==2)
                            {
                                viewPager.setCurrentItem(0);
                            }
                            else
                                viewPager.setCurrentItem(2);
                        }
                        else if(viewPager.getCurrentItem() == 2){
                            if(bnr==3)
                            {
                                viewPager.setCurrentItem(0);
                            }
                            else
                                viewPager.setCurrentItem(3);
                        }
                        else if(viewPager.getCurrentItem() == 3){
                            if(bnr==4)
                            {
                                viewPager.setCurrentItem(0);
                            }
                            else
                                viewPager.setCurrentItem(4);
                        }else {
                            viewPager.setCurrentItem(0);
                        }

                    }
                });
            }catch (Exception e){}
        }
    }


    private void getBanner() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUserInter.class);



        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<List<ModelMerBanner>> call = apiInterface.getMerBanner(1);
        call.enqueue(new Callback<List<ModelMerBanner>>() {
            @Override
            public void onResponse(Call<List<ModelMerBanner>> call, retrofit2.Response<List<ModelMerBanner>> response) {
                // progressDialog.dismiss();
                if(response.body()!=null) {
                    List<ModelMerBanner> MerDataa = response.body();
                    for (int i = 0; i < MerDataa.size(); i++) {
                        images.add(MerDataa.get(i).getImage());
                    }
                    runBnr();
                }
            }

            @Override
            public void onFailure(Call<List<ModelMerBanner>> call, Throwable t) {
                // progressDialog.dismiss();
                Toast.makeText(mContext, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }







}
