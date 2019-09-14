package com.maku.easydata.interfaces;

import com.google.gson.JsonObject;
import com.maku.easydata.models.serverrequest.SosRequest;
import com.maku.easydata.models.serverresponse.SosResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @POST("sos/addSos")
    @Headers( "Content-Type: application/json" )
    Call<SosRequest> savePost(@Body JsonObject sosResult);

    @GET("sos/getSos")
    @Headers("Content-Type: application/json")
    Call<ArrayList<SosResult>> getSos();

    @DELETE("sos/deletSos/{id}")
    Call<SosResult> deleteSos(@Path("id") String sosId);

}
