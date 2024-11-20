package co.edu.uniquindio.caribe_airlines.dataStructures;

import java.util.ArrayDeque;
import java.util.Deque;

public class Bicola<T> {
    private Deque<T> deque;

    public Bicola() {
        deque = new ArrayDeque<>();
    }

    public void enQueue(T item) {
        deque.addLast(item);
    }

    public T deQueue() {
        return deque.pollFirst();
    }

    public int size() {
        return deque.size();
    }

    public boolean estaLlena(int capacidadMaxima) {
        return deque.size() >= capacidadMaxima;
    }

    public int buscarCarro(T carro) {
        return deque.contains(carro) ? 1 : -1;
    }
}

