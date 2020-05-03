/*
import model.apis.weatherApis.ApixuWeather;
import model.apis.weatherApis.WeatherAPI;
import model.apis.weatherApis.Weatherbit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import services.ClimaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class WhetherApiTest {

    @Spy
    ClimaService climaService;

    @Test
    public void switchEntreApisSiFallaAlguna() throws Exception {
        ApixuWeather apixu = Mockito.mock(ApixuWeather.class);
        Weatherbit weatherBit = Mockito.mock(Weatherbit.class);

        List<WeatherAPI> apis = new ArrayList<>();
        apis.add(weatherBit);
        apis.add(apixu);
        climaService.setWeatherAPIs(apis);

        Mockito.doThrow(new Exception("Dias excedidos")).when(weatherBit).getTemperaturaByCiudadYFecha(any(LocalDate.class), anyString());
        Mockito.doReturn(19.0).when(apixu).getTemperaturaByCiudadYFecha(any(LocalDate.class), anyString());

        assertEquals(climaService.getTemperaturaByCiudad("Buenos Aires"), 19.0, 0.001);

        verify(apixu, times(1)).getTemperaturaByCiudadYFecha(any(LocalDate.class), anyString());
        verify(weatherBit, times(1)).getTemperaturaByCiudadYFecha(any(LocalDate.class), anyString());


    }
}
*/
