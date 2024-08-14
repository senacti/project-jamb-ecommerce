import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import org.junit.Before;
import org.junit.Test;
import com.tienda.online.modelo.Carrito;
import com.tienda.online.modelo.Cliente;
import com.tienda.online.modelo.Producto;

public class VentasModuloTest {

    private Producto producto;
    private Cliente cliente;
    private Carrito carrito;

    @Before
    public void setUp() {
        // Create instances of Producto, Cliente, and Carrito for testing

        producto = new Producto(1, "Laptop", "laptop.png",
                new ByteArrayInputStream(new byte[0]), "High-end gaming laptop",
                1500.00, 10, null, null, null, true);

        cliente = new Cliente(1, "12345678", "John Doe",
                "john@example.com", "password", "123 Main St", null);

        carrito = new Carrito(1, 1, "Laptop", "laptop.png",
                "High-end gaming laptop", 1500.00, 2, 3000.00);
    }

    @Test
    public void testCarritoSubTotalCalculation() {
        double expectedSubTotal = 3000.00;
        assertEquals(expectedSubTotal, carrito.getSubTotal(), 0.01);
    }

    @Test
    public void testClienteEmail() {
        assertEquals("john@example.com", cliente.getEmail());
    }

    @Test
    public void testProductoPrecio() {
        assertEquals(1500.00, producto.getPrecio(), 0.01);
    }
}
