package co.edu.uniquindio.caribe_airlines.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Ruta {
    private String origen;
    private String destino;
    private String duracion;
    private String horaSalida;

    // Constructor sin argumentos requerido por algunas bibliotecas
    public Ruta() {
    }
}