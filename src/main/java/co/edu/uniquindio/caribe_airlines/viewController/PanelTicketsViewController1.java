package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.Cliente;
import co.edu.uniquindio.caribe_airlines.Model.Ticket;
import co.edu.uniquindio.caribe_airlines.Model.Vuelo;
import co.edu.uniquindio.caribe_airlines.Utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.text.DecimalFormat;

public class PanelTicketsViewController1 {

    public Button btnSiguiente, btnCancelar;
    public AnchorPane panelTickets1;
    public TableView<Vuelo> TablaVuelos;
    public TableColumn<Vuelo,String> columAvion, columAsientos, columTipo;
    public ImageView imagenAvion;
    public Label txtPiloto, txtFVuelo, txtSalida, txtDuracion, txtRuta, txtCostoP, txtSubTotal;

    private Ticket ticketCliente;
    private Cliente cliente;
    private ModelFactoryController controller;

//----------------------------------------------------------------------------------------------------//

    public void setObjetos(Ticket ticket, Cliente c) {
        this.ticketCliente = ticket;
        this.cliente = c;
        initialize();
    }

    private void initialize (){
        if (ticketCliente != null){
            controller = ModelFactoryController.getInstance();
            vuelosDisponibles();
            calcularCostos();
        }
    }

    private void vuelosDisponibles() {
        TablaVuelos.getItems().clear();
        ObservableList<Vuelo> vuelos = FXCollections.observableList(controller.obtenerVuelos(ticketCliente));
        TablaVuelos.setItems(vuelos);

        columAsientos.setCellValueFactory(data -> new SimpleStringProperty(""+data.getValue().getAvion().getCapacidadPasajeros()));
        columAvion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAvion().getModelo()));
        columTipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoVuelo()));

        TablaVuelos.refresh();
    }

    public void nextPanel() {

        try {
            if (TablaVuelos.getSelectionModel().getSelectedItem() != null){
                ticketCliente.setVuelo(TablaVuelos.getSelectionModel().getSelectedItem());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets2.fxml"));
                AnchorPane nuevoPanel = loader.load();

                PanelTicketsViewController2 controller2 = loader.getController();
                controller2.setObjetos(ticketCliente, cliente);
                panelTickets1.getChildren().setAll(nuevoPanel);
            }
            else{
                JOptionPane.showMessageDialog(null,"Por favor seleccione un vuelo primero");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void cancelarReserva() {
        String msj = "¿Esta seguro de que desea cancelar la reserva?";

        if (Utils.verificarDesicion(msj)){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets.fxml"));
                AnchorPane primerPanel = loader.load();

                PanelTicketsViewController controller = loader.getController();
                controller.setObjetos(null, cliente);
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

    private void calcularCostos() {
        double costoP, subTotal;
        if (ticketCliente.getTipoServicio().equals("Economica")){
            costoP = Utils.calcularCostosEco(ticketCliente);
        }
        else{
            costoP = Utils.calcularCostosEje(ticketCliente);
        }

        subTotal = costoP*ticketCliente.getTotalPersonas();

        if (ticketCliente.getTipoVuelo().equals("Nacional")){
            subTotal = subTotal + (subTotal*0.008);
        }
        else {
            subTotal = subTotal + (subTotal*0.0097);
        }
        ticketCliente.setValorPagado(subTotal);
        DecimalFormat df = new DecimalFormat("#.00");

        txtCostoP.setText("$ "+df.format(costoP)+" USD");
        txtSubTotal.setText("$ "+df.format(subTotal)+" USD");
    }
}
