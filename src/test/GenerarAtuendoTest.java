/*
import app.Configuracion;
import model.entities.*;
import model.enums.Capa;
import model.enums.Categoria;
import model.enums.TipoClima;
import model.handlers.GeneradorAtuendoHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.PrototipoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenerarAtuendoTest {

    //TIPO PRENDAS
    public static TipoPrenda remera;
    public static TipoPrenda chomba;
    public static TipoPrenda buzo;
    public static TipoPrenda campera;
    public static TipoPrenda pantalon;
    public static TipoPrenda joggin;
    public static TipoPrenda bermuda;
    public static TipoPrenda zapatillas;
    public static TipoPrenda zapatos;

    //PRENDAS
    public static Prenda remera1;
    public static Prenda remera2;
    public static Prenda chomba1;
    public static Prenda buzo1;
    public static Prenda buzo2;
    public static Prenda campera1;
    public static Prenda bermuda1;
    public static Prenda pantalon1;
    public static Prenda joggin1;
    public static Prenda zapatillas1;
    public static Prenda zapatos1;

    //TELAS
    public static Tela jean;
    public static Tela algodon;
    public static Tela impermeable;
    public static Tela pique;
    public static Tela cuero;

    public static Guardarropa guardarropa;
    public static GeneradorAtuendoHandler generadorAtuendoHandler;

    @Mock
    public static Configuracion configuracion;

    @Mock
    public static PrototipoService prototipoService;


    @Before
    public void setup() {
        //SETUP TELAS
        jean = new Tela("jean");
        algodon = new Tela("algodon");
        pique = new Tela("pique");
        impermeable = new Tela("impermeable");
        cuero = new Tela("cuero");

        //SETUP TIPO PRENDAS
        remera = new TipoPrenda("Remera", Categoria.ParteSuperior, algodon, Capa.INTERIOR, 7, false);
        chomba = new TipoPrenda("Chomba", Categoria.ParteSuperior, pique, Capa.INTERIOR, 8, true);
        buzo = new TipoPrenda("Buzo", Categoria.ParteSuperior, algodon, Capa.INTERMEDIO, 15, false);
        campera = new TipoPrenda("Campera", Categoria.ParteSuperior, impermeable, Capa.EXTERIOR, 25, false);
        pantalon = new TipoPrenda("Pantalon", Categoria.ParteInferior, jean, Capa.INTERIOR, 15, false);
        joggin = new TipoPrenda("Joggin", Categoria.ParteInferior, algodon, Capa.INTERIOR, 5, false);
        bermuda = new TipoPrenda("Bermuda", Categoria.ParteInferior, jean, Capa.INTERIOR, 8, false);
        zapatos = new TipoPrenda("Zapatos", Categoria.Calzado, cuero, Capa.INTERIOR, 8, true);
        zapatillas = new TipoPrenda("Zapatillas", Categoria.Calzado, cuero, Capa.INTERIOR, 7, true);

        //SETUP PRENDAS
        remera1 = new Prenda("rojo", remera);
        remera2 = new Prenda("blanco", remera);
        chomba1 = new Prenda("gris", chomba);
        buzo1 = new Prenda("negro", buzo);
        buzo2 = new Prenda("azul", buzo);
        campera1 = new Prenda("rojo", campera);
        pantalon1 = new Prenda("azul", pantalon);
        bermuda1 = new Prenda("negro", bermuda);
        joggin1 = new Prenda("blanco", joggin);
        zapatillas1 = new Prenda("blanco", zapatillas);
        zapatos1 = new Prenda("negro", zapatos);

        List<Prenda> prendas = new ArrayList<Prenda>();
        Collections.addAll(prendas, remera2, remera1, chomba1, pantalon1, buzo2, buzo1, campera1, joggin1, bermuda1, zapatillas1, zapatos1);
        guardarropa = new Guardarropa(prendas);
        generadorAtuendoHandler = spy(new GeneradorAtuendoHandler(null));
        configuracion = mock(Configuracion.class);
        prototipoService = spy(PrototipoService.class);
    }

*/
/*    @Test
    public void validarAtuendoTest() {
        Atuendo atuendo = Mockito.spy(new Atuendo(remera1, pantalon1, zapatillas1));
        Mockito.doReturn(true).when(atuendo).sirveParaClima(any(TipoClima.class));
        assertTrue(generadorAtuendoHandler.validarAtuendo(atuendo, TipoClima.CALIDO));
    }*//*


    @Test
    public void filterByCapaTest() {
        List<Prenda> prendas = new ArrayList<>();
        Collections.addAll(prendas, remera2, remera1, pantalon1, buzo1, campera1);
        List<Prenda> result = generadorAtuendoHandler.filterByCapa(prendas, Capa.INTERIOR);
        List<Prenda> exectedOutput = prendas.subList(0, 2);
        assertTrue(result.containsAll(exectedOutput));
    }


    @Test
    public void getPrendasByCategoriaTest() {
        List<Prenda> prendas = new ArrayList<>();
        Collections.addAll(prendas, remera2, remera1, pantalon1, buzo1, campera1, joggin1, bermuda1);
        List<Prenda> result = generadorAtuendoHandler.getPrendasByCategoria(Categoria.ParteInferior, prendas);
        List<Prenda> exectedOutput = new ArrayList<>();
        Collections.addAll(exectedOutput, bermuda1, joggin1, pantalon1);
        assertTrue(result.containsAll(exectedOutput));
    }

    @Test
    public void agregarCapaTest() {
        Atuendo atuendo = spy(new Atuendo(remera1, pantalon1, zapatillas1));
        when(atuendo.sirveParaClima(TipoClima.MUY_FRIO))
                .thenReturn(false)
                .thenReturn(true);

        List<Prenda> prendasSuperiores = new ArrayList<>();
        Collections.addAll(prendasSuperiores, buzo1, campera1, buzo2);
        generadorAtuendoHandler.agregarCapa(atuendo, prendasSuperiores, TipoClima.MUY_FRIO, Capa.INTERMEDIO);

        assertTrue(atuendo.getSuperiores().contains(buzo2));
        assertFalse(atuendo.getSuperiores().contains(buzo1));
        assertFalse(atuendo.getSuperiores().contains(campera1));

    }

    @Test
    public void generarAtuendoCapaInteriorTest() {
        List<Prenda> prendasSuperiores = generadorAtuendoHandler.getPrendasByCategoria((Categoria.ParteSuperior), guardarropa.getPrendas());
        List<Prenda> prendasInferiores = generadorAtuendoHandler.getPrendasByCategoria((Categoria.ParteInferior), guardarropa.getPrendas());
        List<Prenda> calzados = generadorAtuendoHandler.getPrendasByCategoria((Categoria.Calzado), guardarropa.getPrendas());

        doReturn(true).when(generadorAtuendoHandler).validarAtuendo(any(Atuendo.class), any(TipoClima.class));
        Atuendo result = generadorAtuendoHandler.generarAtuendoCapaInterior(TipoClima.CALIDO, prendasSuperiores, prendasInferiores, calzados);

        assertEquals(result.getSuperiores().size(), 1);
        assertEquals(result.getSuperiores().get(0).getTipo().getNombreTipo(), "Remera");
        assertEquals(result.getInferior(), joggin1);
        assertEquals(result.getCalzado(), zapatillas1);

    }

*/
/*
    @Test
    public void generarAtuendoRandomTest() throws Exception {
        description("devuelve un atuendo acorde al tipo de clima");
        doReturn(new Atuendo(remera1, bermuda1, zapatillas1)).when(generadorAtuendoHandler).generarAtuendoCapaInterior(any(TipoClima.class), anyList(), anyList(), anyList());
        Atuendo result = generadorAtuendoHandler.generarAtuendoRandom(TipoClima.FRIO, guardarropa.getPrendas());
        assertEquals(2, result.getSuperiores().size());


        description("devuelve un atuendo acorde al tipo de clima calido");
        doReturn(new Atuendo(remera1, joggin1, zapatillas1)).when(generadorAtuendoHandler).generarAtuendoCapaInterior(any(TipoClima.class), anyList(), anyList(), anyList());
        Atuendo result2 = generadorAtuendoHandler.generarAtuendoRandom(TipoClima.CALIDO, guardarropa.getPrendas());
        assertEquals(1, result2.getSuperiores().size());

        description("devuelve un atuendo acorde al tipo de clima muy frio");
        doReturn(new Atuendo(remera1, pantalon1, zapatillas1)).when(generadorAtuendoHandler).generarAtuendoCapaInterior(any(TipoClima.class), anyList(), anyList(), anyList());
        Atuendo result3 = generadorAtuendoHandler.generarAtuendoRandom(TipoClima.MUY_FRIO, guardarropa.getPrendas());
        assertEquals(3, result3.getSuperiores().size());

    }
*//*


    @Test
    public void seleccionarPrendasPorTipoPrendasSuccessTest() {
        description("devuelve una lista con una prenda por cada tipo de prenda solicitado");
        List<TipoPrenda> tipoPrendas = new ArrayList<>();
        Collections.addAll(tipoPrendas, buzo, campera);
        List<Prenda> result = generadorAtuendoHandler.seleccionarPrendasPorTipoPrendas(guardarropa.getPrendas(), tipoPrendas);
        assertEquals(result.size(), 2);
        assertTrue(result.stream().anyMatch(prenda -> prenda.getTipo().equals(buzo)));
        assertTrue(result.stream().anyMatch(prenda -> prenda.getTipo().equals(campera)));
    }

    @Test(expected = NoSuchElementException.class)
    public void seleccionarPrendasPorTipoPrendasFailsTest() {
        description("rompe si no encuentra una premda para algun tipo de prenda solicitado");
        List<TipoPrenda> tipoPrendas = new ArrayList<>();
        Collections.addAll(tipoPrendas, buzo, campera);
        guardarropa.getPrendas().remove(campera1);
        generadorAtuendoHandler.seleccionarPrendasPorTipoPrendas(guardarropa.getPrendas(), tipoPrendas);
    }

*/
/*    @Test
    public void generarAtuendoByPrototiposTest() {

        Prototipo prototipo1 = new Prototipo(Arrays.asList(zapatillas, bermuda, remera));
        Prototipo prototipo2 = new Prototipo(Arrays.asList(zapatillas, pantalon, chomba, buzo));
        Prototipo prototipo3 = new Prototipo(Arrays.asList(zapatos, pantalon, remera, campera));
        List<Prototipo> prototipos = Arrays.asList(prototipo1, prototipo2, prototipo3);

        when(prototipoService.prototiposByTipoClima(any(TipoClima.class))).thenReturn(prototipos);


        when(generadorAtuendoHandler.seleccionarPrendasPorTipoPrendas(anyList(), anyList()))
                .thenThrow(new NoSuchElementException())
                .thenCallRealMethod();
        List<Prenda> prendas = Arrays.asList(zapatillas1, bermuda1, remera1, pantalon1, chomba1, zapatos1);
        Atuendo result = generadorAtuendoHandler.generarAtuendoByPrototipos(TipoClima.FRIO, prendas);

        assertTrue(result.getSuperiores().contains(buzo1));
        assertTrue(result.getSuperiores().contains(remera1));
        assertEquals(zapatillas1, result.getCalzado());
        assertEquals(pantalon1, result.getInferior());
        assertEquals(4, result.getPrendas().size());


    }*//*



}
*/
