package com.example.myapplication.network;

import com.example.myapplication.modelo.Exhibits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceExhibits {

    @GET("v1/3b3782a8-c219-45e9-9291-f598334bbb48")
    Call<List<Exhibits>> getAllExhibits();
}
