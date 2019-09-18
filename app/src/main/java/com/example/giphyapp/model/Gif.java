package com.example.giphyapp.model;

import androidx.databinding.BaseObservable;

public class Gif extends BaseObservable {
    private ImageData images;

    public ImageData getImages() {
        return images;
    }

    public void setImages(ImageData images) {
        this.images = images;
    }
}
