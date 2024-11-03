package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;
import co.edu.uniquindio.caribe_airlines.Model.Ticket;
import co.edu.uniquindio.caribe_airlines.Model.Vuelo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelTicketsController1 {

    public Button btnSiguiente, btnAnterior;
    public AnchorPane panelTickets1;
    public TableView<Vuelo> TablaVuelos;
    public ImageView imagenAvion;
    public TableColumn<Vuelo,String> columAvion, columAsientos, columTipo;

    private  Ticket ticketCliente;
    private CaribeAirlines caribeAirlines;

    private void initialize (){
        caribeAirlines = CaribeAirlines.getInstance();
        vuelosDisponibles();
    }

    private void vuelosDisponibles() {
        TablaVuelos.getItems().clear();
        ObservableList<Vuelo> vuelos = FXCollections.observableList(caribeAirlines.obtenerVuelos(ticketCliente));
        TablaVuelos.setItems(vuelos);

        columAsientos.setCellValueFactory(data -> new SimpleStringProperty(""+data.getValue().getAvion().getCapacidadPasajeros()));
        columAvion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAvion().getModelo()));
        columTipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoVuelo()));

        TablaVuelos.refresh();
    }


    public void cambiarPanel(AnchorPane panel){
        this.panelTickets1 = panel;
    }

    public void nextPanel(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets2.fxml"));
            panelTickets1.getChildren().setAll((Node) loader.load());

            PanelTicketsController2 controller2 = loader.getController();
            controller2.cambiarPanel(panelTickets1);

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void previousPanel(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets.fxml"));
            panelTickets1.getChildren().setAll((Node) loader.load());

            PanelTicketsController controller = loader.getController();
            controller.cambiarPanel(panelTickets1);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTicket(Ticket ticket) {
        this.ticketCliente = ticket;
        initialize();
    }
}
