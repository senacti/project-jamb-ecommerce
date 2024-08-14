package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends Conexion {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public Cliente Validar(String email, String pass) {
        String sql = "select * from cliente where Email=? and Password=?";
        Cliente c = new Cliente();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt(1));
                c.setNumero_doc(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setDireccion(rs.getString(4));
                c.setEmail(rs.getString(5));
                c.setPass(rs.getString(6));
            }
        } catch (Exception e) {
        }
        return c;
    }

    public int AgregarCliente(Cliente c) {
        String sql = "INSERT INTO cliente (Numero_doc, Nombres, Direccion, Email, Password, tipo_identificacion_id)values(?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNumero_doc());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getPass());
            ps.setInt(6, 1);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return 1;
    }

    public List<Cliente> listar() throws Exception {
        List<Cliente> clientes;
        Cliente cli;
        ResultSet rs;
        String sql = "SELECT C.idCliente, C.Nombres, TI.nombre_tipo_identifacion, "
                + "C.Numero_doc, C.Direccion, C.Email, C.Password "
                + "FROM cliente C INNER JOIN tipo_identificacion TI "
                + "ON TI.id_tipo_identificacion = C.tipo_identificacion_id";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            clientes = new ArrayList<>();
            while (rs.next() == true) {
                cli = new Cliente();
                cli.setId(rs.getInt("idCliente"));
                cli.setTipoIdentificacion(new TipoIdentificacion());
                cli.getTipoIdentificacion().setNombre_tipo_identificacion((rs.getString("nombre_tipo_identifacion")));
                cli.setNumero_doc(rs.getString("Numero_doc"));
                cli.setNombres(rs.getString("Nombres"));
                cli.setDireccion(rs.getString("Direccion"));
                cli.setEmail(rs.getString("Email"));
                cli.setPass(rs.getString("Password"));
                clientes.add(cli);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return clientes;
    }

    public void registrar(Cliente cli) throws Exception {
        String sql;
        sql = "INSERT INTO cliente (nombres, direccion, email, password, "
                + "tipo_identificacion_id, numero_doc)"
                + "VALUES('" + cli.getNombres() + "', '"
                + cli.getDireccion() + "', '"
                + cli.getEmail() + "', '"
                + cli.getPass() + "', "
                + cli.getTipoIdentificacion().getIdTipoIdentificacion() + ", "
                + cli.getNumero_doc() + ")";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public Cliente leer(Cliente cliente) throws Exception {
        Cliente clien = null;
        ResultSet rs = null;
        String sql = "SELECT C.idCliente, C.nombres, C.direccion, C.email, "
                + "C.tipo_identificacion_id, C.numero_doc, C.password "
                + "FROM cliente C WHERE C.idCliente = " + cliente.getId();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                clien = new Cliente();
                clien.setId(cliente.getId());
                clien.setNombres(rs.getString("nombres"));
                clien.setDireccion(rs.getString("direccion"));
                clien.setEmail(rs.getString("email"));
                clien.setPass(rs.getString("password"));
                clien.setTipoIdentificacion(new TipoIdentificacion());
                clien.getTipoIdentificacion().setIdTipoIdentificacion(rs.getInt("tipo_identificacion_id"));
                clien.setNumero_doc(rs.getString("numero_doc"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return clien;
    }

    public void actualizar(Cliente cliente) throws Exception {
        String sql;
        sql = "UPDATE Cliente SET nombres = '" + cliente.getNombres()
                + "', direccion = '" + cliente.getDireccion()
                + "', email = '" + cliente.getEmail()
                + "', password = '" + cliente.getPass()
                + "', tipo_identificacion_id = " + cliente.getTipoIdentificacion().getIdTipoIdentificacion()
                + ", numero_doc = '" + cliente.getNumero_doc()
                + "' WHERE idCliente = " + cliente.getId();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }
}
