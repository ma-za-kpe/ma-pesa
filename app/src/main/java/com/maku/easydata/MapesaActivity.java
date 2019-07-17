package com.maku.easydata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.maku.easydata.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class MapesaActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private static final String TAG = "MapesaActivity";

//    vars
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final String APP_ID = "ca-app-pub-1222362664019591~8722623706";
    private static final long COUNTER_TIME = 5;
    private static final int GAME_OVER_REWARD = 1;

    private int coinCount;
    private TextView coinCountText;
    private CountDownTimer countDownTimer;
    private boolean gameOver;
    private boolean gamePaused;
    private RewardedVideoAd rewardedVideoAd;
    private Button retryButton;
    private Button showVideoButton;
    private long timeRemaining;

    //shared preferences
    private SharedPreferences mSharedPreferences;
    private String mPhone;
    private String mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapesa);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, APP_ID);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        // Create the "retry" button, which tries to show an interstitial between game plays.
        retryButton = findViewById(R.id.retry_button);
        retryButton.setVisibility(View.INVISIBLE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        // Create the "show" button, which shows a rewarded video if one is loaded.
        showVideoButton = findViewById(R.id.show_video_button);
        showVideoButton.setVisibility(View.INVISIBLE);
        showVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRewardedVideo();
            }
        });

        // Display current coin count to user.
        coinCountText = findViewById(R.id.coin_count_text);
        coinCount = 0;
        coinCountText.setText("Airtime of 5Kshs wil be sent to you after watching videos.");

        startGame();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPhone = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NUMBER, null);
        mCode = mSharedPreferences.getString(Constants.PREFERENCES_ID_COUNTRY_CODE, null);

        Log.d(TAG, "onCreate: mobile number is: " + mPhone);
        Log.d(TAG, "onCreate: country code is: " + mCode);

    }


    @Override
    public void onPause() {
        super.onPause();
        pauseGame();
        rewardedVideoAd.pause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!gameOver && gamePaused) {
            resumeGame();
        }
        rewardedVideoAd.resume(this);
    }

    private void pauseGame() {
        countDownTimer.cancel();
        gamePaused = true;
    }

    private void resumeGame() {
        createTimer(timeRemaining);
        gamePaused = false;
    }

    private void loadRewardedVideoAd() {
        if (!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }

    private void addCoins(int coins) {
        coinCount += coins;
//        coinCountText.setText("Airtime of 5Kshs wil be sent to you after watching videos " + coinCount );
        coinCountText.setText("Continue: " );
    }

    private void startGame() {
        // Hide the retry button, load the ad, and start the timer.
        retryButton.setVisibility(View.INVISIBLE);
        showVideoButton.setVisibility(View.INVISIBLE);
        loadRewardedVideoAd();
        createTimer(COUNTER_TIME);
        gamePaused = false;
        gameOver = false;
    }

    // Create the game timer, which counts down to the end of the level
    // and shows the "retry" button.
    private void createTimer(long time) {
        final TextView textView = findViewById(R.id.timer);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(time * 1000, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                timeRemaining = ((millisUnitFinished / 1000) + 1);
                textView.setText("Watch in: " + timeRemaining);
            }

            @Override
            public void onFinish() {
                if (rewardedVideoAd.isLoaded()) {
                    showVideoButton.setVisibility(View.VISIBLE);
                }
                textView.setText("");
                addCoins(GAME_OVER_REWARD);
                retryButton.setVisibility(View.VISIBLE);
                gameOver = true;
            }
        };
        countDownTimer.start();
    }

    private void showRewardedVideo() {
        showVideoButton.setVisibility(View.INVISIBLE);
        if (rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        // Preload the next video ad.
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this,
                String.format(" onRewarded! currency: %s amount: %d", reward.getType(),
                        reward.getAmount()),
                Toast.LENGTH_SHORT).show();
        addCoins(reward.getAmount());
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();

        if (mCode.equals("+254") || mCode.equals("+255") || mCode.equals("+256")) {
            Log.d(TAG, "onRewardedVideoCompleted: country code : " + mCode);

        }

        // here, we send the 5shs airtime to the user

        // Create a HashMap object called capitalCities
        HashMap<String, String> countryCode = new HashMap<String, String>();

        // Add keys and values (Country, City)
        countryCode.put("+254" , "KES");
        countryCode.put("+256" , "UGX");
        countryCode.put("+255" , "TZS");

//        for (Map.Entry<String, String> entry : countryCode.entrySet()) {
//            Log.d(TAG, "onRewardedVideoCompleted: " + entry.getKey() + " = " + entry.getValue());
//
//            if (entry.getKey().equals(mCode)) {
//                Log.d(TAG, "onRewardedVideoCompleted: value is" + entry.getValue());
//                break;
//            }
//        }

        String countryCodevalue = countryCode.get(mCode); // value = "y"

        Log.d(TAG, "onRewardedVideoCompleted: country code to send data to after video watched is: " + countryCodevalue);

    //bring in Africas Talking Api

        /* Set your app credentials */
        String username = "sandbox";
        String apiKey = "d36a57c32d37b8bb558fb3776e190d9cec80a0a8c4be28b5d77e1c84a322eb3e";

//        /* Initialize the SDK */
//        AfricasTalking.initialize(username, apiKey);
//        /* Get airtime service */
//        AirtimeService airtime = AfricasTalking.getService(AfricasTalking.SERVICE_AIRTIME);
//
        /* Set the phone number, currency code and amount */
        String phoneNumber = mPhone;
        String currencyCode = mCode;
        float amount = 1;
//
//        try {
//            /* That's it, hit send and we'll take care of the rest */
//            AirtimeResponse response = airtime.send(phoneNumber, currencyCode, amount);
//            System.out.println(response.toString());
//        } catch(Exception ex) {
//            ex.printStackTrace();
//        }


    }
}
