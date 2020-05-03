package model.entities;

import model.Hasher;
import model.factories.TipoUsuarioFactory;
import model.tipoUsuario.TipoUsuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "usuario")
public class Usuario extends EntidadPersistente {

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String nombre;

    @JoinTable(name = "usuario_guardarropa", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_guardarropa"))
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Guardarropa> guardarropas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "usuario")
    private List<Evento> eventos;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "usuario_atuendo", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_atuendo"))
    private List<Atuendo> atuendosSugeridos;

    @Column
    private int modificadorTemp;

    @Column
    private String correo;

    @Column
    private String telefono;

    @Column
    private String tipoUsuario;

    public Usuario() {
    }

    public Usuario(String usuario, String nombre) {
        this.username = usuario;
        this.nombre = nombre;
    }

    public Usuario(String username, String password, String nombre, int modificadorTemp, String correo, String telefono, String tipoUsuario) {

        this.username = username;
        this.password = Hasher.generarSHA256(password);
        this.nombre = nombre;
        this.guardarropas = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.atuendosSugeridos = new ArrayList<>();
        this.modificadorTemp = modificadorTemp;
        this.correo = correo;
        this.telefono = telefono;
        this.tipoUsuario = tipoUsuario;
    }

    public int getModificadorTemp() {
        return this.modificadorTemp;
    }

    public void setModificadorTemp(int modificadorTemp) {
        this.modificadorTemp = modificadorTemp;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Hasher.generarSHA256(password);
        ;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoUsuario getTipoUsuario() {
        try {
            return new TipoUsuarioFactory().construir(this.tipoUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void agregarGuardarropas(Guardarropa guardarropa) {
        guardarropas.add(guardarropa);
    }


    public void agregarPrendaAGuardarropa(Prenda prenda, Guardarropa guardarropa) {
        guardarropa.addPrenda(prenda);
    }

    public void crearPrenda(String color1, String color2, TipoPrenda tipo) {
    }

    public void cambiarTipoUsuario(String tipo) {
        this.setTipoUsuario(tipo);
    }

    public List<Atuendo> getAtuendosSugeridos() {
        return atuendosSugeridos;
    }

    public void setAtuendosSugeridos(List<Atuendo> atuendosSugeridos) {
        this.atuendosSugeridos = atuendosSugeridos;
    }

    public List<Prenda> getPrendaFromGuardarropa(Guardarropa guardarropa) {
        return getTipoUsuario().getPrendasFromGuardarropas(guardarropa);
    }

    public List<Guardarropa> getGuardarropas() {
        return guardarropas;
    }

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
