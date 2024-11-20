package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.Cliente;
import co.edu.uniquindio.caribe_airlines.Model.Ticket;
import co.edu.uniquindio.caribe_airlines.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.text.DecimalFormat;

public class PanelTicketsViewController3 {
    public AnchorPane panelTicket3;
    public Button btnSiguiente, btnCancelar;
    public TextField txtID, txtNom, txtApe, txtCorreo, txtFecha, txtNumTarjeta;
    public Label lblValorF;
    private Cliente cliente;
    private Ticket ticketCliente;
    private ModelFactoryController controller;

    public void setObjetos(Ticket ticket, Cliente c){
        this.ticketCliente = ticket;
        this.cliente = c;
        initialize();
    }

    public void initialize(){
        this.controller = ModelFactoryController.getInstance();
        if (cliente != null && ticketCliente != null){
            txtID.setText(cliente.getIdentificacion());
            txtNom.setText(cliente.getNombre());
            txtApe.setText(cliente.getApellido());
            txtCorreo.setText(cliente.getCorreoElectronico());
            txtFecha.setText(cliente.getFechaNacimiento());

            DecimalFormat df = new DecimalFormat("#.00");
            lblValorF.setText("$ "+df.format(ticketCliente.getValorPagado())+" USD");
        }

        txtNumTarjeta.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")){
                return change;
            }
            return null;
        }));
    }

    public void nextPanel(ActionEvent actionEvent) {
        if (!txtNumTarjeta.getText().isEmpty()){
            if (Utils.esNumeroValido(txtNumTarjeta.getText())){

            }
        }else{
            JOptionPane.showMessageDialog(null, "Por favor ingrese un numero de tarjeta");
        }
    }

    public void cancelarReserva(ActionEvent actionEvent) {
        String msj = "¿Esta seguro de que desea cancelar la reserva?";

        if (Utils.verificarDesicion(msj)){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets.fxml"));
                AnchorPane primerPanel = loader.load();

                PanelTicketsViewController controller = loader.getController();
                controller.setObjetos(null, cliente);
                panelTicket3.getChildren().setAll(primerPanel);

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
