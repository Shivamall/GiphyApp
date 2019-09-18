package com.example.giphyapp.model;

import androidx.databinding.BaseObservable;

public class ImageData extends BaseObservable {
    private GifData original;

    public GifData getOriginal() {
        return original;
    }

    public void setOriginal(GifData original) {
        this.original = original;
    }
}
