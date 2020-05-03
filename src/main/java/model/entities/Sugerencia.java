package model.entities;

import model.entities.notificaciones.Notificacion;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity(name = "sugerencia")
public class Sugerencia extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "id_atuendo", referencedColumnName = "id")
    private Atuendo atuendoGenerado;

    @Column
    private LocalDateTime fecha;

    @Column
    private double temperatura;

    @ManyToOne
    @JoinColumn(name = "id_evento", referencedColumnName = "id")
    private Evento evento;


    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "id_notificacion", referencedColumnName = "id")
    private Notificacion notificacion;

    public Sugerencia(Atuendo atuendoGenerado, LocalDateTime fecha, double temperatura, Evento evento, Usuario usuario) {
        this.atuendoGenerado = atuendoGenerado;
        this.fecha = fecha;
        this.temperatura = temperatura;
        this.evento = evento;
        this.usuario = usuario;
    }

    public Sugerencia() {

    }


    public Atuendo getAtuendoGenerado() {
        return atuendoGenerado;
    }

    public void setAtuendoGenerado(Atuendo atuendoGenerado) {
        this.atuendoGenerado = atuendoGenerado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public void notificar(Notificacion notificacion) {
        notificacion.setDestinatario(usuario);
        notificacion.armarMensaje();
        notificacion.setFecha(LocalDateTime.now());
        evento.getNotificador().enviarMensaje(notificacion);
        setNotificacion(notificacion);
    }
}
