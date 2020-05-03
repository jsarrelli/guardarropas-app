package app;

import model.entities.Evento;
import model.entities.notificaciones.Notificacion;
import model.entities.notificaciones.NotificacionCambioBrusco;
import model.entities.notificaciones.NotificacionCreacionSugerencia;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import services.EventoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/* https://www.mkyong.com/java/java-scheduledexecutorservice-examples/
 */

@Component
public class Scheduler {
    EventoService eventoService = EventoService.getInstance();

    /**
     * evento periodico que toma los eventos que ocurren mañana, genera una sugerencia y notifica a los usuarios
     */
    @Scheduled(cron = "0 0 12 * * * *")
    public void taskSugerirAtuendos() {
        List<Evento> enventosDeMañana = eventoService.obtenerEventosDeFecha(LocalDate.now().plusDays(1));
        Notificacion notificacion = new NotificacionCreacionSugerencia();
        eventoService.sugerirAtuendos(enventosDeMañana, notificacion);
        //cuando finaliza de sugerir los atuendos, actualiza la fecha de los eventos
    }

    /**
     * este evento se corre cada una hora chequeando los eventos que suceden en la proxima hora y
     * aquellos para los que ocurrieron cambios bruscos genera nuevos atuendos y notifica a los usuarios
     */
    @Scheduled(cron = "0 0 */1 * * * *")
    public void taksAlertaCambioTemperatura() {
        List<Evento> eventosARenotificar = eventoService.obtenerEventosProximaHora().parallelStream().filter(Evento::seProdujoCambioBruscoTemperatua).collect(Collectors.toList());
        Notificacion notificacionCambioTemperatura = new NotificacionCambioBrusco();
        eventoService.sugerirAtuendos(eventosARenotificar, notificacionCambioTemperatura);
    }

}
