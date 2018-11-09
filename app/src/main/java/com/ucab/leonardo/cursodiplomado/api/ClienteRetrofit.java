package com.ucab.leonardo.cursodiplomado.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteRetrofit {
    private static final String URL_BASE = "https://curso-diplomado-backend.herokuapp.com";
    private static Retrofit retrofit;

    public static Retrofit obtenerClienteRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
