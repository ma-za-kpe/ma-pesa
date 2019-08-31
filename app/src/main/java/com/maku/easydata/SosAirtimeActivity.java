package com.maku.easydata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SosAirtimeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SosAirtimeActivity";

    private Spinner spinner;
    private static final String[] paths = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100","110", "120", "130", "140", "150", "160", "170", "180", "190", "200"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_airtime);

        spinner = (Spinner)findViewById(R.id.spinner);

        //        create adapter
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(SosAirtimeActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Log.d("item", (String) parent.getItemAtPosition(position));
                break;
            case 1:
                Log.d(TAG, "onItemSelected: 1");
                break;
            case 2:
                Log.d(TAG, "onItemSelected: 2");
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
