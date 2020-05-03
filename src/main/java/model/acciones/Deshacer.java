package model.acciones;

import model.entities.Atuendo;

public class Deshacer implements Accion {
    private Atuendo atuendo;

    public Deshacer(Atuendo atuendo) {
        super();
        this.atuendo = atuendo;
    }

    @Override
    public void ejectutar() {
        atuendo.setLikes(0);

    }

}
