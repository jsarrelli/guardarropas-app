package model.entities;

import com.google.gson.JsonObject;
import model.ProcesadorImagenes;
import model.enums.Categoria;

import javax.persistence.*;

@Entity
@Table(name = "prenda")
public class Prenda extends EntidadPersistente {

    @Column
    private String nombre;

    @Column
    private String color1;

    @Column
    private String color2;

    @ManyToOne
    @JoinColumn(name = "id_tipo_prenda", referencedColumnName = "id")
    private TipoPrenda tipoPrenda;

    @ManyToOne
    @JoinColumn(name = "id_guardarropa", referencedColumnName = "id")
    private Guardarropa guardarropa;

    @Column
    private String foto;


    public Prenda(String color1, String color2, TipoPrenda tipoPrenda) {
        super();
        this.color1 = color1;
        this.color2 = color2;
        this.tipoPrenda = tipoPrenda;
    }

    public Prenda(String color1, TipoPrenda tipoPrenda) {
        super();
        this.color1 = color1;
        this.color2 = null;
        this.tipoPrenda = tipoPrenda;
    }

    public Prenda() {

    }


    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }


    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public TipoPrenda getTipoPrenda() {
        return tipoPrenda;
    }

    public void setTipoPrenda(TipoPrenda tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }

    public Guardarropa getGuardarropa() {
        return guardarropa;
    }

    public void setGuardarropa(Guardarropa guardarropa) {
        this.guardarropa = guardarropa;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public TipoPrenda getTipo() {
        return tipoPrenda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean esParte(Categoria categoria) {
        return this.tipoPrenda.getCategoria().equals(categoria);
    }

    public void cargarImagen(String path) {
        ProcesadorImagenes procesador = ProcesadorImagenes.getInstance();
        procesador.cargarImagen(path);
    }

    public void subirImagen(String pathOrigen) {
        ProcesadorImagenes procesador = ProcesadorImagenes.getInstance();
    }

    public void borrarImagen(String pathImagen) {
        ProcesadorImagenes procesador = ProcesadorImagenes.getInstance();
        procesador.borrarImagen(pathImagen);
    }


    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", getId());
        jsonObject.addProperty("nombre", nombre);
        jsonObject.addProperty("color1", color1);
        jsonObject.addProperty("color2", color2);
        jsonObject.addProperty("tipoPrenda", tipoPrenda.getNombreTipo());
        jsonObject.addProperty("guardarropa", guardarropa.getNombre());
        jsonObject.addProperty("foto", foto);
        jsonObject.addProperty("tela", tipoPrenda.getTela().getNombreTela());
        return jsonObject;
    }
}
