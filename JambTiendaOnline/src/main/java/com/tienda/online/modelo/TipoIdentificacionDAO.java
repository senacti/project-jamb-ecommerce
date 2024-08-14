package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipoIdentificacionDAO extends Conexion {

    public List<TipoIdentificacion> listar() throws Exception {
        List<TipoIdentificacion> tipoIdentificacion;
        TipoIdentificacion ti;
        ResultSet rs;
        String sql = "SELECT TI.id_tipo_identificacion, TI.nombre_tipo_identifacion, "
                + "TI.vigencia FROM tipo_identificacion TI WHERE TI.vigencia = 1";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            tipoIdentificacion = new ArrayList<>();
            while (rs.next() == true) {
                ti = new TipoIdentificacion();
                ti.setIdTipoIdentificacion(rs.getInt("id_tipo_identificacion"));
                ti.setNombre_tipo_identificacion(rs.getString("nombre_tipo_identifacion"));
                ti.setVigencia(rs.getBoolean("vigencia"));
                tipoIdentificacion.add(ti);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return tipoIdentificacion;
    }

    public void registrar(TipoIdentificacion tipo) throws Exception {
        String sql;

        sql = "INSERT INTO tipo_identificacion (nombre_tipo_identifacion, vigencia) "
                + "VALUES('" + tipo.getNombre_tipo_identificacion() + "',"
                + (tipo.isVigencia() == true ? "1" : "0") + ")";

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void desactivarTipoIdentificacion(TipoIdentificacion tp) {
        String sql = "UPDATE tipo_identificacion SET vigencia = 0 WHERE id_tipo_identificacion = " + tp.getIdTipoIdentificacion();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(false);
        } catch (Exception e) {
            System.out.println("Error al desactivar" + e.getMessage());
        }
    }

    public TipoIdentificacion leer(TipoIdentificacion tipo) throws Exception {
        TipoIdentificacion tp = null;
        ResultSet rs = null;
        String sql = "SELECT TI.id_tipo_identificacion, TI.nombre_tipo_identifacion, TI.vigencia "
                + "FROM tipo_identificacion TI WHERE TI.id_tipo_identificacion = " + tipo.getIdTipoIdentificacion();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                tp = new TipoIdentificacion();
                tp.setIdTipoIdentificacion(tipo.getIdTipoIdentificacion());
                tp.setNombre_tipo_identificacion(rs.getString("nombre_tipo_identifacion"));
                tp.setVigencia(rs.getBoolean("vigencia"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return tp;
    }

    public void actualizar(TipoIdentificacion tp) throws Exception {
        String sql;
        sql = "UPDATE tipo_identificacion SET nombre_tipo_identifacion = '" + tp.getNombre_tipo_identificacion()
                + "', vigencia = " + (tp.isVigencia() == true ? "1" : "0")
                + " WHERE id_tipo_identificacion = " + tp.getIdTipoIdentificacion();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public boolean validacion(TipoIdentificacion tp) throws Exception {
        boolean repetido = false;
        ResultSet rs = null;
        String sql = "SELECT COUNT(id_tipo_identificacion) FROM tipo_identificacion "
                + "WHERE nombre_tipo_identifacion = '" + tp.getNombre_tipo_identificacion().trim() + "' AND id_tipo_identificacion <> " + tp.getIdTipoIdentificacion();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            rs.next();
            repetido = rs.getBoolean(1);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return repetido;
    }
}
