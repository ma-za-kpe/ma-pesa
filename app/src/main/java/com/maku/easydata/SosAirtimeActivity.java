package com.maku.easydata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maku.easydata.Constants.Constants;
import com.maku.easydata.interfaces.APIService;
import com.maku.easydata.models.serverrequest.SosRequest;
import com.maku.easydata.utils.ApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SosAirtimeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RewardedVideoAdListener {

    private static final String TAG = "SosAirtimeActivity";

    /*
   Google ads
   * */
    private RewardedVideoAd mRewardedVideoAd;

    /*shared preferences*/
    private SharedPreferences mSharedPreferences;

    /*vas*/
    List<String> spinnerArray =  new ArrayList<String>();
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
    private TextView mTextViewSuccess;
    private EditText mEditTextCustom;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_airtime);

        /*initialize google ads*/
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        /*shared preferences*/
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPhone = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NUMBER, null);
        mName = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NAME, null);
        mCode = mSharedPreferences.getString(Constants.PREFERENCES_ID_COUNTRY_CODE, null);

        /*initialize widgets*/
        spinner = (Spinner)findViewById(R.id.spinner);
        mButtonSos = findViewById(R.id.sosAT);
        mTextViewSuccess = findViewById(R.id.successs);
        mEditTextCustom = findViewById(R.id.custom);
        progressBar = (ProgressBar) findViewById(R.id.myProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        /*remove the success and custom textviews from view*/
        mTextViewSuccess.setVisibility(View.GONE);
        mEditTextCustom.setVisibility(View.GONE);

        //        create adapter for spinner class
        spinnerArray.add("10");
        spinnerArray.add("20");
        spinnerArray.add("30");
        spinnerArray.add("40");
        spinnerArray.add("50");
        spinnerArray.add("Watch for more");
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(SosAirtimeActivity.this,
                android.R.layout.simple_spinner_item,spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mAPIService = ApiUtils.getAPIService();

        /*set onClick listeners*/
        mButtonSos.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*date*/
         c = Calendar.getInstance().getTime();

        /*get users network name*/
        TelephonyManager manager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        carrierName = manager.getNetworkOperatorName();

         selected = parent.getItemAtPosition(position).toString();

         if (selected.equals("Watch for more")) {
             if(!mRewardedVideoAd.isLoaded()){
                 progressBar.setVisibility(View.VISIBLE);
                 Toast.makeText(this, "Ad is loading", Toast.LENGTH_SHORT).show();
                 mRewardedVideoAd.loadAd(getString(R.string.test_ad_unit_id), new AdRequest.Builder().build());

             }
         }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sosAT:

                /*parse to interger, and date to string*/
                Integer amount = Integer.parseInt(selected);
                String date = c.toString();

                if(!TextUtils.isEmpty(selected)) {
                    postSos(amount, mName, mCode, mPhone, carrierName, date);
                    mTextViewSuccess.setVisibility(View.VISIBLE);
                }

                /*disable button or 2hrs*/
                disableButtonTwoHours(v.getId());

                break;
            default:
                break;
        }
    }

    private void disableButtonTwoHours(final int id) {
        new CountDownTimer(7200000, 1000) {

            public void onTick(long millisUntilFinished) {

                if (id == R.id.sosAT) {
                    mButtonSos.setText(millisUntilFinished / 1000 + " secs / 2Hrs");
                    mButtonSos.setEnabled(false);
                    spinner.setEnabled(false);
                }

            }

            public void onFinish() {
                if (id == R.id.ten) {
                    mButtonSos.setEnabled(true);
                    mButtonSos.setText("N'Sort");
                }

            }

        }.start();

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
                        showResponse(response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<SosRequest> call, Throwable t) {
                    try {

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

    /*move back to fragment safely*/
    @Override
    public void onBackPressed(){
        finish();
    }

    /*Load a reward based video ad*/
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.ad_unit_id), new AdRequest.Builder().build());

    }

    /*Rewarded ad callbacks*/
    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(getBaseContext(),
                "Ad loaded.", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
        mRewardedVideoAd.show();
        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(getBaseContext(),
                "Ad opened.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(getBaseContext(),
                "Ad started.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(getBaseContext(),
                "Ad closed, watch video fully.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(getBaseContext(),
                "Close Ad and enter custom amount.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(getBaseContext(),
                "Ad left application.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(getBaseContext(),
                "Ad failed to load.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        // Reward the user for watching the ad.
        Toast.makeText(getBaseContext(), "Close Ad and enter custom amount", Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.GONE);
        mTextViewSuccess.setVisibility(View.GONE);
        /*add more items to the spinner*/
        spinnerArray.add("80");
        spinnerArray.add("100");
        spinnerArray.add("120");
        spinnerArray.add("140");
        spinnerArray.add("150");
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /*remove the success text from view*/
        mTextViewSuccess.setVisibility(View.GONE);
        mRewardedVideoAd.destroy(this);

        /*remove array items*/
        spinnerArray.remove("80");
        spinnerArray.remove("100");
        spinnerArray.remove("120");
        spinnerArray.remove("140");
        spinnerArray.remove("150");
    }
}
