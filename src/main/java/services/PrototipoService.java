package services;

import app.Properties;
import model.entities.Prototipo;
import model.enums.TipoClima;
import org.springframework.stereotype.Service;
import repositories.PrototipoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PrototipoService extends BaseService {
    private static PrototipoService instance;

    private PrototipoService() {
        this.repository = new PrototipoRepository();
    }

    public static PrototipoService getInstance() {
        if (instance == null) {
            return new PrototipoService();
        }
        return instance;
    }

    public List<Prototipo> prototiposByTipoClima(TipoClima tc) {
        List<Prototipo> prototipos = findAll();
        int maximoCapas = Integer.parseInt(Properties.getProperty("LIMITE_SUPERPOSICION_PRENDAS"));
        return prototipos.stream()
                .filter(p -> p.tipoClimaAsociado() == tc)
                .filter(p -> p.getParteSuperior().size()<=maximoCapas)
                .collect(Collectors.toList());
    }

}
