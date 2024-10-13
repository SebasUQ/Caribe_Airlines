package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;
import co.edu.uniquindio.caribe_airlines.Model.Ruta;
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

        ObservableList<String> modalidades = FXCollections.observableArrayList();
        modalidades.add ("Ida y Vuelta");
        modalidades.add("Solo Ida");
        combModalidad.setItems(modalidades);

        ObservableList<String> servicios = FXCollections.observableArrayList();
        servicios.add ("Ejecutiva");
        servicios.add ("Economica");
        combServicio.setItems(servicios);

        cargarRutas();
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


    public void nextPanel(ActionEvent actionEvent) {
        try {
            if (verificarDesicion()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets1.fxml"));
                panelTickets.getChildren().setAll((Node) loader.load());

                PanelTicketsController1 controller1 = loader.getController();
                controller1.cambiarPanel(panelTickets);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
