package model.entities;

import model.enums.Capa;
import model.enums.Categoria;

import javax.persistence.*;

@Entity
@Table(name = "tipo_prenda")
public class TipoPrenda extends EntidadPersistente {

    @Column
    private String nombreTipo;

    @Column
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_tela", referencedColumnName = "id")
    private Tela tela;

    @Column
    @Enumerated(EnumType.STRING)
    private Capa capa;

    @Column
    private int abrigo;

    @Column
    private boolean formal;

    public TipoPrenda(String nombreTipo, Categoria categoria, Tela tela, Capa capa, int abrigo, boolean formal) {
        super();
        this.nombreTipo = nombreTipo;
        this.categoria = categoria;
        this.tela = tela;
        this.capa = capa;
        this.abrigo = abrigo;
        this.formal = formal;
    }

    public TipoPrenda() {

    }

    public boolean esFormal() {
        return formal;
    }

    public boolean esInformal() {
        return !formal;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Tela getTela() {
        return tela;
    }

    public void setTela(Tela tela) {
        this.tela = tela;
    }

    public Capa getCapa() {
        return capa;
    }

    public void setCapa(Capa capa) {
        this.capa = capa;
    }

    public int getAbrigo() {
        return abrigo;
    }

    public void setAbrigo(int abrigo) {
        this.abrigo = abrigo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + abrigo;
        result = prime * result + capa.getValue();
        result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
        result = prime * result + ((nombreTipo == null) ? 0 : nombreTipo.hashCode());
        result = prime * result + ((tela == null) ? 0 : tela.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoPrenda other = (TipoPrenda) obj;
        if (abrigo != other.abrigo)
            return false;
        if (capa != other.capa)
            return false;
        if (categoria != other.categoria)
            return false;
        if (nombreTipo == null) {
            if (other.nombreTipo != null)
                return false;
        } else if (!nombreTipo.equals(other.nombreTipo))
            return false;
        if (tela == null) {
            if (other.tela != null)
                return false;
        } else if (!tela.equals(other.tela))
            return false;
        return true;
    }

}
