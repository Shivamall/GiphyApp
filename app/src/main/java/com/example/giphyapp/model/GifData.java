package com.example.giphyapp.model;

import androidx.databinding.BaseObservable;

public class GifData extends BaseObservable {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
