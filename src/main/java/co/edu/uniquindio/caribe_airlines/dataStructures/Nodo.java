package co.edu.uniquindio.caribe_airlines.dataStructures;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

        @JsonCreator
        public Nodo(@JsonProperty("data")T data) {
            this.data = data;
            this.next = null;
        }

}
