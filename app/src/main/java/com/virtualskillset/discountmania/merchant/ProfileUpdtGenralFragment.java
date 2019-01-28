package com.virtualskillset.discountmania.merchant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.retro.ModelMerchantAdd;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.merchant.retro.ModelSubC;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.merchant.retro.SubCat;
import com.virtualskillset.discountmania.user.model.ModelDistricts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileUpdtGenralFragment extends Fragment {
    private TextView inputMobile;
    int PLACE_PICKER_REQUEST = 1,run=0;
    private Spinner spn1,spn2,spn_dis, spn_state;
    private String Category,SubCatageory,district, state;
    private Button btn_register, btn_loc;
    private EditText inputEmail, inputBusinessDes, inputWorkDays, inputWorkTime, inputStreetAdd, inputArea, inputPincode, inputPAN, inputGST, inputTIN, inputCIN, inputWhatsNO, inputFBlink, inputInstaLink, inputTwitterLink, inputYouTubeLink, inputShopName, inputOwnerName;
    private double lat, lng;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private ApiInterfaceMer uploadAPIService;
    private ProgressDialog progressDialog;
    private CheckBox tnc;
    private EditText disMin, disMax;
    private TextView tncc;
    private LatLng goglLatLng;
    private Context ctx;
    private ModelMerchantList modelData;
    private ArrayList<String> subcat=new ArrayList<>();
    ArrayAdapter<String> subadp,mainCat ;
    private List<ModelDistricts> modelDist;
    private ArrayAdapter<String> spnAdp_state,spnAdp_dis;
    private ArrayList<String>arr_state=new ArrayList<>(),arr_dis=new ArrayList<>();


    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    public ProfileUpdtGenralFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_updt_genral, container, false);
        spn_dis = (Spinner) v.findViewById(R.id.spinner4);
        spn_state = (Spinner) v.findViewById(R.id.spinner3);
        btn_register = (Button) v.findViewById(R.id.btnRegister2);
        btn_loc = (Button) v.findViewById(R.id.loc);
        inputShopName = (EditText) v.findViewById(R.id.sh_name);
        inputOwnerName = (EditText) v.findViewById(R.id.own_name);
        inputMobile = (TextView) v.findViewById(R.id.mob);
        inputEmail = (EditText) v.findViewById(R.id.mail);
        inputBusinessDes = (EditText) v.findViewById(R.id.business_des);
        inputWorkDays = (EditText) v.findViewById(R.id.open);
        inputWorkTime = (EditText) v.findViewById(R.id.close);
        inputStreetAdd = (EditText) v.findViewById(R.id.street);
        inputArea = (EditText) v.findViewById(R.id.area);
        inputPincode = (EditText) v.findViewById(R.id.pincode);
        inputPAN = (EditText) v.findViewById(R.id.pan);
        inputGST = (EditText) v.findViewById(R.id.gst);
        inputTIN = (EditText) v.findViewById(R.id.tin);
        inputCIN = (EditText) v.findViewById(R.id.cin);
        inputWhatsNO = (EditText) v.findViewById(R.id.whtnm);
        inputFBlink = (EditText) v.findViewById(R.id.face);
        inputInstaLink = (EditText) v.findViewById(R.id.instagram);
        inputTwitterLink = (EditText) v.findViewById(R.id.twitter);
        inputYouTubeLink = (EditText) v.findViewById(R.id.youtube);
        tnc = (CheckBox) v.findViewById(R.id.checkBox);
        disMax = (EditText) v.findViewById(R.id.disMax);
        disMin = (EditText) v.findViewById(R.id.disMin);
        tncc = (TextView) v.findViewById(R.id.tncc);
        spn1=(Spinner)v.findViewById(R.id.spinner1);
        spn2=(Spinner)v.findViewById(R.id.spinner2);


        progressDialog = new ProgressDialog(ctx);
        progressDialog.setCancelable(false);
        init();
        getdata();

        /////////////////////category spinner//////////////
        mainCat = new ArrayAdapter<String>(ctx,
                R.layout.spinner_item,
                getResources().getStringArray(R.array.category));
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn1.setAdapter(mainCat);
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category=getResources().getStringArray(R.array.category)[position];
                //GetSubCat(Category);
               if(run==0)
                {
                    run=1;
                }else { getSubCat();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////////////sub cat spinner///////
        subadp = new ArrayAdapter<String>(ctx,
                R.layout.spinner_item, subcat);
        //myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spn2.setAdapter(subadp);
        spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    SubCatageory=subcat.get(position);
                }
                else {
                    SubCatageory="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* /////////////distict Spinner////////////
        ArrayAdapter<String> disadp = new ArrayAdapter<String>(ctx,
                R.layout.spinner_item,
                getResources().getStringArray(R.array.district));
        //myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dis.setAdapter(disadp);
        spn_dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district = getResources().getStringArray(R.array.district)[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////State Spinner////////////
        ArrayAdapter<String> steadp = new ArrayAdapter<String>(ctx,
                R.layout.spinner_item,
                getResources().getStringArray(R.array.state));
       // myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_state.setAdapter(steadp);
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = getResources().getStringArray(R.array.state)[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/ /////////////////////category state//////////////
        spnAdp_state = new ArrayAdapter<String>(ctx,
                R.layout.spinner_item,
                arr_state);
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn_state.setAdapter(spnAdp_state);
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = arr_state.get(position);
                arr_dis.clear();
                for (int i = 0; i < modelDist.get(position).getDistricts().size(); i++) {
                    arr_dis.add(modelDist.get(position).getDistricts().get(i).getDistrictname());
                }
                spnAdp_dis.notifyDataSetChanged();

                if (modelData.getDistrict()!=null)
                {
                    int spn_dt=spnAdp_dis.getPosition(modelData.getDistrict());
                    spn_dis.setSelection(spn_dt);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////distict Spinner////////////
        spnAdp_dis = new ArrayAdapter<String>(ctx,
                R.layout.spinner_item,
                arr_dis);
        // myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spn_dis.setAdapter(spnAdp_dis);
        spn_dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district=arr_dis.get(position);

                // getSubCat();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tncc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Light_Dialog);
                } else {
                    builder = new AlertDialog.Builder(ctx);
                }
                builder.setTitle("Terms & Conditions")
                        .setMessage("*I confirm that i will provide the offers, discounts to the App users in case i refuse to give such schemes then Discount Mania Team will take legal action on me.")
                        .setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.dismiss();
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String disMN = disMin.getText().toString().trim();


                                    if (tnc.isChecked() && !disMN.isEmpty()) {
                                        uploadForm();
                                    } else if(!tnc.isChecked()) {
                                        Toast.makeText(ctx, "Accept terms & conditions", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        disMin.setError("Please enter Min Discount");
                                        disMin.requestFocus();
                                      }
                }

        });


        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    try {
                        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                    } catch (Exception e) { }
            }
        });

        getdist();
        return v;
    }
    public int subCatListValue(String str)
    {
        int pos=0;
        try {
            for (int i = 0; i < subcat.size(); i++) {
                if (subcat.get(i).contains(str)) {
                    pos = i;
                }
            }
        }catch(Exception e){pos=0;}
        return pos;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, ctx);
                goglLatLng = place.getLatLng();
                lat = goglLatLng.latitude;
                lng = goglLatLng.longitude;
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(ctx, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void init() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //  OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);

    }

    private void uploadForm() {
        if(progressDialog.isShowing())
        {
            progressDialog.setMessage("Please Wait...");
        }else {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        User user = SharedPrefManager.getInstance(ctx).getUser();
        int mid=0;
        if(!user.getRole().equals("1"))
        {
            mid=MerchantUpdatePfActivity.m_id;
        }
        else {
            mid=user.getId();
        }

        //MultipartBody requestBody = builder.build();

        final ModelMerchantAdd mer = new ModelMerchantAdd(inputOwnerName.getText().toString(),inputShopName.getText().toString(),inputStreetAdd.getText().toString(), inputArea.getText().toString(), inputPincode.getText().toString(), district, state, inputEmail.getText().toString(), inputWhatsNO.getText().toString(),Category,SubCatageory, inputPAN.getText().toString(), inputTIN.getText().toString(), inputCIN.getText().toString(), inputGST.getText().toString(), inputBusinessDes.getText().toString(), inputFBlink.getText().toString(), inputTwitterLink.getText().toString(), inputInstaLink.getText().toString(), inputYouTubeLink.getText().toString(), inputWorkDays.getText().toString(), inputWorkTime.getText().toString(), String.valueOf(lat), String.valueOf(lng), disMin.getText().toString(), disMax.getText().toString(),"PUT");
        Call<ModelMerchantAdd> call = uploadAPIService.updtMerchant(mid,mer);
        call.enqueue(new Callback<ModelMerchantAdd>() {
            @Override
            public void onResponse(Call<ModelMerchantAdd> call, retrofit2.Response<ModelMerchantAdd> response) {
                progressDialog.dismiss();
                final ModelMerchantAdd loginResponse = response.body();
                if (loginResponse != null) {


                    //boolean responseCode = loginResponse.getResponseCode();

                    String resp = response.body().getResponseMessage();


                    Toast.makeText(ctx, resp, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelMerchantAdd> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ctx, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

   /* private boolean checkAndRequestPermissions() {


        int loc = ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(ctx,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
*/
    private void getdata() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);

        if(progressDialog.isShowing())
        {
        progressDialog.setMessage("Preparing data...");}
        else {
            progressDialog.setMessage("Preparing data...");
            progressDialog.show();
        }
        User user = SharedPrefManager.getInstance(ctx).getUser();
        int mid=0;
        if(!user.getRole().equals("1"))
        {
            mid=MerchantUpdatePfActivity.m_id;
        }
        else {
            mid=user.getId();
        }
        Call<ModelMerchantList> call = uploadAPIService.getMerchantDetails(mid);
        call.enqueue(new Callback<ModelMerchantList>() {
            @Override
            public void onResponse(Call<ModelMerchantList> call, retrofit2.Response<ModelMerchantList> response) {
                progressDialog.dismiss();
                modelData = response.body();

                if (modelData != null) {
                    inputShopName.setText(modelData.getBName());
                    inputOwnerName.setText(modelData.getName());
                    inputMobile.setText(modelData.getNumber()+"");
                    inputEmail.setText(modelData.getEmail());
                    inputBusinessDes.setText(modelData.getBDesc());
                    inputWorkDays.setText(modelData.getDay());
                    inputWorkTime .setText(modelData.getTime());
                    inputStreetAdd .setText(modelData.getStreet());
                    inputArea.setText(modelData.getLocality());
                    inputPincode.setText(modelData.getPin());
                    inputPAN .setText(modelData.getPan());
                    inputGST .setText(modelData.getGst());
                    inputTIN.setText(modelData.getTin());
                    inputCIN .setText(modelData.getCin());
                    inputWhatsNO.setText(modelData.getWhattsapp());
                    inputFBlink .setText(modelData.getFacebook());
                    inputInstaLink .setText(modelData.getInsta());
                    inputTwitterLink .setText(modelData.getTwitter());
                    inputYouTubeLink .setText(modelData.getYoutube());
                    disMax .setText(modelData.getDMax());
                    disMin .setText(modelData.getDMin());
                    int spinnerPosition = mainCat.getPosition(modelData.getBCat());
                   // int subCatPos=subadp.getPosition(modelData.getBSubCat());
//set the default according to value
                    spn1.setSelection(spinnerPosition);
                }
                else {
                    Toast.makeText(ctx, response.message() + " Error Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelMerchantList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ctx, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    private void getSubCat() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder() .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);

        if(progressDialog.isShowing())
        {
           progressDialog.setMessage("Preparing Category...");
        }
        else {
            progressDialog.setMessage("Preparing Category..."); progressDialog.show();
        }


        Call<ModelSubC> call = uploadAPIService.getSubCatt(Category);
        call.enqueue(new Callback<ModelSubC>() {
            @Override
            public void onResponse(Call<ModelSubC> call, retrofit2.Response<ModelSubC> response) {
               progressDialog.dismiss();
                ModelSubC loginResponse = response.body();

                if (loginResponse != null) {

                    List<SubCat> lst=loginResponse.getSubCat();
                    subcat.clear();
                    subcat.add("Default");
                    for (int i=0;i<lst.size();i++)
                    {
                        subcat.add(lst.get(i).getSubCat());
                    }
                    subadp.notifyDataSetChanged();
                    if(modelData.getBSubCat()!=null) {
                        int subCatPos = subCatListValue(modelData.getBSubCat());
                        spn2.setSelection(subCatPos);
                    }
                }
                else {
                    Toast.makeText(ctx, response.message()+" Error Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelSubC> call, Throwable t) {
               progressDialog.dismiss();
                Toast.makeText(ctx, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    private void getdist() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterfaceMer.class);

        if(progressDialog.isShowing())
        {
            progressDialog.setMessage("Please wait...");}
        else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        //User user = SharedPrefManager.getInstance(AUserFilters.this).getUser();

        Call<List<ModelDistricts>> call = uploadAPIService.getStateDistricts();
        call.enqueue(new Callback<List<ModelDistricts>>() {
            @Override
            public void onResponse(Call<List<ModelDistricts>> call, retrofit2.Response<List<ModelDistricts>> response) {
                progressDialog.dismiss();
                modelDist = response.body();

                if (modelData != null) {
                    for(int i=0;i<modelDist.size();i++)
                    {
                        arr_state.add(modelDist.get(i).getState());
                    }
                    spnAdp_state.notifyDataSetChanged();

                    if(modelData.getState()!=null)
                    {
                        int spn_st=spnAdp_state.getPosition(modelData.getState());
                        spn_state.setSelection(spn_st);
                    }
                }
                else {
                    Toast.makeText(ctx, response.message() + " Error Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelDistricts>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ctx, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


}
