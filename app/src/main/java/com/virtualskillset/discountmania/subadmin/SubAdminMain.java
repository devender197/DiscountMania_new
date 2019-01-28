package com.virtualskillset.discountmania.subadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.virtualskillset.discountmania.AboutActivity;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.AdminAddMerchantActivity;
import com.virtualskillset.discountmania.admin.ViewMerListAvtivity;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SubAdminMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        View hView =  navigationView.getHeaderView(0);
        TextView namee=(TextView)hView.findViewById(R.id.nam);
        user=SharedPrefManager.getInstance(getApplicationContext()).getUser();
        namee.setText(user.getName());

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Instructions fragment = new Instructions();
        Fragment fragment=SbAdminDashFragment.newInstance();
        fragmentTransaction.add(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sub_admin_main, menu);
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
        if (id == R.id.nav_home) {
            fragment = SbAdminDashFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragment.setArguments(args);
            fragmentTransaction.replace(R.id.frame, fragment);
            // fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if (id == R.id.nav_add_mer) {
            if(user.getRole().equals("4"))
            {
                startActivity(new Intent(getApplicationContext(),AdminAddMerchantActivity.class));
            }else
            {
                startActivity(new Intent(getApplicationContext(),SubAdminAddMerchnat.class));
            }

        }   else if (id == R.id.nav_logout) {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            finish();
        }
        else if (id == R.id.nav_merchant) {
            startActivity(new Intent(getApplicationContext(),SubMerListAvtivity.class));
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
