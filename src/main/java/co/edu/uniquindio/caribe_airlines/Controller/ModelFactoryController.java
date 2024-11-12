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
        cargarXML();

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
        guardarXML(caribeAirlines);
    }

    public void eliminarTripulante(Tripulante tripulante) throws Exception {
        caribeAirlines.eliminarTripulante(tripulante);
        guardarXML(caribeAirlines);
    }

    public void actualizarTripulante(Tripulante tripulanteActualizado) throws Exception {
        caribeAirlines.actualizarTripulante(tripulanteActualizado);
        guardarXML(caribeAirlines);
    }

    public List<Tripulante> obtenerTripulantesDisponibles(){
        return caribeAirlines.obtenerTripulantesDisponibles();
    }

    public void asignarTripulacionAAvion(Avion avion, List<Tripulante> tripulacion){
        caribeAirlines.asignarTripulacionAAvion(avion, tripulacion);
        guardarXML(caribeAirlines);
    }

    public void removerTripulacionDeAvion(Avion avion, Tripulante tripulante){
        caribeAirlines.removerTripulacionDeAvion(avion, tripulante);
        guardarXML(caribeAirlines);
    }

    public List<Tripulante> getTripulantes(){
        return caribeAirlines.getTripulantes().toArrayList();
    }

//----------------------------------CARGADO Y GUARDADO DE ARCHIVOS---------------------------------//
    private void cargarXML() {
        try{
            this.caribeAirlines = Persistencia.cargarArchivo();
        }catch (FileNotFoundException e){
            try {
                this.caribeAirlines = Persistencia.cargarRespaldo();
            }catch (FileNotFoundException e1){
                caribeAirlines = new CaribeAirlines();
            }
        }
    }

    private void guardarXML( CaribeAirlines caribeAirlines){
        try {
            Persistencia.guardarArchivo(caribeAirlines);
        }catch (FileNotFoundException e){
            try {
                Persistencia.guardarRespaldo(caribeAirlines);
            }catch (FileNotFoundException e1){
                e1.printStackTrace();
            }
        }

    }
}
