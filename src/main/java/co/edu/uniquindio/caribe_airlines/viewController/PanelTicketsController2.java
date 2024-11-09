package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.Ticket;
import co.edu.uniquindio.caribe_airlines.Utils.Utils;
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
    private ModelFactoryController controller;

//----------------------------------------------------------------------------------------------//

    public void setTicket(Ticket ticketCliente) {
        this.ticketCliente = ticketCliente;
        initialize();
    }

    private void initialize(){
        controller = ModelFactoryController.getInstance();
    }

    public void nextPanel(ActionEvent actionEvent) {

    }

    public void cancelarReserva(ActionEvent actionEvent) {

        String msj = "¿Esta seguro de que desea cancelar la reserva?";

        if (Utils.verificarDesicion(msj)){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets.fxml"));
                AnchorPane primerPanel = loader.load();

                PanelTicketsController controller = loader.getController();
                controller.setTicket(null);
                panelTickets2.getChildren().setAll(primerPanel);

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
