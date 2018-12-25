package com.example.android.myappimgs.remote;

import com.example.android.myappimgs.network.Service;

public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL = "https://mytrips8.herokuapp.com/rest/";

    public static Service getUserService(){
        return RetrofitClient.getClient(API_URL).create(Service.class);
    }

}
