package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;
import co.edu.uniquindio.caribe_airlines.Model.Tripulante;
import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelTripulacionController {


    public AnchorPane panelTripulacion;
    @FXML
    private TextField txtNom, txtID, txtDirec, txtCorreo, txtEstud, txtRango;
    @FXML
    private DatePicker fNacimiento;
    @FXML
    private TableView<Tripulante> listaTripulantes;
    @FXML
    private TableColumn<Tripulante, String> Nombres, IDs, Correos;
    @FXML
    private Button btnAgregar, btnEliminar, btnActualizar, sgtPagina;

    private CaribeAirlines caribeAirlines;

    @FXML
    public void initialize() {
        caribeAirlines = CaribeAirlines.getInstance();
        loadTripulantes();
    }

    private void clear(){
        txtNom.setText("");
        txtID.setText("");
        txtCorreo.setText("");
        txtDirec.setText("");
        txtEstud.setText("");
        txtRango.setText("");
        fNacimiento.setValue(null);
    }

    private void loadTripulantes() {

        listaTripulantes.getItems().clear();
        ObservableList<Tripulante> lista = FXCollections.observableList(caribeAirlines.getTripulantes().toArrayList());
        listaTripulantes.setItems(lista);

        Nombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        IDs.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        Correos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        listaTripulantes.refresh();

    }

    @FXML
    private void handleAddTripulante() {
        try {
            Tripulante tripulante = new Tripulante(
                    txtID.getText(),
                    txtNom.getText(),
                    txtDirec.getText(),
                    txtCorreo.getText(),
                    fNacimiento.getValue().toString(),
                    txtEstud.getText(),
                    txtRango.getText()
            );
            caribeAirlines.registrarTripulante(tripulante);
            loadTripulantes();
            clear();
        } catch (Exception e) {
            // Handle exception (e.g., show an alert)
        }
    }

    @FXML
    private void handleUpdateTripulante() {
        int selectedItem = listaTripulantes.getSelectionModel().getSelectedIndex();
        if (selectedItem != -1) {
            Tripulante tripulante = listaTripulantes.getItems().get(selectedItem);
            tripulante.setNombre(txtNom.getText());
            tripulante.setDireccion(txtDirec.getText());
            tripulante.setEmail(txtCorreo.getText());
            tripulante.setFechaNacimiento(fNacimiento.getValue().toString());
            tripulante.setEstudios(txtEstud.getText());
            tripulante.setRango(txtRango.getText());
            caribeAirlines.actualizarTripulante(tripulante);
            loadTripulantes();
            clear();
        }
    }

    @FXML
    private void handleDeleteTripulante() {
        int selectedItem = listaTripulantes.getSelectionModel().getSelectedIndex();
        if (selectedItem != -1) {
            Tripulante tripulante = listaTripulantes.getItems().get(selectedItem);
            caribeAirlines.eliminarTripulante(tripulante);
            loadTripulantes();
        }
    }

    public void cambiarPanel (AnchorPane panel){
        this.panelTripulacion = panel;
    }

    public void nextPanel(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTripulacion1.fxml"));
            panelTripulacion.getChildren().setAll((Node) loader.load());

            PanelTripulacionController1 controller1 = loader.getController();
            controller1.cambiarPanel(panelTripulacion);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
