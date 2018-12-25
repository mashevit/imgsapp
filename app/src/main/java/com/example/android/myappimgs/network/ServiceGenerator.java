package com.example.android.myappimgs.network;


import com.example.android.myappimgs.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String API_BASE_URL = "https://mytrips8.herokuapp.com/rest/";
    public static String Token = "";

  //  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

  /*  private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());



*/



    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_BASE_URL);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .cache(null);

        AuthenticationInterceptor interceptor =
                new AuthenticationInterceptor(Token);

        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);

           // builder.client(httpClient.build());
           // retrofit = builder.build();
        }
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
        return  retrofit.create(serviceClass);
    }




/*    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }


    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        Retrofit retrofit = null;
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }*/




    public static class AuthenticationInterceptor implements Interceptor {

        private String authToken;

        public AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Authorization", authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }
}
