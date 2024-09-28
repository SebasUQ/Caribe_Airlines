package co.edu.uniquindio.caribe_airlines.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelTicketsController1 {

    public Button btnSiguiente;
    public Button btnAnterior;
    public AnchorPane panelTickets1;

    public void cambiarPanel(AnchorPane panel){
        this.panelTickets1 = panel;
    }

    public void nextPanel(ActionEvent actionEvent) {


    }

    public void previousPanel(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets.fxml"));
            panelTickets1.getChildren().setAll((Node) loader.load());

            PanelTicketsController controller1 = loader.getController();
            controller1.cambiarPanel(panelTickets1);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
