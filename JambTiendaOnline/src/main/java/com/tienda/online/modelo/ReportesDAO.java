package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportesDAO extends Conexion {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r = 0;

    public List<Reportes> listarReportesPorCliente(int idC) {
        List<Reportes> reportes = null;
        Reportes r = null;
        String sql = "SELECT C.idCompras, cli.Nombres, c.FechaCompras, c.Monto, "
                + "c.Despachado, c.Anular FROM compras c INNER JOIN cliente cli "
                + "ON c.idCliente = cli.idCliente "
                + "WHERE c.idCliente = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idC);
            rs = ps.executeQuery();
            reportes = new ArrayList<>();
            while (rs.next()) {
                r = new Reportes();
                r.setId(rs.getInt("idCompras"));
                r.setCliente(new Cliente());
                r.getCliente().setNombres((rs.getString("Nombres")));
                r.setCompra(new Compra());
                r.getCompra().setFecha(rs.getString("FechaCompras"));
                r.getCompra().setMonto(rs.getDouble("Monto"));
                r.getCompra().setDespachado(rs.getBoolean("Despachado"));
                r.getCompra().setAnularCompra(rs.getBoolean("Anular"));
                reportes.add(r);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return reportes;
    }

    public List<Reportes> listarReportesByDespacho(boolean des) {
        List<Reportes> reportes = null;
        Reportes r = null;
        String sql = "SELECT c.idCompras, cli.Nombres, c.FechaCompras, c.Monto, "
                + "c.Despachado, c.Anular FROM compras C INNER JOIN cliente cli "
                + "ON c.idCliente = cli.idCliente "
                + "WHERE C.Despachado = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, des);
            rs = ps.executeQuery();
            reportes = new ArrayList<>();
            while (rs.next()) {
                r = new Reportes();
                r.setId(rs.getInt("idCompras"));
                r.setCliente(new Cliente());
                r.getCliente().setNombres((rs.getString("Nombres")));
                r.setCompra(new Compra());
                r.getCompra().setFecha(rs.getString("FechaCompras"));
                r.getCompra().setMonto(rs.getDouble("Monto"));
                r.getCompra().setDespachado(rs.getBoolean("Despachado"));
                r.getCompra().setAnularCompra(rs.getBoolean("Anular"));
                reportes.add(r);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return reportes;
    }

    public List<Reportes> listarReportesByAnulacion(boolean anulado) {
        List<Reportes> reportes = null;
        Reportes r = null;
        String sql = "SELECT c.idCompras, cli.Nombres, c.FechaCompras, c.Monto, "
                + "c.Despachado, c.Anular FROM compras C INNER JOIN cliente cli "
                + "ON c.idCliente = cli.idCliente "
                + "WHERE C.Anular = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, anulado);
            rs = ps.executeQuery();
            reportes = new ArrayList<>();
            while (rs.next()) {
                r = new Reportes();
                r.setId(rs.getInt("idCompras"));
                r.setCliente(new Cliente());
                r.getCliente().setNombres((rs.getString("Nombres")));
                r.setCompra(new Compra());
                r.getCompra().setFecha(rs.getString("FechaCompras"));
                r.getCompra().setMonto(rs.getDouble("Monto"));
                r.getCompra().setDespachado(rs.getBoolean("Despachado"));
                r.getCompra().setAnularCompra(rs.getBoolean("Anular"));
                reportes.add(r);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return reportes;
    }

    /*-------------------PÁGINA PRINCIPAL---------------------------*/
    //TOTAL DE PEDIDOS POR ENTREGAR.
    public List<Compra> contarTotalPedidosPorEntregar() throws Exception {
        List<Compra> compra;
        Compra com;
        ResultSet rs = null;
        String sql = "SELECT COUNT(c.idCompras) AS total FROM compras c WHERE c.Despachado = 0";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            compra = new ArrayList<>();
            while (rs.next() == true) {
                com = new Compra();
                com.setId(rs.getInt("total"));
                compra.add(com);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null && rs.isClosed() == false) {
                rs.close();
            }
            rs = null;
        }
        return compra;
    }

    //TOTAL DE PEDIDOS REALIZADOS EN EL CARRITO DE COMPRAS
    public List<Compra> contarTotalCompras() throws Exception {
        List<Compra> compra;
        Compra com;
        ResultSet rs = null;
        String sql = "SELECT COUNT(c.idCompras) AS totalVentas FROM compras c";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            compra = new ArrayList<>();
            while (rs.next() == true) {
                com = new Compra();
                com.setId(rs.getInt("totalVentas"));
                compra.add(com);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return compra;
    }

    //TOTAL DE CLIENTES REGISTRADOS
    public List<Cliente> totalClientes() throws Exception {
        List<Cliente> cliente;
        Cliente cli;
        ResultSet rs = null;
        String sql = "SELECT COUNT(c.idCliente) AS totalClientes FROM cliente c";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            cliente = new ArrayList<>();
            while (rs.next() == true) {
                cli = new Cliente();
                cli.setId(rs.getInt("totalClientes"));
                cliente.add(cli);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return cliente;
    }

    //MONTO TOTAL EN CAJA
    public List<Compra> montoTotal() throws Exception {
        List<Compra> compra;
        Compra com;
        ResultSet rs = null;
        String sql = "SELECT SUM(c.Monto) AS totalCaja FROM compras c";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            compra = new ArrayList<>();
            while (rs.next() == true) {
                com = new Compra();
                com.setMonto(rs.getDouble("totalCaja"));
                compra.add(com);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return compra;
    }

    /*---------------------------------REPORTES GRÁFICOS-------------------------*/
    //REPORTES DE COMPRAS EN LOS ÚLTIMOS AÑOS.
    public int obtenerContadorPorMesesAnio(int mes, int anio) {
        int contador = 0;
        String sql = "SELECT COUNT(C.FechaCompras) AS Contador FROM compras C WHERE YEAR(DATE(C.FechaCompras)) = ? AND MONTH(DATE(C.FechaCompras)) = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, anio);
            ps.setInt(2, mes);
            rs = ps.executeQuery();
            if (rs.next()) {
                contador = rs.getInt("Contador");
            }
        } catch (Exception e) {
            System.out.println("eror" + e.getMessage());
        }
        return contador;
    }

    //REPORTES DE COMPRAS EN LOS ÚLTIMOS MESES.
    public int obtenerContadoPorDiaSemana(String fechaInicial, String fechaFinal, int diasS) {
        int contador = 0;
        String sql = "SELECT COUNT(C.FechaCompras) AS Contador FROM compras C WHERE DATE(C.FechaCompras) BETWEEN ? AND ? AND DAYOFWEEK(C.FechaCompras) = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicial);
            ps.setString(2, fechaFinal);
            ps.setInt(3, diasS);
            rs = ps.executeQuery();
            if (rs.next()) {
                contador = rs.getInt("Contador");
            }
        } catch (Exception e) {
            System.out.println("eror" + e.getMessage());
        }
        return contador;
    }
    //REPORTES DE LOS 10 PRODUCTOS MAS VENDIDOS

    public Map<String, Object> top10ProductoMasVendidos() {
        Map<String, Object> top = new HashMap();
        List<String> nombresP = new ArrayList<>();
        List<Integer> contadores = new ArrayList<>();
        String sql = "SELECT P.Nombres, (SELECT COUNT(DC.idProducto) FROM detalle_compras DC WHERE DC.idProducto = P.idProducto) AS VENTAS FROM producto P ORDER BY VENTAS DESC LIMIT 10";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            while (rs.next()) {
                nombresP.add(rs.getString("Nombres"));
                contadores.add(rs.getInt("VENTAS"));
            }
            top.put("nombresP", nombresP);
            top.put("contadores", contadores);
            this.cerrar(false);
        } catch (Exception e) {
            System.out.println("eror" + e.getMessage());
        }
        return top;
    }
    
    //REPORRTES DE LOS 10 CLIENTES QUE MAS COMPRAN
    
      public Map<String, Object> top10ClientesQueMasCompran() {
        Map<String, Object> top = new HashMap();
        List<String> nombreCli = new ArrayList<>();
        List<Integer> contadores = new ArrayList<>();
        String sql = "SELECT C.Nombres, (SELECT COUNT(COM.idCliente) FROM compras COM WHERE COM.idCliente = C.idCliente) AS VENTAS FROM cliente C ORDER BY VENTAS DESC LIMIT 10";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            while (rs.next()) {
                nombreCli.add(rs.getString("Nombres"));
                contadores.add(rs.getInt("VENTAS"));
            }
            top.put("nombresCli", nombreCli);
            top.put("contadores", contadores);
            this.cerrar(false);
        } catch (Exception e) {
            System.out.println("eror" + e.getMessage());
        }
        return top;
    }
}
