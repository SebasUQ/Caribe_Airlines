package co.edu.uniquindio.caribe_airlines.Model;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import co.edu.uniquindio.caribe_airlines.dataStructures.Nodo;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class CaribeAirlines implements Serializable {

    // Estructuras de datos personalizadas para tripulantes y aeronaves
    private MiListaEnlazada<Tripulante> tripulantes;
    private MiListaEnlazada<Avion> aeronaves;
    private MiListaEnlazada<Vuelo> vuelosProgramados;
    private MiListaEnlazada<Cliente> clientes;
    private MiListaEnlazada<Ruta> rutas;

    private static final Logger LOGGER = Logger.getLogger(CaribeAirlines.class.getName());

    @JsonCreator
    public CaribeAirlines() {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "No se pudo crear el archivo de logs", e);
        }

        // Inicializar las listas personalizadas
        this.tripulantes = new MiListaEnlazada<>();
        this.aeronaves = new MiListaEnlazada<>();
        this.vuelosProgramados = new MiListaEnlazada<>();
        this.clientes = new MiListaEnlazada<>();
        this.rutas = new MiListaEnlazada<>();

        // Cargar datos desde archivos si es necesario
        //initializeRutas();
        //leerTripulantes();
        //initializeAeronaves();
    }

    private void initializeAeronaves() {

        aeronaves.add(new Avion("Airbus A320", 150, 19000, new HashMap<>(), new ArrayList<>()));
        aeronaves.add(new Avion("Airbus A320", 150, 19000, new HashMap<>(), new ArrayList<>()));
        aeronaves.add(new Avion("Airbus A330", 252, 52000, new HashMap<>(), new ArrayList<>()));
        aeronaves.add(new Avion("Airbus A330", 252, 52000, new HashMap<>(), new ArrayList<>()));
        aeronaves.add(new Avion("Boeing 787", 250, 50000, new HashMap<>(), new ArrayList<>()));
        aeronaves.add(new Avion("Boeing 787", 250, 50000, new HashMap<>(), new ArrayList<>()));
        // Add more predefined aircraft as needed
    }

    private void initializeRutas(){
        rutas.add(new Ruta("CDMX","Monterrey", "2(h):45(m)","6:00 am","Nacional"));
        rutas.add(new Ruta("CDMX","Cancun", "3(h):12(m)","8:00 am","Nacional"));
        rutas.add(new Ruta("CDMX","Buenos Aires", "9(h):05(m)","11:20 pm","Internacional"));
        rutas.add(new Ruta("CDMX","Los Angeles", "3(h):25(m)","9:45 am","Internacional"));
        rutas.add(new Ruta("CDMX","Bogota", "3(h):45(m)","1:30 pm","Internacional"));
        rutas.add(new Ruta("CDMX","Panama", "2(h):55(m)","2:45 pm","Internacional"));
    }

//----------------------------------------Gestion Clientes-----------------------------------------//

    public Cliente clienteRegistrado(String id){
        Cliente cliente = null;
        for (Cliente c : clientes){
            if(c.getIdentificacion().equals(id)){
                cliente = c;
                break;
            }
        }
        return cliente;
    }

    public boolean verificarContrasena(Cliente c, String con){
        boolean cent = false;
        if (c.getContrasena().equals(con)){
            cent = true;
        }
        return cent;
    }

    public void registrarCliente (Cliente cliente){
        clientes.add(cliente);
    }


//------------------------------------------Manejo Vuelos------------------------------------------//

    public List<Vuelo> obtenerVuelos(Ticket t){
        List<Vuelo> vuelosDisponibles = new ArrayList<>();
        if (vuelosProgramados.isEmpty()){
            initializeVuelos(t);
            vuelosDisponibles = obtenerVuelos(t);
        }
        else{
            for(Vuelo v: vuelosProgramados){
                if (v.getFechaVuelo().equals(t.getFechaInicio()) && v.getRuta().getDestino().equals(t.getDestino())){
                    vuelosDisponibles.add(v);
                }
            }
            if (vuelosDisponibles.isEmpty()){
                initializeVuelos(t);
                vuelosDisponibles = obtenerVuelos(t);
            }
        }
        return vuelosDisponibles;
    }

   private void initializeVuelos(Ticket t){
        if (t.getTipoVuelo().equals("Nacional")){
            vuelosProgramados.add(new Vuelo("Nacional",t.getFechaInicio(),obtenerRuta(t),aeronaves.get(0), new MiListaEnlazada<>()));
            vuelosProgramados.add(new Vuelo("Nacional",t.getFechaInicio(),obtenerRuta(t),aeronaves.get(1), new MiListaEnlazada<>()));
        }
        else{
            vuelosProgramados.add(new Vuelo("Internacional",t.getFechaInicio(),obtenerRuta(t),aeronaves.get(2), new MiListaEnlazada<>()));
            vuelosProgramados.add(new Vuelo("Internacional",t.getFechaInicio(),obtenerRuta(t),aeronaves.get(3), new MiListaEnlazada<>()));
            vuelosProgramados.add(new Vuelo("Internacional",t.getFechaInicio(),obtenerRuta(t),aeronaves.get(4), new MiListaEnlazada<>()));
            vuelosProgramados.add(new Vuelo("Internacional",t.getFechaInicio(),obtenerRuta(t),aeronaves.get(5), new MiListaEnlazada<>()));
        }
   }

   private Ruta obtenerRuta(Ticket t){
        Ruta r = null;
        if (t.getDestino().equals("Monterrey")){
            r = rutas.get(0);
        }
        if (t.getDestino().equals("Cancun")){
            r = rutas.get(1);
        }
        if (t.getDestino().equals("Buenos Aires")){
            r = rutas.get(2);
        }
        if (t.getDestino().equals("Los Angeles")){
            r = rutas.get(3);
        }
        if (t.getDestino().equals("Bogota")){
           r = rutas.get(4);
        }
        if (t.getDestino().equals("Panama")){
           r = rutas.get(5);
        }
        return r;
   }


//------------------------------------------Manejo Tripulación------------------------------------------//
    public void registrarTripulante(Tripulante tripulante) throws Exception {
        if (buscarTripulante(tripulante.getId()) != null) {
            throw new Exception("El tripulante ya está registrado");
        }
        tripulantes.add(tripulante);
        guardarTripulantes();
    }

    public void eliminarTripulante(String id) {
        Tripulante tripulante = buscarTripulante(id);
        if (tripulante != null) {
            tripulantes.remove(tripulante);
            guardarTripulantes();
        }
    }

    public Tripulante buscarTripulante(String id) {
        return tripulantes.find(new Tripulante(id, null, null, null, null, null, null));
    }


    public List<Tripulante> obtenerTripulantesDisponibles() {
        List<Tripulante> disponibles = new ArrayList<>();
        for (Tripulante tripulante : tripulantes.toArrayList()) {
            boolean asignado = false;
            for (Avion avion : aeronaves.toArrayList()) {
                if (avion.getTripulacion().contains(tripulante)) {
                    asignado = true;
                    break;
                }
            }
            if (!asignado) {
                disponibles.add(tripulante);
            }
        }
        return disponibles;
    }

    public void asignarTripulacionAAvion(Avion avion, Tripulante tripulante) {
        if (avion != null && tripulante != null && necesitaTripulante(avion, tripulante.getRango())) {
            avion.getTripulacion().add(tripulante);
            guardarAeronaves();
        }
    }

    public void removerTripulacionDeAvion(Avion avion, Tripulante tripulante) {
        if (avion != null && tripulante != null) {
            avion.getTripulacion().remove(tripulante);
            guardarAeronaves();
        }
    }

    public boolean validarTripulacionCompleta(Avion avion) {
        long comandantes = avion.getTripulacion().stream()
                .filter(t -> t.getRango().equals("Comandante")).count();
        long copilotos = avion.getTripulacion().stream()
                .filter(t -> t.getRango().equals("Copiloto")).count();
        long auxiliares = avion.getTripulacion().stream()
                .filter(t -> t.getRango().equals("Auxiliar de vuelo")).count();

        if (avion.getModelo().equals("Airbus A320")) {
            return comandantes == 1 && copilotos == 1 && auxiliares == 3;
        } else if (avion.getModelo().equals("Airbus A330") || avion.getModelo().equals("Boeing 787")) {
            return comandantes == 1 && copilotos == 1 && auxiliares == 7;
        }
        return false;
    }

    public boolean necesitaTripulante(Avion avion, String rango) {
        if (avion == null || rango == null) {
            return false;
        }

        long count = avion.getTripulacion().stream()
                .filter(t -> t.getRango().equals(rango))
                .count();

        switch (avion.getModelo()) {
            case "Airbus A320":
                switch (rango) {
                    case "Comandante": return count < 1;
                    case "Copiloto": return count < 1;
                    case "Auxiliar de vuelo": return count < 3;
                }
                break;
            case "Airbus A330":
            case "Boeing 787":
                switch (rango) {
                    case "Comandante": return count < 1;
                    case "Copiloto": return count < 1;
                    case "Auxiliar de vuelo": return count < 7;
                }
                break;
        }
        return false;
    }

    public boolean necesitaMasTripulacion(Avion avion) {
        return necesitaTripulante(avion, "Comandante") ||
                necesitaTripulante(avion, "Copiloto") ||
                necesitaTripulante(avion, "Auxiliar de vuelo");
    }

//------------------------------------CARGADO Y GUARDADO DE ARCHIVOS------------------------------------//

    // Método para leer tripulantes de archivo
    private void leerTripulantes() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/tripulantes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    Tripulante tripulante = new Tripulante(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                    tripulantes.add(tripulante);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al leer tripulantes", e);
        }
    }


    // Métodos para guardar la información en archivos
    private void guardarTripulantes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/resources/tripulantes.txt"))) {
            for (Tripulante tripulante : tripulantes.toArrayList()) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s\n",
                        tripulante.getId(),
                        tripulante.getNombre(),
                        tripulante.getDireccion(),
                        tripulante.getEmail(),
                        tripulante.getFechaNacimiento(),
                        tripulante.getEstudios(),
                        tripulante.getRango());
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al guardar tripulantes", e);
        }
    }

    private void guardarAeronaves() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/resources/aeronaves.txt"))) {
            for (Avion avion : aeronaves.toArrayList()) {
                writer.printf("%s,%d,%d,%d,%d\n",
                        avion.getModelo(),
                        avion.getCapacidadPasajeros(),
                        avion.getCapacidadCarga(),
                        avion.getAsientosDisponibles().get("Ejecutiva"),
                        avion.getAsientosDisponibles().get("Economica"));
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al guardar aeronaves", e);
        }
    }
}