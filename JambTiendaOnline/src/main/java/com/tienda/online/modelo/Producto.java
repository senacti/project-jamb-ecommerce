package com.tienda.online.modelo;

import java.io.InputStream;

public class Producto {

    int id;
    String nombres;
    String imagen;
    InputStream foto;
    String descripcion;
    double precio;
    int stock;
    Categoria categoria;
    Marca marca;
    Proveedor proveedor;
    boolean estado;

    public Producto() {
    }

    public Producto(int id, String nombres, String imagen, InputStream foto, String descripcion, double precio, int stock, Categoria categoria, Marca marca, Proveedor proveedor, boolean estado) {
        this.id = id;
        this.nombres = nombres;
        this.imagen = imagen;
        this.foto = foto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.marca = marca;
        this.proveedor = proveedor;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public InputStream getFoto() {
        return foto;
    }

    public void setFoto(InputStream foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombreCategoria() {
        return this.categoria != null ? this.categoria.getNombrecate() : "---";
    }

    public String getNombreMarca() {
        return this.marca != null ? this.marca.getMarca() : "---";
    }

    public String getNombreProveedor() {
        return this.proveedor != null ? this.proveedor.getRazon_social() : "---";
    }

    public String getEstadoString() {
        return this.estado ? "Activo" : "Inactivo";
    }

}
