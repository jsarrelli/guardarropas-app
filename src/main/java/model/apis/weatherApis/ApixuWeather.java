package model.apis.weatherApis;

import app.Properties;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

/**
 * @author julisarrelli
 * <p>
 * https://www.apixu.com/doc/request.aspx
 */
public class ApixuWeather implements WeatherAPI {
    public static final String URL_SOURCE = Properties.getProperty("WEATHER_APIXU_URL_SOURCE");
    public static final String DIAS = Properties.getProperty("WEATHER_APIXU_DIAS");


    public double getTemperaturaByCiudadYFecha(LocalDate estimatedDate, String city) throws Exception {
        double response = 0;
        if (daysFromToday(estimatedDate) <= Integer.parseInt(DIAS)) {

            response = requestTemp(city, estimatedDate);

        } else {
            throw new Exception("Dias excedidos");
        }

        return response;
    }

    private double requestTemp(String city, LocalDate date) throws IOException {
        URL url = new URL(URL_SOURCE + city + "&forecast_days=" + daysFromToday(date));

        InputStreamReader reader = new InputStreamReader(url.openStream());

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";

        while (in.hasNext()) {
            result += in.nextLine();
        }

        in.close();
        reader.close();

        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(result);

        double avgTemp = json.getAsJsonObject("forecast").getAsJsonArray("forecastday").get(0).getAsJsonObject()
                .getAsJsonObject("day").get("avgtemp_c").getAsDouble();

        return avgTemp;

    }

    private int daysFromToday(LocalDate estimatedDate) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(today, estimatedDate);
        return period.getDays();
    }
}