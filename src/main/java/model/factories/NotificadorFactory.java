package model.factories;

import model.apis.notificacionAPIs.NotificadorAPI;
import model.apis.notificacionAPIs.NotificadorGmail;
import model.apis.notificacionAPIs.NotificadorSMS;
import model.apis.notificacionAPIs.NotificadorWSP;

public class NotificadorFactory {

    public NotificadorFactory() {
    }

    public NotificadorAPI construir(String identificador) throws Exception {
        switch (identificador) {
            case "GMAIL":
                return new NotificadorGmail();
            case "SMS":
                return new NotificadorSMS();
            case "WSP":
                return new NotificadorWSP();
            default:
                throw new Exception("No existe identificador");
        }
    }


    public String deconstruir(NotificadorAPI notificadorAPI) {
        if (NotificadorGmail.class.equals(notificadorAPI.getClass())) {
            return "GMAIL";
        } else if (NotificadorSMS.class.equals(notificadorAPI.getClass())) {
            return "SMS";
        } else if (NotificadorWSP.class.equals(notificadorAPI.getClass())) {
            return "WSP";
        }
        return null;
    }

}
