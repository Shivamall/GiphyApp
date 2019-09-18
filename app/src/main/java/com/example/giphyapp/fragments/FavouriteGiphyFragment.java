package com.example.giphyapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giphyapp.R;
import com.example.giphyapp.databinding.FavouriteGiphyFragmentBinding;
import com.example.giphyapp.databinding.SearchFragmentBinding;
import com.example.giphyapp.model.Gif;
import com.example.giphyapp.viewmodel.GifListViewModel;

import java.util.List;

public class FavouriteGiphyFragment extends Fragment {
    private Context mContext;
    private GifListViewModel viewModel;
    private FavouriteGiphyFragmentBinding mBinding;
    private boolean isAlreadyCalled;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SearchFragmentBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        isAlreadyCalled = false;
        viewModel = ViewModelProviders.of(this).get(GifListViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }



        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean endHasBeenReached = lastVisible >= totalItemCount - 4;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if (!isAlreadyCalled && viewModel.getAdapter().getItemCount() > 0) {
                        isAlreadyCalled = true;
                        getNextGif();
                        setLoadMoreProgress();
                    }
                }
            }
        });
        mBinding.setModel(viewModel);
        setupListUpdate();
        return mBinding.getRoot();
    }

    private void getNextGif() {
        viewModel.setOffset(viewModel.getOffset()+30);
        viewModel.fetchList("");
    }

    public void setLoadMoreProgress() {
        if (isAlreadyCalled) {
            viewModel.loadMore.set(View.VISIBLE);
        } else {
            viewModel.loadMore.set(View.GONE);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    private void setupListUpdate() {
        String query = "";
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList(query);
        viewModel.getGifList().observe(this, new Observer<List<Gif>>() {
            @Override
            public void onChanged(List<Gif> gifList) {
                isAlreadyCalled = false;
                viewModel.loading.set(View.GONE);
                viewModel.setGifInAdapter(gifList);
                setLoadMoreProgress();
            }
        });
        setupListClick();
    }

    private void setupListClick() {
        viewModel.getGifListLiveData().observe(this, new Observer<Gif>() {
            @Override
            public void onChanged(Gif gif) {
                if (gif != null) {
                    Toast.makeText(mContext, "You selected a " + gif.getImages().getOriginal().getUrl(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
