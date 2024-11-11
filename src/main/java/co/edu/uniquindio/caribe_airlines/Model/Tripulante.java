package co.edu.uniquindio.caribe_airlines.Model;

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

    public Tripulante(String id, String nombre, String direccion, String email, String fechaNacimiento, String estudios, String rango) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.estudios = estudios;
        this.rango = rango;
    }

}