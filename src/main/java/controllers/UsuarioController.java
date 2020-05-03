package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exepciones.NoEntityFoundException;
import model.entities.Usuario;
import model.factories.TipoUsuarioFactory;
import model.Hasher;
import services.LogginService;
import services.UsuarioService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Optional;


public class UsuarioController {

    private static Gson gson = new Gson();

    private static UsuarioService usuarioService = UsuarioService.getInstance();
    private static LogginService logginService = LogginService.getInstance();

    public static String registrarUsuario(Request req, Response res) {

        JsonObject parametros = new JsonParser().parse(req.body()).getAsJsonObject();
        String username = parametros.get("username").getAsString();
        HashMap<String, String> criterio = new HashMap<>();
        criterio.put("username", username);
        Optional usuario = usuarioService.findBy(criterio).findAny();
        if (!usuario.isPresent()) {
            Usuario user = new Usuario();
            user.setNombre(parametros.get("nombre").getAsString());
            user.setUsername(username);
            user.setPassword(parametros.get("password").getAsString());
            user.setCorreo(parametros.get("correo").getAsString());
            user.setModificadorTemp(0);
            user.setTipoUsuario("FREE");
            usuarioService.save(user);
            res.status(200);
            return gson.toJson("Usuario " + user.getUsername() + " creado con éxito.");
        }
        res.status(404);
        return gson.toJson("Nombre de usuario no disponible.");

    }

    public static String loginUsuario(Request req, Response res) {

        HashMap<String, String> criterio = new HashMap<>();
        JsonObject parametros = new JsonParser().parse(req.body()).getAsJsonObject();
        String filterUser = parametros.get("username").getAsString();
        String passPlana = parametros.get("password").getAsString();
        String hash = Hasher.generarSHA256(passPlana);
        criterio.put("username", filterUser);
        Optional usuario = usuarioService.findBy(criterio).findFirst();
        if (usuario.isPresent()) {
            String password = ((Usuario) usuario.get()).getPassword();
            if (hash.equals(password)) {
                res.status(200);
                String idUser = String.valueOf(((Usuario) usuario.get()).getId());
                String token = logginService.codeToken(idUser);
                return token;
            }
        }
        res.status(404);
        return "Usuario o contraseña ingresados son incorrectos.";
    }

    public static String getUsuario(Request req, Response res) throws NoEntityFoundException {

        int id = Integer.valueOf(req.params(":id"));
        Usuario usuario = (Usuario) usuarioService.findById(id);
        res.type("application/json");

        JsonObject respose = new JsonObject();
        respose.addProperty("nombre", usuario.getNombre());
        respose.addProperty("id", usuario.getId());
        String tipoUsuario = new TipoUsuarioFactory().deconstruir(usuario.getTipoUsuario());
        respose.addProperty("tipo", tipoUsuario);
        return respose.toString();
    }


    public static String cambiarTipo(Request req, Response res) throws NoEntityFoundException {
        JsonObject parametros = new JsonParser().parse(req.body()).getAsJsonObject();
        String tipoUsuario = parametros.get("tipo").getAsString();
        int id = parametros.get("id").getAsInt();
        Usuario usuario = (Usuario) usuarioService.findById(id);
        usuario.setTipoUsuario(tipoUsuario);
        usuarioService.save(usuario);
        return gson.toJson("El usuario " + usuario.getUsername() + "ahora es " + tipoUsuario);

    }

    public static String getToken(Request req, Response res) {
        return logginService.codeToken(req.params(":id"));
    }


}
