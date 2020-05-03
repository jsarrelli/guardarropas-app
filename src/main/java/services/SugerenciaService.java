package services;

import repositories.SugerenciaRepository;

public class SugerenciaService extends BaseService {
    private static SugerenciaService instance;

    private SugerenciaService() {
        this.repository = new SugerenciaRepository();
    }

    public static SugerenciaService getInstance() {
        if (instance == null) {
            return new SugerenciaService();
        }
        return instance;
    }
}
