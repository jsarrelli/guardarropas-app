package controllers;

import com.google.gson.JsonObject;
import services.ClimaService;
import spark.Request;
import spark.Response;

public class ClimaController {
    private static ClimaService climaService = ClimaService.getInstance();


    public static String clima(Request req, Response res) {
        double temperatura = climaService.getTemperaturaByCiudad("buenos aires");
        JsonObject response = new JsonObject();
        response.addProperty("temperatura", temperatura);
        return response.toString();
    }

}
