package co.edu.uniquindio.caribe_airlines.viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelEmpleadoViewController {
    public AnchorPane panelEmpleado;
    public Tab tabFlotas, tabTripulacion, Perfil;

//----------------------------------------------------------------------------------------------------//

    public void cambiarContenido (Node pane){
        panelEmpleado.getChildren().setAll(pane);
    }

    public void initialize(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelPerfilEmpleado.fxml"));
            AnchorPane pane = loader.load();
            PanelPerfilEmpleadoViewController controller = loader.getController();
            controller.setVentanaI(this);
            Perfil.setContent(pane);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
