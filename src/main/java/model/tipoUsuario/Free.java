package model.tipoUsuario;

import app.Properties;
import model.entities.Guardarropa;
import model.entities.Prenda;

import java.util.List;


public class Free implements TipoUsuario {


    int limitePrendas;

    public Free() {
        this.limitePrendas = Integer.parseInt(Properties.getProperty("MAX_PRENDAS_GUARDARROPAS"));
    }

    public void agregarPrendaAGuardarropa(Guardarropa grp, Prenda p) throws Exception {
        if (grp.getPrendas().size() >= limitePrendas) {
            throw new Exception("No se pueden agregar mas prendas");
        } else {
            grp.addPrenda(p);
        }
    }

    public List<Prenda> getPrendasFromGuardarropas(Guardarropa guardarropa) {
        return guardarropa.getPrendas().subList(0, limitePrendas);
    }
}
