package com.ucab.leonardo.cursodiplomado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("empresa")
    @Expose
    private String empresa;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("edad")
    @Expose
    private String edad;

    @SerializedName("email")
    @Expose
    private String email;

    private int imagen;

    public Usuario(String nombre, String apellido, String empresa, String direccion, String edad,
                   String email, int imagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.empresa = empresa;
        this.direccion = direccion;
        this.edad = edad;
        this.email = email;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
