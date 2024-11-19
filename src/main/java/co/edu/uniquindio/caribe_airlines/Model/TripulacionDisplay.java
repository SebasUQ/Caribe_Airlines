package co.edu.uniquindio.caribe_airlines.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TripulacionDisplay {
    private String avion;
    private String tripulante;
    private String rango;

    @JsonCreator
    public TripulacionDisplay(){

    }
}

