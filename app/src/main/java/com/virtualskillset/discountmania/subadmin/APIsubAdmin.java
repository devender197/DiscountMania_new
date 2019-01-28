package com.virtualskillset.discountmania.subadmin;

import com.virtualskillset.discountmania.admin.retro.ModelMerchantAdd;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.merchant.retro.ModelSubC;
import com.virtualskillset.discountmania.subadmin.helperSubAdmin.ModelSubAdmin;
import com.virtualskillset.discountmania.user.model.ModelDistricts;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface APIsubAdmin {
    @FormUrlEncoded
    @POST("api/dashSubAdmin")
    Call<ModelSubAdmin> getSbAdminDash(@Field("number") String id);

    @POST("api/merchant")
    Call<ModelMerchantAdd> createMerchant(@Body ModelMerchantAdd mer);

    @POST("api/merchant")
    Call<ModelMerchantAdd> createMerchantFile(@Body RequestBody file);

    @FormUrlEncoded
    @POST("api/subcat")
    Call<ModelSubC> getSubCatt(
            @Field("cat") String category
    );

    @GET("api/district")
    Call <List<ModelDistricts>>  getStateDistricts();

    @FormUrlEncoded
    @POST("api/subAdminMerchants")
    Call <List<ModelMerchantList>> getMerchantList(@Field("number") String id);
}
