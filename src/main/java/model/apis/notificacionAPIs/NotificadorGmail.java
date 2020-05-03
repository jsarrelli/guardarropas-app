package model.apis.notificacionAPIs;

import app.Properties;
import model.entities.notificaciones.Notificacion;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificadorGmail implements NotificadorAPI {


    public void enviarMensaje(Notificacion notificacion) {

        String remitente = Properties.getProperty("mail_user");
        String correoDestinatario = notificacion.getCorreo();
        String host = Properties.getProperty("mail_host");
        String clave = Properties.getProperty("mail_clav");
        InternetAddress direccion = null;
        try {
            direccion = new InternetAddress(correoDestinatario);
        } catch (AddressException e) {
            e.printStackTrace();
        }


        java.util.Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        props.put("mail.smtp.auth", Properties.getProperty("mail_auth"));
        props.put("mail.smtp.starttls.enable", Properties.getProperty("mail_enable"));
        props.put("mail.smtp.port", Properties.getProperty("mail_port"));
        props.put("mail.smtp.ssl.trust", Properties.getProperty("mail_trust"));

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, direccion);   //Se pueden añadir varios de la misma manera
            message.setSubject(notificacion.getAsunto());
            message.setText(notificacion.getCuerpo());
            Transport transport = session.getTransport("smtp");
            transport.connect(host, remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Correo enviado a " + correoDestinatario);
        } catch (MessagingException me) {
            me.printStackTrace();
        }


    }


}

