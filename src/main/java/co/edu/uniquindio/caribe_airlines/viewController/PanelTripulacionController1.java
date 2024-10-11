package co.edu.uniquindio.caribe_airlines.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelTripulacionController1 {
    public Button btnVolver;
    public AnchorPane panelTripulacion1;

    public void cambiarPanel (AnchorPane panel){
        this.panelTripulacion1 = panel;
    }


    public void previousPanel(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTripulacion.fxml"));
            panelTripulacion1.getChildren().setAll((Node) loader.load());

            PanelTripulacionController controller = loader.getController();
            controller.cambiarPanel(panelTripulacion1);



        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
