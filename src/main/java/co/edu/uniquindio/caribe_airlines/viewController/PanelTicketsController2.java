package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.Ticket;
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
    public Button btnSiguiente, btnAnterior;
    public TextField alturaP1, largoP1, anchoP1, pesoP1, altura2, largo2, ancho2, peso2, pesoM;
    public ComboBox<String> comboEquipaje1, comboEquipaje2;
    public RadioButton opcionSI, opcionNO;
    private Ticket ticketCliente;

//----------------------------------------------------------------------------------------------//

    public void setTicket(Ticket ticketCliente) {
        this.ticketCliente = ticketCliente;
        initialize();
    }

    private void initialize(){

    }

    public void nextPanel(ActionEvent actionEvent) {

    }

    public void previousPanel(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets1.fxml"));
            panelTickets2.getChildren().setAll((Node) loader.load());

            PanelTicketsController1 controller = loader.getController();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
