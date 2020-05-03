package model.entities;

import com.google.gson.JsonObject;
import model.apis.notificacionAPIs.NotificadorAPI;
import model.enums.Periodicidad;
import model.factories.GeneradorAtuendoFactory;
import model.factories.NotificadorFactory;
import model.generadoresAtuendo.GeneradorTipoDeAtuendo;
import services.ClimaService;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "evento")
public class Evento extends EntidadPersistente {

    @Column
    private String nombreEvento;
    @Column
    private String direccion;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_guardarropa", referencedColumnName = "id")
    private Guardarropa guardarropa;
    @Column
    private LocalDateTime fecha;
    @Column
    @Enumerated(EnumType.STRING)
    private Periodicidad periodicidad;
    @Column
    private boolean activado;
    @Column
    private String generadorTipoDeAtuendo;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "evento")
    private List<Sugerencia> sugerencias;
    @Column
    private String notificador;

    public Evento(String nombreEvento, Usuario usuario, String direccion, Guardarropa guardarropa, LocalDateTime fecha,
                  Periodicidad periodicidad, String generadorTipoDeAtuendo, String notificador) {
        this.nombreEvento = nombreEvento;
        this.usuario = usuario;
        this.direccion = direccion;
        this.guardarropa = guardarropa;
        this.fecha = fecha;
        this.periodicidad = periodicidad;
        this.activado = true;
        this.generadorTipoDeAtuendo = generadorTipoDeAtuendo;
        this.sugerencias = new ArrayList<>();
        this.notificador = notificador;
    }

    public Evento() {
    }

    public String getNombre() {
        return nombreEvento;
    }

    public void setNombre(String nombre) {
        nombreEvento = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public GeneradorTipoDeAtuendo getGeneradorTipoDeAtuendo() {
        try {
            return new GeneradorAtuendoFactory().construir(generadorTipoDeAtuendo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setGeneradorTipoDeAtuendo(String generadorTipoDeAtuendo) {
        this.generadorTipoDeAtuendo = generadorTipoDeAtuendo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario user) {
        this.usuario = user;
    }

    public Guardarropa getGuardarropa() {
        return guardarropa;
    }

    public void setGuardarropa(Guardarropa guardarropa) {
        this.guardarropa = guardarropa;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public Periodicidad getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Periodicidad periodicidad) {
        this.periodicidad = periodicidad;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<Sugerencia> getSugerencias() {
        return sugerencias;
    }

    public void setSugerencias(List<Sugerencia> sugerencias) {
        this.sugerencias = sugerencias;
    }

    public NotificadorAPI getNotificador() {
        try {
            return new NotificadorFactory().construir(this.notificador);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setNotificador(String notificador) {
        this.notificador = notificador;
    }

    /* Si el evento tiene alguna periodicidad actuliza su fecha de acuerdo a la misma,
                si el evento es unico lo desactiva*/
    public void actualizarFecha() {
        switch (periodicidad) {
            case UNICO:
                desactivarEvento();
            case ANUAL:
                fecha = fecha.plusYears(1);
                break;
            case DIARIO:
                fecha = fecha.plusDays(1);
                break;
            case MENSUAL:
                fecha = fecha.plusMonths(1);
                break;
            case SEMANAL:
                fecha = getFecha().plusWeeks(1);
                break;
        }
    }

    public void desactivarEvento() {
        activado = false;
    }


    /**
     * En base a la ultima sugerencia realizada te dice si se produjo o no un cambio brusco de temperatura
     *
     * @return
     */
    public boolean seProdujoCambioBruscoTemperatua() {

        ClimaService climaService = ClimaService.getInstance();
        double temperaturaActual = climaService.getTemperaturaByCiudadYFecha(LocalDate.now(), "buenos aires");
        Sugerencia ultimaSugerencia = obtenerUltimaSugerencia();

        if (Math.abs(temperaturaActual - ultimaSugerencia.getTemperatura()) > 10) {
            return true;
        }
        return false;
    }

    /**
     * te devuelve la ultima sugerencia realizada
     *
     * @return
     */
    public Sugerencia obtenerUltimaSugerencia() {
        return sugerencias.stream().max(Comparator.comparing(Sugerencia::getFecha)).get();
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", getId());
        jsonObject.addProperty("nombre", nombreEvento);
        jsonObject.addProperty("direccion", direccion);
        jsonObject.addProperty("notificador", notificador);
        jsonObject.addProperty("formalidad", generadorTipoDeAtuendo);
        jsonObject.addProperty("guardarropa", guardarropa.getNombre());
        jsonObject.addProperty("periodicidad", periodicidad.toString());
        jsonObject.addProperty("fecha", fecha.toString());
        return jsonObject;
    }


}
