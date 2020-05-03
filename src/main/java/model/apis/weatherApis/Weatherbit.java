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
 * @author julisarrelli https://www.weatherbit.io/api/weather-forecast-16-day
 */
public class Weatherbit implements WeatherAPI {
    public static final String URL_SOURCE = Properties.getProperty("WEATHERBIT_URL_SOURCE");
    public static final String KEY = Properties.getProperty("WEATHERBIT_KEY");
    public static final String FORMAT = Properties.getProperty("WEATHERBIT_FORMAT");
    public static final String DIAS = Properties.getProperty("WEATHERBIT_DIAS");

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
        URL url = new URL(URL_SOURCE + KEY + "&city=" + city);

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

        double temp = json.getAsJsonArray("data").get(daysFromToday(date)).getAsJsonObject().get("temp").getAsDouble();

        return temp;

    }

    private int daysFromToday(LocalDate estimatedDate) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(today, estimatedDate);
        return period.getDays();
    }
}
