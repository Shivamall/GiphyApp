package com.example.giphyapp.viewmodel;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giphyapp.adapter.GifAdapter;
import com.example.giphyapp.model.Gif;
import com.example.giphyapp.model.GifList;

import java.util.List;

public class GifListViewModel extends ViewModel {

    private GifList gifList;
    private GifAdapter adapter;
    public MutableLiveData<Gif> gifListLiveData;
    public ObservableInt loading;
    public ObservableInt loadMore;
    public int offset = 0;

    public void init() {
        gifList = new GifList();
        gifListLiveData = new MutableLiveData<>();
        adapter = new GifAdapter(this);
        loading = new ObservableInt(View.GONE);
        loadMore = new ObservableInt(View.GONE);
    }

//    Call to Retrofit api with search string and offset
    public void fetchList(String query) {
        if(!TextUtils.isEmpty(query)) {
            offset = 0;
            gifList.fetchList(query, offset);
        } else {
            gifList.fetchList(query, offset);
        }

    }

    public MutableLiveData<List<Gif>> getGifList() {
        return gifList.getGifList();
    }

    public GifAdapter getAdapter() {
        return adapter;
    }

    public void setGifInAdapter(List<Gif> gifList) {
        if(offset == 0) {
            this.adapter.setList(gifList);
        } else {
            this.adapter.appendList(gifList);
        }
    }

    public MutableLiveData<Gif> getGifListLiveData() {
        return gifListLiveData;
    }

//Add To Favourites Text Click Handle
    public void onItemClick(Integer index) {
        Gif gif = getGifAt(index);
        gifListLiveData.setValue(gif);
    }

    public Gif getGifAt(Integer index) {
        if (gifList.getGifList().getValue() != null &&
                index != null &&
                gifList.getGifList().getValue().size() > index) {
            return gifList.getGifList().getValue().get(index);
        }
        return null;
    }

//    Getter Setter of Offset which is page no
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
