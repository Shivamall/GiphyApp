package com.example.giphyapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giphyapp.BR;
import com.example.giphyapp.R;
import com.example.giphyapp.databinding.TrendingGifBinding;
import com.example.giphyapp.model.Gif;
import com.example.giphyapp.viewmodel.GifListViewModel;

import java.util.List;

public class GifAdapter  extends RecyclerView.Adapter<GifAdapter.GifViewHolder>{
    private List<Gif> mList;
    private GifListViewModel viewModel;
    public GifAdapter(GifListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setList(List<Gif> gifList) {
        mList = gifList;
        notifyDataSetChanged();
    }

//    Appending Further 30 gifs on recycler
    public void appendList(List<Gif> gifList) {
        mList.addAll(gifList);
        notifyDataSetChanged();
    }

    @NonNull
    public GifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TrendingGifBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.trending_gif, parent, false);
        return new GifViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GifViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class GifViewHolder extends RecyclerView.ViewHolder {
        final TrendingGifBinding binding;

        GifViewHolder(TrendingGifBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(GifListViewModel viewModel, Integer position) {
            binding.setVariable(BR.model, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
