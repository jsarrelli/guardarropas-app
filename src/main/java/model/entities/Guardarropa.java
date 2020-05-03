package model.entities;

import com.google.gson.JsonObject;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "guardarropa")
public class Guardarropa extends EntidadPersistente {

    @Column
    private String nombre;
    @OneToMany(mappedBy = "guardarropa")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Prenda> prendas;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuario_guardarropa", joinColumns = @JoinColumn(name = "id_guardarropa"), inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    private List<Usuario> usuarios;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "guardarropa")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Evento> evento;

    public Guardarropa() {
    }

    public Guardarropa(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public Guardarropa(String nombre) {
        this.nombre = nombre;
        this.usuarios = new ArrayList<Usuario>();
        this.prendas = new ArrayList<Prenda>();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Evento> getEvento() {
        return evento;
    }

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
    }

    /**
     * es llamado por los tipos de usuarios
     *
     * @param prenda
     */
    public void addPrenda(Prenda prenda) {
        prendas.add(prenda);
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public JsonObject toJson() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", getId());
        jsonObject.addProperty("nombre", nombre);
        List<Integer> idsCompartidos = usuarios.stream().map(usuario -> usuario.getId()).collect(Collectors.toList());
        jsonObject.addProperty("usuarios", idsCompartidos.toString());
        jsonObject.addProperty("cantPrendas", prendas.size());

        return jsonObject;

    }
}
