package com.virtualskillset.discountmania.admin;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.retro.Model;
import com.virtualskillset.discountmania.helper.URLs;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends Fragment {
    private APIService uploadAPIService;
    private ProgressDialog progressDialog;
    private Spinner spn;
    private Button submit;
    private EditText sub;
    private ProgressDialog pDialog;
    private String str,subCt;

    public AddCategoryFragment() {
        // Required empty public constructor
    }

    public static AddCategoryFragment newInstance() {
        AddCategoryFragment fragment = new AddCategoryFragment();
        return fragment;
    }

    private Context mContext;

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_category, container, false);
        try{
            getActivity().setTitle("Add Sub Category");}catch (NullPointerException e){};

        spn=(Spinner)v.findViewById(R.id.spinner2);
        submit=(Button)v.findViewById(R.id.button);
        sub=(EditText)v.findViewById(R.id.sub);
        pDialog = new ProgressDialog(mContext);
        pDialog.setCancelable(false);
        init();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(mContext,
                R.layout.spinner_item,
                getResources().getStringArray(R.array.category));
       // myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(myAdapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str=getResources().getStringArray(R.array.category)[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sub.getText().toString().trim().isEmpty())
                {
                    sub.setError("Please enter sub Category");
                    sub.requestFocus();
                   // Toast.makeText(mContext,"Enter the sub category",Toast.LENGTH_LONG).show();
                }
                else {
                    String message = sub.getText().toString();
                    message = Character.toUpperCase(message.charAt(0)) + message.substring(1);
                    subCt=message;
                    //submitcat(str,message );
                    uploadForm();
                }
            }
        });

        return v;

    }
    private void init(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

       /* OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build();
*/
        uploadAPIService = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
    }

    private void uploadForm() {
        progressDialog.show();


       // builder.addFormDataPart("pic", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));


        //MultipartBody requestBody = builder.build();
        final Model login = new Model(str,subCt);
        Call<Model> call = uploadAPIService.createCategory(login);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                progressDialog.dismiss();
                Model loginResponse = response.body();
                if (loginResponse != null) {


                    String responseCode = loginResponse.getResponseCode();
                    if (responseCode != null && responseCode.equals("404")) {
                        Toast.makeText(mContext, "Invalid Details \n Please try again", Toast.LENGTH_SHORT).show();
                    } else {
                        String resp=response.body().getResponseMessage();
                        Toast.makeText(mContext, resp, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


}
