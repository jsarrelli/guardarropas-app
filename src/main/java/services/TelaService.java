package services;

import repositories.TelaRepository;

public class TelaService extends BaseService {
    private static TelaService instance;

    private TelaService() {
        this.repository = new TelaRepository();
    }

    public static TelaService getInstance() {
        if (instance == null) {
            return new TelaService();
        }
        return instance;
    }

}
