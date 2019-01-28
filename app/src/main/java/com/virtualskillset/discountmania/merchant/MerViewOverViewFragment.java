package com.virtualskillset.discountmania.merchant;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_social_links;
import com.virtualskillset.discountmania.merchant.retro.ModelSocialLinks;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MerViewOverViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MerViewOverViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerViewOverViewFragment extends Fragment implements RevAdapter_social_links.ItemClickListener {
    private TextView bDays,bTime,bCat,bsubCat,bDes,bAdrs,bNam,bOwn,mail,disCoun,mobile;
    ApiInterfaceMer apiInterface;
    private ProgressDialog progressDialog;
    private ModelMerchantList MerData;
    ImageView shr,direction;
    //private Button whats,fb,youTube,insta,twitter;
    private int Call_PERMISSION_CODE = 23;
    String adrsh;
    private RecyclerView recyclerView;
    private RevAdapter_social_links adapter;
    private List<ModelSocialLinks> links=new ArrayList<>();

    public MerViewOverViewFragment() {
        // Required empty public constructor
    }
    private Context mContext;

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    public static MerViewOverViewFragment newInstance(/*String param1, String param2*/) {
        MerViewOverViewFragment fragment = new MerViewOverViewFragment();
      /*  Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @NonNull
    @Override
    public LifecycleOwner getViewLifecycleOwner() {
        return super.getViewLifecycleOwner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_mer_view_overview, container, false);
        bDays = (TextView)v. findViewById(R.id.time1);
        bTime=(TextView)v. findViewById(R.id.time4);
        bCat=(TextView)v. findViewById(R.id.cat);
        bsubCat=(TextView)v. findViewById(R.id.payt);
        bDes=(TextView)v. findViewById(R.id.t3);
        bAdrs=(TextView)v. findViewById(R.id.adt);
        bNam=(TextView)v. findViewById(R.id.name);
        bOwn=(TextView)v. findViewById(R.id.pert);
        mail=(TextView)v. findViewById(R.id.emt);
        mobile=(TextView)v.findViewById(R.id.t2);
        shr=(ImageView)v.findViewById(R.id.shr);
       // payMethd=(TextView)v.findViewById(R.id.m);
        disCoun=(TextView)v.findViewById(R.id.oft);
        direction=(ImageView)v.findViewById(R.id.imageView2);
        /*whats=(Button)v.findViewById(R.id.whts);
        fb=(Button)v.findViewById(R.id.fac);
        youTube=(Button)v.findViewById(R.id.you);
        insta= (Button)v.findViewById(R.id.inst);
        twitter=(Button)v.findViewById(R.id.twi);*/
        recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);
        getMerDetail();

       /* whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean installed = appInstalledOrNot("com.whatsapp");
                    if(installed) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        "https://api.whatsapp.com/send?phone=+91" + MerData.getNumber() + "&text=I'm%20DiscountMania%20App%20User"
                                )));
                    }
                    else
                    {
                        Toast.makeText(mContext, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                .show();
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "Unable to send please send manually"+MerData.getNumber(), Toast.LENGTH_SHORT)
                            .show();
                }
            }

        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*String abc = MerData.getFacebook();
                Pattern p = Pattern.compile("[^0-9]");

                String numericOutput = p.matcher(abc).replaceAll("");
                Intent facebookIntent = getOpenFacebookIntent(mContext,numericOutput);
                startActivity(facebookIntent);*//*
                try {
                    Intent facebookIntent = newFacebookIntent(mContext, MerData.getFacebook());
                    startActivity(facebookIntent);
                }catch (Exception e){
                    Toast.makeText(mContext,"Unable to open facebook",Toast.LENGTH_LONG).show();
                }
            }
        });

        youTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null;
                String url=MerData.getYoutube();
                if(url!=null && !url.isEmpty()) {
                    try {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.google.android.youtube");
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        try {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);}catch (Exception k){Toast.makeText(mContext, "Page Not Found"+MerData.getNumber(), Toast.LENGTH_SHORT)
                                .show();}
                    }
                }
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/"+MerData.getInsta());
                Intent insta = new Intent(Intent.ACTION_VIEW, uri);
                insta.setPackage("com.instagram.android");
                try {
                    boolean installed = appInstalledOrNot("com.instagram.android");
                    if(installed) {
                        startActivity(insta);
                    }
                    else
                    {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+MerData.getInsta())));
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "Unable to open Profile"+MerData.getNumber(), Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String twitter_user_name=MerData.getTwitter();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitter_user_name)));
                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitter_user_name)));
                }
            }
        });*/


        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isReadCallNotAllowed()){
                //If permission is already having then showing the toast
                // Toast.makeText(MainActivity.this,"You already have the permission",Toast.LENGTH_LONG).show();
                //Existing the method with return
                // return;
                requestCallPermission();
            }
                else {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + MerData.getNumber()));
                mContext.startActivity(intent);}
        }
        });

        shr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = "";
                if (MerData.getBSubCat() != null && !MerData.getBSubCat().isEmpty()) {
                    sub = "(" + MerData.getBSubCat() + ")";
                }

                try {
                    Bitmap icon = BitmapFactory.decodeResource(getResources(),
                            R.drawable.mershrbnr);
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
                    String sAux = "Business name :-" + MerData.getBName() + "\n" + "\nCategory :-" + MerData.getBCat() + "  " + sub + "\n" + "\nDescription :-" + MerData.getBDesc() + "\n" + "\nAddress :-" + adrsh;
                    String link = "\n\nDownload The Discount Maina App and get discount at near by shops\n\n";
                    sAux = sAux + link + "https://play.google.com/store/apps/details?id=com.virtualskillset.discountmania \n\n";
                    share.putExtra(Intent.EXTRA_TEXT, sAux);
                    share.putExtra(Intent.EXTRA_STREAM,
                            Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/temporary_file.jpg"));
                    startActivity(Intent.createChooser(share, "Share with"));

                }
                catch (Exception r) {
                    try {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "Discount Mania");
                        String sAux = "Business name :-" + MerData.getBName() + "\n" + "\nCategory :-" + MerData.getBCat() + "  " + sub + "\n" + "\nDescription :-" + MerData.getBDesc() + "\n" + "\nAddress :-" + adrsh;
                        String link = "\n\nDownload The Discount Maina App and get discount at near by shops\n\n";
                        sAux = sAux + link + "https://play.google.com/store/apps/details?id=com.virtualskillset.discountmania \n\n";
                        i.putExtra(Intent.EXTRA_TEXT, sAux);
                        startActivity(Intent.createChooser(i, "choose one"));
                    } catch (Exception e) {
                        //e.toString();
                    }
                }
            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (MerData.getLati().equals("0.0") && MerData.getLongi().equals("0.0")) {
                        Toast.makeText(mContext, "Location not provided by Merchant", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        double latitude = Double.parseDouble(MerData.getLati());
                        double longitude = Double.parseDouble(MerData.getLongi());
                /*String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude,longitude );
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mContext.startActivity(intent);*/
                        String bname = MerData.getBName();

                        String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + "Business :-" + bname + ")";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                        startActivity(intent);
                    }
                }
                    catch(Exception e)
                        {
                            Toast.makeText(mContext, "Location not provided by Merchant", Toast.LENGTH_SHORT).show();
                        }

            }
        });


        return v;
    }


  /*  // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
private void getMerDetail() {
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
            .create(ApiInterfaceMer.class);


    progressDialog = new ProgressDialog(getContext());
    progressDialog.setCancelable(false);
    progressDialog.setMessage("Please Wait...");
    progressDialog.show();

    //MultipartBody requestBody = builder.build()

    //final ModelMerchant merr = new ModelMerchant(Category);
    // ModelSubC mod= new ModelSubC(Category);
    Call<ModelMerchantList> call = apiInterface.getMerchantDetails(MerDetailsActivity.m_id);
    call.enqueue(new Callback<ModelMerchantList>() {
        @Override
        public void onResponse(Call<ModelMerchantList> call, retrofit2.Response<ModelMerchantList> response) {
            progressDialog.dismiss();

            if(response.body()!=null) {
                MerData=response.body();
                try {
                    ((MerDetailsActivity) getActivity()).getSupportActionBar().setTitle(MerData.getBName());
                } catch (Exception e) {
                }

                bDays.setText(MerData.getDay());
                bTime.setText(MerData.getTime());
                bCat.setText(MerData.getBCat());
                bsubCat.setText(MerData.getBSubCat());
                bDes.setText(MerData.getBDesc());
                mobile.setText(MerData.getNumber() + "");
                if (MerData.getStreet() != null && MerData.getLocality() != null && MerData.getPin() != null) {
                    adrsh = MerData.getStreet() + ", " + MerData.getLocality() + ", " + MerData.getDistrict() + ", " + MerData.getState() + " " + MerData.getPin();
                    bAdrs.setText(adrsh);
                } else if (MerData.getLocality() != null && MerData.getStreet() != null) {
                    adrsh = MerData.getStreet() + ", " + MerData.getLocality() + ", " + MerData.getDistrict() + ", " + MerData.getState();
                    bAdrs.setText(adrsh);
                } else if (MerData.getPin() != null && MerData.getStreet() != null) {
                    adrsh = MerData.getStreet() + ", " + MerData.getDistrict() + ", " + MerData.getState() + " " + MerData.getPin();
                    bAdrs.setText(adrsh);
                } else if (MerData.getPin() != null && MerData.getLocality() != null) {
                    adrsh = MerData.getLocality() + ", " + MerData.getDistrict() + ", " + MerData.getState() + " " + MerData.getPin();
                    bAdrs.setText(adrsh);
                } else if (MerData.getLocality() == null && MerData.getStreet() == null && MerData.getPin() != null) {
                    adrsh = MerData.getDistrict() + ", " + MerData.getState() + " " + MerData.getPin();
                    bAdrs.setText(adrsh);
                } else if (MerData.getPin() == null && MerData.getStreet() == null && MerData.getLocality() != null) {
                    adrsh = MerData.getLocality() + ", " + MerData.getDistrict() + ", " + MerData.getState();
                    bAdrs.setText(adrsh);
                } else if (MerData.getPin() == null && MerData.getLocality() == null && MerData.getStreet() != null) {
                    adrsh = MerData.getStreet() + ", " + MerData.getDistrict() + ", " + MerData.getState();
                    bAdrs.setText(adrsh);
                } else {
                    adrsh = MerData.getDistrict() + ", " + MerData.getState();
                    bAdrs.setText(adrsh);
                }
                bNam.setText(MerData.getBName());
                bOwn.setText(MerData.getName());
                mail.setText(MerData.getEmail());
                //payMethd.setText(MerData.getPayMethod());
                if (MerData.getDMin() != null) {
                    if (MerData.getDMax() != null) {
                        disCoun.setText(MerData.getDMin() + " - " + MerData.getDMax() + "%");
                    } else {
                        disCoun.setText(MerData.getDMin() + "%");
                    }
                }
                int numberOfColumns = 0;
                links.clear();
                if (MerData.getWhattsapp() != null) {
                    ModelSocialLinks mo = new ModelSocialLinks(MerData.getWhattsapp() + "", "wa");
                    links.add(mo);
                    numberOfColumns = numberOfColumns + 1;
                }
                if (MerData.getFacebook() != null) {
                    ModelSocialLinks mo = new ModelSocialLinks(MerData.getFacebook(), "fb");
                    links.add(mo);
                    numberOfColumns = numberOfColumns + 1;
                }

                if (MerData.getInsta() != null) {
                    ModelSocialLinks mo = new ModelSocialLinks(MerData.getInsta(), "ig");
                    links.add(mo);
                    numberOfColumns = numberOfColumns + 1;
                }

                if (MerData.getYoutube() != null) {
                    ModelSocialLinks mo = new ModelSocialLinks(MerData.getYoutube(), "yt");
                    links.add(mo);
                    numberOfColumns = numberOfColumns + 1;
                }
                if (MerData.getTwitter() != null) {
                    ModelSocialLinks mo = new ModelSocialLinks(MerData.getTwitter(), "tt");
                    links.add(mo);
                    numberOfColumns = numberOfColumns + 1;
                }

                // recyclerView.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns,2, false));
                if (numberOfColumns != 0) {
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext, numberOfColumns));
                    adapter = new RevAdapter_social_links(mContext, links);
                    adapter.setClickListener(MerViewOverViewFragment.this);
                    recyclerView.setAdapter(adapter);
                }

            }else {
                bNam.setText("No Data Found");
            }

        }

        @Override
        public void onFailure(Call<ModelMerchantList> call, Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
            call.cancel();
        }
    });

}

    //We are calling this method to check the permission status
    private boolean isReadCallNotAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return false;

        //If permission is not granted returning false
        return true;
    }

    //Requesting permission
    private void requestCallPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CALL_PHONE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},Call_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == Call_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(mContext,"Now you can call Merchants",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(mContext,"Permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

   /* public static Intent getOpenFacebookIntent(Context context,String url) {

      try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/"+url)); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/"+url)); //catches and opens a url to the desired page
        }

    }*/

    public static Intent newFacebookIntent(Context context, String url) {
        Uri uri = Uri.parse(url);
        PackageManager pm=context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    @Override
    public void onItemClick(View view, int position, String type) {
        if(type.equals("wa"))
        {
            btnWhatsapp();
        }
        else if(type.equals("fb"))
        {
            btnFb();
        }
        else if(type.equals("ig"))
        {
            btninsta();
        }
        else if(type.equals("yt"))
        {
            btnYouTube();
        }
        else if(type.equals("tt"))
        {
            btnTwitter();
        }
    }

    public void btnWhatsapp()
    {
        try {
            boolean installed = appInstalledOrNot("com.whatsapp");
            if(installed) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                "https://api.whatsapp.com/send?phone=+91" + MerData.getNumber() + "&text=I'm%20DiscountMania%20App%20User"
                        )));
            }
            else
            {
                Toast.makeText(mContext, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }

        } catch (Exception e) {
            Toast.makeText(mContext, "Unable to send please send manually"+MerData.getWhattsapp(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void btnFb()
    {
        try {
            Intent facebookIntent = newFacebookIntent(mContext, MerData.getFacebook());
            startActivity(facebookIntent);
        }catch (Exception e){
            Toast.makeText(mContext,"Unable to open facebook",Toast.LENGTH_LONG).show();
        }
    }

    public void btninsta()
    {
        Uri uri = Uri.parse("http://instagram.com/_u/"+MerData.getInsta());
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);
        insta.setPackage("com.instagram.android");
        try {
            boolean installed = appInstalledOrNot("com.instagram.android");
            if(installed) {
                startActivity(insta);
            }
            else
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MerData.getInsta())));
            }

        } catch (Exception e) {
            Toast.makeText(mContext, "Unable to open Profile", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void btnYouTube()
    {
        Intent intent=null;
        String url=MerData.getYoutube();
        if(url!=null && !url.isEmpty()) {
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                try {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);}catch (Exception k){Toast.makeText(mContext, "Page Not Found"+MerData.getNumber(), Toast.LENGTH_SHORT)
                        .show();}
            }
        }
    }
    public void btnTwitter()
    {
        String twitter_user_name=MerData.getTwitter();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitter_user_name)));
        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitter_user_name)));
        }
    }
}
