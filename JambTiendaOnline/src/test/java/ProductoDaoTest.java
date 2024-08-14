package com.tienda.online.controlador;

import com.tienda.online.modelo.Producto;
import com.tienda.online.modelo.ProductoDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ProductoDaoTest {

    @Mock
    private ProductoDAO productoDAO;

    @InjectMocks
    private Producto producto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testActualizarProducto() throws Exception {
        doNothing().when(productoDAO).actualizar(producto);

        producto.setId(1);
        producto.setNombres("Producto Actualizado");
        producto.setPrecio(15000);
        producto.setStock(60);
        producto.setEstado(true);

        productoDAO.actualizar(producto);

        verify(productoDAO, times(1)).actualizar(producto);
    }

    @Test
    public void testDesactivarProducto() throws Exception {
        doNothing().when(productoDAO).cambiarEstado(producto);

        producto.setId(1);
        producto.setEstado(false);

        productoDAO.cambiarEstado(producto);

        verify(productoDAO, times(1)).cambiarEstado(producto);
    }

}
