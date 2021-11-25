package com.example.myapplication.network;

import com.example.myapplication.modelo.Exhibits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceExhibits {
    //v1/8c99be4c-a98c-4920-ba2a-cee5cd5eb76a
    @GET("v1/a38e06e8-3b7d-43fc-a165-ee2a7ccd5c35")
    Call<List<Exhibits>> getAllExhibits();

    @GET("v1/20fc5fef-b1b4-440d-8062-4581e2a73478")
    Call<Exhibits> getOneFirstExhibit();

    @GET("v1/8e735e18-59d6-4c35-a838-f63b7615bd72")
    Call<Exhibits> getOneSecondExhibit();

    @GET("v1/d0532eb6-001e-48f8-9c94-39d2e6052b2f")
    Call<Exhibits> getOneThirdExhibit();

    @GET("v1/fb038cd4-6e42-4560-b867-0fd086f37e42")
    Call<Exhibits> getOneFourthExhibit();

    @GET("v1/417d8944-8d1a-45fc-8f21-f2c0f5e895ef")
    Call<Exhibits> getOneFifthExhibit();

    @GET("v1/c3049623-7b5a-4999-90c8-4f4a817ced49")
    Call<Exhibits> getOneSixthExhibit();

    @GET("v1/65c19be1-3c32-4ef4-9673-0837b9ef5fa1")
    Call<Exhibits> getOneSeventhExhibit();

    @GET("v1/7429634a-957c-4a29-b47b-f82387023e80")
    Call<Exhibits> getOneEighthExhibit();

    @GET("v1/c12e83ef-9ea2-43b8-b13e-1197a4200bc4")
    Call<Exhibits> getOneNinthExhibit();

    @GET("v1/cf78d999-d60a-4d19-b976-c456fc8f1d59")
    Call<Exhibits> getOneTenthExhibit();

}
