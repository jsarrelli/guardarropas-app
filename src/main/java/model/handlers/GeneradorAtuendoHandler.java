package model.handlers;


import app.Properties;
import model.entities.*;
import model.enums.Capa;
import model.enums.Categoria;
import model.enums.TipoClima;
import org.springframework.beans.factory.annotation.Autowired;
import services.PrototipoService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class GeneradorAtuendoHandler {
    // private static Logger logger = LogManager.getLogger(GeneradorAtuendoHandler.class);
    private Usuario usuario;

    private PrototipoService prototipoService;


    public GeneradorAtuendoHandler(Usuario usuario) {
        this.usuario = usuario;
        this.prototipoService = PrototipoService.getInstance();
    }

    /**
     * genera un atuendo en base a los prototipos posibles para ese tipo de clima
     *
     * @param tc
     * @param prendas
     * @return
     * @throws Exception
     */
    public Atuendo generarAtuendoByPrototipos(TipoClima tc, List<Prenda> prendas) throws NoSuchElementException {
        System.out.println("Generando atuendo by prototipos");
        List<Prototipo> prototipos = prototipoService.prototiposByTipoClima(tc);
        if (prototipos == null || prototipos.isEmpty()) {
            throw new NoSuchElementException("no hay prototipos para clima " + tc.name());
        }
        for (Prototipo prototipo : prototipos) {
            Atuendo atuendoGenerado = null;
            List<Prenda> prendasSeleccionadas;
            try {
                prendasSeleccionadas = seleccionarPrendasPorTipoPrendas(prendas, prototipo.getPrendas());
                atuendoGenerado = new Atuendo(prendasSeleccionadas);
                return atuendoGenerado;
            } catch (Exception e) {
                //logger.info("No se pudo armar prototipo");
            }

        }
        return null;
    }

    /**
     * a partir de una lista de prendas y una lista de tipo de prendas
     * te devuelve una lista que contenga una prenda acorde a cada tipo de prenda requerido
     *
     * @param prendas
     * @param tipoPrendas
     * @return una lista de prendas o null en caso de que no exista ninguna prenda para algun tipoPrenda
     */
    public ArrayList<Prenda> seleccionarPrendasPorTipoPrendas(List<Prenda> prendas, List<TipoPrenda> tipoPrendas) throws NoSuchElementException {
        return (ArrayList<Prenda>) tipoPrendas.stream()
                .map(tipoPrenda ->
                        prendas.stream().filter(prenda -> prenda.getTipo().equals(tipoPrenda)).findAny().get())
                .collect(Collectors.toList());
    }

    /**
     * te arma un atuendo random que abrigue lo minimo indispensable para ese tipo de clima
     * utilizando las prendas que tiene
     *
     * @param tc
     * @param prendas
     * @return
     */
    public Atuendo generarAtuendoRandom(TipoClima tc, List<Prenda> prendas) throws Exception {
        List<Prenda> prendasSuperiores = getPrendasByCategoria((Categoria.ParteSuperior), prendas);
        List<Prenda> prendasInferiores = getPrendasByCategoria((Categoria.ParteInferior), prendas);
        List<Prenda> calzados = getPrendasByCategoria((Categoria.Calzado), prendas);

        System.out.println("Generando atuendo random");
        Atuendo atuendoGenerado = generarAtuendoCapaInterior(tc, prendasSuperiores, prendasInferiores, calzados);

        if (!validarAtuendo(atuendoGenerado, tc))
            agregarCapa(atuendoGenerado, prendasSuperiores, tc, Capa.INTERMEDIO);

        if (!validarAtuendo(atuendoGenerado, tc))
            agregarCapa(atuendoGenerado, prendasSuperiores, tc, Capa.EXTERIOR);

        if (!validarAtuendo(atuendoGenerado, tc))
            throw new Exception("No se pudo generar atuendo random");

        // de ser posible, le agregamos un accesorio cualquiera
        List<Prenda> accesorios = getPrendasByCategoria((Categoria.Accesorio), prendas);
        if (!accesorios.isEmpty()) atuendoGenerado.getAccesorios().add(accesorios.stream().findAny().get());

        return atuendoGenerado;
    }

    /**
     * te genera un atuendo con todas prendas de capa Interior que intente cubrir ese tipo de clima
     * de no ser posible cubrirlo, te devuelve el mas abrigado
     *
     * @param tc
     * @param prendasSuperiores
     * @param prendasInferiores
     * @param calzados
     * @return
     */
    public Atuendo generarAtuendoCapaInterior(TipoClima tc, List<Prenda> prendasSuperiores, List<Prenda> prendasInferiores, List<Prenda> calzados) {
        Atuendo atuendoGenerado = null;
        for (Prenda calzado : filterByCapa(calzados, Capa.INTERIOR)) {
            for (Prenda prendasInferior : filterByCapa(prendasInferiores, Capa.INTERIOR)) {
                for (Prenda prendasSuperior : filterByCapa(prendasSuperiores, Capa.INTERIOR)) {
                    atuendoGenerado = new Atuendo(prendasSuperior, prendasInferior, calzado);
                    if (validarAtuendo(atuendoGenerado, tc)) return atuendoGenerado;
                }
            }
        }
        return atuendoGenerado;
    }

    /**
     * le va agregando capas de prendas superiores al atuendo para intentar cubrir ese tipo de clima
     * de no ser posible, te devuelve el autendo mas abrigado de esa capa
     *
     * @param atuendo
     * @param prendasSuperiores
     * @param tc
     * @param capa
     * @return
     */
    public void agregarCapa(Atuendo atuendo, List<Prenda> prendasSuperiores, TipoClima tc, Capa capa) {
        filterByCapa(prendasSuperiores, capa).stream().forEach(prenda -> {
            atuendo.getPrendas().add(prenda);
            if (atuendo.sirveParaClima(tc, usuario)) return;
            atuendo.getPrendas().remove(prenda);
        });

        if (!atuendo.sirveParaClima(tc, usuario))
            atuendo.getPrendas().add(prendasSuperiores.get(prendasSuperiores.size() - 1));
    }


    public Comparator comparadorPrendasByNivelAbrigo() {
        return (Comparator<Prenda>) (a, b) -> Integer.valueOf(a.getTipo().getAbrigo()).compareTo(Integer.valueOf(b.getTipo().getAbrigo()));
    }

    public List<Prenda> getPrendasByCategoria(Categoria categoria, List<Prenda> prendas) {
        List<Prenda> prendasFiltradas = prendas.stream().filter(prenda -> prenda.getTipo().getCategoria().equals(categoria))
                .collect(Collectors.toList());
        prendasFiltradas.sort(comparadorPrendasByNivelAbrigo());
        return prendasFiltradas;
    }

    public List<Prenda> filterByCapa(List<Prenda> prendas, Capa capa) {
        List<Prenda> prendasFiltradas = prendas.stream().filter(prenda -> prenda.getTipo().getCapa().getValue() == capa.getValue()).collect(Collectors.toList());
        prendasFiltradas.sort(comparadorPrendasByNivelAbrigo());
        return prendasFiltradas;
    }

    public boolean validarAtuendo(Atuendo atuendo, TipoClima tc) {
        int maximoCapas = Integer.parseInt(Properties.getProperty("LIMITE_SUPERPOSICION_PRENDAS"));
        if (atuendo.sirveParaClima(tc, usuario) && atuendo.getSuperiores().size() <= maximoCapas) {
            return true;
        }
        return false;
    }
}

