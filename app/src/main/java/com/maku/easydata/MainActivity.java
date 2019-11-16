package com.maku.easydata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.africastalking.AfricasTalking;
import com.africastalking.utils.Logger;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.maku.easydata.Constants.Constants;
import com.maku.easydata.interfaces.IMainActivity;
import com.maku.easydata.models.FragmentM;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainActivity, BottomNavigationViewEx.OnNavigationItemSelectedListener ,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private SharedPreferences mSharedPreferences;

    private FirebaseAuth mAuth;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
//            case R.id.bottom_nav_data: {
//
//                if (mDataFragment == null) {
//                    mDataFragment = new DataFragment();
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.add(R.id.main_content_frame, mDataFragment, getString(R.string.tag_fragment_data));
//                    transaction.commit();
//                    mFragmentsTags.add(getString(R.string.tag_fragment_data));
//                    mFragments.add(new FragmentM(mDataFragment, getString(R.string.tag_fragment_data)));
//                }
//                else {
//                    mFragmentsTags.remove(getString(R.string.tag_fragment_data));
//                    mFragmentsTags.add(getString(R.string.tag_fragment_data));
//                }
//                setFragmentVisibilities(getString(R.string.tag_fragment_data));
//                break;
//            }

            case R.id.bottom_nav_airtime: {
                if (mAirtimeFragment == null) {
                    mAirtimeFragment = new AirtimeFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mAirtimeFragment, getString(R.string.tag_fragment_airtime));
                    transaction.commit();
                    mFragmentsTags.add(getString(R.string.tag_fragment_airtime));
                    mFragments.add(new FragmentM(mAirtimeFragment, getString(R.string.tag_fragment_airtime)));
                }
                else {
                    mFragmentsTags.remove(getString(R.string.tag_fragment_airtime));
                    mFragmentsTags.add(getString(R.string.tag_fragment_airtime));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_airtime));
                break;
            }

            case R.id.settings: {

                if (mSettingsFragment == null) {
                    mSettingsFragment = new SettingsFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mSettingsFragment, getString(R.string.tag_fragment_settings));
                    transaction.commit();
                    mFragmentsTags.add(getString(R.string.tag_fragment_settings));
                    mFragments.add(new FragmentM(mSettingsFragment, getString(R.string.tag_fragment_settings)));
                }
                else {
                    mFragmentsTags.remove(getString(R.string.tag_fragment_settings));
                    mFragmentsTags.add(getString(R.string.tag_fragment_settings));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_settings));
                break;
            }

            case R.id.agreement: {

                if (mAgreementFragment == null) {
                    mAgreementFragment = new AgreementFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mAgreementFragment, getString(R.string.tag_fragment_agreement));
                    transaction.commit();
                    mFragmentsTags.add(getString(R.string.tag_fragment_agreement));
                    mFragments.add(new FragmentM(mAgreementFragment, getString(R.string.tag_fragment_agreement)));
                }
                else {
                    mFragmentsTags.remove(getString(R.string.tag_fragment_agreement));
                    mFragmentsTags.add(getString(R.string.tag_fragment_agreement));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_agreement));
                break;
            }

            case R.id.Share: {

                if (mShareFragment == null) {
                    mShareFragment = new ShareFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mShareFragment, getString(R.string.tag_fragment_share));
                    transaction.commit();
                    mFragmentsTags.add(getString(R.string.tag_fragment_share));
                    mFragments.add(new FragmentM(mShareFragment, getString(R.string.tag_fragment_share)));
                }
                else {
                    mFragmentsTags.remove(getString(R.string.tag_fragment_share));
                    mFragmentsTags.add(getString(R.string.tag_fragment_share));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_share));
                break;
            }

            case R.id.SignOut: {
                logOut();
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void logOut() {
//        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(getApplicationContext(),loginActivity.class);
        startActivity(i);
    }


    //widgets
    private BottomNavigationViewEx mBottomNavigationViewEx;

    //vars
    private ImageView mHeaderImage;
    private DrawerLayout mDrawerLayout;
    private String mPhone;
    private EditText editTextName;
    private String mName;
    private String mCode;

    //constants
    private static final int DATA_FRAGMENT = 0;
    private static final int AIRTIME_FRAGMENT = 1;


    //Fragments
    private AirtimeFragment mAirtimeFragment;
    private DataFragment mDataFragment;
    private ShareFragment mShareFragment;
    private SettingsFragment mSettingsFragment;
    private AgreementFragment  mAgreementFragment;

    //vars
    private ArrayList<String> mFragmentsTags = new ArrayList<>();
    private ArrayList<FragmentM> mFragments = new ArrayList<>();
    private int mExitCount = 0;

//Africas talking

    //For the emulator, connecting to local host
    private final String host = "192.168.1.116";
    private final int port = 8088;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mBottomNavigationViewEx = findViewById(R.id.bottom_nav_view);
        editTextName = findViewById(R.id.input_name);

        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        mHeaderImage = headerView.findViewById(R.id.header_image);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPhone = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NUMBER, null);
        mName = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NAME, null);
        mCode = mSharedPreferences.getString(Constants.PREFERENCES_ID_COUNTRY_CODE, null);

//        array of country codes


        //number
        TextView number = (TextView) headerView.findViewById(R.id.header_title);
        number.setText(mPhone);

        //name
        TextView name = (TextView) headerView.findViewById(R.id.header_name);
        name.setText(mName);

         /*
        Invoke the method that will connect us to our server
         */
        connectToServer();

        initBottomNavigationView();
        setHeaderImage();
        setNavigationViewListener();
        init();

    }

    /*
   implementation of connectToServer()
    */
    private void connectToServer(){

        //Initialize te sdk, and connect to our server. Do this in a try catch block
        try{

            /*
            Put a notice in our log that we are attempting to initialize
             */
            AfricasTalking.initialize(host, port,true);

            //Use AT's Logger to get any message
            AfricasTalking.setLogger(new Logger() {
                @Override
                public void log(String message, Object... args) {

                    /*
                    Log this too
                     */
                }
            });

            /*
            Final log to tell us if successful
             */
        } catch (IOException e){

            /*
            Log our failure to connect
             */
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setHeaderImage() {

        // better to set the header with glide. It's more efficient than setting the source directly
//        Glide.with(this)
//                .load(R.drawable.)
//                .into(mHeaderImage);
    }


    private void init(){

        if (mAirtimeFragment == null) {
            mAirtimeFragment = new AirtimeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mAirtimeFragment, getString(R.string.tag_fragment_airtime));
            transaction.commit();
            mFragmentsTags.add(getString(R.string.tag_fragment_airtime));
            mFragments.add(new FragmentM(mAirtimeFragment, getString(R.string.tag_fragment_airtime)));
        }
        else {
            mFragmentsTags.remove(getString(R.string.tag_fragment_airtime));
            mFragmentsTags.add(getString(R.string.tag_fragment_airtime));
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_airtime));
    }

    private void initBottomNavigationView() {
        mBottomNavigationViewEx.enableAnimation(false);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        int backStackCount = mFragmentsTags.size();
        if(backStackCount > 1){
            //remove the fragment at the top
            String topFragmentTag = mFragmentsTags.get(backStackCount - 1);
//get the second fragment from the top
            String newTopFragmentTag = mFragmentsTags.get(backStackCount - 2);
            setFragmentVisibilities(newTopFragmentTag);

            mFragmentsTags.remove(topFragmentTag);

            mExitCount = 0;
        }
        else if( backStackCount == 1){
            String topFragmentTag = mFragmentsTags.get(backStackCount - 1);
            if(topFragmentTag.equals(getString(R.string.tag_fragment_data))){
//                mDataFragment.scrollToTop();
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }
            else{
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }
        }

        if(mExitCount >= 2){
            super.onBackPressed();
        }
    }

    private void setNavigationIcon(String tagname) {
        Menu menu = mBottomNavigationViewEx.getMenu();
        MenuItem menuItem = null;

    }

    private void setFragmentVisibilities(String tagname){
        if(tagname.equals(getString(R.string.tag_fragment_data)))
            showBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_airtime)))
            showBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_settings)))
            hideBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_agreement)))
            hideBottomNavigation();
        else if(tagname.equals(getString(R.string.tag_fragment_share)))
            hideBottomNavigation();

        for(int i = 0; i < mFragments.size(); i++){
            if(tagname.equals(mFragments.get(i).getTag())){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show((mFragments.get(i).getFragment()));
                transaction.commit();
            }
            else{
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide((mFragments.get(i).getFragment()));
                transaction.commit();
            }
        }
        setNavigationIcon(tagname);

        printBackStack();
    }

    private void printBackStack() {
        for (int i = 0; i < mFragmentsTags.size(); i++) {
        }
    }

    private void hideBottomNavigation() {
        if (mBottomNavigationViewEx != null) {
            mBottomNavigationViewEx.setVisibility(View.GONE);
        }
    }

    private void showBottomNavigation() {
        if (mBottomNavigationViewEx != null) {
            mBottomNavigationViewEx.setVisibility(View.VISIBLE);
        }
    }

}
