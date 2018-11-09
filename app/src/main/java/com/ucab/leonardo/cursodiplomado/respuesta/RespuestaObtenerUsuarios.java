package com.ucab.leonardo.cursodiplomado.respuesta;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ucab.leonardo.cursodiplomado.modelos.Usuario;

import java.util.List;

public class RespuestaObtenerUsuarios {
    @SerializedName("usuarios")
    @Expose
    private List<Usuario> usuarios = null;

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
