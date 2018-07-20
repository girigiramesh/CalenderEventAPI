package com.retrofit_recyclerview;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Hari Prasad on 2/4/17.
 */

public interface TrackervigilApi {
    String BASE_URL = "http://52.183.29.32";

    @GET("edunix/api/HolidayEvent")
    Call<String> getCalender(@Header("Authorization") String authorization, @Header("domain-name") String domain_name);

    //Header, Boby, Field, Query,
    //GET, POST, PUT, DELETE
    //CREATE, READ, UPDATE, DELETE
    @FormUrlEncoded
    @POST("edunix/TOKEN")
    Call<String> login(@Field("Content-Type") String titles,
                       @Field("grant_type") String grant_type,
                       @Field("username") String username,
                       @Field("password") String password,
                       @Field("domain-name") String domain_name);
}
