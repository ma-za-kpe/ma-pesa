package com.maku.easydata;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DataFragment extends Fragment {
    private static final String TAG = "DataFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        return view;
    }

//    public void scrollToTop(){
//        mRecyclerView.smoothScrollToPosition(0);
//    }


}
