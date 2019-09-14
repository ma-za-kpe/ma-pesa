package com.maku.easydata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.africastalking.AfricasTalking;
import com.africastalking.models.airtime.AirtimeResponse;
import com.africastalking.services.AirtimeService;
import com.maku.easydata.adapter.SosAdapter;
import com.maku.easydata.interfaces.APIService;
import com.maku.easydata.models.serverresponse.SosResult;
import com.maku.easydata.models.serverresponse.SosResult;
import com.maku.easydata.models.serverresponse.SosResult;
import com.maku.easydata.utils.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareAirtimeActivity extends AppCompatActivity {

    private static final String TAG = "ShareAirtimeActivity";

    /*vars*/
    ArrayList<SosResult> mArrayList;
    private LinearLayoutManager mLinearLayoutManager;

    /*widgets*/
    private RecyclerView mRecyclerView;
    private SosAdapter mSosAdapter;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_airtime);

        /*Initialise the api service here*/
        mAPIService = ApiUtils.getAPIService();

        /*initialise view*/
        mArrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.sosPeople);

        initRecyclerview();

        fetchJSON();

    }

    private void initRecyclerview() {
        Log.d(TAG, "initRecyclerview: ");
        mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void fetchJSON(){

        mAPIService.getSos().enqueue(new Callback<ArrayList<SosResult>>() {
            @Override
            public void onResponse(Call<ArrayList<SosResult>> call, Response<ArrayList<SosResult>> response) {

                if (response.isSuccessful())  {
                    Log.d(TAG, "onResponse: " + response.body());
                    process(response);
                }else{
                    Log.d(TAG, "onResponse: there is no response");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SosResult>> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

    /*process response from server and display in the recyclerview */
    private void process(Response<ArrayList<SosResult>> response) {

        mArrayList =  response.body();
        Log.d(TAG, "process: " + mArrayList);
        mSosAdapter = new SosAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mSosAdapter);

    }

    /*working with onclick share button*/
    public void onClickCalled(String code, String phone, String amount ) {
        Log.d(TAG, "onClickCalled: " + code + " " + phone + " " + amount);
        switch (code) {
            case "+254":
                Log.d(TAG, "onClick: Kenya");

                HashMap<String,String> recipient = new HashMap<>();
                recipient.put(phone, "KES " + amount);
                sendAirtime(recipient);

                break;

            default:
                break;
        }
    }

    /*
 implementation of sendAirtime()
  */
    private void sendAirtime(final HashMap<String, String> recipient){

        /*
        Run our code in a separate thread from the UI thread, using AsyncTask. Required by Android for all Network calls
         */

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, String, Void> taskSendAirtime = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                /*
                Where we put our code. This is the code that will be executed in the thread
                 */
                try {

                /*
                Log that we are trying to get service
                 */
                    Log.e("AIRTIME NOTICE", "Trying to get airtime service");
                    AirtimeService service = AfricasTalking.getAirtimeService();

                    //Now that we have the service, send the airtime, get the response
                    AirtimeResponse response = service.send(recipient);

                    //Log our success message
                    Log.e("AIRTIME SUCCESS","Sent airtime worth " + response.totalAmount + " to " );

                } catch (IOException e){

                    /*
                    Log our failure
                     */
                    Log.e("AIRTIME FAILURE","Failed to send airtime with exception " + e.toString());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(),"Success ",Toast.LENGTH_LONG).show();
            }
        };

        //Execute our task
        taskSendAirtime.execute();
    }
}



