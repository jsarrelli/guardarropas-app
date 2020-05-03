package model.handlers;

import model.entities.Usuario;

public class CalificadorHandler {

    public void tuvoFrio(Usuario usuario) {
        usuario.setModificadorTemp(usuario.getModificadorTemp() - 1);
    }

    public void tuvoCalor(Usuario usuario) {
        usuario.setModificadorTemp(usuario.getModificadorTemp() + 1);
    }


}
