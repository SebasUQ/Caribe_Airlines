package co.edu.uniquindio.caribe_airlines.dataStructures;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@Getter
@Setter
public class MiListaEnlazada<T> implements Iterable<T>, Serializable {

    private Nodo<T> head;
    private int size;

    @JsonCreator
    public MiListaEnlazada() {
        this.head = null;
        this.size = 0;
    }

    public void add(T data) {
        Nodo<T> nuevoNodo = new Nodo<>(data);
        if (head == null) {
            head = nuevoNodo;
        } else {
            Nodo<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(nuevoNodo);
        }
        size++;
    }

    public void remove(T data) {

        if (head.data.equals(data)){
            head = head.getNext();
        }
        else{
            Nodo<T> aux = head;
            while (aux.getNext() != null){
                if(aux.getNext().data.equals(data)){
                    aux.setNext(aux.getNext().getNext());
                    break;
                }

                aux = aux.getNext();
            }
        }
        size--;

    }

    public T find(T data) {
        Nodo<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get (int i){
        if (i < 0 || i > size){
            return null;
        }
        int c = 0;
        Nodo<T> aux = head;
        while (c < i){
            aux = aux.getNext();
            c++;
        }
        return aux.getData();
    }

    // Método para convertir la lista enlazada en un ArrayList
    public ArrayList<T> toArrayList() {
        ArrayList<T> arrayList = new ArrayList<>();
        Nodo<T> current = head;
        while (current != null) {
            arrayList.add(current.data); // Agregar cada elemento de la lista enlazada al ArrayList
            current = current.next;
        }
        return arrayList;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterador<>(head);
    }
}

