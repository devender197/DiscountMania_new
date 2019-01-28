package com.virtualskillset.discountmania.merchant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
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
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.user.AUserDigitalCard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MerchantMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    ApiInterfaceMer apiIfc;
    ImageView profileImg;
    private TextView oName,bName;
    private ModelMerchantList model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MerchantNewCustomer.class));
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Instructions fragment = new Instructions();
        Fragment fragment= MerDashFragment.newInstance();
        fragmentTransaction.add(R.id.frame, fragment);
        fragmentTransaction.commit();
        View hView =  navigationView.getHeaderView(0);
        oName=(TextView)hView.findViewById(R.id.mer);
        bName=(TextView)hView.findViewById(R.id.textView);
        profileImg=(ImageView)hView.findViewById(R.id.imageView);
        /*RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_user_24dp);
        requestOptions.error(R.drawable.ic_user_24dp);

        Glide.with(this).load(photoUrl)
                .apply(requestOptions).thumbnail(0.5f).into(user_imageView);*/
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MerProfilePicActivity.class));
            }
        });

        getImage();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           //super.onBackPressed();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame);
            if (currentFragment.getClass().getSimpleName().equals("MerDashFragment")) {
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
                    Fragment fragment=MerDashFragment.instantiate(MerchantMain.this,MerDashFragment.class.getName());
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
                } catch (Exception e) { }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.merchant_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
        User user= SharedPrefManager.getInstance(getApplicationContext()).getUser();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),MerDetailsActivity.class).putExtra("mid",user.getId()));
        }   else if (id == R.id.nav_logout) {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            finish();
        } else if (id == R.id.nav_pro_updt) {
            startActivity(new Intent(getApplicationContext(),MerchantUpdatePfActivity.class).putExtra("mid",user.getId()));
        }
        else if (id == R.id.nav_icon) {
            startActivity(new Intent(getApplicationContext(),MerProfilePicActivity.class));
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
                    fo.flush();
                    fo.close();
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
        else if (id == R.id.nav_home) {
            /*fragment = MerDashFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragment.setArguments(args);
            fragmentTransaction.replace(R.id.frame, fragment);
            // fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/
            try {
                fragment=MerDashFragment.instantiate(MerchantMain.this,MerDashFragment.class.getName());
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
            } catch (Exception e) { }
        }
        else if (id == R.id.nav_add_offer) {
            startActivity(new Intent(getApplicationContext(),MerAddOfferActivity.class));
        }
        else if (id == R.id.nav_digitalcard) {
            startActivity(new Intent(getApplicationContext(),AUserDigitalCard.class));
        }
        else if (id == R.id.nav_newcus)
        {
            startActivity(new Intent(getApplicationContext(),MerchantNewCustomer.class));
        }
        else if (id == R.id.nav_happycus) {
            startActivity(new Intent(getApplicationContext(),TransactionsActivity.class));
        }
        else if (id == R.id.nav_gstsoft) {
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
        else if (id == R.id.nav_personal_ca) {
            Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_ask) {
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
                .create(ApiInterfaceMer.class);


        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        Call<ModelMerchantList> call = apiIfc.getMerchantDetails(user.getId());
        call.enqueue(new Callback<ModelMerchantList>() {
            @Override
            public void onResponse(Call<ModelMerchantList> call, retrofit2.Response<ModelMerchantList> response) {
                model=response.body();
                oName.setText(model.getName());
                bName.setText(model.getBName());

               // int loader = R.drawable.ic_admin;
                String image_url = "http://discountmania.org/images/merchant/profile/"+model.getImage();

                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.admin)
                        .error(R.drawable.admin);
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
            public void onFailure(Call<ModelMerchantList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


}
