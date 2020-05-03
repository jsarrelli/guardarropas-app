package model.apis.notificacionAPIs;

import app.Properties;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import model.entities.notificaciones.Notificacion;


public class NotificadorSMS implements NotificadorAPI {

    public void enviarMensaje(Notificacion notificacion) {

        String acc = Properties.getProperty("TW_SID");
        String token = Properties.getProperty("TW_TOKEN");
        String telefono = Properties.getProperty("TW_TEL");

        Twilio.init(acc, token);

        Message message = Message
                .creator(
                        new PhoneNumber("+" + notificacion.getTelefono()), new PhoneNumber(telefono), notificacion.getCuerpo())
                .create();

        System.out.println("Mensaje: " + message.getBody() + " enviado a " + notificacion.getNombre() + "[" + notificacion.getTelefono() + "]");

    }


}

