package com.virtualskillset.discountmania;

import com.virtualskillset.discountmania.helper.model.ModelResponse;
import com.virtualskillset.discountmania.helper.model.ModelVersion;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

interface ApiInterface {

    @FormUrlEncoded
    @POST("api/password")
    Call<ModelResponse> getResetResp(
            @Field("number") String mobile,
            @Field("password") String password);


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

    @POST("api/login")
    Call<ModelLogin> getLoginResponse(@Body ModelLogin login);

    @FormUrlEncoded
    @POST("api/version")
    Call<ModelVersion> getVersion(@Field("id") int id );

}
