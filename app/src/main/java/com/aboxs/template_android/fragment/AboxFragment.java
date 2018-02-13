package com.aboxs.template_android.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.aboxs.template_android.activity.MainActivity;

/**
 * Created by Abox's on 10/02/2018.
 */

public class AboxFragment extends Fragment {
    private FragmentInteractionListener mListener;

    public interface FragmentInteractionListener{
        void onFragmentInteraction(String title,boolean isTabSolid,boolean isTabVisible);
        int getToolbarHeight();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String title = getTitle();
        mListener.onFragmentInteraction(title==null?"":title,isTabSolid(),isTabVisible());
        if(!isTabSolid()){
            final View view = getView();
            if (view != null) {
                if(view.getTag()==null) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + mListener.getToolbarHeight(), view.getPaddingRight(), view.getPaddingBottom());
                                }
                            });
                        }
                    });
                    view.setTag(true);
                }
            }
        }
    }

    protected String getTitle() {
        return null;
    }

    protected boolean isTabSolid() {
        return true;
    }

    protected boolean isTabVisible(){
        return true;
    }
}
