package co.edu.uniquindio.caribe_airlines.Model;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Ticket implements Serializable {

    private String tipoVuelo;
    private String tipoServicio;
    private String modalidad;
    private String destino;
    private String fechaInicio;
    private String fechaRetorno;
    private Vuelo vuelo;
    private MiListaEnlazada<Asiento> asientos;
    private double valorPagado;
    private String idTicket;
    private int totalPersonas;
    private MiListaEnlazada<Equipaje> equipajes;


}
