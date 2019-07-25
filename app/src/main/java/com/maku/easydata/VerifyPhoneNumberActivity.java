package com.maku.easydata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.maku.easydata.Constants.Constants;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VerifyPhoneNumberActivi";
    //It is the verification id that will be sent to the user
    private String mVerificationId;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private String mobile;
    private String name;
    private TextView number;
    private  TextView textViewName;
    private Button buttonVerify;
    private EditText editTextCode;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        //first we intialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        number = findViewById(R.id.num);
        textViewName = findViewById(R.id.name);
        buttonVerify = findViewById(R.id.btn_verify_login);
        editTextCode = findViewById(R.id.input_code);

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        name = intent.getStringExtra("name");
        number.setText(mobile);
//        editTextCode.setText(name);

        Log.d(TAG, "Test user mobile " + mobile);

        //if the automatic sms detection worked,
        sendVerificationCode(mobile);
        buttonVerify.setOnClickListener(this);
        addToSharedPreferences(mobile, name);
    }

    private void addToSharedPreferences(String fullNumber, String name) {
        mEditor.putString(Constants.PREFERENCES_ID_PHONE_NUMBER, fullNumber).apply();
        mEditor.putString(Constants.PREFERENCES_ID_PHONE_NAME, name).apply();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonVerify) {

            //if the automatic sms detection did not work, user can also enter the code manually
            //so adding a click listener to the button
            String code = editTextCode.getText().toString().trim();
            if (code.isEmpty() || code.length() < 6) {
                editTextCode.setError("Enter valid code");
                editTextCode.requestFocus();
                return;
            }

            //verifying the code entered manually
            verifyVerificationCode(code);
        }
    }

    //firebase phone authentication

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneNumberActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            verification successful we will start the Network activity
                            Intent intent = new Intent(VerifyPhoneNumberActivity.this, NetworkActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.snack), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }
}
