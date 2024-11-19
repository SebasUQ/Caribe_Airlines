package co.edu.uniquindio.caribe_airlines.Model;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Asiento {

    private String codigo;
    private String estado;
    @JsonCreator
    public Asiento(){

    }
}
