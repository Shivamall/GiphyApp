package com.example.giphyapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giphyapp.R;
import com.example.giphyapp.databinding.SearchFragmentBinding;
import com.example.giphyapp.model.Gif;
import com.example.giphyapp.viewmodel.GifListViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class SearchFragment extends Fragment implements MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener {
    private Context mContext;
    private GifListViewModel viewModel;
    private SearchFragmentBinding mBinding;
    private boolean isAlreadyCalled;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        isAlreadyCalled = false;
        viewModel = ViewModelProviders.of(this).get(GifListViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Giphy");
        setHasOptionsMenu(true);

//        Search Click
        mBinding.searchView.setOnQueryTextListener(this);
        mBinding.searchView.setOnSearchViewListener(this);
        mBinding.setModel(viewModel);

//        OnScrollListener For Pagination
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
        setupListUpdate();
        return mBinding.getRoot();
    }

    private void getNextGif() {
//        Updating offset to get next 30 gifs
        viewModel.setOffset(viewModel.getOffset()+30);
        viewModel.fetchList("");
    }

    public void setLoadMoreProgress() {

//        Showing loading When pagination is called
        if (isAlreadyCalled) {
            viewModel.loadMore.set(View.VISIBLE);
        } else {
            viewModel.loadMore.set(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mBinding.searchView.setMenuItem(item);


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

//    Showing Gif Url when click on Add to favourites Text
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

//    Sending search view text to Searchapi
    @Override
    public boolean onQueryTextSubmit(String query) {
        viewModel.fetchList(query);


        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSearchViewShown() {

    }

    @Override
    public void onSearchViewClosed() {

    }
}
