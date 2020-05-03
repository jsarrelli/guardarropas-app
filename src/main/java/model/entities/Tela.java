package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tela")
public class Tela extends EntidadPersistente {

    @Column
    private String nombre;

    public Tela(String nombreTela) {
        super();
        this.nombre = nombreTela;
    }

    public Tela() {

    }

    public String getNombreTela() {
        return nombre;
    }

    public void setNombreTela(String nombreTela) {
        this.nombre = nombreTela;
    }

}
