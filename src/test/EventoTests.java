/*
import model.entities.Evento;
import model.enums.Periodicidad;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;


public class EventoTests {

    @Test
    public void actualizarFechaEventoDiario() {
        Evento evento = EntityFactoryTest.createEvento();
        evento.setPeriodicidad(Periodicidad.DIARIO);

        evento.actualizarFecha();
        assertEquals(LocalDate.now().plusDays(1), evento.getFecha().toLocalDate());
    }

    @Test
    public void actualizarFechaEventoSemanal() {
        Evento evento = EntityFactoryTest.createEvento();
        evento.setPeriodicidad(Periodicidad.SEMANAL);

        evento.actualizarFecha();
        assertEquals(LocalDate.now().plusWeeks(1), evento.getFecha().toLocalDate());
    }

    @Test
    public void actualizarFechaEventoMensual() {
        Evento evento = EntityFactoryTest.createEvento();
        evento.setPeriodicidad(Periodicidad.MENSUAL);

        evento.actualizarFecha();
        assertEquals(LocalDate.now().plusMonths(1), evento.getFecha().toLocalDate());
    }

    @Test
    public void actualizarFechaEventoAnual() {
        Evento evento = EntityFactoryTest.createEvento();
        evento.setPeriodicidad(Periodicidad.ANUAL);

        evento.actualizarFecha();
        assertEquals(LocalDate.now().plusYears(1), evento.getFecha().toLocalDate());
    }

    @Test
    public void actualizarFechaEventoUnico() {
        Evento evento = EntityFactoryTest.createEvento();
        evento.setPeriodicidad(Periodicidad.UNICO);

        assertTrue(evento.isActivado());
        evento.actualizarFecha();
        assertFalse(evento.isActivado());
    }


}
*/
