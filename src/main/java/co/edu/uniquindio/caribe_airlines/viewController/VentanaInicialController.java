package co.edu.uniquindio.caribe_airlines.viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.io.IOException;

public class VentanaInicialController {
    @Getter
    @FXML
    public AnchorPane PanelInicial,PanelInterno;
    public Tab tabIniciar,tabRegistrarse;

    public void cambiarContenido (Node pane){
        PanelInicial.getChildren().setAll(pane);
    }

//----------------------------------------------------------------------------------------------------//
    @FXML
    public void initialize(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelIniciarSesion.fxml"));
            AnchorPane pane = loader.load();
            PanelIniciarSesionViewController controller = loader.getController();
            controller.setVentanaI(this);
            tabIniciar.setContent(pane);

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
