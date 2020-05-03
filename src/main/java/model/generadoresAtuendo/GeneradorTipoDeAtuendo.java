package model.generadoresAtuendo;

import model.entities.Atuendo;
import model.entities.Guardarropa;
import model.entities.Usuario;
import model.enums.TipoClima;

public interface GeneradorTipoDeAtuendo {
    Atuendo generarAtuendo(TipoClima tipoClima, Guardarropa guardarropa, Usuario usuario) throws Exception;
}
