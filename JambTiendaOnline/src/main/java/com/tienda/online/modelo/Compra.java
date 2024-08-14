
package com.tienda.online.modelo;

public class Compra {
    int id;
    int idCliente;
    String fecha;
    Double monto;
    int idPago;
    String estado;
    boolean despachado;
    String nombresClienteDni;
    boolean anularCompra;

    public Compra() {
    }

    public Compra(int id, int idCliente, String fecha, Double monto, int idPago, String estado, boolean despachado, String nombresClienteDni, boolean anularCompra) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.monto = monto;
        this.idPago = idPago;
        this.estado = estado;
        this.despachado = despachado;
        this.nombresClienteDni = nombresClienteDni;
        this.anularCompra = anularCompra;
    }

    public boolean isAnularCompra() {
        return anularCompra;
    }

    public void setAnularCompra(boolean anularCompra) {
        this.anularCompra = anularCompra;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isDespachado() {
        return despachado;
    }

    public void setDespachado(boolean despachado) {
        this.despachado = despachado;
    }

    public String getNombresClienteDni() {
        return nombresClienteDni;
    }

    public void setNombresClienteDni(String nombresClienteDni) {
        this.nombresClienteDni = nombresClienteDni;
    }

    
    

   
   
}
