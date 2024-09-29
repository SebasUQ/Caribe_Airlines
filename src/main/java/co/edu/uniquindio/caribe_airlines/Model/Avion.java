package co.edu.uniquindio.caribe_airlines.Model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor

public class Avion implements Serializable {
    private String modelo;
    private int capacidadPasajeros;
    private int capacidadCarga;
}
