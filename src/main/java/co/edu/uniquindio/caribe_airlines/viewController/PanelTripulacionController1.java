package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
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
    private TableColumn<TripulacionDisplay, String> colAvion, colTripulante, colRango;
    @FXML
    private ListView<Tripulante> listaTripulantesDisponibles;
    @FXML
    private ComboBox<Avion> comboAviones;
    @FXML
    private Button btnAsignar, btnEliminar;

    private ModelFactoryController controller;
    private List<Avion> avionesList;
    private List<Tripulante> tripulantesDisponiblesList;

    @FXML
    public void initialize() {
        controller = ModelFactoryController.getInstance();
        avionesList = controller.getAeronaves();
        tripulantesDisponiblesList = controller.obtenerTripulantesDisponibles();

        tablaTripulacion.getItems().clear();
        colAvion.setCellValueFactory(new PropertyValueFactory<>("avion"));
        colTripulante.setCellValueFactory(new PropertyValueFactory<>("tripulante"));
        colRango.setCellValueFactory(new PropertyValueFactory<>("rango"));

        cargarTripulacion();
        cargarTripulantesDisponibles();
        cargarAviones();
    }

    private void cargarTripulacion() {
        List<TripulacionDisplay> tripulacionList = avionesList.stream()
                .flatMap(avion -> avion.getTripulacion().stream()
                        .map(tripulante -> new TripulacionDisplay(avion.getModelo(),
                                tripulante.getNombre(), tripulante.getRango())))
                .collect(Collectors.toList());

        ObservableList<TripulacionDisplay> data = FXCollections.observableArrayList(tripulacionList);
        tablaTripulacion.setItems(data);
    }

    private void cargarTripulantesDisponibles() {
        listaTripulantesDisponibles.getItems().clear();
        ObservableList<Tripulante> tripulantes = FXCollections.observableArrayList(tripulantesDisponiblesList);
        listaTripulantesDisponibles.setItems(tripulantes);
        listaTripulantesDisponibles.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Tripulante item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null){
                    setText(null);
                }
                else{
                    setText("Nombre:   "+item.getNombre()+"   ID: "+item.getId()+"   Rango: "+item.getRango() );
                }
            }
        });
        listaTripulantesDisponibles.refresh();
    }

    private void cargarAviones() {
        ObservableList<Avion> aviones = FXCollections.observableArrayList(avionesList);
        comboAviones.setItems(aviones);
        comboAviones.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem (Avion item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null){
                    setText(null);
                }
                else {
                    setText(item.getModelo());
                }
            }
        });
    }

    public void asignarTripulante(ActionEvent actionEvent) {
        Tripulante selectedTripulante = listaTripulantesDisponibles.getSelectionModel().getSelectedItem();
        Avion selectedAvion = comboAviones.getSelectionModel().getSelectedItem();
        if (selectedTripulante != null && selectedAvion != null) {
            selectedAvion.getTripulacion().add(selectedTripulante);
            tripulantesDisponiblesList.remove(selectedTripulante);
            controller.asignarTripulacionAAvion(selectedAvion, selectedAvion.getTripulacion());
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
            Tripulante tripulante = controller.getTripulantes().stream()
                    .filter(t -> t.getNombre().equals(selectedTripulacion.getTripulante()))
                    .findFirst().orElse(null);
            if (avion != null && tripulante != null) {
                controller.removerTripulacionDeAvion(avion, tripulante);
                tripulantesDisponiblesList.add(tripulante);
                cargarTripulacion();
                cargarTripulantesDisponibles();
            }
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

    private static class TripulacionDisplay {
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
