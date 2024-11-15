package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;
import co.edu.uniquindio.caribe_airlines.Model.Tripulante;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.time.LocalDate;

public class PanelTripulacionController {
    @FXML private AnchorPane panelTripulacion;
    @FXML private TextField txtNom, txtID, txtDirec, txtCorreo, txtEstud;
    @FXML private ComboBox<String> comboRango;
    @FXML private DatePicker fNacimiento;
    @FXML private TableView<Tripulante> listaTripulantes;
    @FXML private TableColumn<Tripulante, String> Nombres, IDs, Correos;
    @FXML private Button btnAgregar, btnEliminar, sgtPagina;

    private CaribeAirlines caribeAirlines;
    private final String[] RANGOS_DISPONIBLES = {
            "Comandante",
            "Copiloto",
            "Auxiliar de Vuelo"
    };

    @FXML
    public void initialize() {
        caribeAirlines = CaribeAirlines.getInstance();

        // Inicializar ComboBox de rangos
        comboRango.setItems(FXCollections.observableArrayList(RANGOS_DISPONIBLES));

        // Configurar columnas de la tabla
        Nombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        IDs.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        Correos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        loadTripulantes();
    }

    private void clear() {
        txtNom.clear();
        txtID.clear();
        txtCorreo.clear();
        txtDirec.clear();
        txtEstud.clear();
        comboRango.getSelectionModel().clearSelection();
        fNacimiento.setValue(null);
    }

    private void loadTripulantes() {
        listaTripulantes.getItems().clear();
        ObservableList<Tripulante> lista = FXCollections.observableList(caribeAirlines.getTripulantes().toArrayList());
        listaTripulantes.setItems(lista);
    }

    @FXML
    private void handleAddTripulante() {
        if (validarCampos()) {
            try {
                Tripulante tripulante = new Tripulante(
                        txtID.getText(),
                        txtNom.getText(),
                        txtDirec.getText(),
                        txtCorreo.getText(),
                        fNacimiento.getValue().toString(),
                        txtEstud.getText(),
                        comboRango.getValue()
                );
                caribeAirlines.registrarTripulante(tripulante);
                loadTripulantes();
                clear();
                mostrarMensaje("Éxito", "Tripulante agregado correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarMensaje("Error", "Error al agregar tripulante: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validarCampos() {
        if (txtNom.getText().isEmpty() || txtID.getText().isEmpty() ||
                txtDirec.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                txtEstud.getText().isEmpty() || comboRango.getValue() == null ||
                fNacimiento.getValue() == null) {

            mostrarMensaje("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    @FXML
    private void handleDeleteTripulante() {
        Tripulante selectedTripulante = listaTripulantes.getSelectionModel().getSelectedItem();
        if (selectedTripulante != null) {
            caribeAirlines.eliminarTripulante(selectedTripulante.getId());
            loadTripulantes();
            mostrarMensaje("Éxito", "Tripulante eliminado correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Error", "Por favor seleccione un tripulante para eliminar", Alert.AlertType.WARNING);
        }
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cambiarPanel(AnchorPane panel) {
        this.panelTripulacion = panel;
    }

    public void nextPanel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTripulacion1.fxml"));
            panelTripulacion.getChildren().setAll((Node) loader.load());

            PanelTripulacionController1 controller1 = loader.getController();
            controller1.cambiarPanel(panelTripulacion);
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cambiar de panel: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}