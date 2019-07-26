package com.maku.easydata;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maku.easydata.Constants.Constants;
import com.maku.easydata.interfaces.IMainActivity;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SettingsFragment";

    private SharedPreferences mSharedPreferences;
    private String mPhone;
    private String mName;


    //widgets
    private TextView mFragmentHeading;
    private RelativeLayout mBackArrow;

    //vars
    private IMainActivity mInterface;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        mBackArrow = view.findViewById(R.id.back_arrow);
        mFragmentHeading = view.findViewById(R.id.fragment_heading);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mPhone = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NUMBER, null);
        mName = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NAME, null);

        //email
        TextView number = (TextView) view.findViewById(R.id.number);
        number.setText(mPhone);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(mName);

        initToolbar();

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");

        if(v.getId() == R.id.back_arrow){
            Log.d(TAG, "onClick: navigating back.");
            mInterface.onBackPressed();
        }
    }

    private void initToolbar(){
        Log.d(TAG, "initToolbar: initializing toolbar.");
        mBackArrow.setOnClickListener(this);
        mFragmentHeading.setText(getString(R.string.tag_fragment_settings));

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterface = (IMainActivity) getActivity();
    }

}
