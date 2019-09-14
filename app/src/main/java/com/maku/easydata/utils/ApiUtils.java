package com.maku.easydata.utils;

import com.maku.easydata.interfaces.APIService;
import com.maku.easydata.network.RetrofitClient;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://mighty-bastion-99648.herokuapp.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
