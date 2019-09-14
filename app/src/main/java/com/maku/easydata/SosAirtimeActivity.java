package com.maku.easydata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maku.easydata.Constants.Constants;
import com.maku.easydata.interfaces.APIService;
import com.maku.easydata.models.serverrequest.SosRequest;
import com.maku.easydata.utils.ApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SosAirtimeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "SosAirtimeActivity";

    /*
   Google ads
   * */
    private AdView mAdView;

    /*shared preferences*/
    private SharedPreferences mSharedPreferences;

    /*vas*/
    private static final String[] paths = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100","110", "120", "130", "140", "150", "160", "170", "180", "190", "200"};
    private String mPhone;
    private String mName;
    private String mCode;
    private String carrierName;
    private String selected;
    private Date c;
    private APIService mAPIService;


    /*widgets*/
    private Spinner spinner;
    private Button mButtonSos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_airtime);

        /*initialize google ads*/
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*shared preferences*/
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPhone = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NUMBER, null);
        mName = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NAME, null);
        mCode = mSharedPreferences.getString(Constants.PREFERENCES_ID_COUNTRY_CODE, null);

        /*initialize widgets*/
        spinner = (Spinner)findViewById(R.id.spinner);
        mButtonSos = findViewById(R.id.sosAT);

        //        create adapter for spinner class
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(SosAirtimeActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mAPIService = ApiUtils.getAPIService();

        /*set onClick listeners*/
        mButtonSos.setOnClickListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*date*/
         c = Calendar.getInstance().getTime();

        /*get users network name*/
        TelephonyManager manager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        carrierName = manager.getNetworkOperatorName();

         selected = parent.getItemAtPosition(position).toString();

        Log.d(TAG, "onItemSelected: " + selected + " on " + carrierName + " date is " + c+ " name is " + mName+ " country is " + mCode+ " phone number is " + mPhone);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sosAT:
                Log.d(TAG, "onClick: "+ selected + " on " + carrierName + " date is " + c+ " name is " + mName+ " country is " + mCode+ " phone number is " + mPhone);

                /*parse to interger, and date to string*/
                Integer amount = Integer.parseInt(selected);
                String date = c.toString();

                if(!TextUtils.isEmpty(selected)) {
                    postSos(amount, mName, mCode, mPhone, carrierName, date);
                } else {

                }

                break;
            default:
                break;
        }
    }

    private void postSos(Integer amount, String name, String code, String phone, String carrierName, String c) {

        try {

            JsonObject gson = new JsonObject();

            /*recreate json object to post*/
            JSONObject paramObject = new JSONObject();
            paramObject.put("amount", amount);
            paramObject.put("nickName", name);
            paramObject.put("countryCode", code);
            paramObject.put("phoneNumber", phone);
            paramObject.put("network", carrierName);
            paramObject.put("date", c);

            // parse the json object
            JsonParser jsonParser = new JsonParser();
            gson = (JsonObject) jsonParser.parse(paramObject.toString());

            mAPIService.savePost(gson).enqueue(new Callback<SosRequest>() {
                @Override
                public void onResponse(Call<SosRequest> call, Response<SosRequest> response) {
                    if(response.isSuccessful()) {
                        Log.d(TAG, "post submitted to API." + response.body().toString());
                        showResponse(response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<SosRequest> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());

                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showResponse(String toString) {
    }


}
