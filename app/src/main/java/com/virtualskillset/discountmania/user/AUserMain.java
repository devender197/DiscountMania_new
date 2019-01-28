package com.virtualskillset.discountmania.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.AboutActivity;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.merchant.TransactionsActivity;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.user.model.ModelUserData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AUserMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    User user;
    ApiUserInter apiIfc;
    private ImageView profileImg;
    private TextView oName,bName;
    private ModelUserData model;
    //ApiUserInter apiIfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        user=SharedPrefManager.getInstance(AUserMain.this).getUser();
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        View hView =  navigationView.getHeaderView(0);
       // TextView namee=(TextView)hView.findViewById(R.id.usr);
        oName=(TextView)hView.findViewById(R.id.usr);
        bName=(TextView)hView.findViewById(R.id.textView);
        profileImg=(ImageView)hView.findViewById(R.id.imageView);
        if(user.getId()==0)
        {
        // find MenuItem you want to change
        MenuItem nav_camara = menu.findItem(R.id.nav_logout);
        // set new title to the MenuItem
        nav_camara.setTitle("Login/signup"); }
      //  namee.setText(user.getName());

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Instructions fragment = new Instructions();
        Fragment fragment= FUserCategory.newInstance();
        fragmentTransaction.add(R.id.frame, fragment);
        fragmentTransaction.commit();
        if(user.getId()!=0) {
            getImage();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame);
            if (currentFragment.getClass().getSimpleName().equals("FUserCategory")) {
                new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Exit")
                        .setMessage("Are you sure you want to close this application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

               // super.onBackPressed();
            }
            else {
                // Log.i("MainActivity", "nothing on backstack, calling super");
                try {
                    Fragment fragment=FUserCategory.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //noinspection SimplifiableIfStatement
        if (id == R.id.abt) {
            startActivity(new Intent(getApplicationContext(),AboutActivity.class));
            return true;
        }
        else if(id== R.id.prv)
        {
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.discountmania.org/privacy.php"));
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;

        if (id == R.id.nav_profile) {
            // Handle the camera action
            if(user.getId()==0)
            {
                SharedPrefManager.getInstance(AUserMain.this).logout();
                finish();
            }
            else {
            startActivity(new Intent(AUserMain.this,AUserProfile.class));}
        }   else if (id == R.id.nav_logout) {
            SharedPrefManager.getInstance(AUserMain.this).logout();
            finish();
        }
        else if (id == R.id.nav_share) {
           try {
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.dmlogo);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "temporary_file.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
               String link = "\n\n \" We increase your savings \"\n Join and earn upto 50000 per month ....\n\n";
            link = link + "https://play.google.com/store/apps/details?id=com.virtualskillset.discountmania \n\n";
            share.putExtra(Intent.EXTRA_TEXT, link);
            share.putExtra(Intent.EXTRA_STREAM,
                    Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/temporary_file.jpg"));
            startActivity(Intent.createChooser(share, "Share with"));

          } catch (Exception e) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Share Discount Mania");
                    String link = "\n\n \" We increase your savings \"\n Join and earn upto 50000 per month ....\n\n";
                    link = link + "https://play.google.com/store/apps/details?id=com.virtualskillset.discountmania \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, link);
                    startActivity(Intent.createChooser(i, "Share With"));
                } catch (Exception ee) {
                }
            }
        }
        else if (id == R.id.nav_best_offer) {
            startActivity(new Intent(AUserMain.this,AUserBestOffers.class));
        }
        else if (id == R.id.nav_benfits) {
                startActivity(new Intent(AUserMain.this,TransactionsActivity.class));

        }
        else if (id == R.id.nav_team) {
            startActivity(new Intent(getApplicationContext(), AUserMall.class));
        }
        else if (id == R.id.nav_digitalcard) {
            if(user.getId()==0)
            {
                SharedPrefManager.getInstance(AUserMain.this).logout();
                finish();
            }
            else {
            startActivity(new Intent(AUserMain.this,AUserDigitalCard.class));
            }
        }
        else if (id == R.id.nav_doorstep) {
            Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_itr) {
            Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_insurance) {
            Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_loan) {
            Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_credit) {
            Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_gstsoft) {
            Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_it) {
            Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getImage() {
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
                .create(ApiUserInter.class);


        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        Call<ModelUserData> call = apiIfc.getUserDetails(user.getId());
        call.enqueue(new Callback<ModelUserData>() {
            @Override
            public void onResponse(Call<ModelUserData> call, retrofit2.Response<ModelUserData> response) {
                model=response.body();
                oName.setText(model.getName());
                try{
                if(model.getPaid()==1)
                {
                    bName.setText("Prime");
                }
                else {
                    bName.setText("Basic");
                }}catch (Exception e){ bName.setText("Unknown");}

                // int loader = R.drawable.ic_admin;
                String image_url = "http://discountmania.org/images/client/"+model.getImage();

                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.admin)
                        .error(R.drawable.ic_admin);
                //.centerCrop()
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                //.priority(Priority.HIGH);
                Glide.with(getApplicationContext())
                        .load(image_url)
                        .apply(options)
                        .into(profileImg);

               /* // ImageLoader class instance
                ImageLoader imgLoader = new ImageLoader(MerchantMain.this);
                imgLoader.DisplayImage(image_url, loader, profileImg);*/

            }

            @Override
            public void onFailure(Call<ModelUserData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
}
