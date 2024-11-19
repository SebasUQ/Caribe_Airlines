package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelPerfilEmpleadoViewController {
    public AnchorPane panelPerfilE;
    public Button btnCerrarSesion;
    public PanelEmpleadoViewController ventanaI;
    private ModelFactoryController modelFactoryController;

//----------------------------------------------------------------------------------------------------//

    public void setVentanaI(PanelEmpleadoViewController ventanaInicialController) {
        this.ventanaI = ventanaInicialController;
        initialize();
    }

    public void initialize(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public void cerrarSesion() {
        try{
            modelFactoryController.guardarArchivo();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/ventanaInicial.fxml"));
            AnchorPane pane = loader.load();
            ventanaI.cambiarContenido(pane);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
