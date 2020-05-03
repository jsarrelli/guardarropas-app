package model.apis.notificacionAPIs;

import app.Properties;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import model.entities.notificaciones.Notificacion;


public class NotificadorWSP implements NotificadorAPI {

    public void enviarMensaje(Notificacion notificacion) {

        String acc = Properties.getProperty("TW_SID");
        String token = Properties.getProperty("TW_TOKEN");
        String telefono = Properties.getProperty("TW_WSP");

        Twilio.init(acc, token);

        Message message = Message
                .creator(new PhoneNumber("whatsapp:+" + notificacion.getTelefono()), new PhoneNumber("whatsapp:" + telefono), notificacion.getCuerpo())
                .create();

        System.out.println(message.getBody());

    }


}

