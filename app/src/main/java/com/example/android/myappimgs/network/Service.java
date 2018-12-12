package com.example.android.myappimgs.network;

import com.example.android.myappimgs.ImgModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface  Service {


    @GET("trippic/5")
    Call<List<ImgModel>> getQuestions();
}
