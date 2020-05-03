package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exepciones.NoEntityFoundException;
import model.entities.Guardarropa;
import model.entities.Usuario;
import services.EventoService;
import services.GuardarropaService;
import services.LogginService;
import services.UsuarioService;
import spark.Request;
import spark.Response;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class GuardarropasController {

    private static Gson gson = new Gson();
    private static LogginService logginService = LogginService.getInstance();
    private static GuardarropaService guardarropaService = GuardarropaService.getInstance();
    private static EventoService eventoService = EventoService.getInstance();
    private static UsuarioService usuarioService = UsuarioService.getInstance();

    public static String cantidad(Request req, Response res) throws AuthenticationException {
        Usuario usuario = logginService.getActualUser(req);
        int cantidad = usuario.getGuardarropas().size();
        JsonObject response = new JsonObject();
        response.addProperty("cantidad", cantidad);
        return response.toString();
    }

    public static String mostrarTodos(Request req, Response res) throws AuthenticationException {
        Usuario usuario = logginService.getActualUser(req);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(usuario.getGuardarropas().stream().map(guardarropa -> guardarropa.toJson()).collect(Collectors.toList()));
    }

    public static String nuevo(Request req, Response res) throws AuthenticationException {
        Usuario usuario = logginService.getActualUser(req);
        JsonObject parametros = new JsonParser().parse(req.body()).getAsJsonObject();
        Guardarropa guardarropa = new Guardarropa(parametros.get("nombre").getAsString());
        guardarropa.addUsuario(usuario);

        String userneNameCompartido = parametros.get("usuarioCompartido").getAsString();
        if (!userneNameCompartido.isEmpty()) {
            HashMap<String, String> criterios = new HashMap<>();
            criterios.put("username", userneNameCompartido);

            Optional maybeUsuarioCoompartido = usuarioService.findBy(criterios).findFirst();
            if (!maybeUsuarioCoompartido.isPresent()) {
                res.status(404);
                return "No se pudo encontrar el usuario compartido";
            }
            guardarropa.addUsuario((Usuario) maybeUsuarioCoompartido.get());
        }

        guardarropaService.save(guardarropa);
        res.status(200);
        return gson.toJson("guardarropa creado");
    }

    public static String eliminar(Request req, Response res) throws NoEntityFoundException {
        int id = Integer.parseInt(req.params("id"));
        Guardarropa guardarropaBorrado = (Guardarropa) guardarropaService.findById(id);
        guardarropaService.delete(guardarropaBorrado);

        res.body(gson.toJson("ok"));
        return gson.toJson("guardarropa borrado con exito");
    }

}
