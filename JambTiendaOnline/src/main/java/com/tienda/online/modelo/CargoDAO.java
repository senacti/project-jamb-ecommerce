package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO extends Conexion {

    public List<Cargo> listar() throws Exception {
        List<Cargo> cargo;
        Cargo car;
        ResultSet rs;
        String sql = "SELECT C.id_cargo, c.nombre_cargo, C.vigencia "
                + "FROM cargo C WHERE C.vigencia = 1";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            cargo = new ArrayList<>();
            while (rs.next() == true) {
                car = new Cargo();
                car.setId_cargo(rs.getInt("id_cargo"));
                car.setNombre_cargo(rs.getString("nombre_cargo"));
                car.setVigencia(rs.getBoolean("vigencia"));
                cargo.add(car);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return cargo;
    }

    public void registrar(Cargo cargo) throws Exception {
        String sql;

        sql = "INSERT INTO cargo (nombre_cargo, vigencia) "
                + "VALUES('" + cargo.getNombre_cargo() + "',"
                + (cargo.isVigencia() == true ? "1" : "0") + ")";

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void desactivarCargo(Cargo cargo) {
        String sql = "UPDATE cargo SET vigencia = 0 WHERE id_cargo = " + cargo.getId_cargo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(false);
        } catch (Exception e) {
            System.out.println("Error al desactivar" + e.getMessage());
        }
    }

    public Cargo leer(Cargo cargo) throws Exception {
        Cargo car = null;
        ResultSet rs = null;
        String sql = "SELECT C.id_cargo, C.nombre_cargo, C.vigencia "
                + "FROM cargo C WHERE C.id_cargo = " + cargo.getId_cargo();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                car = new Cargo();
                car.setId_cargo(cargo.getId_cargo());
                car.setNombre_cargo(rs.getString("nombre_cargo"));
                car.setVigencia(rs.getBoolean("vigencia"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return car;
    }

    public void actualizar(Cargo cargo) throws Exception {
        String sql;
        sql = "UPDATE Cargo SET nombre_cargo = '" + cargo.getNombre_cargo()
                + "', vigencia = " + (cargo.isVigencia() == true ? "1" : "0")
                + " WHERE id_cargo = " + cargo.getId_cargo();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public boolean validacion(Cargo car) throws Exception {
        boolean repetido = false;
        ResultSet rs = null;
        String sql = "SELECT COUNT(id_cargo) FROM Cargo "
                + "WHERE nombre_cargo = '" + car.getNombre_cargo().trim() + "' AND id_cargo <> " + car.getId_cargo();
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
