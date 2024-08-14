package com.tienda.online.modelo;

public class Reportes {

    private int id;
    private Cliente cliente;
    private Compra compra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public String getNombreCliente() {
        return this.cliente != null ? this.cliente.getNombres() : "";
    }

    public Double getMonto() {
        return this.compra != null ? this.compra.getMonto() : 0;
    }

    public String getFechaCompras() {
        return this.compra != null ? this.compra.getFecha() : "";
    }

    public String getDespachado() {
        return this.compra != null ? (this.compra.isDespachado() ? "Despachado" : "No Despachado") : "";
    }

    public String getAnulado() {
        return this.compra != null ? (this.compra.isAnularCompra() ? "Anulado" : "No Anulado") : "";
    }

}
