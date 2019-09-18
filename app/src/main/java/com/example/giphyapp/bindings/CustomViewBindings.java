package com.example.giphyapp.bindings;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;

public class CustomViewBindings {

    @BindingAdapter("imageUrl")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            Glide.with(imageView).load(imageUrl).into(imageView);
        }
    }
}
