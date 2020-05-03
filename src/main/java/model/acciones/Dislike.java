package model.acciones;

import model.entities.Atuendo;

public class Dislike implements Accion {
    private Atuendo atuendo;

    public Dislike(Atuendo atuendo) {
        super();
        this.atuendo = atuendo;
    }

    @Override
    public void ejectutar() throws Exception {
        if (atuendo.getLikes() == -1) {
            throw new Exception("Este atuendo ya se encuentra dislikeado");
        }

        atuendo.setLikes(atuendo.getLikes() - 1);

    }

}
