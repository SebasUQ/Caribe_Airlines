package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;
import co.edu.uniquindio.caribe_airlines.Model.Tripulante;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class PanelTripulacionController {

    @FXML
    private TextField txtNom, txtID, txtDirec, txtCorreo, txtEstud, txtRango;
    @FXML
    private DatePicker fNacimiento;
    @FXML
    private TreeTableView<Tripulante> ListaTripulantes;
    @FXML
    private TreeTableColumn<Tripulante, String> Nombres, IDs, Correos;
    @FXML
    private Button btnAgregar, btnEliminar, btnActualizar;

    private CaribeAirlines caribeAirlines;

    @FXML
    public void initialize() {
        caribeAirlines = CaribeAirlines.getInstance();
        initializeTable();
        loadTripulantes();
    }


        private void initializeTable() {
            Nombres.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));
            IDs.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            Correos.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        }


    private void loadTripulantes() {
        TreeItem<Tripulante> root = new TreeItem<>(new Tripulante("Root", "", "", "", "", "", ""));
        for (Tripulante tripulante : caribeAirlines.getTripulantes().toArrayList()) {
            root.getChildren().add(new TreeItem<>(tripulante));
        }
        ListaTripulantes.setRoot(root);
        ListaTripulantes.setShowRoot(false);
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
        } catch (Exception e) {
            // Handle exception (e.g., show an alert)
        }
    }

    @FXML
    private void handleUpdateTripulante() {
        TreeItem<Tripulante> selectedItem = ListaTripulantes.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Tripulante tripulante = selectedItem.getValue();
            tripulante.setNombre(txtNom.getText());
            tripulante.setDireccion(txtDirec.getText());
            tripulante.setEmail(txtCorreo.getText());
            tripulante.setFechaNacimiento(fNacimiento.getValue().toString());
            tripulante.setEstudios(txtEstud.getText());
            tripulante.setRango(txtRango.getText());
            caribeAirlines.actualizarTripulante(tripulante);
            loadTripulantes();
        }
    }

    @FXML
    private void handleDeleteTripulante() {
        TreeItem<Tripulante> selectedItem = ListaTripulantes.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            caribeAirlines.eliminarTripulante(selectedItem.getValue().getId());
            loadTripulantes();
        }
    }
}
