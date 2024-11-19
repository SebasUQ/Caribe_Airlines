package co.edu.uniquindio.caribe_airlines.Controller;

import co.edu.uniquindio.caribe_airlines.Model.*;
import co.edu.uniquindio.caribe_airlines.Utils.Persistencia;

import java.util.List;

public class ModelFactoryController {

    private CaribeAirlines caribeAirlines;

    private static class SingletonHolder {
        private final static ModelFactoryController INSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ModelFactoryController() {
        cargarArchivo();
        if (caribeAirlines == null){
            caribeAirlines = new CaribeAirlines();
        }
    }

    //----------------------------------------Gestión de Clientes-----------------------------------------//

    public Cliente estaRegistrado(String id) {
        return caribeAirlines.clienteRegistrado(id);
    }

    public boolean verificarContrasena(Cliente c, String con) {
        return caribeAirlines.verificarContrasena(c, con);
    }

    public void registrarCliente(Cliente cliente) {
        caribeAirlines.registrarCliente(cliente);
        guardarArchivo();
    }

    //------------------------------------------Manejo de Vuelos------------------------------------------//

    public List<Vuelo> obtenerVuelos(Ticket t) {
        return caribeAirlines.obtenerVuelos(t);
    }

    public List<Ruta> getRutas() {
        return caribeAirlines.getRutas().toArrayList();
    }

    public List<Avion> getAeronaves() {
        return caribeAirlines.getAeronaves().toArrayList();
    }

    //---------------------------------------Gestión de Tripulación---------------------------------------//

    public void registrarTripulante(Tripulante tripulante) throws Exception {
        caribeAirlines.registrarTripulante(tripulante);
    }

    public void eliminarTripulante(Tripulante tripulante) {
        caribeAirlines.eliminarTripulante(tripulante.getId());
    }

    public List<Tripulante> obtenerTripulantesDisponibles() {
        return caribeAirlines.obtenerTripulantesDisponibles();
    }

    public void asignarTripulacionAAvion(Avion avion, List<Tripulante> tripulacion) {
        for (Tripulante t : tripulacion) {
            caribeAirlines.asignarTripulacionAAvion(avion, t);
        }
    }

    public void removerTripulacionDeAvion(Avion avion, Tripulante tripulante) {
        caribeAirlines.removerTripulacionDeAvion(avion, tripulante);
    }

    public List<Tripulante> getTripulantes() {
        return caribeAirlines.getTripulantes().toArrayList();
    }

    public boolean validarTripulacionCompleta(Avion avion) {
        return caribeAirlines.validarTripulacionCompleta(avion);
    }

    public boolean necesitaMasTripulacion(Avion avion) {
        return caribeAirlines.necesitaMasTripulacion(avion);
    }

    //-----------------------------CARGADO Y GUARDADO DE ARCHIVOS-------------------------------//

    public void guardarArchivo(){
        Persistencia.guardarArchivoJSON(caribeAirlines);
        Persistencia.guardarArchivoRESPALDO_JSON(caribeAirlines);
    }

    private void cargarArchivo(){
        this.caribeAirlines = Persistencia.cargarArchivo();
        if (caribeAirlines == null){
            this.caribeAirlines = Persistencia.cargarRespaldo();
        }
    }

}