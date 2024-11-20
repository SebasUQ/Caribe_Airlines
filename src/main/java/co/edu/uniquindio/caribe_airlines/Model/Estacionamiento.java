package co.edu.uniquindio.caribe_airlines.Model;

import java.util.*;

public class Estacionamiento {
    private static final int CAPACIDAD_MAXIMA = 10;
    private Deque<CarroEmbarque> bicola;
    private Queue<CarroEmbarque> colaEspera;
    private Stack<CarroEmbarque> pilaTemporales;

    public Estacionamiento() {
        bicola = new ArrayDeque<>(CAPACIDAD_MAXIMA);
        colaEspera = new LinkedList<>();
        pilaTemporales = new Stack<>();
    }

    public void llegada(CarroEmbarque carro) {
        if (bicola.size() < CAPACIDAD_MAXIMA) {
            bicola.addLast(carro);
        } else {
            colaEspera.offer(carro);
        }
    }

    public CarroEmbarque salida() {
        if (!bicola.isEmpty()) {
            CarroEmbarque carro = bicola.removeFirst();
            // Si hay carros en espera, agregar uno
            if (!colaEspera.isEmpty()) {
                bicola.addLast(colaEspera.poll());
            }
            return carro;
        }
        return null;
    }

    public void retirar(String idCarro) {
        // Si la bicola está vacía, no hay nada que hacer
        if (bicola.isEmpty()) {
            return;
        }

        // Crear una cola temporal para almacenar los carros que se mueven
        Deque<CarroEmbarque> temporal = new ArrayDeque<>();
        boolean carroEncontrado = false;

        // Buscar el carro y mover los demás a la cola temporal
        while (!bicola.isEmpty()) {
            CarroEmbarque carro = bicola.removeFirst();
            if (carro.getId().equals(idCarro)) {
                carroEncontrado = true;
                break;
            }
            temporal.addLast(carro);
        }

        // Guardar los carros que están después del carro retirado
        Deque<CarroEmbarque> carrosDespues = new ArrayDeque<>();
        while (!bicola.isEmpty()) {
            carrosDespues.addLast(bicola.removeFirst());
        }

        // Devolver los carros a la bicola en el orden correcto
        while (!temporal.isEmpty()) {
            bicola.addLast(temporal.removeFirst());
        }

        // Agregar los carros que estaban después del carro retirado
        while (!carrosDespues.isEmpty()) {
            bicola.addLast(carrosDespues.removeFirst());
        }

        // Si hay carros en espera y hay espacio, mover uno a la bicola
        if (!colaEspera.isEmpty() && bicola.size() < CAPACIDAD_MAXIMA) {
            bicola.addLast(colaEspera.poll());
        }
    }

    public List<CarroEmbarque> getCarrosEnBicola() {
        return new ArrayList<>(bicola);
    }

    public List<CarroEmbarque> getCarrosEnEspera() {
        return new ArrayList<>(colaEspera);
    }
}
