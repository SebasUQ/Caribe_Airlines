package co.edu.uniquindio.caribe_airlines.Controller;

import co.edu.uniquindio.caribe_airlines.Model.*;
import co.edu.uniquindio.caribe_airlines.Utils.Persistencia;

import java.io.FileNotFoundException;
import java.util.List;


public class ModelFactoryController {

    private CaribeAirlines caribeAirlines;

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController (){
        cargarArchivo();
        if (caribeAirlines == null){
            caribeAirlines = new CaribeAirlines();
        }
    }

//----------------------------------------Gestion Clientes-----------------------------------------//

    public Cliente estaRegistrado(String id){
        return caribeAirlines.clienteRegistrado(id);
    }

    public boolean verificarContrasena (Cliente c, String con){
        return caribeAirlines.verificarContrasena(c,con);
    }

    public void registrarCliente(Cliente cliente){
        caribeAirlines.registrarCliente(cliente);
    }


//------------------------------------------Manejo Vuelos------------------------------------------//

    public List<Vuelo> obtenerVuelos(Ticket t){
        return caribeAirlines.obtenerVuelos(t);
    }

    public List<Ruta> getRutas(){
        return caribeAirlines.getRutas().toArrayList();
    }

    public List<Avion> getAeronaves(){
        return caribeAirlines.getAeronaves().toArrayList();
    }

//---------------------------------------Gestion Tripulacion---------------------------------------//

    public void registrarTripulante(Tripulante tripulante) throws Exception {
        caribeAirlines.registrarTripulante(tripulante);
        guardarArchivo();
    }

    public void eliminarTripulante(Tripulante tripulante) throws Exception {
        caribeAirlines.eliminarTripulante(tripulante);
        guardarArchivo();
    }

    public void actualizarTripulante(Tripulante tripulanteActualizado) throws Exception {
        caribeAirlines.actualizarTripulante(tripulanteActualizado);
        guardarArchivo();
    }

    public List<Tripulante> obtenerTripulantesDisponibles(){
        return caribeAirlines.obtenerTripulantesDisponibles();
    }

    public void asignarTripulacionAAvion(Avion avion, List<Tripulante> tripulacion){
        caribeAirlines.asignarTripulacionAAvion(avion, tripulacion);

    }

    public void removerTripulacionDeAvion(Avion avion, Tripulante tripulante){
        caribeAirlines.removerTripulacionDeAvion(avion, tripulante);

    }

    public List<Tripulante> getTripulantes(){
        return caribeAirlines.getTripulantes().toArrayList();
    }

//----------------------------------CARGADO Y GUARDADO DE ARCHIVOS---------------------------------//

    private void guardarArchivo(){
        Persistencia.guardarArchivoJSON(caribeAirlines);
        Persistencia.guardarArchivoRESPALDO_JSON(caribeAirlines);
    }

    private void cargarArchivo(){
        CaribeAirlines objeto = Persistencia.cargarArchivo();
        if (objeto != null){
            this.caribeAirlines = objeto;
        }
        else{
            this.caribeAirlines = Persistencia.cargarRespaldo();
        }
    }
}
