package com.example.myapplication.network;

import com.example.myapplication.modelo.ItemMuseo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceExhibits {

    @GET("/find-all-items-museo-dto")
    Call<List<ItemMuseo>> getAllItemsMuseo();

    @GET("/find-one-items-museo/{id}")
    Call<ItemMuseo> getOneItemMuseoById(@Path(value = "id", encoded = true) long id);

}
