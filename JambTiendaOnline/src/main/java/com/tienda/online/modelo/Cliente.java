
package com.tienda.online.modelo;

public class Cliente {
    int id;
    String numero_doc;
    String nombres;
    String email;
    String pass;
    String direccion;
    TipoIdentificacion tipoIdentificacion;

    public Cliente() {
    }

    public Cliente(int id, String numero_doc, String Nombres, String email, String pass, String direccion, TipoIdentificacion tipoIdentificacion) {
        this.id = id;
        this.numero_doc = numero_doc;
        this.nombres = Nombres;
        this.email = email;
        this.pass = pass;
        this.direccion = direccion;
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero_doc() {
        return numero_doc;
    }

    public void setNumero_doc(String numero_doc) {
        this.numero_doc = numero_doc;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String Nombres) {
        this.nombres = Nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    
}
