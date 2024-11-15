package co.edu.uniquindio.caribe_airlines.viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import co.edu.uniquindio.caribe_airlines.Model.*;

import java.io.IOException;

public class PanelTripulacionController1 {
    @FXML public Button btnVolver;
    @FXML public AnchorPane panelTripulacion1;
    @FXML private TableView<TripulacionDisplay> tablaTripulacion;
    @FXML private TableColumn<TripulacionDisplay, String> colAvion;
    @FXML private TableColumn<TripulacionDisplay, String> colTripulante;
    @FXML private TableColumn<TripulacionDisplay, String> colRango;
    @FXML private ListView<Tripulante> listaTripulantesDisponibles;
    @FXML private ComboBox<Avion> comboAviones;
    @FXML private Button btnAsignar;
    @FXML private Button btnEliminar;
    @FXML private Button btnVerTripulacion;

    private CaribeAirlines caribeAirlines;
    private ObservableList<Tripulante> tripulantesDisponibles;
    private ObservableList<Avion> aviones;
    private ObservableList<TripulacionDisplay> tripulacionDisplayList;

    @FXML
    public void initialize() {
        caribeAirlines = CaribeAirlines.getInstance();
        tripulacionDisplayList = FXCollections.observableArrayList();

        configurarColumnas();
        configurarListaTripulantes();
        configurarComboAviones();

        actualizarDatos();
    }

    private void configurarColumnas() {
        colAvion.setCellValueFactory(new PropertyValueFactory<>("avion"));
        colTripulante.setCellValueFactory(new PropertyValueFactory<>("tripulante"));
        colRango.setCellValueFactory(new PropertyValueFactory<>("rango"));
        tablaTripulacion.setItems(tripulacionDisplayList);
    }

    private void configurarListaTripulantes() {
        listaTripulantesDisponibles.setCellFactory(lv -> new ListCell<Tripulante>() {
            @Override
            protected void updateItem(Tripulante tripulante, boolean empty) {
                super.updateItem(tripulante, empty);
                if (empty || tripulante == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - %s (%s)",
                            tripulante.getNombre(),
                            tripulante.getRango(),
                            tripulante.getId()));
                }
            }
        });
    }

    private void configurarComboAviones() {
        comboAviones.setCellFactory(lv -> new ListCell<Avion>() {
            @Override
            protected void updateItem(Avion avion, boolean empty) {
                super.updateItem(avion, empty);
                if (empty || avion == null) {
                    setText(null);
                } else {
                    setText(avion.getModelo());
                }
            }
        });

        comboAviones.setButtonCell(new ListCell<Avion>() {
            @Override
            protected void updateItem(Avion avion, boolean empty) {
                super.updateItem(avion, empty);
                if (empty || avion == null) {
                    setText(null);
                } else {
                    setText(avion.getModelo());
                }
            }
        });

        comboAviones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarTablaTripulacion();
                actualizarEstadoBotones();
            }
        });
    }

    private int getTripulacionRequerida(Avion avion) {
        switch (avion.getModelo()) {
            case "Airbus A320":
                return 5; // 1 comandante + 1 copiloto + 3 auxiliares
            case "Airbus A330":
            case "Boeing 787":
                return 9; // 1 comandante + 1 copiloto + 7 auxiliares
            default:
                return 0;
        }
    }

    private void actualizarEstadoBotones() {
        Avion avionSeleccionado = comboAviones.getSelectionModel().getSelectedItem();
        btnAsignar.setDisable(avionSeleccionado == null ||
                !caribeAirlines.necesitaMasTripulacion(avionSeleccionado));
    }

    private void actualizarTablaTripulacion() {
        tripulacionDisplayList.clear();
        Avion avionSeleccionado = comboAviones.getSelectionModel().getSelectedItem();

        if (avionSeleccionado != null) {
            for (Tripulante tripulante : avionSeleccionado.getTripulacion()) {
                tripulacionDisplayList.add(new TripulacionDisplay(
                        avionSeleccionado.getModelo(),
                        tripulante.getNombre(),
                        tripulante.getRango()
                ));
            }

            // Mostrar información de la tripulación actual
            String infoTripulacion = String.format("Tripulación actual: %d/%d",
                    avionSeleccionado.getTripulacion().size(),
                    getTripulacionRequerida(avionSeleccionado));
            System.out.println(infoTripulacion); // Para debugging
        }
    }

    private void actualizarDatos() {
        tripulantesDisponibles = FXCollections.observableArrayList(
                caribeAirlines.obtenerTripulantesDisponibles());
        listaTripulantesDisponibles.setItems(tripulantesDisponibles);

        aviones = FXCollections.observableArrayList(
                caribeAirlines.getAeronaves().toArrayList());
        comboAviones.setItems(aviones);

        if (!aviones.isEmpty()) {
            comboAviones.getSelectionModel().selectFirst();
            actualizarTablaTripulacion();
            actualizarEstadoBotones();
        }
    }

    @FXML
    public void handleAsignarTripulante() {
        Tripulante tripulanteSeleccionado = listaTripulantesDisponibles.getSelectionModel().getSelectedItem();
        Avion avionSeleccionado = comboAviones.getSelectionModel().getSelectedItem();

        if (tripulanteSeleccionado == null || avionSeleccionado == null) {
            mostrarAlerta("Por favor, seleccione un tripulante y un avión.");
            return;
        }

        try {
            if (caribeAirlines.necesitaTripulante(avionSeleccionado, tripulanteSeleccionado.getRango())) {
                caribeAirlines.asignarTripulacionAAvion(avionSeleccionado, tripulanteSeleccionado);
                actualizarDatos();
                actualizarTablaTripulacion();
                mostrarInformacion("Tripulante asignado exitosamente.");
            } else {
                mostrarAlerta("No se puede asignar más tripulantes de este rango al avión seleccionado.");
            }
        } catch (Exception e) {
            mostrarError("Error al asignar tripulante: " + e.getMessage());
        }
    }

    @FXML
    public void handleEliminarTripulante() {
        TripulacionDisplay tripulacionSeleccionada = tablaTripulacion.getSelectionModel().getSelectedItem();
        if (tripulacionSeleccionada == null) {
            mostrarAlerta("Por favor, seleccione un tripulante para eliminar.");
            return;
        }

        Avion avionSeleccionado = comboAviones.getSelectionModel().getSelectedItem();
        Tripulante tripulanteAEliminar = avionSeleccionado.getTripulacion().stream()
                .filter(t -> t.getNombre().equals(tripulacionSeleccionada.getTripulante()))
                .findFirst()
                .orElse(null);

        if (tripulanteAEliminar != null) {
            caribeAirlines.removerTripulacionDeAvion(avionSeleccionado, tripulanteAEliminar);
            actualizarDatos();
            actualizarTablaTripulacion();
            mostrarInformacion("Tripulante removido exitosamente.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        mostrarDialogo(Alert.AlertType.WARNING, "Advertencia", mensaje);
    }

    private void mostrarError(String mensaje) {
        mostrarDialogo(Alert.AlertType.ERROR, "Error", mensaje);
    }

    private void mostrarInformacion(String mensaje) {
        mostrarDialogo(Alert.AlertType.INFORMATION, "Información", mensaje);
    }

    private void mostrarDialogo(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cambiarPanel(AnchorPane panel) {
        this.panelTripulacion1 = panel;
    }

    public void previousPanel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTripulacion.fxml"));
            panelTripulacion1.getChildren().setAll((Node) loader.load());

            PanelTripulacionController controller = loader.getController();
            controller.cambiarPanel(panelTripulacion1);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cambiar de panel: " + e.getMessage());
        }
    }
}