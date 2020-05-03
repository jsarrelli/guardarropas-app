package model.entities.notificaciones;

import model.entities.EntidadPersistente;
import model.entities.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_notificacion")
public abstract class Notificacion extends EntidadPersistente {

    @Column
    String nombre;

    @Column
    String telefono;

    @Column
    String correo;

    @Column
    String asunto;

    @Column
    String cuerpo;

    @Column
    LocalDateTime fecha;

    public Notificacion(Usuario user) {
        this.nombre = user.getNombre();
        this.telefono = user.getTelefono();
        this.correo = user.getCorreo();
    }

    public Notificacion() {

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setDestinatario(Usuario user) {
        this.nombre = user.getNombre();
        this.telefono = user.getTelefono();
        this.correo = user.getCorreo();
    }

    public abstract void armarMensaje();
}
