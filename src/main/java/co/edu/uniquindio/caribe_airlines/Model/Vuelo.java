package co.edu.uniquindio.caribe_airlines.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Vuelo implements Serializable {
    private Ruta ruta;
    private Avion avion;
    private List<Cliente> pasajeros;
    private LocalDate fecha;
}
