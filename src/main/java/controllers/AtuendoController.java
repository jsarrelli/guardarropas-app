package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exepciones.NoEntityFoundException;
import model.entities.*;
import model.entities.notificaciones.NotificacionCreacionSugerencia;
import model.enums.TipoClima;
import services.*;
import spark.Request;
import spark.Response;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

public class AtuendoController {

    private static Gson gson = new Gson();
    private static AtuendoService atuendoService = AtuendoService.getInstance();
    private static GuardarropaService guardarropaService = GuardarropaService.getInstance();
    private static LogginService logginService = LogginService.getInstance();
    private static ClimaService climaService = ClimaService.getInstance();
    private static EventoService eventoService = EventoService.getInstance();
    private static SugerenciaService sugerenciaService = SugerenciaService.getInstance();

    public static String sugerenciaHoy(Request req, Response res) throws Exception {
        try {
            LocalDate date = LocalDate.now();
            Usuario usuario = logginService.getActualUser(req);
            int idGuardarropa = Integer.parseInt(req.params(":idGuardarropa"));
            Atuendo atuendo = generarAtuendo(date, idGuardarropa, usuario);
            return atuendo.toJson().toString();
        } catch (Exception e) {
            res.status(504);
            e.printStackTrace();
            res.body("No se pudo generer el atuendo");
            throw e;
        }
    }

    private static Atuendo generarAtuendo(LocalDate date, int idGuardarropa, Usuario usuario) throws Exception {
        Guardarropa guardarropa = (Guardarropa) guardarropaService.findById(idGuardarropa);
        double temperatura = climaService.getTemperaturaByCiudadYFecha(date, "buenos aires");
        TipoClima tipoClima = TipoClima.getTipoClima(temperatura);
        Atuendo atuendo = null;
        atuendo = guardarropaService.getAtuendoForTipoClima(tipoClima, guardarropa.getPrendas(), usuario);
        atuendoService.save(atuendo);
        return atuendo;
    }

    public static String sugerirEvento(Request req, Response res) throws Exception {
        try {
            Usuario usuario = logginService.getActualUser(req);
            JsonObject parametros = new JsonParser().parse(req.body()).getAsJsonObject();
            int idEvento = parametros.get("idEvento").getAsInt();
            int idGuardarropa = parametros.get("idGuardarropa").getAsInt();
            Evento evento = (Evento) eventoService.findById(idEvento);
            double temperatura = climaService.getTemperaturaByCiudadYFecha(evento.getFecha().toLocalDate(), "buenos aires");
            Atuendo atuendo = generarAtuendo(evento.getFecha().toLocalDate(), idGuardarropa, usuario);
            Sugerencia sugerencia = new Sugerencia(atuendo, evento.getFecha(), temperatura, evento, usuario);
            NotificacionCreacionSugerencia notificacion = new NotificacionCreacionSugerencia(usuario);
            sugerencia.notificar(notificacion);
            sugerenciaService.save(sugerencia);
            return atuendo.toJson().toString();
        } catch (Exception e) {
            res.status(504);
            e.printStackTrace();
            res.body("No se pudo generer el atuendo");
            throw e;
        }

    }

    public static String sugerenciaManana(Request req, Response res) throws Exception {
        try {
            LocalDate date = LocalDate.now().plusDays(1);
            Usuario usuario = logginService.getActualUser(req);
            int idGuardarropa = Integer.parseInt(req.params(":idGuardarropa"));
            Atuendo atuendo = generarAtuendo(date, idGuardarropa, usuario);
            return atuendo.toJson().toString();
        } catch (Exception e) {
            res.status(504);
            e.printStackTrace();
            res.body("No se pudo generer el atuendo");
            throw e;
        }
    }

    public static String dislike(Request req, Response res) {
        return gson.toJson("");
    }

    public static String like(Request req, Response res) {
        return gson.toJson("");
    }

}
