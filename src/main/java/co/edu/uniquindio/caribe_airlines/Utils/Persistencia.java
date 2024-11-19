package co.edu.uniquindio.caribe_airlines.Utils;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;

import java.io.*;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Persistencia {

    private static final String RUTA_PRINCIPAL = "src/main/resources/co/edu/uniquindio/caribe_airlines/Archivos/Caribe_Airlines.json";
    private static final String RUTA_RESPALDO = "src/main/resources/co/edu/uniquindio/caribe_airlines/Archivos/Caribe_Airlines_RESPALDO.json";


    public static void guardarArchivoJSON(CaribeAirlines caribeAirlines){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(RUTA_PRINCIPAL), caribeAirlines);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public static void guardarArchivoRESPALDO_JSON(CaribeAirlines caribeAirlines){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(RUTA_RESPALDO), caribeAirlines);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }


    public static CaribeAirlines cargarArchivo(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return mapper.readValue(new File(RUTA_PRINCIPAL), CaribeAirlines.class);
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            return null; // Devuelve null si hay un error
        }
    }

    public static CaribeAirlines cargarRespaldo(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return mapper.readValue(new File(RUTA_RESPALDO), CaribeAirlines.class);
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            return null; // Devuelve null si hay un error
        }
    }
}
