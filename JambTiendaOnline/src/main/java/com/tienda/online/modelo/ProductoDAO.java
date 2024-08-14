package com.tienda.online.modelo;

import com.tienda.online.config.Conexion;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class ProductoDAO extends Conexion {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r = 0;

    public List buscar(String nombre) {
        List list = new ArrayList();
        String sql = "select * from producto where Nombres like '%" + nombre + "%'";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setImagen(rs.getString(3));
//                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                list.add(p);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Producto listarId(int id) {
        Producto p = new Producto();
        String sql = "select * from producto where IdProducto=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setImagen(rs.getString(3));
//                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
            }
        } catch (Exception e) {
        }
        return p;
    }

    public List listar() {
        List lista = new ArrayList();
        String sql = "SELECT P.idProducto, P.Nombres, P.Foto, P.Descripcion, "
                + "P.Precio, P.Stock, C.nombre_categoria, M.nombre_marca, "
                + "PRO.razon_social, P.estado FROM producto P INNER JOIN categoria C "
                + "ON C.id = P.categoria_id INNER JOIN marca M "
                + "ON M.id = P.marca_id INNER JOIN proveedor PRO ON  PRO.id = P.proveedor_id";
        try {
            ps = getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("idProducto"));
                p.setNombres(rs.getString("Nombres"));
                p.setImagen(rs.getString("Foto"));
//                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setStock(rs.getInt("Stock"));
                p.setCategoria(new Categoria());
                p.getCategoria().setNombrecate(rs.getString("nombre_categoria"));
                p.setMarca(new Marca());
                p.getMarca().setMarca(rs.getString("nombre_marca"));
                p.setProveedor(new Proveedor());
                p.getProveedor().setRazon_social(rs.getString("razon_social"));
                p.setEstado(rs.getBoolean("estado"));
                lista.add(p);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public void listarImg(int id, HttpServletResponse response) {
        String sql = "select * from producto where IdProducto=" + id;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setContentType("image/*");
        try {
            outputStream = response.getOutputStream();
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                inputStream = rs.getBinaryStream("Foto");
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            int i = 0;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }
        } catch (Exception e) {
        }
    }

    public int AgregarNuevoProducto(Producto p) {
        String sql = "insert into producto(Nombres,Foto,Descripcion,Precio,Stock,categoria_id,marca_id,proveedor_id,estado)values(?,?,?,?,?,?,?,?,?)";
        try {
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getImagen());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setInt(6, p.getCategoria().getCodigo());
            ps.setInt(7, p.getMarca().getCodigo());
            ps.setInt(8, p.getProveedor().getId());
            ps.setBoolean(9, true);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al registrar el producto" + e.getMessage());
        }
        return r;
    }

    public void registrarProducto(Producto pro) throws Exception {
        String sql;
        sql = "INSERT INTO producto (Nombres, Foto, Descripcion, Precio, Stock, "
                + "categoria_id, marca_id, proveedor_id, estado)"
                + "VALUES('" + pro.getNombres() + "', '"
                + pro.getImagen() + "', '"
                + pro.getDescripcion() + "', "
                + pro.getPrecio() + ", "
                + pro.getStock() + ", "
                + pro.getCategoria().getCodigo() + ", "
                + pro.getMarca().getCodigo() + ", "
                + pro.getProveedor().getId() + ", " + 1 + ")";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public void delete(int id) {
        String sql = "delete from producto where IdProducto=" + id;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar" + e.getMessage());
        }
    }

    public Producto leer(Producto producto) throws Exception {
        Producto prod = null;
        ResultSet rs = null;
        String sql = "SELECT P.idProducto, P.Nombres, P.Foto, P.Descripcion, "
                + "P.Precio, P.Stock, P.Categoria_id, P.Marca_id, P.Proveedor_id, "
                + "P.Estado FROM producto P "
                + "WHERE P.idProducto = " + producto.getId();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                prod = new Producto();
                prod.setId(producto.getId());
                prod.setNombres(rs.getString("Nombres"));
                prod.setImagen(rs.getString("Foto"));
                prod.setDescripcion(rs.getString("Descripcion"));
                prod.setPrecio(rs.getDouble("Precio"));
                prod.setStock(rs.getInt("Stock"));
                prod.setEstado(rs.getBoolean("Estado"));
                prod.setCategoria(new Categoria());
                prod.getCategoria().setCodigo(rs.getInt("Categoria_id"));
                prod.setMarca(new Marca());
                prod.getMarca().setCodigo(rs.getInt("Marca_id"));
                prod.setProveedor(new Proveedor());
                prod.getProveedor().setId(rs.getInt("Proveedor_id"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return prod;
    }

    public void actualizar(Producto producto) throws Exception {
        String sql;
        sql = "UPDATE producto SET Nombres = '" + producto.getNombres()
                + "', Foto = '" + producto.getImagen()
                + "', Descripcion = '" + producto.getDescripcion()
                + "', Precio = " + producto.getPrecio()
                + ", Stock = " + producto.getStock()
                + ", Estado = " + (producto.isEstado() == true ? "1" : "0")
                + ", Categoria_id = " + producto.getCategoria().getCodigo()
                + ", Marca_id = " + producto.getMarca().getCodigo()
                + ", Proveedor_id = " + producto.getProveedor().getId()
                + " WHERE idProducto = " + producto.getId();

        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public void cambiarEstado(Producto producto) throws Exception {
        String sql = "UPDATE producto P SET P.estado = 0 WHERE idProducto = " + producto.getId();
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
