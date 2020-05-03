package services;

import repositories.AtuendoRepository;

public class AtuendoService extends BaseService {

    private static AtuendoService instance;

    private AtuendoService() {
        this.repository = new AtuendoRepository();
    }

    public static AtuendoService getInstance() {
        if (instance == null) {
            return new AtuendoService();
        }
        return instance;
    }
}
