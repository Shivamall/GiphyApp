package com.example.giphyapp.model;

import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.example.giphyapp.net.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GifList extends BaseObservable {
    private List<Gif> data = new ArrayList<>();
    private MutableLiveData<List<Gif>> gifList = new MutableLiveData<>();

    public MutableLiveData<List<Gif>> getGifList() {
        return gifList;
    }

    public void setGifList(MutableLiveData<List<Gif>> gifList) {
        this.gifList = gifList;
    }

    public List<Gif> getData() {
        return data;
    }

    public void setData(List<Gif> data) {
        this.data = data;
    }

    public void fetchList(String query, int offset) {

        Callback<GifList> callback = new Callback<GifList>() {
            @Override
            public void onResponse(Call<GifList> call, Response<GifList> response) {
                GifList body = response.body();
                gifList.setValue(body.data);
            }

            @Override
            public void onFailure(Call<GifList> call, Throwable t) {
                Log.e("Test", t.getMessage(), t);
            }
        };
        if (TextUtils.isEmpty(query)) {
            Api.getApi().getTrendingGif(Api.API_KEY, 30, offset).enqueue(callback);
        } else {
            Api.getApi().getGifByName(Api.API_KEY, query,30,offset).enqueue(callback);
        }
    }
}
