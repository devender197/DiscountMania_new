package com.virtualskillset.discountmania.merchant;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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

public class MerDetailsActivity extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    int dotscount;
    ImageView[] dots;
    public static int m_id;
    ApiInterfaceMer apiInterface;
    ArrayList<String> images=new ArrayList<>();
    ViewPagerAdapter viewPagerAdapter;
    //Bitmap bitmap;
    //ArrayList<Bitmap> bmp_images = new ArrayList<Bitmap>();
    Toolbar toolbar;
    Timer timer;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}
        m_id=getIntent().getIntExtra("mid",0);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        getMerDetail();
    }
    public void runBnr()
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        /*Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        bmp_images.add(largeIcon);*/

        viewPagerAdapter = new ViewPagerAdapter(this,images);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];


        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        try{
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));}catch (Exception e){}

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                try {
                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                }catch (Exception e){}
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new MerDetailsActivity.MyTimerTask(), 2000, 4000);
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mer_details, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       *//* if (id == R.id.action_settings) {
            return true;
        }*//*

        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_mer_details, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0){
                return new MerViewOverViewFragment();
            }
            else if(position==1)
            {
                return new MerViewProductsFragment();
            }
            else if(position==2)
            {
                return new MerViewOfferFragment();
            }
            else if(position==3)
            {
                return new MerViewReviewFragment();
            }
            else
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            MerDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

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

        }
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
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);



        //MultipartBody requestBody = builder.build()

        //final ModelMerchant merr = new ModelMerchant(Category);
        // ModelSubC mod= new ModelSubC(Category);
        Call<List<ModelMerBanner>> call = apiInterface.getMerBanner(m_id);
        call.enqueue(new Callback<List<ModelMerBanner>>() {
            @Override
            public void onResponse(Call<List<ModelMerBanner>> call, retrofit2.Response<List<ModelMerBanner>> response) {
                // progressDialog.dismiss();
                if(response.body()!=null) {
                    List<ModelMerBanner> MerDataa = response.body();
                    if (MerDataa.size() != 0) {
                        for (int i = 0; i < MerDataa.size(); i++) {
                            images.add(MerDataa.get(i).getImage());
                        }
                        runBnr();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<ModelMerBanner>> call, Throwable t) {
                // progressDialog.dismiss();
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
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer!=null)
            timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (images.size() != 0) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new MerDetailsActivity.MyTimerTask(), 2000, 4000);
        }
    }
}
