package com.maku.easydata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.maku.easydata.Constants.Constants;
import com.maku.easydata.interfaces.IMainActivity;
import com.maku.easydata.models.FragmentM;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainActivity, BottomNavigationViewEx.OnNavigationItemSelectedListener ,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private SharedPreferences mSharedPreferences;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.bottom_nav_home: {
                Log.d(TAG, "onNavigationItemSelected: DataFragment.");

                if (mDataFragment == null) {
                    mDataFragment = new DataFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mDataFragment, getString(R.string.tag_fragment_data));
                    transaction.commit();
                    mFragmentsTags.add(getString(R.string.tag_fragment_data));
                    mFragments.add(new FragmentM(mDataFragment, getString(R.string.tag_fragment_data)));
                }
                else {
                    mFragmentsTags.remove(getString(R.string.tag_fragment_data));
                    mFragmentsTags.add(getString(R.string.tag_fragment_data));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_data));
                break;
            }

            case R.id.bottom_nav_connections: {

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
                Log.d(TAG, "onNavigationItemSelected: Signout.");
                logOut();
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void logOut() {
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: starting main activity");

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

        initBottomNavigationView();
        setHeaderImage();
        setNavigationViewListener();
        init();

    }

    private void setNavigationViewListener() {
        Log.d(TAG, "setNavigationViewListener: initializing navigation drawer onclicklistener.");
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setHeaderImage() {
        Log.d(TAG, "setHeaderImage: setting header image for navigation drawer.");

        // better to set the header with glide. It's more efficient than setting the source directly
//        Glide.with(this)
//                .load(R.drawable.)
//                .into(mHeaderImage);
    }


    private void init(){

        if (mDataFragment == null) {
            mDataFragment = new DataFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_frame, mDataFragment, getString(R.string.tag_fragment_data));
            transaction.commit();
            mFragmentsTags.add(getString(R.string.tag_fragment_data));
            mFragments.add(new FragmentM(mDataFragment, getString(R.string.tag_fragment_data)));
        }
        else {
            mFragmentsTags.remove(getString(R.string.tag_fragment_data));
            mFragmentsTags.add(getString(R.string.tag_fragment_data));
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_data));
    }

    private void initBottomNavigationView() {
        Log.d(TAG, "initBottomNavigationView: initializing bottom navigation view.");
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
        if (tagname.equals(getString(R.string.tag_fragment_data))) {
            Log.d(TAG, "setNavigationIcon: Data fragment is visible");
            menuItem = menu.getItem(DATA_FRAGMENT);
            menuItem.setChecked(true);
        }
        else if (tagname.equals(getString(R.string.tag_fragment_airtime))) {
            Log.d(TAG, "setNavigationIcon: airtime fragment is visible");
            menuItem = menu.getItem(AIRTIME_FRAGMENT);
            menuItem.setChecked(true);
        }
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
        Log.d(TAG, "printBackStack: ----------------------------------- ");
        for (int i = 0; i < mFragmentsTags.size(); i++) {
            Log.d(TAG, "printBackStack: " + i + ": " + mFragmentsTags.get(i));
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
