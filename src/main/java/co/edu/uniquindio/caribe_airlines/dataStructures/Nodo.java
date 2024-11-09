package co.edu.uniquindio.caribe_airlines.dataStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Nodo<T> implements Serializable {

        public T data;
        public Nodo<T> next;

        Nodo(T data) {
            this.data = data;
            this.next = null;
        }

}
