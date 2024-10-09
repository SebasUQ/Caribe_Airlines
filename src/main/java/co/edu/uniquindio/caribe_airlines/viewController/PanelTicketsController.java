package co.edu.uniquindio.caribe_airlines.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelTicketsController {

    // Componenetes
    public Button btnSiguiente;
    public AnchorPane panelTickets;
    public ComboBox combModalidad;
    public ComboBox combServicio;
    public ComboBox combOrigen;
    public ComboBox combDestino;
    public DatePicker fInicio;
    public DatePicker fRetorno;
    public TextField numPersonas;
    public TableView tablaVuelos;
    public TableColumn columOrigen;
    public TableColumn columDestino;
    public TableColumn columDuracion;
    public TableColumn columSalida;

    //----------------------------------------------------------------------------------------------//


    public void cambiarPanel (AnchorPane panel){
        this.panelTickets = panel;
    }


    public void nextPanel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets1.fxml"));
            panelTickets.getChildren().setAll((Node) loader.load());

            PanelTicketsController1 controller1 = loader.getController();
            controller1.cambiarPanel(panelTickets);



        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
