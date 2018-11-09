package com.ucab.leonardo.cursodiplomado;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/usuarios")
    Call<RespuestaObtenerUsuarios> obtenerUsuarios();

    @POST("/usuarios")
    Call<RespuestCrearUsuario> crearUsuario(@Body PeticionCrearUsuario peticion);
}
