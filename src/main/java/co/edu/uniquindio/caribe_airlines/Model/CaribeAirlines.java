package co.edu.uniquindio.caribe_airlines.Model;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import co.edu.uniquindio.caribe_airlines.dataStructures.Nodo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class CaribeAirlines {

    private static CaribeAirlines instance;

    // Estructuras de datos personalizadas para tripulantes y aeronaves
    private MiListaEnlazada<Tripulante> tripulantes;
    private MiListaEnlazada<Avion> aeronaves;
    private MiListaEnlazada<Ruta> rutas;
    private MiListaEnlazada<Vuelo> vuelos;

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
        this.rutas = new MiListaEnlazada<>();
        this.vuelos = new MiListaEnlazada<>();

        // Cargar datos desde archivos si es necesario
        leerTripulantes();
        initializeAeronaves();
        initializeRutas();
    }

    private void initializeRutas() {
        rutas.add(new Ruta("CDMX", "Monterrey", "2H 45M", "6:00 AM"));
        rutas.add(new Ruta("CDMX", "Cancún", "3H 12M", "8:00 AM"));
        rutas.add(new Ruta("CDMX", "Buenos Aires", "9H 5M", "11:30 PM"));
        rutas.add(new Ruta("CDMX", "Los Ángeles", "3H 25M", "9:45 AM"));
        rutas.add(new Ruta("CDMX", "Bogotá", "3H 45M", "1:30 PM"));
        rutas.add(new Ruta("CDMX", "Panamá", "2H 55M", "2:45 PM"));
    }

    private void initializeAeronaves() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/aeronaves.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String modelo = data[0];
                    int capacidadPasajeros = Integer.parseInt(data[1]);
                    int capacidadCarga = Integer.parseInt(data[2]);
                    Map<String, Integer> asientos = new HashMap<>();
                    asientos.put("Ejecutiva", Integer.parseInt(data[3]));
                    asientos.put("Economica", Integer.parseInt(data[4]));
                    aeronaves.add(new Avion(modelo, capacidadPasajeros, capacidadCarga, asientos, new ArrayList<>()));
                } else {
                    System.err.println("Datos incompletos: " + line);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al leer las aeronaves", e);
        }
    }

    // Obtener la instancia única de CaribeAirlines
    public static CaribeAirlines getInstance() {
        if (instance == null) {
            instance = new CaribeAirlines();
        }
        return instance;
    }

    // Método para leer tripulantes de archivo
    private void leerTripulantes() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/tripulantes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) { // Ensure there are enough elements
                    Tripulante tripulante = new Tripulante(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                    tripulantes.add(tripulante);
                    System.out.println("Tripulante cargado: " + line);
                } else {
                    // Handle the case where data is incomplete
                    System.err.println("Datos incompletos: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CRUD Tripulación
    public void registrarTripulante(Tripulante tripulante) throws Exception {
        if (tripulantes.find(tripulante) != null) {
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

    public void actualizarTripulante(Tripulante tripulanteActualizado) {
        eliminarTripulante(tripulanteActualizado.getId());
        tripulantes.add(tripulanteActualizado);
        guardarTripulantes();
    }

    // Métodos para guardar la información en archivos
    private void guardarTripulantes() {
        try (FileWriter fw = new FileWriter(new File("src/main/resources/tripulantes.txt"), false)) {
            Nodo<Tripulante> current = tripulantes.getHead();
            StringBuilder contenido = new StringBuilder();
            while (current != null) {
                contenido.append(current.data.getId()).append(",").append(current.data.getNombre()).append(",")
                        .append(current.data.getDireccion()).append(",").append(current.data.getEmail()).append(",")
                        .append(current.data.getFechaNacimiento()).append(",").append(current.data.getEstudios()).append(",")
                        .append(current.data.getRango()).append("\n");
                current = current.next;
            }
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write(contenido.toString());
            bfw.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al guardar los tripulantes", e);
        }
    }

    private void guardarAeronaves() {
        try (FileWriter fw = new FileWriter(new File("src/main/resources/aeronaves.txt"))) {
            Nodo<Avion> current = aeronaves.getHead();
            while (current != null) {
                Avion avion = current.data;
                fw.write(String.format("%s,%d,%d,%d,%d\n",
                        avion.getModelo(),
                        avion.getCapacidadPasajeros(),
                        avion.getCapacidadCarga(),
                        avion.getAsientosDisponibles().get("Ejecutiva"),
                        avion.getAsientosDisponibles().get("Economica")));
                current = current.next;
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al guardar las aeronaves", e);
        }
    }

    public boolean necesitaTripulante(Avion avion, String rango) {
        long count = avion.getTripulacion().stream().filter(t -> t.getRango().equals(rango)).count();
        String modelo = avion.getModelo();
        if (modelo.equals("Airbus A320")) {
            if (rango.equals("Comandante")) return count < 1;
            if (rango.equals("Copiloto")) return count < 1;
            if (rango.equals("Auxiliar de vuelo")) return count < 3;
        } else if (modelo.equals("Airbus A330") || modelo.equals("Boeing 787")) {
            if (rango.equals("Comandante")) return count < 1;
            if (rango.equals("Copiloto")) return count < 1;
            if (rango.equals("Auxiliar de vuelo")) return count < 7;
        }
        return false;
    }

    public List<Tripulante> obtenerTripulantesDisponibles() {
        List<Tripulante> disponibles = new ArrayList<>();
        Nodo<Tripulante> nodoTripulante = tripulantes.getHead();
        while (nodoTripulante != null) {
            Tripulante tripulante = nodoTripulante.getData();
            boolean asignado = false;
            Nodo<Avion> nodoAvion = aeronaves.getHead();
            while (nodoAvion != null) {
                Avion avion = nodoAvion.getData();
                if (avion.getTripulacion().contains(tripulante)) {
                    asignado = true;
                    break;
                }
                nodoAvion = nodoAvion.getNext();
            }
            if (!asignado) {
                disponibles.add(tripulante);
            }
            nodoTripulante = nodoTripulante.getNext();
        }
        return disponibles;
    }

    public void asignarTripulacionAAvion(Avion avion, Tripulante tripulante) {
        if (necesitaTripulante(avion, tripulante.getRango())) {
            avion.getTripulacion().add(tripulante);
            guardarAeronaves();
        }
    }

    public void removerTripulacionDeAvion(Avion avion, Tripulante tripulante) {
        avion.getTripulacion().remove(tripulante);
        guardarAeronaves();
    }
}