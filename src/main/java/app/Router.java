package app;

import controllers.*;
import exepciones.NoEntityFoundException;

import javax.naming.AuthenticationException;

import static spark.Spark.*;


public class Router {


    public static void init() {
        configure();
    }

    private static void configure() {

        /* - USUARIOS - */

        //datos de usuario
        get("/usuario/:id", UsuarioController::getUsuario);

        put("/usuario/tipo", UsuarioController::cambiarTipo);

        get("/usuario/registrar", UsuarioController::getToken);

        post("/usuario/login", UsuarioController::loginUsuario);

        post("/usuario/registrar", UsuarioController::registrarUsuario);

        /* - PRENDAS - */

        //cantidad de prendas

        /**
         * devolver la cantidad de prendas que tiene el usuario en total por todos los guardarropas
         * {
         * "cantidad": "1"
         * }
         */
        get("/prendas/cantidad", PrendasController::cantidad);

        put("/prenda/guardarropa", PrendasController::actualizar);

        //todas las prendas
        /**
         * devuelve todas las prendas de todos los gurdarropas
         */
        get("/prendas", PrendasController::mostrarTodas);


        //nueva prenda
        /**
         * recibe los nombre, dos colores, url de foto, id guardarropa, id tipoPrenda
         * y devuelve success o error
         */
        post("/prenda", PrendasController::nueva);

        //borrar prenda
        delete("/prenda/:id", PrendasController::eliminar);

        //tipos de prenda
        get("/tiposPrenda", PrendasController::mostrarTiposPrenda);


        /* - GUARDARROPAS - */

        //cantidad de guardarropas
        /**
         * cantidad de guardarropas que tiene un usuario
         */
        get("/guardarropas/cantidad", GuardarropasController::cantidad);

        //todos los guardarropas
        /**
         * devuelve id guardarropa, nombre, el id de los usuarios compartidos y cantidad de prendas actuales por guardarropa
         */
        //todo me sigue refresheando mal, fijate si no es error mio nomas
        get("/guardarropas", GuardarropasController::mostrarTodos);

        //nuevo guardarropas
        post("/guardarropa", GuardarropasController::nuevo);


        //borrar guardarropa
        delete("/guardarropa/:id", GuardarropasController::eliminar);


        /* - ATUENDO - */

        //sugerencia para hoy
        /**
         * devuelve la sugerencia de hoy en base a las prendas de ese guardarropa
         */
        post("/sugerencia/hoy/:idGuardarropa", AtuendoController::sugerenciaHoy);

        // devuelve la sugerencia de mañana en base a las prendas de ese guardarropa
        post("/sugerencia/manana/:idGuardarropa", AtuendoController::sugerenciaManana);

        get("/sugerencia/sugerirEvento", AtuendoController::sugerirEvento);

        //TODO
        //dislike atuendo
        put("/atuendo/dislike/:id", AtuendoController::dislike);

        //TODO
        //like atuendo
        put("/atuendo/like/:id", AtuendoController::like);


        /* - EVENTOS - */


        //cantidad de eventos
        /**
         * devuelve la cantidad de eventos que tiene un usuario
         */
        get("/eventos/cantidad", EventoController::cantidad);


        //todos los eventos
        /**
         * te muestra todos los eventos que tiene un usuario
         * id, nombre, direccion, notificador, formalidad, nombre guardarropa, periodicidad, fecha
         */
        //todo tambien esta el error del refresh que no pude arreglarlo
        get("/eventos", EventoController::mostrarTodos);

        //nuevo evento
        /**
         *
         *      "nombre": "Evento2",
         *       "calle": "calle2",
         *       "ciudad": "ciudad2",
         *       "pais": "pais2",
         *       "notificador": "notificador2",
         *       "formalidad": "formaldiad2",
         *       "guardarropa": "guardarropa2",
         *       "periodicidad": "periodicidad2",
         *       "fecha": "fecha2"
         */
        post("/evento", EventoController::nuevo);

        //delete evento
        delete("/evento/:id", EventoController::eliminar);

        //formalidades de un evento
        get("/formalidades", EventoController::mostrarFormalidades);

        //notificadores para un evento
        get("/notificadores", EventoController::mostrarNotificadores);

        //periodicidades para un evento
        get("/periodicidades", EventoController::mostrarPeriodicidades);


        /* - CLIMA -*/

        //temperatura actual
        get("/clima", ClimaController::clima);

        exception(AuthenticationException.class, (exception, request, response) -> {
            response.status(404);
            response.body("Authentication failed, papurri");
        });

        exception(NoEntityFoundException.class, (exception, request, response) -> {
            response.status(404);
            response.body(exception.getMessage());
        });
    }
}