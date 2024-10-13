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
public class Ruta implements Serializable {
    private String origen;
    private String destino;
    private String duracion;
    private String horaSalida;
    private String tipoRuta;
}
