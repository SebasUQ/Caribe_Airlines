package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;
import co.edu.uniquindio.caribe_airlines.Model.Ruta;
import co.edu.uniquindio.caribe_airlines.Model.Ticket;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

import java.io.IOException;

public class PanelTicketsController {

    // Componenetes
    public Button btnSiguiente;
    public AnchorPane panelTickets;
    public ComboBox combModalidad, combServicio, combOrigen,combDestino;
    public DatePicker fInicio, fRetorno;
    public TextField numPersonas;
    public TableView<Ruta> tablaVuelos;
    public TableColumn<Ruta, String> columOrigen, columDestino, columDuracion, columSalida;

    private CaribeAirlines caribeAirlines;

//----------------------------------------------------------------------------------------------//

    public void cambiarPanel (AnchorPane panel){
        this.panelTickets = panel;
    }

    @FXML
    private void initialize(){
        caribeAirlines = CaribeAirlines.getInstance();
        iniciatializeComb();
        cargarRutas();
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
        destinos.add("Monterrey");
        destinos.add("Los Angeles");
        destinos.add("Bogota");
        destinos.add("Panama");
        combDestino.setItems(destinos);
    }

    private void cargarRutas(){
        tablaVuelos.getItems().clear();
        ObservableList<Ruta> rutas = FXCollections.observableList(caribeAirlines.getRutas().toArrayList());
        tablaVuelos.setItems(rutas);

        columOrigen.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getOrigen()));
        columDestino.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDestino()));
        columDuracion.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDuracion()));
        columSalida.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getHoraSalida()));

        tablaVuelos.refresh();
    }

    private boolean verificarDesicion(){
        boolean centinela = false;
        String msj = "¿Esta seguro de que todos los datos son correctos?";

        int result = JOptionPane.showConfirmDialog(null,
                msj,null, JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION) {
            centinela=true;
        }
        return centinela;
    }

    @FXML
    private void definirFecha(ActionEvent actionEvent) {
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


    public void nextPanel(ActionEvent actionEvent) {
        try {
            if (datosCompletos()){
                if (verificarDesicion()){
                    Ticket ticket = recopilarInfo();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets1.fxml"));
                    panelTickets.getChildren().setAll((Node) loader.load());

                    PanelTicketsController1 controller1 = loader.getController();
                    controller1.cambiarPanel(panelTickets);
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

        String tipoVuelo = "Internacional";
        if (combDestino.getValue().equals("Monterrey") || combDestino.getValue().equals("Cancun")){
            tipoVuelo = "Nacional";
        }

        String retorno = fRetorno.getValue().toString();
        if (fRetorno.isDisabled()){
            retorno = "--------";
        }

        Ticket ticket = new Ticket(
                tipoVuelo,
                combServicio.getValue().toString(),
                combModalidad.getValue().toString(),
                fInicio.getValue().toString(),
                retorno,
                null,
                null,
                0,
                null
        );
        return  ticket;
    }

}
