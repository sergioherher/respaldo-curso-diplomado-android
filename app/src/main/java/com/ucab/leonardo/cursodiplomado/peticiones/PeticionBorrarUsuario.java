package com.ucab.leonardo.cursodiplomado.peticiones;

public class PeticionBorrarUsuario {

    private String email;

    public PeticionBorrarUsuario(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
