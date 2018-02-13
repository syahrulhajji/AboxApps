package com.aboxs.template_android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboxs.template_android.R;
import com.aboxs.template_android.activity.MainActivity;
import com.aboxs.template_android.database.SharePreference;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends AboxFragment {
    private SharePreference sharePreference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view) ;
        sharePreference = new SharePreference(getContext());
        ((MainActivity)getActivity()).hideMenuSlide();


        return view;
    }

    @OnClick(R.id.btn_register)
    void registerClick(){
        ((MainActivity)getActivity()).replaceFragment(new LoginFragment());
    }

    @Override
    protected boolean isTabSolid() {
        return false;
    }
}
