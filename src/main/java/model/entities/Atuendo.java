package model.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.enums.Categoria;
import model.enums.TipoClima;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Entity
@Table(name = "atuendo")
public class Atuendo extends EntidadPersistente {

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "atuendo_prenda", joinColumns = @JoinColumn(name = "id_atuendo"), inverseJoinColumns = @JoinColumn(name = "id_prenda"))
    private List<Prenda> prendas;

    @ManyToOne
    @JoinColumn(name = "id_guardarropa", referencedColumnName = "id")
    private Guardarropa guardarropa;

    @Column
    private int likes;

    public Atuendo(List<Prenda> prendas) {
        super();
        this.prendas = prendas;
        this.likes = 0;

    }

    public Atuendo(Prenda prendaSuperior, Prenda prendaInferior, Prenda calzado) {
        this.prendas = new ArrayList<>();
        Collections.addAll(this.prendas, prendaSuperior, prendaInferior, calzado);
    }

    public Atuendo() {
        this.prendas = new ArrayList<>();
    }

    public int getNivelDeAbrigo() {
        return prendas.stream().mapToInt(prenda -> prenda.getTipo().getAbrigo()).sum();
    }

    public boolean sirveParaClima(TipoClima tp, Usuario usuario) {
        float nivelAbrigo = getNivelDeAbrigo() + usuario.getModificadorTemp();
        return nivelAbrigo >= tp.getMinAbrigo() && nivelAbrigo <= tp.getMaxAbrigo();
    }

    public boolean sirveParaClima(TipoClima tp) {
        float nivelAbrigo = getNivelDeAbrigo();
        return nivelAbrigo >= tp.getMinAbrigo() && nivelAbrigo <= tp.getMaxAbrigo();
    }


    public Prenda getInferior() {
        return prendas.stream()
                .filter(prenda -> prenda.getTipo().getCategoria().equals(Categoria.ParteInferior))
                .findFirst().orElseThrow(() -> new NoSuchElementException("El atuendo no tiene prenda inferior"));
    }


    public Prenda getCalzado() {
        return prendas.stream()
                .filter(prenda -> prenda.getTipo().getCategoria().equals(Categoria.Calzado))
                .findFirst().orElseThrow(() -> new NoSuchElementException("El atuendo no tiene calzado"));
    }


    public List<Prenda> getAccesorios() {
        return prendas.stream()
                .filter(prenda -> prenda.getTipo().getCategoria().equals(Categoria.Accesorio))
                .collect(Collectors.toList());
    }

    public List<Prenda> getSuperiores() {
        return prendas.stream()
                .filter(prenda -> prenda.getTipo().getCategoria().equals(Categoria.ParteSuperior))
                .collect(Collectors.toList());
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public Guardarropa getGuardarropa() {
        return guardarropa;
    }

    public void setGuardarropa(Guardarropa guardarropa) {
        this.guardarropa = guardarropa;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", getId());
        jsonObject.add("calzado", getCalzado().toJson());
        jsonObject.add("inferior", getInferior().toJson());

        JsonArray superiores = new JsonArray();
        getSuperiores().stream().forEach(prendaSuperior-> superiores.add(prendaSuperior.toJson()));
        jsonObject.add("superior",superiores );

        JsonArray accesorios = new JsonArray();
        getAccesorios().stream().forEach(accesorio-> accesorios.add(accesorio.toJson()));
        jsonObject.add("accesorio", accesorios);

        return jsonObject;
    }
}
