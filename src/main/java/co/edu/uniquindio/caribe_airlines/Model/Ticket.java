package co.edu.uniquindio.caribe_airlines.Model;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Ticket {

    private String tipoVuelo;
    private String modalidad;
    private String fechaInicio;
    private String fechaRetorno;
    private Vuelo vuelo;
    private MiListaEnlazada<Asiento> asientos;
    private long valorPagado;
    private String idTicket;


}
