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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.uniquindio.caribe_airlines.Model.Avion;
import co.edu.uniquindio.caribe_airlines.Model.CaribeAirlines;
import co.edu.uniquindio.caribe_airlines.Model.Tripulante;

public class PanelTripulacionController1 {
    public Button btnVolver;
    public AnchorPane panelTripulacion1;

    @FXML
    private TableView<TripulacionDisplay> tablaTripulacion;
    @FXML
    private TableColumn<TripulacionDisplay, String> colAvion;
    @FXML
    private TableColumn<TripulacionDisplay, String> colTripulante;
    @FXML
    private TableColumn<TripulacionDisplay, String> colRango;
    @FXML
    private ListView<Tripulante> listaTripulantesDisponibles;
    @FXML
    private ComboBox<Avion> comboAviones;
    @FXML
    private Button btnAsignar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVerTripulacion;

    private CaribeAirlines caribeAirlines;
    private List<Avion> avionesList;
    private List<Tripulante> tripulantesDisponiblesList;

    @FXML
    public void initialize() {
        caribeAirlines = CaribeAirlines.getInstance();
        avionesList = caribeAirlines.getAeronaves().toArrayList();
        tripulantesDisponiblesList = caribeAirlines.obtenerTripulantesDisponibles();

        colAvion.setCellValueFactory(new PropertyValueFactory<>("avion"));
        colTripulante.setCellValueFactory(new PropertyValueFactory<>("tripulante"));
        colRango.setCellValueFactory(new PropertyValueFactory<>("rango"));

        cargarTripulacion();
        cargarTripulantesDisponibles();
        cargarAviones();
    }

    private void cargarTripulacion() {
        Avion selectedAvion = comboAviones.getSelectionModel().getSelectedItem();
        if (selectedAvion != null) {
            List<TripulacionDisplay> tripulacionList = selectedAvion.getTripulacion().stream()
                    .map(tripulante -> new TripulacionDisplay(selectedAvion.getModelo(), tripulante.getNombre(), tripulante.getRango()))
                    .collect(Collectors.toList());

            ObservableList<TripulacionDisplay> data = FXCollections.observableArrayList(tripulacionList);
            tablaTripulacion.setItems(data);
            tablaTripulacion.refresh();
        } else {
            tablaTripulacion.getItems().clear();
        }
    }

    private void cargarTripulantesDisponibles() {
        ObservableList<Tripulante> tripulantes = FXCollections.observableArrayList(tripulantesDisponiblesList);
        listaTripulantesDisponibles.setItems(tripulantes);
    }



    private void cargarAviones() {
        ObservableList<Avion> aviones = FXCollections.observableArrayList(avionesList);
        comboAviones.setItems(aviones);
        if (!aviones.isEmpty()) {
            comboAviones.getSelectionModel().selectFirst();
            cargarTripulacion(); // Cargar la tripulación del primer avión
        }
    }

    public void asignarTripulante(ActionEvent actionEvent) {
        Tripulante selectedTripulante = listaTripulantesDisponibles.getSelectionModel().getSelectedItem();
        Avion selectedAvion = comboAviones.getSelectionModel().getSelectedItem();
        if (selectedTripulante != null && selectedAvion != null) {
            caribeAirlines.asignarTripulacionAAvion(selectedAvion, selectedTripulante);
            tripulantesDisponiblesList.remove(selectedTripulante);
            cargarTripulacion();
            cargarTripulantesDisponibles();
        }
    }

    public void eliminarTripulante(ActionEvent actionEvent) {
        TripulacionDisplay selectedTripulacion = tablaTripulacion.getSelectionModel().getSelectedItem();
        if (selectedTripulacion != null) {
            Avion avion = avionesList.stream()
                    .filter(a -> a.getModelo().equals(selectedTripulacion.getAvion()))
                    .findFirst().orElse(null);
            Tripulante tripulante = caribeAirlines.getTripulantes().toArrayList().stream()
                    .filter(t -> t.getNombre().equals(selectedTripulacion.getTripulante()))
                    .findFirst().orElse(null);
            if (avion != null && tripulante != null) {
                caribeAirlines.removerTripulacionDeAvion(avion, tripulante);
                tripulantesDisponiblesList.add(tripulante);
                cargarTripulacion();
                cargarTripulantesDisponibles();
            }
        }
    }

    public void verTripulacion(ActionEvent actionEvent) {
        Avion selectedAvion = comboAviones.getSelectionModel().getSelectedItem();
        if (selectedAvion != null) {
            List<TripulacionDisplay> tripulacionList = selectedAvion.getTripulacion().stream()
                    .map(tripulante -> new TripulacionDisplay(selectedAvion.getModelo(), tripulante.getNombre(), tripulante.getRango()))
                    .collect(Collectors.toList());

            ObservableList<TripulacionDisplay> data = FXCollections.observableArrayList(tripulacionList);
            tablaTripulacion.setItems(data);
            tablaTripulacion.refresh();
        }
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
        }
    }

    public static class TripulacionDisplay {
        private final String avion;
        private final String tripulante;
        private final String rango;

        public TripulacionDisplay(String avion, String tripulante, String rango) {
            this.avion = avion;
            this.tripulante = tripulante;
            this.rango = rango;
        }

        public String getAvion() {
            return avion;
        }

        public String getTripulante() {
            return tripulante;
        }

        public String getRango() {
            return rango;
        }
    }
}