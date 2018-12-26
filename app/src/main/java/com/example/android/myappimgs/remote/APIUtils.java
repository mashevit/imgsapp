package com.example.android.myappimgs.remote;

import com.example.android.myappimgs.network.Service;
import com.example.android.myappimgs.network.ServiceGenerator;

public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL = ServiceGenerator.API_BASE_URL;

    public static Service getUserService(){
        return RetrofitClient.getClient(API_URL).create(Service.class);
    }

}
