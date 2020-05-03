package model.generadoresAtuendo;

import model.entities.Atuendo;
import model.entities.Guardarropa;
import model.entities.Prenda;
import model.entities.Usuario;
import model.enums.TipoClima;
import services.GuardarropaService;

import java.util.List;
import java.util.stream.Collectors;

public class GeneradorInformal implements GeneradorTipoDeAtuendo {

    public Atuendo generarAtuendo(TipoClima tipoClima, Guardarropa guardarropa, Usuario usuario) throws Exception {
        GuardarropaService guardarropaService = GuardarropaService.getInstance();

        List<Prenda> prendasInformales = guardarropa.getPrendas().stream()
                .filter(p -> p.getTipo().esInformal())
                .collect(Collectors.toList());
        return guardarropaService.getAtuendoForTipoClima(tipoClima, prendasInformales, usuario);
    }

}
