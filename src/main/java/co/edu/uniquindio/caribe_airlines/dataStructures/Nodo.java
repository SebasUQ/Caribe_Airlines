package co.edu.uniquindio.caribe_airlines.dataStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Nodo<T> {

        public T data;
        public Nodo<T> next;

        Nodo(T data) {
            this.data = data;
            this.next = null;
        }

}
