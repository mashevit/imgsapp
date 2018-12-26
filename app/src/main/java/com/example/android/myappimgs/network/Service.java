package com.example.android.myappimgs.network;

import com.example.android.myappimgs.ImgModel;
import com.example.android.myappimgs.pojos.TripsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface  Service {


    @GET("trippicmain/{id}/")
    Call<List<ImgModel>> getQuestions(@Path("id") int id);
    @GET("trippicmain/3/")
    Call<List<Object>> getQuestions1();

    @GET("gettrips/")
    Call<List<TripsModel>> getTrips();


    @POST("login1")
    Call<Void> gettoken(@Body User body);
}
