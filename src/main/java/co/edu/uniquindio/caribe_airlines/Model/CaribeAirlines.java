package co.edu.uniquindio.caribe_airlines.Model;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import co.edu.uniquindio.caribe_airlines.dataStructures.Nodo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class CaribeAirlines implements Serializable {

    private static CaribeAirlines instance;

    // Estructuras de datos personalizadas para tripulantes y aeronaves
    private MiListaEnlazada<Tripulante> tripulantes;
    private MiListaEnlazada<Avion> aeronaves;
    private MiListaEnlazada<Vuelo> vuelosProgramados;
    private MiListaEnlazada<Cliente> clientes;
    private MiListaEnlazada<Ruta> rutas;

    private static final Logger LOGGER = Logger.getLogger(CaribeAirlines.class.getName());

    // Constructor privado para patrón Singleton
    private CaribeAirlines() {
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
        initializeRutas();
        leerTripulantes();
        initializeAeronaves();
    }

    private void initializeAeronaves() {

        aeronaves.add(new Avion("Airbus A320", 150, 19000, new HashMap<>()));
        aeronaves.add(new Avion("Airbus A320", 150, 19000, new HashMap<>()));
        aeronaves.add(new Avion("Airbus A330", 252, 52000, new HashMap<>()));
        aeronaves.add(new Avion("Airbus A330", 252, 52000, new HashMap<>()));
        aeronaves.add(new Avion("Boeing 787", 250, 50000, new HashMap<>()));
        aeronaves.add(new Avion("Boeing 787", 250, 50000, new HashMap<>()));
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

    // Obtener la instancia única de CaribeAirlines
    public static CaribeAirlines getInstance() {
        if (instance == null) {
            instance = new CaribeAirlines();
        }
        return instance;
    }

//------------------------------------------CRUD Tripulación------------------------------------------//
    public void registrarTripulante(Tripulante tripulante) throws Exception {
        if (tripulantes.find(tripulante) != null) {
            throw new Exception("El tripulante ya está registrado");
        }
        tripulantes.add(tripulante);
        guardarTripulantes();
    }

    public void eliminarTripulante(Tripulante tripulante) {
        if (tripulante != null) {
            tripulantes.remove(tripulante);
            guardarTripulantes();
        }
    }

    public void actualizarTripulante(Tripulante tripulanteActualizado) {
        eliminarTripulante(tripulanteActualizado);
        tripulantes.add(tripulanteActualizado);
        guardarTripulantes();
    }

//------------------------------------CARGADO Y GUARDADO DE ARCHIVOS------------------------------------//

    // Método para leer tripulantes de archivo
    private void leerTripulantes() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/tripulantes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) { // Ensure there are enough elements
                    Tripulante tripulante = new Tripulante(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                    tripulantes.add(tripulante);
                } else {
                    // Handle the case where data is incomplete
                    System.err.println("Incomplete data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Métodos para guardar la información en archivos
    private void guardarTripulantes() {
        try (FileWriter fw = new FileWriter(new File("src/main/resources/tripulantes.txt"), false)) {
            Nodo<Tripulante> current = tripulantes.getHead();
            String contenido = "";
            while (current != null) {
                contenido += current.data.getId()+","+current.data.getNombre()+","+current.data.getDireccion()+
                ","+current.data.getEmail()+","+current.data.getFechaNacimiento()+","+current.data.getEstudios()+
                ","+current.data.getRango()+"\n";
                current = current.next;
            }
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write(contenido);
            bfw.close();

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al guardar los tripulantes", e);
        }
    }

    private void guardarAeronaves() {
        try (FileWriter fw = new FileWriter(new File("src/main/resources/aeronaves.txt"))) {
            Nodo<Avion> current = aeronaves.getHead();
            while (current != null) {
                fw.write(current.data.toString() + "\n");
                current = current.next;
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al guardar las aeronaves", e);
        }
    }
}