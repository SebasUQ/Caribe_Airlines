package co.edu.uniquindio.caribe_airlines.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelTicketsController2 {

    public AnchorPane panelTickets2;
    public Button btnSiguiente;
    public Button btnAnterior;
    public TextField alturaP1;
    public TextField largoP1;
    public TextField anchoP1;
    public TextField pesoP1;
    public TextField altura2;
    public TextField largo2;
    public TextField ancho2;
    public TextField peso2;
    public ComboBox comboEquipaje1;
    public ComboBox comboEquipaje2;
    public RadioButton opcionSI;
    public RadioButton opcionNO;
    public TextField pesoM;


    public void cambiarPanel(AnchorPane panel){
        this.panelTickets2 = panel;
    }

    public void nextPanel(ActionEvent actionEvent) {

    }

    public void previousPanel(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets1.fxml"));
            panelTickets2.getChildren().setAll((Node) loader.load());

            PanelTicketsController1 controller = loader.getController();
            controller.cambiarPanel(panelTickets2);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
