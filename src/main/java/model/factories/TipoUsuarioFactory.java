package model.factories;

import model.tipoUsuario.Free;
import model.tipoUsuario.Premium;
import model.tipoUsuario.TipoUsuario;

public class TipoUsuarioFactory {

    public TipoUsuarioFactory() {
    }

    public TipoUsuario construir(String identificador) throws Exception {
        switch (identificador) {
            case "PREMIUM":
                return new Premium();
            case "FREE":
                return new Free();
            default:
                throw new Exception("No existe identificador");
        }
    }


    public String deconstruir(TipoUsuario tipoUsuario) {
        if (Premium.class.equals(tipoUsuario.getClass())) {
            return "PREMIUM";
        } else if (Free.class.equals(tipoUsuario.getClass())) {
            return "FREE";
        }
        return null;
    }
}
