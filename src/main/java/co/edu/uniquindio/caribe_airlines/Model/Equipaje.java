package co.edu.uniquindio.caribe_airlines.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Setter
@Getter
public class Equipaje {
    private String tipo;
    private double peso;
    private double sumDimensiones;
    @JsonCreator
    public Equipaje(){

    }
}
