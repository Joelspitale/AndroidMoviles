package com.example.myapplication.network;

import com.example.myapplication.modelo.ItemMuseo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceExhibits {

    @GET("v1/find-all-items-museo-dto")
    Call<List<ItemMuseo>> getAllItemsMuseo();

    @GET("v1/find-all-items-museo")
    Call<List<ItemMuseo>> getAllItemsMuseoComplete();

    @GET("v1/find-one-items-museo/{id}")
    Call<ItemMuseo> getOneItemMuseoById(@Path(value = "id", encoded = true) long id);

}
