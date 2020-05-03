package model.entities.notificaciones;

import model.entities.Usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("notificacion_cambio_brusco")
public class NotificacionCambioBrusco extends Notificacion {

    public NotificacionCambioBrusco(Usuario user) {
        super(user);
        armarMensaje();
    }

    public NotificacionCambioBrusco() {
    }

    @Override
    public void armarMensaje() {
        this.setAsunto("Se ha encontrado un cambio en las condiciones climaticas en uno de sus eventos");
        this.setCuerpo("Estimado " + this.nombre + ", se ha creado una sugerencia acorde a las nuevas condiciones climaticas. Por favor ingrese a la aplicación para visualizar su nuevo atuendo sugerido.");

    }


}
