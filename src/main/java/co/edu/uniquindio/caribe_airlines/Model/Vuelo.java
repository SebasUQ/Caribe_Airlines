package co.edu.uniquindio.caribe_airlines.Model;

import java.io.Serializable;
import java.util.List;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Vuelo implements Serializable {
    private String tipoVuelo;
    private String fechaVuelo;
    private Ruta ruta;
    private Avion avion;
    private MiListaEnlazada<Tripulante> tripulacion;
    private MiListaEnlazada<Cliente> pasajeros;

}
