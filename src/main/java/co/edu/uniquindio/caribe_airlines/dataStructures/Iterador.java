package co.edu.uniquindio.caribe_airlines.dataStructures;

import lombok.Getter;

import java.util.Iterator;

public class Iterador<T> implements Iterator<T> {

    private Nodo<T> n;
    @Getter
    private int posicion;

    public Iterador (Nodo<T> primero){
        this.n = primero;
        this.posicion = 0;
    }
    @Override
    public boolean hasNext() {
        return n!=null;
    }

    @Override
    public T next() {
        T valor = n.getData();
        n = n.getNext();
        posicion++;
        return valor;
    }
}
