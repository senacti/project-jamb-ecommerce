package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO extends Conexion {

    public List<Proveedor> listar() throws Exception {
        List<Proveedor> proveedores;
        Proveedor prove;
        ResultSet rs;
        String sql = "SELECT P.id, P.razon_social, P.direccion, P.telefono, "
                + "P.email, TI.nombre_tipo_identifacion, P.numero_doc, "
                + "P.estado FROM proveedor P INNER JOIN tipo_identificacion TI "
                + "ON TI.id_tipo_identificacion = p.tipo_identificacion_id";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            proveedores = new ArrayList<>();
            while (rs.next() == true) {
                prove = new Proveedor();
                prove.setId(rs.getInt("id"));
                prove.setTipoIdentificacion(new TipoIdentificacion());
                prove.getTipoIdentificacion().setNombre_tipo_identificacion((rs.getString("nombre_tipo_identifacion")));
                prove.setRazon_social(rs.getString("razon_social"));
                prove.setDireccion(rs.getString("direccion"));
                prove.setTelefono(rs.getString("telefono"));
                prove.setEmail(rs.getString("email"));
                prove.setNumero_doc(rs.getString("numero_doc"));
                prove.setEstado(rs.getBoolean("estado"));
                proveedores.add(prove);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return proveedores;
    }

    public void registrar(Proveedor pro) throws Exception {
        String sql;
        sql = "INSERT INTO proveedor (razon_social, direccion, telefono, email, "
                + "tipo_identificacion_id, numero_doc, estado)"
                + "VALUES('" + pro.getRazon_social() + "', '"
                + pro.getDireccion() + "', '"
                + pro.getTelefono() + "', '"
                + pro.getEmail() + "', "
                + pro.getTipoIdentificacion().getIdTipoIdentificacion() + ", '"
                + pro.getNumero_doc() + "', 1)";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public Proveedor leer(Proveedor proveedor) throws Exception {
        Proveedor provee = null;
        ResultSet rs = null;
        String sql = "SELECT P.id, P.razon_social, P.direccion, P.telefono, "
                + "P.email, P.tipo_identificacion_id, P.numero_doc, "
                + "P.estado FROM proveedor P WHERE P.id=" + proveedor.getId();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                provee = new Proveedor();
                provee.setId(proveedor.getId());
                provee.setRazon_social(rs.getString("razon_social"));
                provee.setDireccion(rs.getString("direccion"));
                provee.setTelefono(rs.getString("telefono"));
                provee.setEmail(rs.getString("email"));
                provee.setTipoIdentificacion(new TipoIdentificacion());
                provee.getTipoIdentificacion().setIdTipoIdentificacion(rs.getInt("tipo_identificacion_id"));
                provee.setNumero_doc(rs.getString("numero_doc"));
                provee.setEstado(rs.getBoolean("estado"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return provee;
    }

    public void actualizar(Proveedor pro) throws Exception {
        String sql;
        sql = "UPDATE Proveedor SET razon_social = '" + pro.getRazon_social()
                + "', direccion = '" + pro.getDireccion()
                + "', email = '" + pro.getEmail()
                + "', telefono = '" + pro.getTelefono()
                + "', tipo_identificacion_id = " + pro.getTipoIdentificacion().getIdTipoIdentificacion()
                + ", numero_doc = '" + pro.getNumero_doc()
                + "', estado = " + (pro.isEstado() == true ? "1" : "0")
                + " WHERE id = " + pro.getId();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public void desactivarProveedor(Proveedor proveedor) throws Exception {
        String sql = "UPDATE proveedor SET estado = 0 WHERE id = " + proveedor.getId();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            System.out.println("Error al desactivar" + e.getMessage());
            this.cerrar(false);
        }
    }

    public boolean validacion(Proveedor pro) throws Exception {
        boolean repetido = false;
        ResultSet rs = null;
        String sql = "SELECT COUNT(id) FROM proveedor "
                + "WHERE razon_social = '" + pro.getRazon_social().trim()
                + "' OR numero_doc = '" + pro.getNumero_doc().trim()
                + "' AND id <> " + pro.getId();
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
