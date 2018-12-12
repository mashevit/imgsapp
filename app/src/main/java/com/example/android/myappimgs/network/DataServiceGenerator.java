package com.example.android.myappimgs.network;


import com.example.android.myappimgs.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by delaroy on 4/30/18.*/



public class DataServiceGenerator {
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080/rest/");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(190, TimeUnit.SECONDS)
                .connectTimeout(190, TimeUnit.SECONDS)
                .writeTimeout(190, TimeUnit.SECONDS)
                .cache(null);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
        return  retrofit.create(serviceClass);
    }
}
