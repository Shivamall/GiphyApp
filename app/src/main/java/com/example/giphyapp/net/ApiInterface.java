package com.example.giphyapp.net;

import com.example.giphyapp.model.GifList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("trending")
    Call<GifList> getTrendingGif(@Query("api_key") String apiKey, @Query("limit") int limit, @Query("offset") int offset);

    @GET("search")
    Call<GifList> getGifByName(@Query("api_key") String apiKey, @Query("q") String name, @Query("limit") int limit, @Query("offset") int offset);
}
