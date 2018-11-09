package com.ucab.leonardo.cursodiplomado;

public class PeticionCrearUsuario {
    private String nombre;
    private String apellido;
    private String empresa;
    private String direccion;
    private String edad;
    private String email;

    public PeticionCrearUsuario(String nombre, String apellido, String empresa, String direccion, String edad, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.empresa = empresa;
        this.direccion = direccion;
        this.edad = edad;
        this.email = email;
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
}
