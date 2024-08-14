package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO extends Conexion {

    public List<Empleado> listar() throws Exception {
        List<Empleado> empleados;
        Empleado emp;
        ResultSet rs;
        String sql = "SELECT E.id_empleado, E.nombres, E.apellidos, E.numero_identicacion, "
                + "E.telefono, TI.nombre_tipo_identifacion, C.nombre_cargo, "
                + "E.vigencia FROM empleado E INNER JOIN cargo C "
                + "on C.id_cargo = E.cargo_id INNER JOIN tipo_identificacion TI "
                + "on TI.id_tipo_identificacion = E.tipo_identificacion_id";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            empleados = new ArrayList<>();
            while (rs.next() == true) {
                emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setCargo(new Cargo());
                emp.getCargo().setNombre_cargo((rs.getString("nombre_cargo")));
                emp.setTipoIdentifcacion(new TipoIdentificacion());
                emp.getTipoIdentifcacion().setNombre_tipo_identificacion((rs.getString("nombre_tipo_identifacion")));
                emp.setTelefono(rs.getString("telefono"));
                emp.setNumero_identificacion(rs.getString("numero_identicacion"));
                emp.setNombres(rs.getString("nombres") + " " + (rs.getString("apellidos")));
                emp.setVigencia(rs.getBoolean("vigencia"));
                empleados.add(emp);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return empleados;
    }

    public void registrar(Empleado emp) throws Exception {
        String sql;
        sql = "INSERT INTO empleado (nombres, apellidos, email, telefono, sexo, "
                + "numero_identicacion, tipo_identificacion_id, cargo_id, vigencia)"
                + "VALUES('" + emp.getNombres() + "', '"
                + emp.getApellidos() + "', '"
                + emp.getEmail() + "', '"
                + emp.getTelefono() + "', '"
                + emp.getSexo() + "', '"
                + emp.getNumero_identificacion() + "', "
                + emp.getTipoIdentifcacion().getIdTipoIdentificacion() + ", "
                + emp.getCargo().getId_cargo() + ", "
                + (emp.isVigencia() == true ? "1" : "0") + ")";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public Empleado leer(Empleado empleado) throws Exception {
        Empleado emple = null;
        ResultSet rs = null;
        String sql = "SELECT E.id_empleado, E.nombres, E.apellidos, E.email, "
                + "E.sexo, E.telefono, E.tipo_identificacion_id, E.cargo_id, "
                + "E.vigencia, E.numero_identicacion FROM empleado E "
                + "WHERE E.id_empleado = " + empleado.getIdEmpleado();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                emple = new Empleado();
                emple.setIdEmpleado(empleado.getIdEmpleado());
                emple.setNombres(rs.getString("nombres"));
                emple.setApellidos(rs.getString("apellidos"));
                emple.setEmail(rs.getString("email"));
                emple.setTelefono(rs.getString("telefono"));
                emple.setSexo(rs.getString("sexo"));
                emple.setVigencia(rs.getBoolean("vigencia"));
                emple.setNumero_identificacion(rs.getString("numero_identicacion"));
                emple.setTipoIdentifcacion(new TipoIdentificacion());
                emple.getTipoIdentifcacion().setIdTipoIdentificacion(rs.getInt("tipo_identificacion_id"));
                emple.setCargo(new Cargo());
                emple.getCargo().setId_cargo(rs.getInt("cargo_id"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return emple;
    }

    public void actualizar(Empleado empleado) throws Exception {
        String sql;
        sql = "UPDATE Empleado SET nombres = '" + empleado.getNombres()
                + "', apellidos = '" + empleado.getApellidos()
                + "', email = '" + empleado.getEmail()
                + "', sexo = '" + empleado.getSexo()
                + "', telefono = '" + empleado.getTelefono()
                + "', numero_identicacion = '" + empleado.getNumero_identificacion()
                + "', vigencia = " + (empleado.isVigencia() == true ? "1" : "0")
                + ", tipo_identificacion_id = " + empleado.getTipoIdentifcacion().getIdTipoIdentificacion()
                + ", cargo_id = " + empleado.getCargo().getId_cargo()
                + " WHERE id_empleado = " + empleado.getIdEmpleado();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public void eliminar(Empleado empleado) throws Exception {
        String sql = "UPDATE empleado E SET E.vigencia = 0 WHERE id_empleado = " + empleado.getIdEmpleado();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }
}
