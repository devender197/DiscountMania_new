package com.virtualskillset.discountmania.merchant;

import com.google.gson.JsonObject;
import com.virtualskillset.discountmania.admin.retro.ModelMerchantAdd;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.retro.ModelMerBanner;
import com.virtualskillset.discountmania.merchant.retro.ModelMerCusVerify;
import com.virtualskillset.discountmania.merchant.retro.ModelMerDash;
import com.virtualskillset.discountmania.merchant.retro.ModelMerOffer;
import com.virtualskillset.discountmania.merchant.retro.ModelMerProduct;
import com.virtualskillset.discountmania.merchant.retro.ModelMerReview;
import com.virtualskillset.discountmania.merchant.retro.ModelMerTrans;
import com.virtualskillset.discountmania.merchant.retro.ModelMerchant;
import com.virtualskillset.discountmania.merchant.retro.ModelProducts;
import com.virtualskillset.discountmania.merchant.retro.ModelSubC;
import com.virtualskillset.discountmania.user.model.ModelDistricts;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

interface ApiInterfaceMer {

    @POST("api/subcat")
    Call<ModelMerchant> getSubCatpt(@Body ModelMerchant logi);

    @FormUrlEncoded
    @POST("api/subcat")
    Call<ModelSubC> getSubCatt(
            @Field("cat") String category
    );

    @POST("api/merchant")
    Call<ModelMerchant> createMerchant(@Body ModelMerchant mer);



    @Multipart
    @POST("/immigration/api/updateProfile")
    Call<ModelMerchant> uploadSingleFile(
            @Header("accessToken") String accessToken,
            @Part MultipartBody.Part file,
            @Part("firstName") RequestBody firstName,
            @Part("lastName") RequestBody lastName,
            @Part("contact") RequestBody contact,
            @Part("countryCode") RequestBody countryCode);

    @Multipart
    //@POST("/upload_multi_files/MultiUpload.php")
    @POST("/upload_multi_files/MultiPartUpload.php")
    Call<ModelMerchant> uploadMultiFile(@Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3);

    //@Multipart
    //@FormUrlEncoded
    //@POST("/upload_multi_files/MultiUpload.php")
    @POST("api/admin")
    Call<ModelResponse> uploadMultiFile(
            // @Header("access_token") String accessToken,
            @Body RequestBody file);

    @FormUrlEncoded
    @PUT("api/admin")
    Call<ResponseBody> getStatus(
            @Field("name") String name,
            @Field("number") String mobile,
            @Field("password") String password,
            @Field("role") String role,
            @Field("status") String status);
            /*@Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("fbID") String fbID,
            @Field("gmailID") String gmailID,
            @Field("twitID") String twitID,
            @Field("gender") String gender,
            @Field("birthDate") String birthDate,
            @Field("location") String location,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("profileImage") String profileImage);*/
    //@Field parameters can only be used with form encoding. (parameter #2)

    @GET("api/merchant")
    Call<JsonObject> getMerchantResponse(@Path("userid") String userId);

    @GET("api/merchant/{userId}")
    Call <ModelMerchantList> getMerchantDetails(@Path("userId") int userId);

    @GET("api/product/{userId}")
    Call <List<ModelProducts>> getMerchantProducts(@Path("userId") String userId);

    ///////////////old/////////
   /* @POST("api/merchant/{userId}")
    Call<ModelResponse> uploadMerchantOffer(@Path("userId") String userId,
                                               // @Header("access_token") String accessToken,
                                               @Body RequestBody file);*/
   //////////////////////////////offers..../////////////////////////

    @POST("api/offer")
    Call<ModelResponse> uploadMerchantOffer(@Body RequestBody file);


    @GET("api/offer/{userId}")
    Call <List<ModelMerOffer>> getMerOffer(@Path("userId") int userId);

    @FormUrlEncoded
    @POST("api/offer/{userId}")
    Call<ModelResponse> MerDeleteOffer(@Path("userId") String userId, @Field("_method") String mthd);

    ////////////////////////////products///////////////////////////////////
    @POST("api/product")
    Call<ModelResponse> addMerchantProduct(@Body RequestBody file);


    @GET("api/product/{userId}")
    Call <List<ModelMerProduct>> getMerProduct(@Path("userId") int userId);

    @FormUrlEncoded
    @POST("api/product/{userId}")
    Call<ModelResponse> MerDeleteProduct(@Path("userId") String userId, @Field("_method") String mthd);


    ////////////////////////banner///////////////
    @POST("api/banner")
    Call<ModelResponse> addMerchantBanner(@Body RequestBody file);


    @GET("api/banner/{userId}")
    Call <List<ModelMerBanner>> getMerBanner(@Path("userId") int userId);

    @FormUrlEncoded
    @POST("api/banner/{userId}")
    Call<ModelResponse> MerDeleteBanner(@Path("userId") String userId, @Field("_method") String mthd);

////////////////////////////dashboard mer//////////////
    @FormUrlEncoded
    @POST("api/dashMerchant")
    Call<ModelMerDash> getMerDash(@Field("id") String id);


   /* @POST("api/merchant/{userId}")
    Call<ModelResponse> uploadMerchantProducts(@Path("userId") String userId,
                                               // @Header("access_token") String accessToken,
                                               @Body RequestBody file);*/


    @POST("api/product")
    Call<ModelResponse> uploadMerchantProducts(// @Header("access_token") String accessToken,
                                               @Body RequestBody file);


    @POST("api/merchant/{userId}")
    Call<ModelResponse> uploadMerchantBanner(@Path("userId") String userId,
                                             // @Header("access_token") String accessToken,
                                             @Body RequestBody file);

    @POST("api/merchant/{userId}")
    Call<ModelMerchantAdd> updtMerchant(@Path("userId") int userId,@Body ModelMerchantAdd mer);

    @POST("api/merchant/{userId}")
    Call<ModelResponse> updtMerchantImage(@Path("userId") int userId,@Body RequestBody file);

    @FormUrlEncoded
    @POST("api/scanned")
    Call<ModelMerCusVerify>  veriCus(@Field("number") String mob);

    @FormUrlEncoded
    @POST("api/transaction")
    Call<ModelResponse>  submitTranscation(@Field("discount") String dis,@Field("amount") String amt,@Field("uid") String uid,@Field("mid") String mid,@Field("mrp") int mrp);

    @FormUrlEncoded
    @POST("api/transaction-m")
    Call<ModelMerTrans>  merTrans(@Field("mid") int id);

    @FormUrlEncoded
    @POST("api/transaction-u")
    Call<ModelMerTrans>  usrTrans(@Field("uid") int id);

    @FormUrlEncoded
    @POST("api/review")
    Call<ModelResponse>  submitReview(@Field("rating") float rt,@Field("desc") String des ,@Field("uid") int uid,@Field("mid") int mid);

    @FormUrlEncoded
    @POST("api/review-m")
    Call<ModelMerReview>  getReview(@Field("mid") int id);

    @GET("api/district")
    Call <List<ModelDistricts>>  getStateDistricts();
}
