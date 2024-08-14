package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ComprasDAO extends Conexion {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public int IdCompra() {
        int idc = 0;
        String sql = "select max(idCompras) from compras";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                idc = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return idc;
    }

    public int guardarCompra(Compra co) {
        String sql = "insert into Compras(idCliente,idPago,FechaCompras,Monto,Estado,Despachado,Anular)values(?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, co.getIdCliente());
            ps.setInt(2, co.getIdPago());
            ps.setString(3, co.getFecha());
            ps.setDouble(4, co.getMonto());
            ps.setString(5, co.getEstado());
            ps.setBoolean(6, co.isDespachado());
            ps.setBoolean(7, co.isAnularCompra());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return 1;
    }

    public int guardarDetalleCompra(DetalleCompra dc) {
        String sql = "insert into Detalle_Compras(idProducto,idCompras, Cantidad, PrecioCompra)values(?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, dc.getIdproducto());
            ps.setInt(2, dc.getIdcompra());
            ps.setInt(3, dc.getCantidad());
            ps.setDouble(4, dc.getPrecioCompra());
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
        }
        return 1;
    }

    public List misCompras(int id) {
        List lista = new ArrayList();
        String sql = "select * from compras where idCliente=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Compra com = new Compra();
                com.setId(rs.getInt(1));
                com.setIdCliente(rs.getInt(2));
                com.setIdPago(rs.getInt(3));
                com.setFecha(rs.getString(4));
                com.setMonto(rs.getDouble(5));
                com.setEstado(rs.getString(6));
                com.setDespachado(rs.getBoolean(7));
                lista.add(com);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public List Detalle(int id) {
        List lista = new ArrayList();
        String sql = "select DC.idDetalle, P.Foto, P.Nombres, DC.idCompras, DC.Cantidad, DC.PrecioCompra FROM detalle_compras DC inner join producto P on P.idProducto = DC.idProducto where idCompras=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetalleCompra dcom = new DetalleCompra();
                dcom.setId(rs.getInt(1));
                dcom.setProducto(new Producto());
                dcom.getProducto().setImagen(rs.getString(2));
                dcom.getProducto().setNombres(rs.getString(3));
                dcom.setIdcompra(rs.getInt(4));
                dcom.setCantidad(rs.getInt(5));
                dcom.setPrecioCompra(rs.getDouble(6));
                lista.add(dcom);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public int Pagar(double monto) {
        String sql = "insert into pago(Monto)values(?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, monto);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return 1;
    }

    public int IdPago() {
        int idpago = 0;
        String sql = "select max(idPago) from pago";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                idpago = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return idpago;
    }

    public void actualizarStock(int cant, int id) {
        String sql = "update producto set stock=? where idProducto=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     * ----------FOR BACKEND------------------
     */
    public List<Compra> listarPedidos() throws Exception {
        List<Compra> compra;
        Compra com;
        ResultSet rs;
        String sql = "SELECT c.idCompras, cli.Nombres, cli.Numero_doc, p.Monto, "
                + "c.FechaCompras, c.Estado, c.Despachado, c.Anular FROM compras C "
                + "INNER JOIN cliente CLI ON CLI.idCliente = C.idCliente "
                + "INNER JOIN pago P ON P.idPago = C.idPago";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            compra = new ArrayList<>();
            while (rs.next() == true) {
                com = new Compra();
                com.setId(rs.getInt("idCompras"));
                com.setNombresClienteDni(rs.getString("Nombres") + " - " + rs.getString("Numero_doc"));
                com.setMonto(rs.getDouble("Monto"));
                com.setFecha(rs.getString("FechaCompras"));
                com.setEstado(rs.getString("Estado"));
                com.setDespachado(rs.getBoolean("Despachado"));
                com.setAnularCompra(rs.getBoolean("Anular"));
                compra.add(com);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return compra;
    }

    public List<DetalleCompra> listarDetalles(Compra compra) throws Exception {
        List<DetalleCompra> detalles = new ArrayList<>();
        DetalleCompra det;
        ResultSet rs = null;
        String sql = "SELECT P.Nombres, P.Foto, DC.Cantidad, DC.PrecioCompra, "
                + "SUM(DC.Cantidad * DC.PrecioCompra) AS Total "
                + "FROM detalle_compras DC INNER JOIN producto P "
                + "ON P.idProducto = DC.idProducto INNER JOIN compras C "
                + "ON C.idCompras = DC.idCompras WHERE C.idCompras = '" + compra.getId() + "'"
                + "GROUP BY P.Nombres, P.Foto, DC.Cantidad, DC.PrecioCompra";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            detalles = new ArrayList<>();
            while (rs.next() == true) {
                det = new DetalleCompra();
                det.setProducto(new Producto());
                det.getProducto().setNombres(rs.getString("Nombres"));
                det.getProducto().setImagen(rs.getString("Foto"));
                det.setCantidad(rs.getInt("Cantidad"));
                det.setPrecioCompra(rs.getDouble("PrecioCompra"));
                det.setMontoTotal(rs.getDouble("Total"));
                detalles.add(det);
            }
            rs.close();
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
            if (rs != null && rs.isClosed() == false) {
                rs.close();
            }
            rs = null;
        }
        return detalles;
    }

    public void cambiarEstado(int idCompra, boolean estado) throws Exception {
        try {
            con = cn.getConnection();
            ps = con.prepareStatement("UPDATE compras C SET C.Estado = ?, C.Despachado = ? WHERE C.idCompras = ?");
            ps.setString(1, estado ? "R" : "E");
            ps.setBoolean(2, estado);
            ps.setInt(3, idCompra);
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void anularVenta(int idCompra, boolean anular) throws Exception {
        try {
            con = cn.getConnection();
            ps = con.prepareStatement("UPDATE compras C SET C.Anular = ? WHERE C.idCompras = ?");
            ps.setBoolean(1, anular);
            ps.setInt(2, idCompra);
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }
}
