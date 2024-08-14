package com.tienda.online.modelo;

public class Empleado {

    private int idEmpleado;
    private String nombres;
    private String apellidos;
    private String sexo;
    private String email;
    private String telefono;
    private TipoIdentificacion tipoIdentifcacion;
    private boolean vigencia;
    private String numero_identificacion;
    private Cargo cargo;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoIdentificacion getTipoIdentifcacion() {
        return tipoIdentifcacion;
    }

    public void setTipoIdentifcacion(TipoIdentificacion tipoIdentifcacion) {
        this.tipoIdentifcacion = tipoIdentifcacion;
    }

    public boolean isVigencia() {
        return vigencia;
    }

    public void setVigencia(boolean vigencia) {
        this.vigencia = vigencia;
    }

    public String getNumero_identificacion() {
        return numero_identificacion;
    }

    public void setNumero_identificacion(String numero_identificacion) {
        this.numero_identificacion = numero_identificacion;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    /*public String getNombreCompletos(){
        return this.nombres + this.apellidos != null ? this.nombres + " " + this.apellidos : "---";
    }*/
    public String getTipoIdentificacion() {
        return this.tipoIdentifcacion != null ? this.tipoIdentifcacion.getNombre_tipo_identificacion() : "---";
    }

    public String getCargoString() {
        return this.cargo != null ? this.cargo.getNombre_cargo() : "---";
    }

    public String getVigenciaString() {
        return this.vigencia ? "Activo" : "Inactivo";
    }

}
