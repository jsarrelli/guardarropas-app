package model.acciones;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class AccionHandler {

    List acciones;

    public AccionHandler() {
        super();
        this.acciones = new ArrayList<Accion>();
    }

    public void addAccion(Accion accion) {
        acciones.add(accion);
    }

    public void votarTodas() {
        acciones.parallelStream().forEach(accion -> votar((Accion) accion));
    }

    public void votar(Accion accion) {
        try {
            accion.ejectutar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
