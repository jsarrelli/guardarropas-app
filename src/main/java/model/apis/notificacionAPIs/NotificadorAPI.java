package model.apis.notificacionAPIs;


import model.entities.notificaciones.Notificacion;

public interface NotificadorAPI {

    void enviarMensaje(Notificacion noti);
}