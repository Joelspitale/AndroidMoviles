package com.example.myapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    //private static final String BASE_URL="http://10.0.2.2:8080/"; //para el emulador de la pc
    private static final String BASE_URL="http://192.168.0.41:8080/"; //direccion privada de mi pc, tengo que estar conectdo a la misma banda de wifi mi celu y la pc donde corro el server

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
