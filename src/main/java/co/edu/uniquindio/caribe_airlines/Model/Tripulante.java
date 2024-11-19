package co.edu.uniquindio.caribe_airlines.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Tripulante implements Serializable {

    // Getters and setters
    private String id;
    private String nombre;
    private String direccion;
    private String email;
    private String fechaNacimiento;
    private String estudios;
    private String rango;

    @JsonCreator
    public Tripulante(@JsonProperty("id") String id,@JsonProperty("nombre") String nombre,@JsonProperty("direccion") String direccion,@JsonProperty("email") String email,@JsonProperty("fechaNacimiento") String fechaNacimiento,@JsonProperty("estudios") String estudios,@JsonProperty("rango") String rango) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.estudios = estudios;
        this.rango = rango;
    }

}

