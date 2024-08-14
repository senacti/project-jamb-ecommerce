package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends Conexion {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r = 0;

    public List listar() {
        List lista = new ArrayList();
        String sql = "SELECT * FROM categoria";
        try {
            ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setCodigo(rs.getInt(1));
                c.setNombrecate(rs.getString(2));
                c.setEstado(rs.getBoolean(3));
                lista.add(c);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public void registrar(Categoria categoria) throws Exception {
        Conexion conex;
        Connection connec = null;
        Statement st = null;
        String sql;

        sql = "INSERT INTO Categoria (nombre_categoria, estado) "
                + "VALUES('" + categoria.getNombrecate() + "',"
                + (categoria.isEstado() == true ? "1" : "0") + ")";

        conex = new Conexion();
        try {
            connec = conex.getConnection();
            st = connec.createStatement();
            st.executeUpdate(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null && st.isClosed() == false) {
                st.close();
            }
            st = null;
            if (connec != null && connec.isClosed() == false) {
                connec.close();
            }
            connec = null;
        }
    }

    public void desactivarCategoria(Categoria categoria) {
        String sql = "UPDATE categoria SET estado = 0 WHERE id = " + categoria.getCodigo();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al desactivar" + e.getMessage());
        }
    }

    public Categoria leer(Categoria categoria) throws Exception {
        Categoria cate = null;
        ResultSet rs = null;
        String sql = "SELECT C.id, C.nombre_categoria, C.estado "
                + "FROM categoria C WHERE C.id = " + categoria.getCodigo();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                cate = new Categoria();
                cate.setCodigo(categoria.getCodigo());
                cate.setNombrecate(rs.getString("nombre_categoria"));
                cate.setEstado(rs.getBoolean("estado"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return cate;
    }

    public void actualizar(Categoria categoria) throws Exception {
        String sql;
        sql = "UPDATE Categoria SET nombre_categoria = '" + categoria.getNombrecate()
                + "', estado = " + (categoria.isEstado() == true ? "1" : "0")
                + " WHERE id = " + categoria.getCodigo();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public boolean validacion(Categoria cat) throws Exception {
        boolean repetido = false;
        ResultSet rs = null;
        String sql = "SELECT COUNT(id) FROM categoria "
                + "WHERE nombre_categoria = '" + cat.getNombrecate().trim() + "' AND id <> " + cat.getCodigo();
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
