package services;

import repositories.TipoPrendaRepository;

public class TipoPrendaService extends BaseService {

    private static TipoPrendaService instance;

    private TipoPrendaService() {
        this.repository = new TipoPrendaRepository();
    }

    public static TipoPrendaService getInstance() {
        if (instance == null) {
            return new TipoPrendaService();
        }
        return instance;
    }


}
