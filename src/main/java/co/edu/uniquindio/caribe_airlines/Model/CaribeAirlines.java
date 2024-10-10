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
        initializeAeronaves();
    }

    private void initializeAeronaves() {
        aeronaves.add(new Avion("Boeing 737", 180, 20000, new HashMap<>()));
        aeronaves.add(new Avion("Airbus A320", 150, 18000, new HashMap<>()));
        aeronaves.add(new Avion("Boeing 777", 396, 35000, new HashMap<>()));
        // Add more predefined aircraft as needed
    }

    // Obtener la instancia única de CaribeAirlines
    public static CaribeAirlines getInstance() {
        if (instance == null) {
            instance = new CaribeAirlines();
        }
        return instance;
    }

    // CRUD Tripulación
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