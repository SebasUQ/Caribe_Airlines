module Caribe_Airlines {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;


    exports co.edu.uniquindio.caribe_airlines.Aplicacion;
    opens co.edu.uniquindio.caribe_airlines.Aplicacion to javafx.fxml;

    exports co.edu.uniquindio.caribe_airlines.viewController to javafx.graphics;
    opens co.edu.uniquindio.caribe_airlines.viewController to javafx.fxml;
    


}