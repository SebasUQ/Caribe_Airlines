package co.edu.uniquindio.caribe_airlines.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelPerfilEmpleadoViewController {
    public AnchorPane panelPerfilE;
    public Button btnCerrarSesion;
    public PanelEmpleadoViewController ventanaI;

//----------------------------------------------------------------------------------------------------//

    public void setVentanaI(PanelEmpleadoViewController ventanaInicialController) {
        this.ventanaI = ventanaInicialController;
    }
    public void cerrarSesion() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/ventanaInicial.fxml"));
            AnchorPane pane = loader.load();
            ventanaI.cambiarContenido(pane);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
