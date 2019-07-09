package com.maku.easydata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.maku.easydata.Constants.Constants;

public class MainActivity extends AppCompatActivity implements BottomNavigationViewEx.OnNavigationItemSelectedListener ,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private SharedPreferences mSharedPreferences;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        switch (item.getItemId()) {


            case R.id.bottom_nav_home: {
                Log.d(TAG, "onNavigationItemSelected: DataFragment.");
                DataFragment dataFragment = new DataFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content_frame, dataFragment, getString(R.string.tag_fragment_data));
                transaction.addToBackStack(getString(R.string.tag_fragment_data));
                transaction.commit();
                item.setChecked(true);
                break;
            }

            case R.id.bottom_nav_connections: {
                Log.d(TAG, "onNavigationItemSelected: AirtimeFragment.");
                AirtimeFragment airtimeFragment = new AirtimeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content_frame, airtimeFragment, getString(R.string.tag_fragment_airtime));
                transaction.addToBackStack(getString(R.string.tag_fragment_airtime));
                transaction.commit();
                item.setChecked(true);
                break;
            }

            case R.id.settings: {
                Log.d(TAG, "onNavigationItemSelected: Settings.");
                SettingsFragment settingsFragment = new SettingsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content_frame, settingsFragment, getString(R.string.tag_fragment_settings));
                transaction.addToBackStack(getString(R.string.tag_fragment_settings));
                transaction.commit();
                break;
            }

            case R.id.agreement: {
                Log.d(TAG, "onNavigationItemSelected: Agreement.");
                AgreementFragment homeFragment = new AgreementFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_agreement));
                transaction.addToBackStack(getString(R.string.tag_fragment_agreement));
                transaction.commit();
                break;
            }

            case R.id.Share: {
                Log.d(TAG, "onNavigationItemSelected: share.");
                ShareFragment shareFragment = new ShareFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content_frame, shareFragment, getString(R.string.tag_fragment_share));
                transaction.addToBackStack(getString(R.string.tag_fragment_share));
                transaction.commit();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationViewEx = findViewById(R.id.bottom_nav_view);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        mHeaderImage = headerView.findViewById(R.id.header_image);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPhone = mSharedPreferences.getString(Constants.PREFERENCES_ID_PHONE_NUMBER, null);

        //email
        TextView number = (TextView) headerView.findViewById(R.id.header_title);
        number.setText(mPhone);

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
        Glide.with(this)
                .load(R.drawable.background_splash)
                .into(mHeaderImage);
    }


    private void init(){
        DataFragment dataFragment = new DataFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_frame, dataFragment, getString(R.string.tag_fragment_data));
        transaction.addToBackStack(getString(R.string.tag_fragment_data));
        transaction.commit();
    }

    private void initBottomNavigationView() {
        Log.d(TAG, "initBottomNavigationView: initializing bottom navigation view.");
        mBottomNavigationViewEx.enableAnimation(false);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
    }
}
