package com.aboxs.template_android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aboxs.template_android.R;
import com.aboxs.template_android.activity.MainActivity;
import com.aboxs.template_android.database.SharePreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends AboxFragment {
    private SharePreference sharePreference;
    @BindView(R.id.et_username) EditText etUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view) ;
        sharePreference = new SharePreference(getContext());
        ((MainActivity)getActivity()).hideMenuSlide();

        return view;
    }

    @OnTextChanged(R.id.et_username)
    void usernameChange(){
        sharePreference.saveName(etUsername.getText().toString());
    }

    @OnClick(R.id.btn_login)
    void loginClick(){
        ((MainActivity)getActivity()).replaceFragment(new HomeFragment());
    }

    @Override
    protected boolean isTabVisible() {
        return false;
    }
}
