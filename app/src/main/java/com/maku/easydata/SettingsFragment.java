package com.maku.easydata;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maku.easydata.Constants.Constants;


public class SettingsFragment extends Fragment {

    private SharedPreferences mSharedPreferences;
    private String mPhone;
    private String mName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mPhone = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NUMBER, null);
        mName = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NAME, null);

        //email
        TextView number = (TextView) view.findViewById(R.id.number);
        number.setText(mPhone);

        TextView name = (TextView) view.findViewById(R.id.input_name);
        name.setText(mName);

        return view;
    }


}
