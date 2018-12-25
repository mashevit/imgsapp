package com.example.android.myappimgs.network;

import com.example.android.myappimgs.ImgModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface  Service {


    @GET("trippic/3/")
    Call<List<ImgModel>> getQuestions();
    @GET("trippic/3/")
    Call<List<Object>> getQuestions1();

    @POST("login1")
    Call<Void> gettoken(@Body User body);
}
