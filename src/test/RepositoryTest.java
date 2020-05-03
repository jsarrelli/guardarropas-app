/*
import model.entities.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import repositories.UsuarioRepositorio;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    @Spy
    UsuarioRepositorio usuarioRepositorio;

    @Test
    public void saveUsuario() {
        Usuario usuario = EntityFactoryTest.createUsuario();
        usuarioRepositorio.save(usuario);
    }

    @Test
    public void findAll() {
        Usuario usuario = EntityFactoryTest.createUsuario();
        usuarioRepositorio.save(usuario);
        usuarioRepositorio.findAll();
    }

    @Test
    public void findBy() {
        Usuario usuario = EntityFactoryTest.createUsuario();
        usuario.setNombre("papa");
        // usuarioRepositorio.save(usuario);
        HashMap<String, String> criterios = new HashMap<>();
        criterios.put("nombre", "papa");
        criterios.put("telefono", "1111");
        usuarioRepositorio.findBy(criterios);
    }


    @Test
    public void delete() {
        Usuario usuario = (Usuario) usuarioRepositorio.findById(8).get();
        usuarioRepositorio.delete(usuario);
        assertEquals(Optional.empty(), usuarioRepositorio.findById(8));
    }
}
*/
