import com.tienda.online.modelo.Compra;
import com.tienda.online.modelo.ComprasDAO;
import com.tienda.online.modelo.DetalleCompra;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ComprasDaoTest {

    private ComprasDAO comprasDAO;
    private Compra compraMock;

    @Before
    public void setUp() {
        comprasDAO = mock(ComprasDAO.class);
        compraMock = mock(Compra.class);
    }

    @Test
    public void testListarPedidos() throws Exception {
        List<Compra> expectedCompras = new ArrayList<>();
        expectedCompras.add(new Compra()); // Add mock data as needed

        when(comprasDAO.listarPedidos()).thenReturn(expectedCompras);

        List<Compra> actualCompras = comprasDAO.listarPedidos();
        assertEquals(expectedCompras, actualCompras);

        verify(comprasDAO, times(1)).listarPedidos();
    }

    @Test
    public void testListarDetalles() throws Exception {
        List<DetalleCompra> expectedDetalles = new ArrayList<>();
        expectedDetalles.add(new DetalleCompra()); // Add mock data as needed

        when(comprasDAO.listarDetalles(compraMock)).thenReturn(expectedDetalles);

        List<DetalleCompra> actualDetalles = comprasDAO.listarDetalles(compraMock);
        assertEquals(expectedDetalles, actualDetalles);

        verify(comprasDAO, times(1)).listarDetalles(compraMock);
    }
    
}
