package co.edu.uniquindio.caribe_airlines.Utils;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;

import java.io.*;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Persistencia {

    private static final String RUTA_PRINCIPAL = "src/main/resources/co/edu/uniquindio/caribe_airlines/Archivos/Caribe_Airlines.json";
    private static final String RUTA_RESPALDO = "src/main/resources/co/edu/uniquindio/caribe_airlines/Archivos/Caribe_Airlines_RESPALDO.json";


    public static void guardarArchivoJSON(CaribeAirlines caribeAirlines){
        Gson gson = new Gson();
        String json = gson.toJson(caribeAirlines);
        try (FileWriter writer = new FileWriter(RUTA_PRINCIPAL)) {
            writer.write(json);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void guardarArchivoRESPALDO_JSON(CaribeAirlines caribeAirlines){
        Gson gson = new Gson();
        String json = gson.toJson(caribeAirlines);
        try (FileWriter writer = new FileWriter(RUTA_RESPALDO)) {
            writer.write(json);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static CaribeAirlines cargarArchivo(){
        CaribeAirlines caribeAirlines = null;
        try (FileReader reader = new FileReader(RUTA_PRINCIPAL)) {
            Gson gson = new Gson();
            Type objeto = new TypeToken<CaribeAirlines>() {}.getType();
            caribeAirlines = gson.fromJson(reader, objeto);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return caribeAirlines;
    }

    public static CaribeAirlines cargarRespaldo(){
        CaribeAirlines caribeAirlines = null;
        try (FileReader reader = new FileReader(RUTA_RESPALDO)) {
            Gson gson = new Gson();
            Type objeto = new TypeToken<CaribeAirlines>() {}.getType();
            caribeAirlines = gson.fromJson(reader, objeto);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return caribeAirlines;
    }
}
