package com.aboxs.template_android.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboxs.template_android.R;
import com.aboxs.template_android.activity.MainActivity;
import com.aboxs.template_android.adapter.AdapterHome;
import com.aboxs.template_android.database.SharePreference;
import com.aboxs.template_android.model.HomeModel;
import com.aboxs.template_android.util.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AboxFragment {
    SharePreference sharePreference;
    Utility utility;
    @BindView(R.id.rv_home)RecyclerView rvHome;
    @BindView(R.id.iv_profile) ImageView ivProfile;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_amount) TextView tvAmount;
    private Bitmap mBitmap;
    private List<HomeModel> homeModelList = new ArrayList<>();
    private AdapterHome mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        sharePreference = new SharePreference(getContext());
        utility = new Utility();
        ((MainActivity)getActivity()).showMenuSlide();

//        mAdapter = new AdapterHome(homeModelList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        rvHome.setLayoutManager(mLayoutManager);
//        rvHome.setItemAnimator(new DefaultItemAnimator());
//        rvHome.setAdapter(mAdapter);

        showImage();
//        dataHome();

        return view;
    }

    private void showImage(){
        if(sharePreference.getImage().equals("image")){}
        else {
            mBitmap = (BitmapFactory.decodeFile(sharePreference.getImage()));
            RoundedBitmapDrawable drawable = utility.createCircleImage(mBitmap);
            ivProfile.setImageDrawable(drawable);
        }
    }

    private void dataHome (){
        HomeModel homeModel = new HomeModel("S", "Syahrul Hajji", "Transfer to Haira","Rp. 2.500.000,-");
        homeModelList.add(homeModel);

        homeModel = new HomeModel("S", "Syahrul Hajji", "Transfer to Ais","Rp. 5.500.000,-");
        homeModelList.add(homeModel);

        homeModel = new HomeModel("S", "Syahrul Hajji", "Transfer to Maya","Rp. 3.500.000,-");
        homeModelList.add(homeModel);

        homeModel = new HomeModel("S", "Syahrul Hajji", "Debit Account","Rp. 7.500.000,-");
        homeModelList.add(homeModel);

        homeModel = new HomeModel("F", "Syahrul Hajji", "Transfer to Lala","Rp. 1.500.000,-");
        homeModelList.add(homeModel);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected String getTitle() {
        return getString(R.string.home);
    }
}
