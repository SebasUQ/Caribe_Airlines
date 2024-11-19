module Caribe_Airlines {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.logging;
    requires java.desktop;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;


    exports co.edu.uniquindio.caribe_airlines.Aplicacion;
    opens co.edu.uniquindio.caribe_airlines.Aplicacion to javafx.fxml;

    exports co.edu.uniquindio.caribe_airlines.viewController to javafx.graphics;
    opens co.edu.uniquindio.caribe_airlines.viewController to javafx.fxml;

    exports co.edu.uniquindio.caribe_airlines.Model;
    opens co.edu.uniquindio.caribe_airlines.Model to com.google.gson;

    exports co.edu.uniquindio.caribe_airlines.dataStructures to com.fasterxml.jackson.databind;
    opens co.edu.uniquindio.caribe_airlines.dataStructures to com.fasterxml.jackson.databind;
}