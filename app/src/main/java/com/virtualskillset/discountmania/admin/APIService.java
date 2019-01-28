package com.virtualskillset.discountmania.admin;

import com.google.gson.JsonObject;
import com.virtualskillset.discountmania.admin.retro.Model;
import com.virtualskillset.discountmania.admin.retro.ModelAdminDashb;
import com.virtualskillset.discountmania.admin.retro.ModelMerchantAdd;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.merchant.retro.ModelMerBanner;
import com.virtualskillset.discountmania.merchant.retro.ModelMerProduct;
import com.virtualskillset.discountmania.merchant.retro.ModelSubC;
import com.virtualskillset.discountmania.subadmin.helperSubAdmin.ModelSubAdList;
import com.virtualskillset.discountmania.user.model.ModelDistricts;
import com.virtualskillset.discountmania.user.model.ModelUserData;

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

interface APIService {
    /*@Multipart
    @POST("/upload_multi_files/MultiUpload.php")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);*/
    @Multipart
    @POST("api/cat")
    /*Call<Model> uploadCategory(
            @Part("cat") RequestBody category,
            @Part("subCat") RequestBody subCategory);*/
    Call<Model> insertUser(
            @Part("cat") String category,
            @Part("subCat") String subCategory);

    @POST("api/cat")
    Call<Model> createCategory(@Body Model login);


    @POST("api/merchant")
    Call<ModelMerchantAdd> createMerchantFile(@Body RequestBody file);

    @POST("api/merchant")
    Call<ModelMerchantAdd> createMerchant(@Body ModelMerchantAdd mer);



    @Multipart
    @POST("/immigration/api/updateProfile")
    Call<Model> uploadSingleFile(
            @Header("accessToken") String accessToken,
            @Part MultipartBody.Part file,
            @Part("firstName") RequestBody firstName,
            @Part("lastName") RequestBody lastName,
            @Part("contact") RequestBody contact,
            @Part("countryCode") RequestBody countryCode);

    @Multipart
    //@POST("/upload_multi_files/MultiUpload.php")
    @POST("/upload_multi_files/MultiPartUpload.php")
    Call<Model> uploadMultiFile(@Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3);

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


    @POST("api/merchant/{userId}")
    Call<ModelResponse> uploadMerchantProducts(@Path("userId") String userId,
            // @Header("access_token") String accessToken,
            @Body RequestBody file);


    @POST("api/merchant/{userId}")
    Call<ModelResponse> uploadMerchantBanner(@Path("userId") String userId,
                                               // @Header("access_token") String accessToken,
                                               @Body RequestBody file);

    @FormUrlEncoded
    @POST("api/subcat")
    Call<ModelSubC> getSubCatt(
            @Field("cat") String category
    );

    @GET("api/merchant")
    Call <List<ModelMerchantList>> getMerchantList();

    @GET("api/merchant/{userId}")
    Call <ModelMerchantList> getMerchantDetails(@Path("userId") int userId);

    @GET("api/dashboard")
    Call <ModelAdminDashb> getMAdminDash();

    @FormUrlEncoded
    @POST("api/merchantStatus")
    Call<ModelResponse> merAprove(
            @Field("approved") int aprv,
            @Field("id") int id);

    @FormUrlEncoded
    @POST("api/merchantStatus")
    Call<ModelResponse> merPaid(
            @Field("paid") int aprv,
            @Field("id") int id);

    @FormUrlEncoded
    @POST("api/merchantStatus")
    Call<ModelResponse> merBlock(
            @Field("status") int aprv,
            @Field("id") int id);


    @POST("api/merchant/{userId}")
    Call<ModelResponse> updtMerchantImage(@Path("userId") int userId,@Body RequestBody file);

    ////////////////////////banner///////////////
    @POST("api/banner")
    Call<ModelResponse> addMerchantBanner(@Body RequestBody file);


    @GET("api/banner/{userId}")
    Call <List<ModelMerBanner>> getMerBanner(@Path("userId") int userId);

    @FormUrlEncoded
    @POST("api/banner/{userId}")
    Call<ModelResponse> MerDeleteBanner(@Path("userId") String userId, @Field("_method") String mthd);

    ////////////////////////////products///////////////////////////////////
    @POST("api/product")
    Call<ModelResponse> addMerchantProduct(@Body RequestBody file);


    @GET("api/product/{userId}")
    Call <List<ModelMerProduct>> getMerProduct(@Path("userId") int userId);

    @FormUrlEncoded
    @POST("api/product/{userId}")
    Call<ModelResponse> MerDeleteProduct(@Path("userId") String userId, @Field("_method") String mthd);

    @GET("api/admin")
    Call <List<ModelSubAdList>> getSubAdList();

    @GET("api/client")
    Call <List<ModelUserData>> getUserList();

    @GET("api/district")
    Call <List<ModelDistricts>> getStateDistricts();
}
