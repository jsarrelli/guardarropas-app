package services;

import repositories.PrendaRepository;

public class PrendaService extends BaseService {
    private static PrendaService instance;

    private PrendaService() {
        this.repository = new PrendaRepository();
    }

    public static PrendaService getInstance() {
        if (instance == null) {
            return new PrendaService();
        }
        return instance;
    }

}
