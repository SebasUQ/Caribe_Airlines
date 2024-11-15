package co.edu.uniquindio.caribe_airlines.Model;

import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Setter
@Getter
public class Equipaje {
    private String tipo;
    private double peso;
    private double sumDimensiones;
}
