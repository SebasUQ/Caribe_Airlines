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
public class Tripulante implements Serializable {

    private String id;
    private String nombre;
    private String direccion;
    private String email;
    private String fechaNacimiento;
    private String estudios;
    private String rango;
}

