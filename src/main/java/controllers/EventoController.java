package controllers;

import com.google.gson.*;
import exepciones.NoEntityFoundException;
import model.entities.Evento;
import model.entities.Guardarropa;
import model.entities.Usuario;
import model.enums.Periodicidad;
import services.EventoService;
import services.GuardarropaService;
import services.LogginService;
import spark.Request;
import spark.Response;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventoController {

    private static Gson gson = new Gson();
    private static EventoService eventoService = EventoService.getInstance();
    private static LogginService logginService = LogginService.getInstance();
    private static GuardarropaService guardarropaService = GuardarropaService.getInstance();

    public static String cantidad(Request req, Response res) throws AuthenticationException {
        JsonObject response = new JsonObject();
        Usuario usuario = logginService.getActualUser(req);
        int cantidadEventos = usuario.getEventos().size();
        response.addProperty("cantidad", cantidadEventos);
        return response.toString();
    }

    public static String mostrarTodos(Request req, Response res) throws AuthenticationException {
        Usuario usuario = logginService.getActualUser(req);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(usuario.getEventos().stream().map(Evento::toJson).collect(Collectors.toList()));

    }

    public static String nuevo(Request req, Response res) throws AuthenticationException, NoEntityFoundException {
        JsonObject parametros = new JsonParser().parse(req.body()).getAsJsonObject();
        String nombre = parametros.get("nombre").getAsString();
        String direccion = parametros.get("direccion").getAsString();
        String notificador = parametros.get("notificador").getAsString();
        String formalidad = parametros.get("formalidad").getAsString();
        int idGuardarropa = parametros.get("guardarropa").getAsInt();
        Guardarropa guardarropa = (Guardarropa) guardarropaService.findById(idGuardarropa);
        Periodicidad periodicidad = Periodicidad.valueOf(parametros.get("periodicidad").getAsString());
        LocalDate fecha = LocalDate.parse(parametros.get("fecha").getAsString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime hora = LocalTime.parse(parametros.get("hora").getAsString(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime momento = fecha.atTime(hora);
        Usuario usuario = logginService.getActualUser(req);
        Evento evento = new Evento(nombre, usuario, direccion, guardarropa, momento, periodicidad, formalidad, notificador);
        eventoService.save(evento);

        res.status(200);
        return gson.toJson("Evento creado");
    }

    public static String eliminar(Request req, Response res) throws NoEntityFoundException {
        int idEvento = Integer.parseInt(req.params("id"));
        Evento evento = (Evento) eventoService.findById(idEvento);
        eventoService.delete(evento);
        return gson.toJson("Evento eliminado");
    }

    public static String mostrarFormalidades(Request req, Response res) {
        JsonObject response = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("FORMAL");
        jsonArray.add("INFORMAL");
        response.add("formalidades", jsonArray);
        return response.toString();
    }

    public static String mostrarNotificadores(Request req, Response res) {
        JsonObject response = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("GMAIL");
        jsonArray.add("SMS");
        jsonArray.add("WSP");
        response.add("notificadores", jsonArray);
        return response.toString();
    }

    public static String mostrarPeriodicidades(Request req, Response res) {
        JsonObject response = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        List<String> listEnum = Stream.of(Periodicidad.values())
                .map(Periodicidad::name)
                .collect(Collectors.toList());
        for (String str : listEnum) {
            jsonArray.add(str);
        }
        response.add("periodicidades", jsonArray);
        return response.toString();
    }


}
