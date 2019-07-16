package com.maku.easydata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maku.easydata.Constants.Constants;

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NetworkActivity";

    private SharedPreferences mSharedPreferences;

    private String mCode;

    private Button network1;
    private Button network2;
    private Button network3;
    private TextView textViewCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCode = mSharedPreferences.getString(Constants.PREFERENCES_ID_COUNTRY_CODE, null);

        network1 = findViewById(R.id.net1);
        network2 = findViewById(R.id.net2);
        network3 = findViewById(R.id.net3);

//        onclick listeners
        network1.setOnClickListener(this);
        network2.setOnClickListener(this);
        network3.setOnClickListener(this);

        textViewCode = findViewById(R.id.code);
        textViewCode.setText(mCode);

        Log.d(TAG, "chooseCountry: code " +  textViewCode.getText().toString());

        chooseCountry(mCode);
    }

    private void chooseCountry(String Code) {

        if ( Code.equals("+254")){

            network1.setText("SAFARICOM");
            network2.setText("TELKOM");
            network3.setText("AIRTEL");

        } else if (mCode == "+255"){

            network1.setText("VODACOM");
            network2.setText("TIGO");
            network3.setText("AIRTEL");

        } else if (mCode == "+256") {

            network1.setText("MTN");
            network2.setText("AFRICEL");
            network3.setText("AIRTEL");

        } else if (mCode == "+234") {

            network1.setText("MTN");
            network2.setText("GLO");
            network3.setText("AIRTEL");

        }

    }

    @Override
    public void onClick(View v) {

        if ( v == network1) {

            Intent intent = new Intent(NetworkActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else  if ( v == network2) {

            Intent intent = new Intent(NetworkActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else  if ( v == network2) {

            Intent intent = new Intent(NetworkActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }
}
