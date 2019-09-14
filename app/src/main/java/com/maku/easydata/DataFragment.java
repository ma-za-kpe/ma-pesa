package com.maku.easydata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DataFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DataFragment";

    private Button mButtonMoreData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        mButtonMoreData = view.findViewById(R.id.moreData);

        mButtonMoreData.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moreData:
                Log.d(TAG, "onClick: more ...");
                break;
        }
    }
}
