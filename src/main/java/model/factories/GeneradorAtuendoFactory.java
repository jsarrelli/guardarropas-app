package model.factories;

import model.generadoresAtuendo.GeneradorFormal;
import model.generadoresAtuendo.GeneradorInformal;
import model.generadoresAtuendo.GeneradorTipoDeAtuendo;


public class GeneradorAtuendoFactory {

    public GeneradorAtuendoFactory() {
    }

    public GeneradorTipoDeAtuendo construir(String identificador) throws Exception {
        switch (identificador) {
            case "FORMAL":
                return new GeneradorFormal();
            case "INFORMAL":
                return new GeneradorInformal();
            default:
                throw new Exception("No existe identificador");
        }
    }


    public String deconstruir(GeneradorTipoDeAtuendo generador) {
        if (GeneradorFormal.class.equals(generador.getClass())) {
            return "FORMAL";
        } else if (GeneradorInformal.class.equals(generador.getClass())) {
            return "INFORMAL";
        }
        return null;
    }
}
