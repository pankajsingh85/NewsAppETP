package com.pankaj.newsapp.api;

import com.pankaj.newsapp.JSONResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    //for dashboard
    @GET("v2/top-headlines")
    Call<JSONResponse> getNews(@Query("country") String country, @Query("apiKey") String apiKey);

    //for fragment
    @GET("v2/top-headlines")
    Call<JSONResponse> getNews(@Query("country") String country,@Query("category") String category, @Query("apiKey") String apiKey);

    //for search view
    @GET("v2/everything")
    Call<JSONResponse> getSearchedNews(@Query("q") String country, @Query("apiKey") String apiKey);

}
