package com.ucab.leonardo.cursodiplomado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestCrearUsuario {
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
