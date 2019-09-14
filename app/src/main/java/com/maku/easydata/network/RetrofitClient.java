package com.maku.easydata.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            // setting custom timeouts (time outs should be small, so as to enhance user experience)
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(150, TimeUnit.SECONDS);
            client.readTimeout(150, TimeUnit.SECONDS);
            client.writeTimeout(150, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }

}
