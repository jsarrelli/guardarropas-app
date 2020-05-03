package model.tipoUsuario;

import model.entities.Guardarropa;
import model.entities.Prenda;

import java.util.List;

public class Premium implements TipoUsuario {

    public void agregarPrendaAGuardarropa(Guardarropa grp, Prenda p) throws Exception {
        grp.addPrenda(p);
    }

    public List<Prenda> getPrendasFromGuardarropas(Guardarropa guardarropa) {
        return guardarropa.getPrendas();
    }
}
