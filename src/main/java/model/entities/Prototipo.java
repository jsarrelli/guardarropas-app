package model.entities;

import model.enums.Categoria;
import model.enums.TipoClima;

import javax.persistence.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static model.enums.TipoClima.*;

@Entity
@Table(name = "prototipo")
public class Prototipo extends EntidadPersistente {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "prototipo_tipo_prenda",
            joinColumns = @JoinColumn(name = "id_prototipo"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo_prenda")
    )
    private List<TipoPrenda> prendas;

    public Prototipo() {

    }

    public Prototipo(List<TipoPrenda> prendas) {
        this.prendas = prendas;
    }

    public List<TipoPrenda> getParteSuperior() {
        return prendas.stream()
                .filter(tipoPrenda -> tipoPrenda.getCategoria().equals(Categoria.ParteSuperior))
                .collect(Collectors.toList());
    }

    public TipoPrenda getParteInferior() {
        return prendas.stream()
                .filter(tipoPrenda -> tipoPrenda.getCategoria().equals(Categoria.ParteInferior))
                .findFirst().orElseThrow(() -> new NoSuchElementException("El atuendo no tiene prenda inferior"));
    }

    public TipoPrenda getCalzado() {
        return prendas.stream()
                .filter(tipoPrenda -> tipoPrenda.getCategoria().equals(Categoria.Calzado))
                .findFirst().orElseThrow(() -> new NoSuchElementException("El atuendo no tiene calzado"));
    }

    public List<TipoPrenda> getAccesorio() {
        return prendas.stream()
                .filter(tipoPrenda -> tipoPrenda.getCategoria().equals(Categoria.Calzado))
                .collect(Collectors.toList());
    }

    public boolean isMonocapa() {
        return getParteSuperior().size() > 1;
    }

    public List<TipoPrenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<TipoPrenda> prendas) {
        this.prendas = prendas;
    }

    public int nivelDeAbrigo() {
        return prendas.stream().mapToInt(tipoPrenda -> tipoPrenda.getAbrigo()).sum();

    }

    public TipoClima tipoClimaAsociado() {
        if (nivelDeAbrigo() >= FRIO.getMinAbrigo() && nivelDeAbrigo() <= FRIO.getMaxAbrigo()) return FRIO;
        if (nivelDeAbrigo() > MUY_FRIO.getMinAbrigo() && nivelDeAbrigo() <= MUY_FRIO.getMaxAbrigo()) return MUY_FRIO;
        if (nivelDeAbrigo() > TEMPLADO.getMinAbrigo() && nivelDeAbrigo() <= TEMPLADO.getMaxAbrigo()) return TEMPLADO;
        if (nivelDeAbrigo() > CALIDO.getMinAbrigo() && nivelDeAbrigo() <= CALIDO.getMaxAbrigo()) return CALIDO;
        else return CALUROSO;
    }

}
