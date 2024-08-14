
package com.tienda.online.modelo;

public class TipoIdentificacion {
    private int idTipoIdentificacion;
    private String nombre_tipo_identificacion;
    private boolean vigencia;

    public int getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(int idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public String getNombre_tipo_identificacion() {
        return nombre_tipo_identificacion;
    }

    public void setNombre_tipo_identificacion(String nombre_tipo_identificacion) {
        this.nombre_tipo_identificacion = nombre_tipo_identificacion;
    }

    public boolean isVigencia() {
        return vigencia;
    }

    public void setVigencia(boolean vigencia) {
        this.vigencia = vigencia;
    }
    
    
}
