package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.Ruta;
import co.edu.uniquindio.caribe_airlines.Model.Ticket;
import co.edu.uniquindio.caribe_airlines.Utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

import java.io.IOException;

public class PanelTicketsController {

    // Componenetes
    public Button btnSiguiente;
    public AnchorPane panelTickets;
    public ComboBox<String> combModalidad, combServicio, combOrigen,combDestino;
    public DatePicker fInicio, fRetorno;
    public TextField numPersonas;
    public TableView<Ruta> tablaVuelos;
    public TableColumn<Ruta, String> columOrigen, columDestino, columDuracion, columSalida;

    private ModelFactoryController controller;
    private Ticket ticketCliente;

//----------------------------------------------------------------------------------------------//
    @FXML
    private void initialize(){
        controller = ModelFactoryController.getInstance();
        iniciatializeComb();
        cargarRutas();

        numPersonas.setTextFormatter(new TextFormatter<>( change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")){
                return change;
            }
            return null;
        }));
    }

    public void setTicket(Ticket ticket) {
        this.ticketCliente = ticket;
        initialize();
    }

    private void iniciatializeComb(){
        ObservableList<String> modalidades = FXCollections.observableArrayList();
        modalidades.add ("Ida y Vuelta");
        modalidades.add("Solo Ida");
        combModalidad.setItems(modalidades);

        ObservableList<String> servicios = FXCollections.observableArrayList();
        servicios.add ("Ejecutiva");
        servicios.add ("Economica");
        combServicio.setItems(servicios);

        combOrigen.setValue("CDMX");
        combOrigen.setDisable(true);

        ObservableList<String> destinos = FXCollections.observableArrayList();
        destinos.add("Monterrey");
        destinos.add("Cancun");
        destinos.add("Buenos Aires");
        destinos.add("Los Angeles");
        destinos.add("Bogota");
        destinos.add("Panama");
        combDestino.setItems(destinos);
    }

    private void cargarRutas(){
        tablaVuelos.getItems().clear();
        ObservableList<Ruta> rutas = FXCollections.observableList(controller.getRutas());
        tablaVuelos.setItems(rutas);

        columOrigen.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getOrigen()));
        columDestino.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDestino()));
        columDuracion.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDuracion()));
        columSalida.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getHoraSalida()));

        tablaVuelos.refresh();
    }

    public void nextPanel(ActionEvent actionEvent) {
        String msj = "¿Esta seguro de que todos los datos son correctos?";
        try {
            if (datosCompletos()){
                if (Utils.verificarDesicion(msj)){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets1.fxml"));
                    AnchorPane nuevoPanel = loader.load();
                    PanelTicketsController1 controller1 = loader.getController();

                    controller1.setTicket(recopilarInfo());
                    panelTickets.getChildren().setAll(nuevoPanel);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Datos incompletos");
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Ticket recopilarInfo() {

        if (ticketCliente == null){
            String tipoVuelo = "Internacional";
            if (combDestino.getValue().equals("Monterrey") || combDestino.getValue().equals("Cancun")){
                tipoVuelo = "Nacional";
            }

            String retorno;
            if (fRetorno.isDisabled()){
                retorno = "--------";
            }
            else{
                retorno = fRetorno.getValue().toString();
            }

            ticketCliente = new Ticket(
                    tipoVuelo,
                    combServicio.getValue().toString(),
                    combModalidad.getValue().toString(),
                    combDestino.getValue().toString(),
                    fInicio.getValue().toString(),
                    retorno,
                    null,
                    null,
                    0,
                    Utils.generarID(),
                    Integer.parseInt(numPersonas.getText())
            );
        }
        return ticketCliente;
    }

    @FXML
    private void definirFecha() {
        if (combModalidad.getValue() != null){
            if(combModalidad.getValue().equals("Solo Ida")){
                fRetorno.setValue(null);
                fRetorno.setDisable(true);
            }
            else{
                fRetorno.setDisable(false);
            }
        }
    }

    private boolean datosCompletos(){
        boolean cent = true;
        if (combModalidad.getValue() == null){
            cent = false;
        }
        if (combDestino.getValue() == null){
            cent = false;
        }
        if (combServicio.getValue() == null){
            cent = false;
        }
        if (fInicio.getValue() == null){
            cent = false;
        }
        if (!fRetorno.isDisabled() && fRetorno.getValue() == null){
            cent = false;
        }
        if (numPersonas.getText().isEmpty()){
            cent = false;
        }
        return cent;
    }

}
