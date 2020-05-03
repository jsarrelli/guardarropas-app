package model.apis.weatherApis;

import java.time.LocalDate;

public interface WeatherAPI {

    public double getTemperaturaByCiudadYFecha(LocalDate estimatedDate, String city) throws Exception;
}
