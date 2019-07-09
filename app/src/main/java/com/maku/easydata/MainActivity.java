package com.maku.easydata;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity implements BottomNavigationViewEx.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

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
        }
        return false;
    }


    //widgets
    private BottomNavigationViewEx mBottomNavigationViewEx;

    //vars


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationViewEx = findViewById(R.id.bottom_nav_view);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);

        initBottomNavigationView();
        init();
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
