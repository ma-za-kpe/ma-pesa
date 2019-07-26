package com.maku.easydata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.maku.easydata.Constants.Constants;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "loginActivity";

    private EditText editTextNumber;
    private EditText editTextName;
    private CountryCodePicker ccp;
    private Button buttonLogin;
    private FirebaseAuth mAuth;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private String fullNumber;
    private String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //first we intialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        editTextNumber = findViewById(R.id.input_number);
        editTextName = findViewById(R.id.input_name);

        ccp = findViewById(R.id.ccp);
        buttonLogin = findViewById(R.id.btn_login);
        buttonLogin.setOnClickListener(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

    }


    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already signed in
        //we will close this activity
        //and take the user to profile activity
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        if (v == buttonLogin) {
            String selected_country_code = ccp.getSelectedCountryCodeWithPlus();
            String mobile = editTextNumber.getText().toString();
            String name = editTextName.getText().toString();

            //check weather number is not null
            if (mobile.isEmpty() || mobile.length() < 9) {
                Toast.makeText(loginActivity.this, "Enter a valid mobile", Toast.LENGTH_SHORT).show();
                return;
            } else {

                fullNumber = selected_country_code + mobile;
                Log.d(TAG, "Test user mobile " + fullNumber);

                addToSharedPreferences(selected_country_code);

                Intent intent = new Intent(loginActivity.this, VerifyPhoneNumberActivity.class);
                intent.putExtra("mobile", fullNumber);
                intent.putExtra("name", name);
                startActivity(intent);

            }

        }

    }

    private void addToSharedPreferences(String selected_country_code) {
        mEditor.putString(Constants.PREFERENCES_ID_COUNTRY_CODE, selected_country_code).apply();
    }

}
