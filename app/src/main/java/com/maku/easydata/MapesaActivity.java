package com.maku.easydata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.africastalking.AfricasTalking;
import com.africastalking.models.airtime.AirtimeResponse;
import com.africastalking.services.AirtimeService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.maku.easydata.Constants.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MapesaActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private static final String TAG = "MapesaActivity";

//    vars
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final String APP_ID = "ca-app-pub-1222362664019591~8722623706";

    private RewardedVideoAd rewardedVideoAd;
    private Button retryButton;
    private Button showVideoButton;
    private long timeRemaining;

    //shared preferences
    private SharedPreferences mSharedPreferences;
    private String mPhone;
    private String mCode;

//    progress bar
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapesa);

        Log.d(TAG, "onCreate: ");

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, APP_ID);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 10) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
//                            textView.setText(progressStatus+"/ "+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        progressBar.setVisibility(View.VISIBLE);

        showVideoButton = findViewById(R.id.show_video_button);
        showVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAd();

            }
        });
        showVideoButton.setVisibility(View.GONE);

    }

    private void showAd() {
        Log.d(TAG, "showAd: ");

        if (rewardedVideoAd.isLoaded()) {
            progressBar.setVisibility(View.GONE);
            rewardedVideoAd.show();
        } else {
            loadRewardedVideoAd();
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void loadRewardedVideoAd() {
        Log.d(TAG, "loadRewardedVideoAd: ");
        if (!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }


    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d(TAG, "onRewardedVideoAdLoaded: ");
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        showVideoButton.setEnabled(true);
        Toast.makeText(this, "An ad has loaded", Toast.LENGTH_SHORT).show();

        showVideoButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.d(TAG, "onRewardedVideoAdOpened: ");
        Toast.makeText(this, "An ad has opened", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d(TAG, "onRewardedVideoStarted: ");

    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d(TAG, "onRewardedVideoAdClosed: ");

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Log.d(TAG, "onRewarded: ");
        Toast.makeText(this,
                String.format("You have received Kshs"),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d(TAG, "onRewardedVideoAdLeftApplication: ");

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.d(TAG, "onRewardedVideoAdFailedToLoad: ");

    }

    @Override
    public void onRewardedVideoCompleted() {
        Log.d(TAG, "onRewardedVideoCompleted: ");

    }
}
