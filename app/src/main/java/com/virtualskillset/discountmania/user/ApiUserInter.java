package com.virtualskillset.discountmania.user;

import com.virtualskillset.discountmania.ModelLogin;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.retro.ModelMerBanner;
import com.virtualskillset.discountmania.merchant.retro.ModelSubC;
import com.virtualskillset.discountmania.user.model.ModelDistricts;
import com.virtualskillset.discountmania.user.model.ModelUserBestOffer;
import com.virtualskillset.discountmania.user.model.ModelUserData;
import com.virtualskillset.discountmania.user.model.ModelUserMerList;
import com.virtualskillset.discountmania.user.model.ModelUserResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiUserInter {
    @FormUrlEncoded
    @POST("api/client")
    Call<ModelUserResponse> getRegResponse(
            @Field("name") String name,
            @Field("number") String mobile,
            @Field("password") String password);
           /* @Field("role") String role,
            @Field("status") String status*/


    @POST("api/login")
    Call<ModelLogin> getLoginResponse(@Body ModelLogin login);


    @GET("api/merchant")
    Call <List<ModelMerchantList>> getMerchantList();

    @FormUrlEncoded
    @POST("api/merchant4user")
    Call <ModelUserMerList> getMerchantListUsr(@Field("category") String cat);

    @FormUrlEncoded
    @POST("api/merchant4user")
    Call <ModelUserMerList> getMerchantListUsrFilter(@Field("category") String cat,@Field("b_subCat") String bsub,@Field("state") String state,@Field("district") String dist);

    @GET("api/offer")
    Call <List<ModelUserBestOffer>> getBestOffer();

    @GET("api/client/{userId}")
    Call <ModelUserData> getUserDetails(@Path("userId") int userId);

    @GET("api/merchant/{userId}")
    Call <ModelMerchantList> getMerDetails(@Path("userId") int userId);

    @POST("api/client/{userId}")
    Call<ModelResponse> updateUserDetails(@Path("userId") int userId,@Body RequestBody file);

    @FormUrlEncoded
    @POST("api/subcat")
    Call<ModelSubC> getSubCatt(
            @Field("cat") String category
    );

   /* @GET("api/district")
    Call <ModelDistricts> getStateDistricts();*/

    @GET("api/state")
    Call <List<ModelDistricts>> getStateDistricts();

    @GET("api/banner/{userId}")
    Call <List<ModelMerBanner>> getMerBanner(@Path("userId") int userId);
}
