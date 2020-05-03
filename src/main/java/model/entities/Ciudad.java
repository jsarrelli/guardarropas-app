package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ciudad")
public class Ciudad extends EntidadPersistente {

    @Column
    private String nombreCiudad;

    @Column
    private String pais;

    public Ciudad(String nombreCiudad, String pais) {
        super();
        this.nombreCiudad = nombreCiudad;
        this.pais = pais;
    }

    public Ciudad() {

    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

}
