package com.maku.easydata.network

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.maku.easydata.model.AirtimerRecipient
import com.maku.easydata.model.SendAirtime
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import timber.log.Timber
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


interface MyApiClient {

    @FormUrlEncoded
    @POST("version1/airtime/send")
    fun doAtSending(@Header("apiKey") apiKey: String, @Field("username") username: String?, @Field("recipients") recipients:String?): Call<SendAirtime?>?

    companion object{
        operator fun invoke() : MyApiClient{

            val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.d("send message $message")
                }
            })

//            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient().newBuilder()
                    .addInterceptor(object : Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                            val ongoing: Request.Builder = chain.request().newBuilder()
                            ongoing.addHeader("Accept", "application/json")
                            ongoing.addHeader("Content-Type", "application/x-www-form-urlencoded")
                            ongoing.addHeader("X-Authorization", "apiKey "+"3ad6c981292c48f6af8db491af1fc0de34a8873a67afba9864e0a9ffc1df9ab4")
                            return chain.proceed(ongoing.build())
                        }
                    })
                    .connectTimeout(160, TimeUnit.SECONDS)
                    .readTimeout(160, TimeUnit.SECONDS)
                    .writeTimeout(160, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build()

            return Retrofit.Builder()
                    .baseUrl("https://api.africastalking.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApiClient::class.java)
        }
    }

}