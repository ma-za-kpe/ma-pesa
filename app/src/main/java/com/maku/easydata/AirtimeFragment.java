package com.maku.easydata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.INPUT_METHOD_SERVICE;


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
    private Button mButtonMoreAirtimeBtn;
    private TextView mTextViewSuccess;

    //    vars
    private String number;

    //Declare the timer
    Timer t;

    //    shared preferences
    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*check the internet connection of the user*/
        //detect internet and show the data
        if(isNetworkStatusAvialable (getActivity())) {
            Toast.makeText(getActivity(), "Internet detected", Toast.LENGTH_LONG).show();

            /*If there is internet connection, */


        } else {
            Toast.makeText(getActivity(), "Please check your Internet Connection", Toast.LENGTH_LONG).show();

        }
    }

    /*check the internet connection*/
    //check internet connection
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
            {
                return netInfos.isConnected();
            }
        }
        return false;
    }

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
        mButtonMoreAirtimeBtn = view.findViewById(R.id.moreAirtimeBtn);
        mTextViewSuccess = view.findViewById(R.id.success);

        /*hide the success text view */
        mTextViewSuccess.setVisibility(View.GONE);

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
        mButtonMoreAirtimeBtn.setOnClickListener(this);

        /**set timer*/
        t = new Timer();

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
                    recipient.put(number, "UGX " + mButtonTen.getText().toString());
                    sendAirtime(recipient);
                    
                    disableButtonFiveMinutes(v.getId());
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
                    recipient.put(number, "UGX " + mButton20.getText().toString());
                    sendAirtime(recipient);

                    disableButtonFiveMinutes(v.getId());
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
                    recipient.put(number, "UGX " + mButton50.getText().toString());
                    sendAirtime(recipient);

                    disableButtonFiveMinutes(v.getId());
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
                    recipient.put(number, "UGX " + mButton100.getText().toString());
                    sendAirtime(recipient);

                    disableButtonFiveMinutes(v.getId());
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
                    recipient.put(number, "UGX " + mButton500.getText().toString());
                    sendAirtime(recipient);

                    disableButtonFiveMinutes(v.getId());
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
                    recipient.put(number, "UGX " + mButton1000.getText().toString());
                    sendAirtime(recipient);

                    disableButtonFiveMinutes(v.getId());
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
                    recipient.put(number, "UGX " + mEditTextMore.getText().toString());
                    sendAirtime(recipient);
                    /*empty edittext */
                    mEditTextMore.setText("");

                    /*Hide keyboard*/
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    /*hide keyboard*/

                    disableButtonFiveMinutes(v.getId());
                }

                break;

            case R.id.sosAirtime:

                Intent intentSos = new Intent(getActivity(), SosAirtimeActivity.class);
                startActivity(intentSos);

                break;

            case R.id.shareAirtime:

                Intent intentShareAT = new Intent(getActivity(), ShareAirtimeActivity.class);
                startActivity(intentShareAT);

                break;

            default:
                break;
        }

    }

    /*this method updates the text on the button that has been clicked to the time left*/
    private void disableButtonFiveMinutes(final int id) {

        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {

                if (id == R.id.ten) {
                    mButtonTen.setText(millisUntilFinished / 1000 + " secs");
                    mButtonTen.setEnabled(false);
                } else if (id == R.id.twenty) {
                    mButton20.setText(millisUntilFinished / 1000 + " secs");
                    mButton20.setEnabled(false);
                } else if (id == R.id.fifty) {
                    mButton50.setText(millisUntilFinished / 1000+ " secs");
                    mButton50.setEnabled(false);
                } else if (id == R.id.oneHundred) {
                    mButton100.setText(millisUntilFinished / 1000+ " secs");
                    mButton100.setEnabled(false);
                } else if (id == R.id.fiveHundred) {
                    mButton500.setText(millisUntilFinished / 1000+ " secs");
                    mButton500.setEnabled(false);
                } else if (id == R.id.oneThousand) {
                    mButton1000.setText(millisUntilFinished / 1000 + " secs");
                    mButton1000.setEnabled(false);
                } else if (id == R.id.moreAirtimeBtn) {
                    mButtonMoreAirtimeBtn.setText(millisUntilFinished / 1000 + " secs");
                    mButtonMoreAirtimeBtn.setEnabled(false);
//                    mTextViewSuccess.setVisibility(View.VISIBLE);
                }
            }

            public void onFinish() {
                if (id == R.id.ten) {
                    mButtonTen.setEnabled(true);
                    mButtonTen.setText("10");
                } else if (id == R.id.twenty) {
                    mButton20.setEnabled(true);
                    mButton20.setText("20");
                } else if (id == R.id.fifty) {
                    mButton50.setEnabled(true);
                    mButton50.setText("50");
                } else if (id == R.id.oneHundred) {
                    mButton100.setEnabled(true);
                    mButton100.setText("100");
                } else if (id == R.id.fiveHundred) {
                    mButton500.setEnabled(true);
                    mButton500.setText("500");
                } else if (id == R.id.oneThousand) {
                    mButton1000.setEnabled(true);
                    mButton1000.setText("1000");
                } else if (id == R.id.moreAirtimeBtn) {
                    mButtonMoreAirtimeBtn.setEnabled(true);
                    mButton1000.setText("Buy");
                }

            }

        }.start();

    }

    /*
    implementation of sendAirtime()
     */
    private void sendAirtime(final HashMap<String, String> recipient){

        /*
        Run our code in a separate thread from the UI thread, using AsyncTask. Required by Android for all Network calls
         */

        @SuppressLint("StaticFieldLeak") AsyncTask <Void, String, Void> taskSendAirtime = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                /*
                Where we put our code. This is the code that will be executed in the thread
                 */
                try {

                /*
                Log that we are trying to get service
                 */
//                    Log.e("AIRTIME NOTICE", "Trying to get airtime service");
                    AirtimeService service = AfricasTalking.getAirtimeService();

                    //Now that we have the service, send the airtime, get the response
                    final AirtimeResponse response = service.send(recipient);
                    //Log our success message

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Bought Airtime worth " + response.totalAmount, Toast.LENGTH_LONG).show();
                        }
                    });

                    /*show the success text view when airtime is sent*/
                } catch (IOException e){

                    /*
                    Log our failure
                     */
                }

                return null;
            }
        };

        //Execute our task
        taskSendAirtime.execute();
    }
}
