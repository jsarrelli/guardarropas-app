package model.acciones;

import model.entities.Atuendo;

public class Like implements Accion {
    private Atuendo atuendo;

    public Like(Atuendo atuendo) {
        super();
        this.atuendo = atuendo;
    }

    @Override
    public void ejectutar() {
        atuendo.setLikes(atuendo.getLikes() + 1);

    }

}
