package model.tipoUsuario;

import model.entities.Guardarropa;
import model.entities.Prenda;

import java.util.List;

public interface TipoUsuario {

    public void agregarPrendaAGuardarropa(Guardarropa grp, Prenda p) throws Exception;

    public List<Prenda> getPrendasFromGuardarropas(Guardarropa guardarropa);
}
