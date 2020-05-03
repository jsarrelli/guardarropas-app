package services;

import repositories.UsuarioRepositorio;

public class UsuarioService extends BaseService {

    private static UsuarioService instance;

    private UsuarioService() {
        this.repository = new UsuarioRepositorio();
    }

    public static UsuarioService getInstance() {
        if (instance == null) {
            return new UsuarioService();
        }
        return instance;
    }
}
