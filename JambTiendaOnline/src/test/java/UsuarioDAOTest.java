import com.tienda.online.modelo.UsuarioDAO;
import com.tienda.online.modelo.Usuario;
import com.tienda.online.modelo.Empleado;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UsuarioDAOTest {

    private UsuarioDAO usuarioDAO;
    private Usuario testUsuario;

    @Before
    public void setUp() throws Exception {
        usuarioDAO = new UsuarioDAO();

        // Ensure test data is in the database
        Usuario newUser = new Usuario();
        newUser.setUsuario("testUser");
        newUser.setClave("testPass");
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(1); // Ensure this ID exists in your Empleado table
        newUser.setEmpleado(empleado);

        usuarioDAO.registrarUsuarios(newUser);
        testUsuario = newUser;
    }

    @Test
    public void testListarUsuarios() throws Exception {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        assertNotNull("The list of users should not be null", usuarios);
        assertFalse("The list of users should not be empty", usuarios.isEmpty());
    }

    @Test
    public void testRegistrarUsuarios() throws Exception {
        Usuario newUser = new Usuario();
        newUser.setUsuario("newUser");
        newUser.setClave("newPass");
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(1);
        newUser.setEmpleado(empleado);

        usuarioDAO.registrarUsuarios(newUser);

        Usuario result = usuarioDAO.identificar(newUser);
        assertNotNull("The new user should be registered and identified", result);
    }
}
