/*
import model.entities.Ciudad;
import model.entities.Evento;
import model.entities.Guardarropa;
import model.entities.Usuario;
import model.enums.Periodicidad;
import model.generadoresAtuendo.GeneradorFormal;
import model.generadoresAtuendo.GeneradorInformal;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EntityFactoryTest {

    public static Ciudad createCiudad() {
        return new Ciudad("Buenos Aires", "Argentina");
    }


    public static Evento createEvento() {
        return new Evento("test", createUsuario(), "alguna direccion", createGuardarropa(),
                LocalDateTime.now(), Periodicidad.UNICO, "FORMAL", "SMS");
    }

    private static Guardarropa createGuardarropa() {
        return new Guardarropa(new ArrayList<>());
    }

    public static GeneradorFormal createGeneradorFormal() {
        return new GeneradorFormal();
    }

    public static GeneradorInformal createGeneradorInformal() {
        return new GeneradorInformal();
    }

    public static Usuario createUsuario() {
        return new Usuario("julisarrelli","pass", "julian", 0, "jsarrelli@gmail.com", "3243243", "PREMIUM");
    }

}
*/
