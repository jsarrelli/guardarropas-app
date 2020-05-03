package model.entities.notificaciones;

import model.entities.Usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("notificacion_creacion_sugerencia")
public class NotificacionCreacionSugerencia extends Notificacion {

    public NotificacionCreacionSugerencia(Usuario user) {
        super(user);
        armarMensaje();
    }

    public NotificacionCreacionSugerencia() {
    }

    @Override
    public void armarMensaje() {
        this.setAsunto("Posee una nueva sugerencia de atuendo disponible");
        this.setCuerpo("Estimado " + this.nombre + ", se ha creado una nueva sugerencia. Por favor ingrese a la aplicación para visualizar su atuendo sugerido.");
    }


}
