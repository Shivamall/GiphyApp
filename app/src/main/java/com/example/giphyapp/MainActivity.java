package com.example.giphyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.giphyapp.adapter.ViewPagerAdapter;
import com.example.giphyapp.databinding.ActivityMainBinding;
import com.example.giphyapp.fragments.FavouriteGiphyFragment;
import com.example.giphyapp.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding mBinding;
//private DataViewModel dataViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragments(new SearchFragment(),"Search");
        adapter.AddFragments(new FavouriteGiphyFragment(), "Favourites");
        mBinding.viewPager.setAdapter(adapter);
        mBinding.tabsLayout.setupWithViewPager(mBinding.viewPager);
    }






}
