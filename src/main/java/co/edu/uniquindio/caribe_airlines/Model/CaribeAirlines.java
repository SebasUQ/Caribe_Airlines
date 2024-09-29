package co.edu.uniquindio.caribe_airlines.Model;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import co.edu.uniquindio.caribe_airlines.dataStructures.Nodo;

import java.io.*;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CaribeAirlines {

    private static CaribeAirlines instance;

    // Estructuras de datos personalizadas para tripulantes y aeronaves
    private MiListaEnlazada<Tripulante> tripulantes;
    private MiListaEnlazada<Avion> aeronaves;

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

        // Cargar datos desde archivos si es necesario
        leerTripulantes();
        leerAeronaves();
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
        try (Scanner scanner = new Scanner(new File("src/main/resources/tripulantes.txt"))) {
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(";");
                tripulantes.add(new Tripulante(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]));
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al leer los tripulantes", e);
        }
    }

    // Método para leer aeronaves de archivo
    private void leerAeronaves() {
        try (Scanner scanner = new Scanner(new File("src/main/resources/aeronaves.txt"))) {
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(";");
                aeronaves.add(new Avion(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2])));
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al leer las aeronaves", e);
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

    // CRUD Aeronaves
    public void registrarAeronave(Avion aeronave) throws Exception {
        if (aeronaves.find(aeronave) != null) {
            throw new Exception("La aeronave ya está registrada");
        }
        aeronaves.add(aeronave);
        guardarAeronaves();
    }

    public void eliminarAeronave(String modelo) {
        Avion aeronave = buscarAeronave(modelo);
        if (aeronave != null) {
            aeronaves.remove(aeronave);
            guardarAeronaves();
        }
    }

    public Avion buscarAeronave(String modelo) {
        return aeronaves.find(new Avion(modelo, 0, 0));
    }

    public void actualizarAeronave(Avion aeronaveActualizada) {
        eliminarAeronave(aeronaveActualizada.getModelo());
        aeronaves.add(aeronaveActualizada);
        guardarAeronaves();
    }

    // Métodos para guardar la información en archivos
    private void guardarTripulantes() {
        try (FileWriter fw = new FileWriter(new File("src/main/resources/tripulantes.txt"))) {
            Nodo<Tripulante> current = tripulantes.getHead();
            while (current != null) {
                fw.write(current.data.toString() + "\n");
                current = current.next;
            }
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

