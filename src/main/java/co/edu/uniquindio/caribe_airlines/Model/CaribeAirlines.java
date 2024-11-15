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
    private MiListaEnlazada<Tripulante> tripulantes;
    private MiListaEnlazada<Avion> aeronaves;
    private MiListaEnlazada<Ruta> rutas;
    private MiListaEnlazada<Vuelo> vuelos;

    private static final Logger LOGGER = Logger.getLogger(CaribeAirlines.class.getName());

    private CaribeAirlines() {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "No se pudo crear el archivo de logs", e);
        }

        this.tripulantes = new MiListaEnlazada<>();
        this.aeronaves = new MiListaEnlazada<>();
        this.rutas = new MiListaEnlazada<>();
        this.vuelos = new MiListaEnlazada<>();

        leerTripulantes();
        initializeAeronaves();
        initializeRutas();
    }

    public static CaribeAirlines getInstance() {
        if (instance == null) {
            instance = new CaribeAirlines();
        }
        return instance;
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
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al leer las aeronaves", e);
        }
    }

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

    // Métodos CRUD para tripulantes
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

    public void actualizarTripulante(Tripulante tripulanteActualizado) {
        eliminarTripulante(tripulanteActualizado.getId());
        tripulantes.add(tripulanteActualizado);
        guardarTripulantes();
    }

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
    public boolean necesitaMasTripulacion(Avion avion) {
        return necesitaTripulante(avion, "Comandante") ||
                necesitaTripulante(avion, "Copiloto") ||
                necesitaTripulante(avion, "Auxiliar de vuelo");
    }
}