package services;

import model.apis.weatherApis.ApixuWeather;
import model.apis.weatherApis.WeatherAPI;
import model.apis.weatherApis.Weatherbit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClimaService {

    private static ClimaService instance;
    private List<WeatherAPI> weatherAPIs;

    private ClimaService() {
        super();
        this.weatherAPIs = new ArrayList<WeatherAPI>();
       // weatherAPIs.add(new ApixuWeather());
        weatherAPIs.add(new Weatherbit());
    }

    public static ClimaService getInstance() {
        if (instance == null) {
            return new ClimaService();
        }
        return instance;
    }

    public List<WeatherAPI> getWeatherAPIs() {
        return weatherAPIs;
    }

    public void setWeatherAPIs(List<WeatherAPI> weatherAPIs) {
        this.weatherAPIs = weatherAPIs;
    }

    public double getTemperaturaByCiudadYFecha(LocalDate estimatedDate, String city) {
        for (WeatherAPI weatherAPI : weatherAPIs) {
            try {
                String cityFormatted = city.replace(" ", "+");
                double temperature = weatherAPI.getTemperaturaByCiudadYFecha(estimatedDate, cityFormatted);

                String formattedDate = estimatedDate.format(DateTimeFormatter.ofPattern("dd-MMM"));
                System.out.println(
                        "El pronostico para el " + formattedDate + " en " + city + " es de " + temperature + " °C");

                return temperature;
            } catch (Exception e) {
                System.out.println("Fallo " + weatherAPI.getClass().getSimpleName());
            }
        }

        return -1;
    }

    public double getTemperaturaByCiudad(String city) {
        return getTemperaturaByCiudadYFecha(LocalDate.now(), city);
    }
}
