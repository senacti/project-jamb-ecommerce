package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends Conexion {

    public Usuario identificar(Usuario usuario) throws Exception {
        Usuario usu = null;
        ResultSet rs = null;
        String sql = "SELECT E.id_empleado, E.nombres, E.apellidos, E.email, "
                + "C.nombre_cargo FROM empleado E INNER JOIN usuario U "
                + "ON U.empleado_id = E.id_empleado INNER JOIN cargo C "
                + "ON c.id_cargo = E.cargo_id WHERE u.nombre_usuario = '" + usuario.getUsuario() + "' AND u.clave = '" + usuario.getClave() + "' AND U.vigencia = 1";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usu = new Usuario();
                usu.setIdEmpleado(rs.getInt("id_empleado"));
                usu.setOnlynames(rs.getString("nombres"));
                usu.setNombres(rs.getString("nombres") + " " + rs.getString("apellidos"));
                usu.setCargo(rs.getString("nombre_cargo"));
                usu.setCorreo(rs.getString("email"));
                usu.setVigencia(true);

            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return usu;
    }

    public List<Usuario> listarUsuarios() throws Exception {
        List<Usuario> usuarios;
        Usuario usu;
        ResultSet rs;
        String sql = "SELECT U.id_usuario, U.nombre_usuario, U.clave, "
                + "U.vigencia, E.nombres, E.apellidos FROM usuario U "
                + "INNER JOIN empleado E on U.empleado_id = E.id_empleado";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            usuarios = new ArrayList<>();
            while (rs.next() == true) {
                usu = new Usuario();
                usu.setIdLogin(rs.getInt("id_usuario"));
                usu.setUsuario(rs.getString("nombre_usuario"));
                usu.setClave(rs.getString("clave"));
                usu.setVigencia(rs.getBoolean("vigencia"));
                usu.setNombres(rs.getString("nombres") + " " + (rs.getString("apellidos")));
                usuarios.add(usu);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return usuarios;
    }

    public List<Empleado> listarEmpleadosSinLogin() throws Exception {
        List<Empleado> empleado;
        Empleado emp;
        ResultSet rs;
        String sql = "SELECT E.id_empleado, E.nombres, E.apellidos "
                + "FROM empleado E WHERE NOT EXISTS(SELECT * FROM usuario U WHERE U.empleado_id=E.id_empleado)";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            empleado = new ArrayList<>();
            while (rs.next() == true) {
                emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombres(rs.getString("nombres") + " " + (rs.getString("apellidos")));
                empleado.add(emp);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return empleado;
    }

    public void registrarUsuarios(Usuario usu) throws Exception {
        String sql;
        sql = "INSERT INTO usuario (nombre_usuario, clave, empleado_id, vigencia)"
                + "VALUES('" + usu.getUsuario() + "', '"
                + usu.getClave() + "', "
                + usu.getEmpleado().getIdEmpleado() + ", 1)";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public Usuario leerUsuario(Usuario usu) throws Exception {
        Usuario usus = null;
        ResultSet rs = null;
        String sql = "SELECT U.*, E.nombres, E.apellidos FROM usuario U INNER JOIN empleado E "
                + "ON E.id_empleado = U.empleado_id WHERE U.id_usuario = " + usu.getIdLogin();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usus = new Usuario();
                usus.setIdLogin(usu.getIdLogin());
                usus.setUsuario(rs.getString("nombre_usuario"));
                usus.setClave(rs.getString("clave"));
                usus.setVigencia(rs.getBoolean("vigencia"));
                usus.setEmpleado(new Empleado());
                usus.getEmpleado().setIdEmpleado(rs.getInt("empleado_id"));
                usus.getEmpleado().setNombres(rs.getString("nombres") + " " + (rs.getString("apellidos")));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return usus;
    }

    public void actualizarUsuario(Usuario usuario) throws Exception {
        String sql;
        sql = "UPDATE Usuario SET nombre_usuario = '" + usuario.getUsuario()
                + "', clave = '" + usuario.getClave()
                + "', empleado_id = " + usuario.getEmpleado().getIdEmpleado()
                + " WHERE id_usuario = " + usuario.getIdLogin();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public void desactivarUsuarios(Usuario usu) throws Exception {
        String sql = "UPDATE usuario U SET U.vigencia = 0 WHERE id_usuario = " + usu.getIdLogin();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    /*--------------------------RECUPERAR CONTRASEÑA-------------------------*/
    public Usuario solicitarCambioContrasenia(Usuario usu) throws Exception {
        Usuario usus = null;
        ResultSet rs = null;
        String sql = "SELECT U.nombre_usuario, E.nombres, E.apellidos FROM usuario U INNER JOIN empleado E ON "
                + "E.id_empleado = U.empleado_id "
                + "WHERE U.nombre_usuario = '" + usu.getUsuario() + "'"; //El dato que envia es string por eso '""'
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usus = new Usuario();
                usus.setUsuario(rs.getString("nombre_usuario"));
                usus.setNombres(rs.getString("nombres") + " " + (rs.getString("apellidos")));
            }
        } catch (Exception e) {
            System.out.println("Error : " +  e.getMessage());
            throw e;           
        } finally {
            this.cerrar(false);
        }
        return usus;
    }

    public void cambiarContrasenia(Usuario usuario) throws Exception {
        String sql;
        sql = "UPDATE Usuario SET clave = '" + usuario.getClave()
                + "' WHERE nombre_usuario = '" + usuario.getUsuario() + "'";

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
