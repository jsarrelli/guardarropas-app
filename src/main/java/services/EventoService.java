package services;

import model.entities.Atuendo;
import model.entities.Evento;
import model.entities.Sugerencia;
import model.entities.notificaciones.Notificacion;
import org.springframework.stereotype.Service;
import repositories.EventoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class EventoService extends BaseService {

    private static EventoService instance;
    private static GuardarropaService guardarropaService = GuardarropaService.getInstance();
    private static ClimaService climaService = ClimaService.getInstance();

    public EventoService() {
        this.repository = new EventoRepository();
    }

    public static EventoService getInstance() {
        if (instance == null) {
            return new EventoService();
        }
        return instance;
    }


    public void sugerirAtuendos(List<Evento> eventos, Notificacion notificacion) {
        eventos.parallelStream().forEach(evento -> {
            try {
                generarSugerencia(evento).notificar(notificacion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * genera una sugerencia y la agrega en su lista de sugerencias
     */
    public Sugerencia generarSugerencia(Evento evento) throws Exception {
        Atuendo atuendoSugerido = guardarropaService.getAtuendoForEvento(evento);
        double temperaturaActual = climaService.getTemperaturaByCiudad("buenos aires");
        Sugerencia sugerencia = new Sugerencia(atuendoSugerido, LocalDateTime.now(), temperaturaActual, evento, evento.getUsuario());
        evento.getSugerencias().add(sugerencia);
        return sugerencia;
    }


    public List<Evento> obtenerEventosProximaHora() {
        List<Evento> eventos = findAll();
        return eventos.stream().filter(evento ->
                evento.getFecha().isBefore(LocalDateTime.now().plusHours(1))
                        && evento.getFecha().isAfter(LocalDateTime.now())).collect(toList());
    }

    public List<Evento> obtenerEventosDeFecha(LocalDate fecha) {
        List<Evento> eventos = findAll();
        return eventos.stream().filter(evento -> evento.getFecha().toLocalDate().equals(fecha)).collect(toList());
    }


}
