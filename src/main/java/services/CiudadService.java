package services;

import repositories.CiudadRepository;

public class CiudadService extends BaseService {
    private static CiudadService instance;

    private CiudadService() {
        this.repository = new CiudadRepository();
    }

    public static CiudadService getInstance() {
        if (instance == null) {
            return new CiudadService();
        }
        return instance;
    }
}
