package co.edu.uniquindio.caribe_airlines.Utils;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Persistencia {

    private static final String RUTA_XML = "src/main/resources/co/edu/uniquindio/caribe_airlines/Archivos/ModelCaribeAirlines.xml";
    private static final String RUTA_XML_RESPALDO = "src/main/resources/co/edu/uniquindio/caribe_airlines/Archivos/ModelCaribeAirlines_RESPALDO.xml";


    public static CaribeAirlines cargarArchivo() throws FileNotFoundException {

        CaribeAirlines caribeAirlines;

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(RUTA_XML));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();

        caribeAirlines = (CaribeAirlines) objetoXML;
        return caribeAirlines;
    }

    public static CaribeAirlines cargarRespaldo() throws FileNotFoundException{
        CaribeAirlines caribeAirlines;

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(RUTA_XML_RESPALDO));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        caribeAirlines = (CaribeAirlines) objetoXML;
        return caribeAirlines;
    }

    public static void guardarArchivo(CaribeAirlines caribeAirlines) throws FileNotFoundException {
        XMLEncoder codificadorXML;

        codificadorXML = new XMLEncoder(new FileOutputStream(RUTA_XML));
        codificadorXML.writeObject(caribeAirlines);
        codificadorXML.close();
    }

    public static void guardarRespaldo(CaribeAirlines caribeAirlines) throws FileNotFoundException {
        XMLEncoder codificadorXML;

        codificadorXML = new XMLEncoder(new FileOutputStream(RUTA_XML_RESPALDO));
        codificadorXML.writeObject(caribeAirlines);
        codificadorXML.close();
    }
}
