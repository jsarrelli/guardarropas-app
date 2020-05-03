package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exepciones.NoEntityFoundException;
import model.entities.Guardarropa;
import model.entities.Prenda;
import model.entities.TipoPrenda;
import model.entities.Usuario;
import services.GuardarropaService;
import services.LogginService;
import services.PrendaService;
import services.TipoPrendaService;
import spark.Request;
import spark.Response;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

public class PrendasController {

    private static Gson gson = new Gson();
    private static TipoPrendaService tipoPrendaService = TipoPrendaService.getInstance();
    private static PrendaService prendaService = PrendaService.getInstance();
    private static GuardarropaService guardarropaService = GuardarropaService.getInstance();

    public static String cantidad(Request req, Response res) throws AuthenticationException {
        Usuario usuario = LogginService.getInstance().getActualUser(req);
        int cantidad = usuario.getGuardarropas().stream().mapToInt(gd -> gd.getPrendas().size()).sum();
        JsonObject response = new JsonObject();
        response.addProperty("cantidad", cantidad);
        return response.toString();
    }

    public static String mostrarTodas(Request req, Response res) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Prenda> prendas = prendaService.findAll();
        return gson.toJson(prendas.stream().map(Prenda::toJson).collect(Collectors.toList()));
    }

    public static String actualizar(Request req, Response res) throws NoEntityFoundException {
        JsonObject parametros = new JsonParser().parse(req.body()).getAsJsonObject();
        int idPrenda = parametros.get("id").getAsInt();
        Prenda prenda = (Prenda) prendaService.findById(idPrenda);
        int idGuardarropa = parametros.get("idGuardarropa").getAsInt();
        Guardarropa guardarropa = (Guardarropa) guardarropaService.findById(idGuardarropa);
        prenda.setGuardarropa(guardarropa);
        prendaService.save(prenda);
        return gson.toJson("Prenda actualizada con exito");
    }

    public static String nueva(Request req, Response res) throws NoEntityFoundException {
        JsonObject parametros = new JsonParser().parse(req.body()).getAsJsonObject();
        Prenda prenda = new Prenda();
        prenda.setNombre(parametros.get("nombre").getAsString());
        prenda.setColor1(parametros.get("color1").getAsString());
        prenda.setColor2(parametros.get("color2").getAsString());
        prenda.setFoto(parametros.get("foto").getAsString());

        int idTipoPrenda = Integer.parseInt(parametros.get("tipoPrenda").getAsString());
        TipoPrenda tipoPrenda = (TipoPrenda) tipoPrendaService.findById(idTipoPrenda);

        int idGuardarropa = parametros.get("guardarropa").getAsInt();
        Guardarropa guardarropa;
        guardarropa = (Guardarropa) guardarropaService.findById(idGuardarropa);
        prenda.setGuardarropa(guardarropa);
        prenda.setTipoPrenda(tipoPrenda);
        prendaService.save(prenda);
        return gson.toJson("Prenda creada con exito");
    }

    public static String eliminar(Request req, Response res) throws NoEntityFoundException {
        int idPrenda = Integer.valueOf(req.params("id"));
        System.out.println(idPrenda);
        Prenda prenda = (Prenda) prendaService.findById(idPrenda);
        prendaService.delete(prenda);
        return gson.toJson("Prenda eliminada");
    }

    public static String mostrarTiposPrenda(Request req, Response res) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(tipoPrendaService.findAll());
    }

}
