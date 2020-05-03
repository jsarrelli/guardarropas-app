package services;

import model.apis.notificacionAPIs.NotificadorAPI;
import model.apis.notificacionAPIs.NotificadorGmail;
import model.apis.notificacionAPIs.NotificadorSMS;
import model.apis.notificacionAPIs.NotificadorWSP;
import model.entities.notificaciones.Notificacion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificadorService {

    private static NotificadorService instance;
    private List<NotificadorAPI> notificadorAPIs;

    private NotificadorService() {
        this.notificadorAPIs = new ArrayList<>();
        notificadorAPIs.add(new NotificadorGmail());
        notificadorAPIs.add(new NotificadorSMS());
        notificadorAPIs.add(new NotificadorWSP());
    }

    public static NotificadorService getInstance() {
        if (instance == null) {
            return new NotificadorService();
        }
        return instance;
    }

    public void enviarMensaje(Notificacion notificacion) {

        for (NotificadorAPI api : notificadorAPIs) {

            api.enviarMensaje(notificacion);

        }
    }
}
