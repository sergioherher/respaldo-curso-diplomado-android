package com.ucab.leonardo.cursodiplomado.api;

import com.ucab.leonardo.cursodiplomado.peticiones.PeticionActualizarUsuario;
import com.ucab.leonardo.cursodiplomado.peticiones.PeticionCrearUsuario;
import com.ucab.leonardo.cursodiplomado.peticiones.PeticionBorrarUsuario;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaCrearUsuario;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaActualizarUsuario;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaObtenerUsuarios;
import com.ucab.leonardo.cursodiplomado.respuesta.RespuestaBorraUsuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.DELETE;

public interface ApiService {
    @GET("/usuarios")
    Call<RespuestaObtenerUsuarios> obtenerUsuarios();

    @Headers("Content-Type: application/json")
    @POST("/usuarios")
    Call<RespuestaCrearUsuario> crearUsuario(@Body PeticionCrearUsuario peticion);

    @Headers("Content-Type: application/json")
    @PUT("/usuarios/{email}")
    Call<RespuestaActualizarUsuario> actualizarUsuario(@Path("email") String email,
                                                       @Body PeticionActualizarUsuario peticion);

    @Headers("Content-Type: application/json")
    @DELETE("/usuarios/{email}")
    Call<RespuestaBorraUsuario> borrarUsuario(@Path("email") String email,
                                                       @Body PeticionBorrarUsuario peticion);
}
