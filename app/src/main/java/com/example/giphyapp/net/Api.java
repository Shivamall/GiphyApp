package com.example.giphyapp.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit api;
    private static final String BASE_URL = "https://api.giphy.com/v1/gifs/";
    public static final String API_KEY = "iRI9MIrPGnrpRvpYkgL8LHxhLVNlWxVv";
    public static ApiInterface getApi() {
        if (api == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.MINUTES)
                    .writeTimeout(20,TimeUnit.MINUTES)
                    .readTimeout(20,TimeUnit.MINUTES)
                    .build();

            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .client(okHttpClient)
                    .build();

        }

        return api.create(ApiInterface.class);
    }

}
