package com.virtualskillset.discountmania.user;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.helper.SharedPrefManager;
import com.virtualskillset.discountmania.helper.URLs;
import com.virtualskillset.discountmania.helper.User;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.user.model.ModelUserData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AUserDigitalCard extends AppCompatActivity {
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    private ImageView iv;
    private TextView text;
    String mob;
    private TextView name,expDate,validthru,mobb;
    ApiUserInter apiInterface;
    private ProgressDialog progressDialog;
    private ModelUserData MerData;
    private ModelMerchantList MerDataa;
    private CardView card1,card2,card3;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usser_digital_card);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}

        iv = (ImageView) findViewById(R.id.iv);
        //t = (TextView) findViewById(R.id.textView7);
        text=(TextView)findViewById(R.id.textView);
        user = SharedPrefManager.getInstance(AUserDigitalCard.this).getUser();
        mob=user.getNumber();
        name=(TextView)findViewById(R.id.name);
        expDate=(TextView)findViewById(R.id.textView9);
        mobb=(TextView)findViewById(R.id.num);
        card1=(CardView)findViewById(R.id.card1);
        card2=(CardView)findViewById(R.id.card2);
        card3=(CardView)findViewById(R.id.card3);
        //validthru=(TextView)findViewById(R.id.);
        if(user.getRole().equals("1")) { getMerDetail(); }
        else {getUsrDetail();}


    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {

        BitMatrix bitMatrix;

        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,

                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {

            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorAccent):getResources().getColor(R.color.white);
            }

        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);

        return bitmap;
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

    private void getUsrDetail() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        apiInterface = new Retrofit.Builder()
                .baseUrl(URLs.URL_Main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUserInter.class);


        progressDialog = new ProgressDialog(AUserDigitalCard.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<ModelUserData> call = apiInterface.getUserDetails(user.getId());
        call.enqueue(new Callback<ModelUserData>() {
            @Override
            public void onResponse(Call<ModelUserData> call, retrofit2.Response<ModelUserData> response) {
                MerData=response.body();
                if(response.body()!=null) {
                    name.setText(user.getName());
                    mobb.setText(user.getNumber());
                    if (MerData.getPaid() == 1) {
                        card1.setVisibility(View.VISIBLE);
                        card2.setVisibility(View.VISIBLE);
                        card3.setVisibility(View.GONE);
                        String inputDateStr = MerData.getExpiry();
                        try {
                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = inputFormat.parse(inputDateStr);
                            String outputDateStr = outputFormat.format(date);
                            expDate.setText(outputDateStr);
                        } catch (Exception e) {
                            expDate.setText(MerData.getExpiry());
                        }
                        try {
                            text.setVisibility(View.GONE);
                            bitmap = TextToImageEncode(mob);
                            iv.setImageBitmap(bitmap);

                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        card3.setVisibility(View.VISIBLE);
                    }
                    // expDate.setText(MerData.getExpiry());
                }else {
                    Toast.makeText(getApplicationContext(), "Unknown Error, Contact Support Team ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelUserData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

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
                .create(ApiUserInter.class);


        progressDialog = new ProgressDialog(AUserDigitalCard.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Call<ModelMerchantList> call = apiInterface.getMerDetails(user.getId());
        call.enqueue(new Callback<ModelMerchantList>() {
            @Override
            public void onResponse(Call<ModelMerchantList> call, retrofit2.Response<ModelMerchantList> response) {
                MerDataa=response.body();
                name.setText(user.getName());
                mobb.setText(user.getNumber());
                if(MerDataa.getPaid()==1)
                {
                    card1.setVisibility(View.VISIBLE);
                    //card2.setVisibility(View.VISIBLE);
                    card3.setVisibility(View.GONE);
                    String inputDateStr = MerDataa.getExpiry();
                    try {
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = inputFormat.parse(inputDateStr);
                        String outputDateStr = outputFormat.format(date);
                        expDate.setText(outputDateStr);
                    }catch (Exception e){expDate.setText(MerDataa.getExpiry());}
                    /*try {
                        text.setVisibility(View.GONE);
                        bitmap = TextToImageEncode(mob);
                        iv.setImageBitmap(bitmap);

                    } catch (WriterException e) { e.printStackTrace(); }*/
                    progressDialog.dismiss();
                }
                else {
                    progressDialog.dismiss();
                    card3.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onFailure(Call<ModelMerchantList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Unable to connect please try later", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }


}

