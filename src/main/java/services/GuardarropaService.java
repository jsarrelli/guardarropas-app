package services;

import model.entities.Atuendo;
import model.entities.Evento;
import model.entities.Prenda;
import model.entities.Usuario;
import model.enums.TipoClima;
import model.handlers.GeneradorAtuendoHandler;
import repositories.GuardarropaRepository;

import java.util.List;

public class GuardarropaService extends BaseService {
    private static GuardarropaService instance;
    private static ClimaService climaService = ClimaService.getInstance();

    private GuardarropaService() {
        this.repository = new GuardarropaRepository();
    }

    public static GuardarropaService getInstance() {
        if (instance == null) {
            return new GuardarropaService();
        }
        return instance;
    }

    public Atuendo getAtuendoForEvento(Evento evento) throws Exception {
        double temperatura = climaService.getTemperaturaByCiudadYFecha(evento.getFecha().toLocalDate(), "buenos aires");
        TipoClima tipoClima = TipoClima.getTipoClima(temperatura);
        return evento.getGeneradorTipoDeAtuendo().generarAtuendo(tipoClima, evento.getGuardarropa(), evento.getUsuario());
    }

    /**
     * genera un atuendo acorde a la temperatura utilizando las prendas
     * primero se intenta matchear con algun prototipo y si no puede te
     * intenta generar un atuendo random que cumpla con el tipoClima
     *
     * @param tc
     * @param prendas
     * @return
     * @throws Exception
     */
    public Atuendo getAtuendoForTipoClima(TipoClima tc, List<Prenda> prendas, Usuario usuario) throws Exception {
        GeneradorAtuendoHandler generadorAtuendoHandler = new GeneradorAtuendoHandler(usuario);
        try {

            Atuendo atuendoGenerado = generadorAtuendoHandler.generarAtuendoByPrototipos(tc, prendas);
            if (atuendoGenerado != null) {
                return atuendoGenerado;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generadorAtuendoHandler.generarAtuendoRandom(tc, prendas);
    }
}
