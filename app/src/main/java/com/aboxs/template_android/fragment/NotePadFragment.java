package com.aboxs.template_android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboxs.template_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotePadFragment extends AboxFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_pad, container, false);

        return view;
    }

}
