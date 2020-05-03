/*
import model.apis.notificacionAPIs.NotificadorGmail;
import model.apis.notificacionAPIs.NotificadorSMS;
import model.apis.notificacionAPIs.NotificadorWSP;
import model.entities.Usuario;
import org.junit.Test;
import services.NotificadorService;

public class NotificacionTest {

    //TODO los tests tienen que tener assertions
    @Test
    public void enviarCorreo() {
        NotificadorGmail gmail = new NotificadorGmail();
        Usuario u = new Usuario("scandia", "Santiago");
        u.setCorreo("candia.g.santiago@gmail.com");

        // gmail.enviarMensaje(u, "Asunto prueba individual", "Cuerpo prueba individual");
    }

    @Test
    public void enviarSMS() {
        NotificadorSMS sms = new NotificadorSMS();
        Usuario u = new Usuario("scandia", "Santiago");
        u.setTelefono("5491141758947");
        //  NotificacionCambioBrusco notificacion = sms.enviarMensaje();
    }

    @Test
    public void enviarWSP() {
        NotificadorWSP wsp = new NotificadorWSP();
        Usuario u = new Usuario("scandia", "Santiago");
        u.setTelefono("5491141758947");
        // wsp.enviarMensaje(u, "Asunto prueba individual", "Cuerpo prueba individual");
    }

    @Test
    public void enviarAPIs() {
        NotificadorService api = NotificadorService.getInstance();
        Usuario u = new Usuario("scandia", "Santiago");
        u.setTelefono("5491141758947");
        u.setCorreo("candia.g.santiago@gmail.com");
        // api.enviarMensaje(u, "Asunto prueba integral", "Cuerpo prueba integral");
    }
}*/
