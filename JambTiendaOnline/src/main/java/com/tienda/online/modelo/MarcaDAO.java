package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO extends Conexion {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r = 0;

    //METODO LISTAR
    public List listar() {
        List lista = new ArrayList();
        String sql = "SELECT * FROM marca";
        try {
            ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Marca m = new Marca();
                m.setCodigo(rs.getInt(1));
                m.setMarca(rs.getString(2));
                m.setEstado(rs.getBoolean(3));
                lista.add(m);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public void registrar(Marca marca) throws Exception {
        Conexion conex;
        Connection connec = null;
        Statement st = null;
        String sql;

        sql = "INSERT INTO MARCA (nombre_marca, estado) "
                + "VALUES('" + marca.getMarca() + "',"
                + (marca.isEstado() == true ? "1" : "0") + ")";

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

    public void desactivarMarca(Marca mar) {
        String sql = "UPDATE marca SET estado = 0 where id=" + mar.getCodigo();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();//Elimina la marca que a seleccionado
        } catch (Exception e) {
            System.out.println("Error al desactivar" + e.getMessage());
        }
    }

    public void actualizar(Marca marca) throws Exception {
        Conexion conex;
        Connection connec = null;
        Statement st = null;
        String sql;

        sql = "UPDATE Marca SET nombre_marca = '" + marca.getMarca()
                + "', estado = " + (marca.isEstado() == true ? "1" : "0")
                + " WHERE id = " + marca.getCodigo();

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

    public Marca leer(Marca marca) throws Exception {
        Conexion conex;
        Connection connec = null;
        Statement st = null;
        Marca mar = null;
        ResultSet res = null;
        String sql = "SELECT M.id, M.nombre_marca, M.estado FROM Marca M WHERE M.id = " + marca.getCodigo();

        conex = new Conexion();
        try {
            connec = conex.getConnection();
            st = connec.createStatement();
            res = st.executeQuery(sql);
            if (res.next() == true) {
                mar = new Marca();
                mar.setCodigo(marca.getCodigo());
                mar.setMarca(res.getString("nombre_marca"));
                mar.setEstado(res.getBoolean("estado"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (res != null && res.isClosed() == false) {
                res.close();
            }
            res = null;
            if (st != null && st.isClosed() == false) {
                st.close();
            }
            st = null;
            if (connec != null && connec.isClosed() == false) {
                connec.close();
            }
            connec = null;
        }
        return mar;
    }

}
