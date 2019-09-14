package com.maku.easydata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.africastalking.AfricasTalking;
import com.africastalking.models.airtime.AirtimeResponse;
import com.africastalking.services.AirtimeService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.maku.easydata.Constants.Constants;

import java.io.IOException;
import java.util.HashMap;

public class AirtimeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AirtimeFragment";

    /*
    Google ads
    * */
    private AdView mAdView;

    /*
    Private member variables to hold references to our active widgets
     */
    private Button mButtonTen;
    private Button mButton20;
    private Button mButton50;
    private Button mButton100;
    private Button mButton500;
    private Button mButton1000;
    private Button mButtonSos;
    private Button mButtonMore;
    private EditText mEditTextMore;
    private Button mButtonshareAirtime;

//    vars
    private String number;

//    shared preferences
private SharedPreferences mSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_airtime, container, false);

//        get shared preferences
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        number = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NUMBER, null);

        /*initialize google ads*/
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


//        initialise the widgets
        mButtonTen = view.findViewById(R.id.ten);
        mButton20 = view.findViewById(R.id.twenty);
        mButton50 = view.findViewById(R.id.fifty);
        mButton100 = view.findViewById(R.id.oneHundred);
        mButton500 = view.findViewById(R.id.fiveHundred);
        mButton1000 = view.findViewById(R.id.oneThousand);
        mButtonSos = view.findViewById(R.id.sosAirtime);
        mButtonMore = view.findViewById(R.id.moreAirtimeBtn);
        mEditTextMore = view.findViewById(R.id.input_number);
        mButtonshareAirtime = view.findViewById(R.id.shareAirtime);

//        onclick listeners
        mButtonTen.setOnClickListener(this);
        mButton20.setOnClickListener(this);
        mButton50.setOnClickListener(this);
        mButton100.setOnClickListener(this);
        mButton500.setOnClickListener(this);
        mButton1000.setOnClickListener(this);
        mButtonSos.setOnClickListener(this);
        mButtonMore.setOnClickListener(this);
        mButtonshareAirtime.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ten:

                /*
                Ensure our Button is valid
                 */
                if (TextUtils.isEmpty(mButtonTen.getText().toString())){
                    /*
                    Show a Toast with error message
                     */
                    Toast.makeText(getActivity(),"Button is empty",Toast.LENGTH_LONG).show();
                } else {

                    /*
                Else, put our values in a Hashmap, with the key as the phone number. Since we are only sending airtime to one person, go direct to
                call our method to sendAirtime, passing in the Hashmap
                 */
                    HashMap<String,String> recipient = new HashMap<>();
                    recipient.put(number, "KES " + mButtonTen.getText().toString());
                    sendAirtime(recipient);
                }

                break;

            case R.id.twenty:

                 /*
                Ensure our Button is valid
                 */
                if (TextUtils.isEmpty(mButton20.getText().toString())){
                    /*
                    Show a Toast with error message
                     */
                    Toast.makeText(getActivity(),"Button is empty",Toast.LENGTH_LONG).show();
                } else {

                    /*
                Else, put our values in a Hashmap, with the key as the phone number. Since we are only sending airtime to one person, go direct to
                call our method to sendAirtime, passing in the Hashmap
                 */
                    HashMap<String,String> recipient = new HashMap<>();
                    recipient.put(number, "KES " + mButton20.getText().toString());
                    sendAirtime(recipient);
                }

                break;

            case R.id.fifty:

                 /*
                Ensure our Button is valid
                 */
                if (TextUtils.isEmpty(mButton50.getText().toString())){
                    /*
                    Show a Toast with error message
                     */
                    Toast.makeText(getActivity(),"Button is empty",Toast.LENGTH_LONG).show();
                } else {

                    /*
                Else, put our values in a Hashmap, with the key as the phone number. Since we are only sending airtime to one person, go direct to
                call our method to sendAirtime, passing in the Hashmap
                 */
                    HashMap<String,String> recipient = new HashMap<>();
                    recipient.put(number, "KES " + mButton50.getText().toString());
                    sendAirtime(recipient);
                }

                break;

            case R.id.oneHundred:

                 /*
                Ensure our Button is valid
                 */
                if (TextUtils.isEmpty(mButton100.getText().toString())){
                    /*
                    Show a Toast with error message
                     */
                    Toast.makeText(getActivity(),"Button is empty",Toast.LENGTH_LONG).show();
                } else {

                    /*
                Else, put our values in a Hashmap, with the key as the phone number. Since we are only sending airtime to one person, go direct to
                call our method to sendAirtime, passing in the Hashmap
                 */
                    HashMap<String,String> recipient = new HashMap<>();
                    recipient.put(number, "KES " + mButton100.getText().toString());
                    sendAirtime(recipient);
                }

                break;

            case R.id.fiveHundred:

                 /*
                Ensure our Button is valid
                 */
                if (TextUtils.isEmpty(mButton500.getText().toString())){
                    /*
                    Show a Toast with error message
                     */
                    Toast.makeText(getActivity(),"Button is empty",Toast.LENGTH_LONG).show();
                } else {

                    /*
                Else, put our values in a Hashmap, with the key as the phone number. Since we are only sending airtime to one person, go direct to
                call our method to sendAirtime, passing in the Hashmap
                 */
                    HashMap<String,String> recipient = new HashMap<>();
                    recipient.put(number, "KES " + mButton500.getText().toString());
                    sendAirtime(recipient);
                }

                break;

            case R.id.oneThousand:

                 /*
                Ensure our Button is valid
                 */
                if (TextUtils.isEmpty(mButton1000.getText().toString())){
                    /*
                    Show a Toast with error message
                     */
                    Toast.makeText(getActivity(),"Button is empty",Toast.LENGTH_LONG).show();
                } else {

                    /*
                Else, put our values in a Hashmap, with the key as the phone number. Since we are only sending airtime to one person, go direct to
                call our method to sendAirtime, passing in the Hashmap
                 */
                    HashMap<String,String> recipient = new HashMap<>();
                    recipient.put(number, "KES " + mButton1000.getText().toString());
                    sendAirtime(recipient);
                }

                break;


            case R.id.moreAirtimeBtn:

                 /*
                Ensure our Button is valid
                 */
                if (TextUtils.isEmpty(mEditTextMore.getText().toString())){
                    /*
                    Show a Toast with error message
                     */
                    Toast.makeText(getActivity(),"Please enter amount",Toast.LENGTH_LONG).show();
                } else {

                    /*
                Else, put our values in a Hashmap, with the key as the phone number. Since we are only sending airtime to one person, go direct to
                call our method to sendAirtime, passing in the Hashmap
                 */
                    HashMap<String,String> recipient = new HashMap<>();
                    recipient.put(number, "KES " + mEditTextMore.getText().toString());
                    sendAirtime(recipient);
                }

                break;

            case R.id.sosAirtime:
                Log.d(TAG, "onClick: sos ...");

                Intent intentSos = new Intent(getActivity(), SosAirtimeActivity.class);
                startActivity(intentSos);

                break;

            case R.id.shareAirtime:
                Log.d(TAG, "onClick: sos ...");

                Intent intentShareAT = new Intent(getActivity(), ShareAirtimeActivity.class);
                startActivity(intentShareAT);

                break;

            default:
                break;
        }

    }

    /*
   implementation of sendAirtime()
    */
    private void sendAirtime(final HashMap<String, String> recipient){

        /*
        Run our code in a separate thread from the UI thread, using AsyncTask. Required by Android for all Network calls
         */

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, String, Void> taskSendAirtime = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                /*
                Where we put our code. This is the code that will be executed in the thread
                 */
                try {

                /*
                Log that we are trying to get service
                 */
                    Log.e("AIRTIME NOTICE", "Trying to get airtime service");
                    AirtimeService service = AfricasTalking.getAirtimeService();

                    //Now that we have the service, send the airtime, get the response
                    AirtimeResponse response = service.send(recipient);

                    //Log our success message
                    Log.e("AIRTIME SUCCESS","Sent airtime worth " + response.totalAmount + " to " + number);

                } catch (IOException e){

                    /*
                    Log our failure
                     */
                    Log.e("AIRTIME FAILURE","Failed to send airtime with exception " + e.toString());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity(),"Success ",Toast.LENGTH_LONG).show();
            }
        };

        //Execute our task
        taskSendAirtime.execute();
    }
}
