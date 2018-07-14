package com.retrofit_recyclerview;

        import retrofit2.Call;
        import retrofit2.http.GET;
        import retrofit2.http.Header;

/**
 * Created by Hari Prasad on 2/4/17.
 */

public interface TrackervigilApi {
    String BASE_URL = "http://52.183.29.32";

    @GET("edunix/api/HolidayEvent")
    Call<String> getCalender(@Header("Authorization") String authorization, @Header("domain-name") String domain_name);
}
