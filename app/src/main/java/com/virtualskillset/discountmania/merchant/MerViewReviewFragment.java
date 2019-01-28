package com.virtualskillset.discountmania.merchant;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_mer_review;
import com.virtualskillset.discountmania.merchant.merAdpter.RevAdapter_mer_view_review;
import com.virtualskillset.discountmania.merchant.retro.ModelMerReview;
import com.virtualskillset.discountmania.merchant.retro.NullOnEmptyConverterFactory;
import com.virtualskillset.discountmania.user.userHelper.GridSpacingItemDecoration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MerViewReviewFragment extends Fragment {
    private Context mContext;
   // private ProgressDialog progressDialog;
    ApiInterfaceMer apiIfc;
    private RatingBar ratingBar;
    private EditText review;
    private Button sub;
    private List<ModelMerReview.Response> reviews=new ArrayList<>();
    private int total=0;
    private double avg=0.0;
    private ProgressBar avgRat;
    private TextView tvAvg,tvAll,username,emptyView;
    User user;
    private RecyclerView recyclerView;
    private RevAdapter_mer_review adapter;

    // Initialise it from onAttach()
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public MerViewReviewFragment() {
        // Required empty public constructor
    }

    int[] gridViewImageId = {
            R.drawable.admin, R.drawable.admin
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mer_view_review, container, false);
        ratingBar=(RatingBar)view.findViewById(R.id.ratingBar);
        review=(EditText)view.findViewById(R.id.editText);
        sub=(Button)view.findViewById(R.id.Submit);
        ProgressBar _progressBar1 = (ProgressBar)view.findViewById (R.id.ratp);
        _progressBar1.setProgress( 4 );
        ((TextView) view.findViewById(R.id.textp)).setText("4"); // merchant Request
        avgRat=(ProgressBar)view.findViewById(R.id.ratp);
        tvAvg=(TextView)view.findViewById(R.id.textp);
        tvAll=(TextView)view.findViewById(R.id.ttl);
        username=(TextView)view.findViewById(R.id.usert);
        user = SharedPrefManager.getInstance(mContext).getUser();
        username.setText(user.getName());
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        emptyView=(TextView)view.findViewById(R.id.empty_view);
        getReview();


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!review.getText().toString().trim().isEmpty() && ratingBar.getRating()!=0) {
                    submitRev();
                }
                else if(review.getText().toString().trim().isEmpty()){
                    review.setError("Give Review");
                    review.requestFocus();
                }
                else
                {
                    Toast.makeText(mContext,"Give Ratings",Toast.LENGTH_LONG).show();
                }
            }

        });


        return view;
    }

    private void submitRev() {
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


       /* progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();*/

        Call<ModelResponse> call = apiIfc.submitReview(ratingBar.getRating(),review.getText().toString(),user.getId(),MerDetailsActivity.m_id);
        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, retrofit2.Response<ModelResponse> response) {
           //     progressDialog.dismiss();
                ModelResponse mdl=response.body();
                Toast.makeText(mContext, mdl.getResponseMessage(), Toast.LENGTH_SHORT).show();
                getReview();

            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
              // progressDialog.dismiss();
                Toast.makeText(mContext, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    private void getReview() {
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


       /* progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();*/

        final User user = SharedPrefManager.getInstance(mContext).getUser();
        Call<ModelMerReview> call = apiIfc.getReview(MerDetailsActivity.m_id);
        call.enqueue(new Callback<ModelMerReview>() {
            @Override
            public void onResponse(Call<ModelMerReview> call, retrofit2.Response<ModelMerReview> response) {
              //  progressDialog.dismiss();
                if(response.body()!=null) {
                    ModelMerReview mdl = response.body();
                    reviews.clear();
                    total = 0;
                    avg = 0.0;
                    for (int i = 0; i < mdl.getResponse().size(); i++) {
                        if (mdl.getResponse().get(i).getUid() == user.getId()) {
                            review.setText(mdl.getResponse().get(i).getDesc());
                            ratingBar.setRating(mdl.getResponse().get(i).getRating());
                        } else {
                            reviews.add(mdl.getResponse().get(i));
                        }
                        total = total + 1;
                        avg = avg + mdl.getResponse().get(i).getRating();
                    }

                    avg = avg / total;
                    if (total == 0) {
                        tvAvg.setText("");
                    } else {
                        tvAvg.setText(new DecimalFormat("##.#").format(avg));
                    }
                    tvAll.setText("Rating based on " + total + " reviews");
                    avgRat.setProgress((int) avg);

                    if (reviews.size() != 0) {
                        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 2, false));
                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
                        adapter = new RevAdapter_mer_review(mContext, reviews);
                        recyclerView.setAdapter(adapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ModelMerReview> call, Throwable t) {
           //     progressDialog.dismiss();
                Toast.makeText(mContext, "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

}
