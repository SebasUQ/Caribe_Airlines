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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.text.DecimalFormat;

public class PanelTicketsController1 {

    public Button btnSiguiente, btnCancelar;
    public AnchorPane panelTickets1;
    public TableView<Vuelo> TablaVuelos;
    public TableColumn<Vuelo,String> columAvion, columAsientos, columTipo;
    public ImageView imagenAvion;
    public Label txtPiloto, txtFVuelo, txtSalida, txtDuracion, txtRuta, txtCostoP, txtSubTotal;

    private Ticket ticketCliente;
    private CaribeAirlines caribeAirlines;

//----------------------------------------------------------------------------------------------//

    private void initialize (){
        caribeAirlines = CaribeAirlines.getInstance();
        vuelosDisponibles();
        calcularCostos();
    }

    public void setTicket(Ticket ticket) {
        this.ticketCliente = ticket;
        initialize();
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

    public void nextPanel() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets2.fxml"));
            AnchorPane nuevoPanel = loader.load();

            PanelTicketsController2 controller2 = loader.getController();
            controller2.setTicket(ticketCliente);
            panelTickets1.getChildren().setAll(nuevoPanel);

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void cancelarReserva() {

        if (verificarDesicion()){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets.fxml"));
                AnchorPane primerPanel = loader.load();

                PanelTicketsController controller = loader.getController();
                controller.setTicket(null);
                panelTickets1.getChildren().setAll(primerPanel);

            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void obtenerInfo() {
        Vuelo vuelo = TablaVuelos.getSelectionModel().getSelectedItem();
        if (vuelo != null){
            txtDuracion.setText(vuelo.getRuta().getDuracion());
            txtFVuelo.setText(vuelo.getFechaVuelo());
            txtRuta.setText(vuelo.getRuta().getOrigen()+" - "+vuelo.getRuta().getDestino());
            txtSalida.setText(vuelo.getRuta().getHoraSalida());

            definirImagen(vuelo);
        }

    }

    private void definirImagen(Vuelo vuelo) {
        Image image;
        if (vuelo.getAvion().getModelo().equals("Airbus A320")){
            image = new Image("file:src/main/resources/co/edu/uniquindio/caribe_airlines/Imagenes/airbusa320.jpeg");
            imagenAvion.setImage(image);
        }
        if (vuelo.getAvion().getModelo().equals("Airbus A330")){
            image = new Image("file:src/main/resources/co/edu/uniquindio/caribe_airlines/Imagenes/airbus330.jpg");
            imagenAvion.setImage(image);
        }
        if (vuelo.getAvion().getModelo().equals("Boeing 787")){
            image = new Image("file:src/main/resources/co/edu/uniquindio/caribe_airlines/Imagenes/Boeign787.jpg");
            imagenAvion.setImage(image);
        }
    }

    private boolean verificarDesicion(){
        boolean centinela = false;
        String msj = "¿Esta seguro de que desea cancelar la reserva?";

        int result = JOptionPane.showConfirmDialog(null,
                msj,null, JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION) {
            centinela=true;
        }
        return centinela;
    }

    private void calcularCostos() {
        double costoP = 0, subTotal;
        if (ticketCliente.getDestino().equals("Monterrey") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 37.27;
        }
        if (ticketCliente.getDestino().equals("Monterrey") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 79.50;
        }
        if (ticketCliente.getDestino().equals("Cancun") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 25.84;
        }
        if (ticketCliente.getDestino().equals("Cancun") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 58.14;
        }
        if (ticketCliente.getDestino().equals("Buenos Aires") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 270.81;
        }
        if (ticketCliente.getDestino().equals("Buenos Aires") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 591.30;
        }
        if (ticketCliente.getDestino().equals("Los Angeles") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 81.49;
        }
        if (ticketCliente.getDestino().equals("Los Angeles") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 150.06;
        }
        if (ticketCliente.getDestino().equals("Bogota") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 163.48;
        }
        if (ticketCliente.getDestino().equals("Bogota") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 320.96;
        }
        if (ticketCliente.getDestino().equals("Panama") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 168.05;
        }
        if (ticketCliente.getDestino().equals("Panama") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 351;
        }
        subTotal = costoP*ticketCliente.getTotalPersonas();

        if (ticketCliente.getTipoServicio().equals("Nacional")){
            subTotal= subTotal + (subTotal*0.8);
        }
        else {
            subTotal = subTotal + (subTotal*0.97);
        }
        DecimalFormat df = new DecimalFormat("#.00");

        txtCostoP.setText("$"+df.format(costoP)+" USD");
        txtSubTotal.setText("$"+df.format(subTotal)+" USD");
    }
}
