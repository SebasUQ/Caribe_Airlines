package co.edu.uniquindio.caribe_airlines.Model;

import java.io.Serializable;

import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Cliente implements Serializable {
    private String identificacion;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String direccion;
    private String correoElectronico;
    private String fechaNacimiento;
    private MiListaEnlazada<Ticket> tickets;

    @JsonCreator
    public Cliente(){

    }
}
