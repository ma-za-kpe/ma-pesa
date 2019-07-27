package com.maku.easydata;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maku.easydata.adapter.DataRecyclerviewAdapter;
import com.maku.easydata.models.DataModel;
import com.maku.easydata.utils.DataPrices;

import java.util.ArrayList;


public class DataFragment extends Fragment {

    private static final String TAG = "DataFragment";

    //constants

    //widgets
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //vars
    private ArrayList<DataModel> mData = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private DataRecyclerviewAdapter mRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

//        recyclerview
        mRecyclerView = view.findViewById(R.id.recycler_view_data);

        findCategory();

        return view;
    }

    private void findCategory() {
        DataPrices dataPrices = new DataPrices();
        if (mData != null) {
            mData.clear();
        }
        for (DataModel category : dataPrices.DATA) {
            mData.add(category);
        }
        if (mRecyclerViewAdapter == null) {
            initRecyclerView();
        }
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new DataRecyclerviewAdapter(mData, getActivity());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    public void scrollToTop(){
        mRecyclerView.smoothScrollToPosition(0);
    }


}
