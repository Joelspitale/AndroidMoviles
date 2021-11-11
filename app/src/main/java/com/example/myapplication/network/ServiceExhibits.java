package com.example.myapplication.network;

import com.example.myapplication.modelo.Exhibits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceExhibits {
//3b3782a8-c219-45e9-9291-f598334bbb48
    @GET("v1/8c99be4c-a98c-4920-ba2a-cee5cd5eb76a")
    Call<List<Exhibits>> getAllExhibits();

    @GET("v1/f078b696-865a-4d65-9825-faa9c94c1f63")
    Call<Exhibits> getOneFirstExhibit();

    @GET("v1/f894a3f2-d9b9-4b6c-bcdd-bc8f72f527a4")
    Call<Exhibits> getOneSecondExhibit();

    @GET("v1/f529918a-17b1-4d3b-9604-e13c0ab2999d")
    Call<Exhibits> getOneThirdExhibit();

    @GET("v1/9ea9bc15-49b4-438a-8980-b0fd6241fdca")
    Call<Exhibits> getOneFourthExhibit();

    @GET("v1/38d1b40a-5998-4caf-a51d-7610caab34af")
    Call<Exhibits> getOneFifthExhibit();

    @GET("v1/60e1451c-8789-4f1d-92ea-330ebe546f68")
    Call<Exhibits> getOneSixthExhibit();

    @GET("v1/ce787d51-c3d2-4079-893b-4d32cdc637ff")
    Call<Exhibits> getOneSeventhExhibit();

    @GET("v1/0037a2f6-5235-49c2-8d31-ec5c0bb542e1")
    Call<Exhibits> getOneEighthExhibit();

    @GET("v1/1e01247a-68a5-4a93-a479-671a0014589e")
    Call<Exhibits> getOneNinthExhibit();

    @GET("v1/a139dbdf-4912-4200-8db1-538eb0134939")
    Call<Exhibits> getOneTenthExhibit();

}
