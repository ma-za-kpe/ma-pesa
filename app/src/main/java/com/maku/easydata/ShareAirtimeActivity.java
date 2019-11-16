package com.maku.easydata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.africastalking.AfricasTalking;
import com.africastalking.models.airtime.AirtimeResponse;
import com.africastalking.services.AirtimeService;
import com.maku.easydata.adapter.SosAdapter;
import com.maku.easydata.interfaces.APIService;
import com.maku.easydata.models.serverresponse.SosResult;
import com.maku.easydata.utils.ApiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareAirtimeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ShareAirtimeActivity";

    /*vars*/
    ArrayList<SosResult> mArrayList;
    private LinearLayoutManager mLinearLayoutManager;

    /*widgets*/
    private RecyclerView mRecyclerView;
    private SosAdapter mSosAdapter;
    private APIService mAPIService;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_airtime);

        /*Initialise the api service here*/
        mAPIService = ApiUtils.getAPIService();

        /*initialise view*/
        mArrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.sosPeople);

        /*initialise recyclerview*/
        initRecyclerview();

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                if(mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                // TODO Fetching data from server
                fetchJSON();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecyclerView.setAdapter(null);
    }

    private void initRecyclerview() {
        mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void fetchJSON(){

        mAPIService.getSos().enqueue(new Callback<ArrayList<SosResult>>() {
            @Override
            public void onResponse(Call<ArrayList<SosResult>> call, Response<ArrayList<SosResult>> response) {

                if (response.isSuccessful())  {
                    process(response);
                    mSwipeRefreshLayout.setRefreshing(false);
                }else{
                    Toast.makeText(getBaseContext(),"Sorry or the inconvinience"+ response.code() +" "+ response.message(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SosResult>> call, Throwable t) {
            }
        });
    }

    /*process response from server and display in the recyclerview */
    private void process(Response<ArrayList<SosResult>> response) {

        mArrayList =  response.body();
        Collections.reverse(mArrayList);
        mSosAdapter = new SosAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mSosAdapter);

    }

    /*working with onclick share button*/
    public void onClickCalled(String code, String phone, String amount ) {
        switch (code) {
            case "+254":

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
                 */
                    AirtimeService service = AfricasTalking.getAirtimeService();

                    //Now that we have the service, send the airtime, get the response
                    AirtimeResponse response = service.send(recipient);

                    //Log our success message

                } catch (IOException e){

                    /*
                    Log our failure
                     */
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**fetch data from the server*/
 @Override
    public void onRefresh() {
     /**
      * Do not Show Swipe Refresh animation
      */
     mSwipeRefreshLayout.setRefreshing(true);
        fetchJSON();
    }

}



