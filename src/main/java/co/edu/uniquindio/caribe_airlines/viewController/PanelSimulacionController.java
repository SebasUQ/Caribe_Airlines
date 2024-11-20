package co.edu.uniquindio.caribe_airlines.viewController;


import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.CarroEmbarque;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;



public class PanelSimulacionController {
    private ModelFactoryController modelFactory;


    @FXML private TableView<CarroEmbarque> tablaBicola;
    @FXML private TableView<CarroEmbarque> tablaEspera;
    @FXML private TableColumn<CarroEmbarque, String> colIdBicola;
    @FXML private TableColumn<CarroEmbarque, String> colDestinoBicola;
    @FXML private TableColumn<CarroEmbarque, Double> colCargaBicola;
    @FXML private TableColumn<CarroEmbarque, String> colIdEspera;
    @FXML private TableColumn<CarroEmbarque, String> colDestinoEspera;
    @FXML private TableColumn<CarroEmbarque, Double> colCargaEspera;

    private final ObservableList<CarroEmbarque> carrosBicola = FXCollections.observableArrayList();
    private final ObservableList<CarroEmbarque> carrosEspera = FXCollections.observableArrayList();

    public PanelSimulacionController() {
        this.modelFactory = ModelFactoryController.getInstance();
    }

    @FXML
    public void initialize() {
        configurarTablas();
         modelFactory = ModelFactoryController.getInstance();
    }

    private void configurarTablas() {
        colIdBicola.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId()));
        colDestinoBicola.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDestinoAvion()));
        colCargaBicola.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCargaActual()).asObject());

        colIdEspera.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId()));
        colDestinoEspera.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDestinoAvion()));
        colCargaEspera.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCargaActual()).asObject());

        tablaBicola.setItems(carrosBicola);
        tablaEspera.setItems(carrosEspera);

        configurarFormatoCarga(colCargaBicola);
        configurarFormatoCarga(colCargaEspera);
    }

    private void configurarFormatoCarga(TableColumn<CarroEmbarque, Double> columna) {
        columna.setCellFactory(column -> new TableCell<CarroEmbarque, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });
    }

    @FXML
    private void añadirCarro() {
        TextInputDialog dialogId = new TextInputDialog();
        dialogId.setTitle("Nuevo Carro");
        dialogId.setHeaderText("Ingrese el ID del Carro");
        dialogId.setContentText("ID:");

        dialogId.showAndWait().ifPresent(id -> {
            TextInputDialog dialogDestino = new TextInputDialog();
            dialogDestino.setTitle("Destino");
            dialogDestino.setHeaderText("Ingrese el Destino del Avión");
            dialogDestino.setContentText("Destino:");

            dialogDestino.showAndWait().ifPresent(destino -> {
                CarroEmbarque carro = new CarroEmbarque(id, destino);
                try {
                    carro.asignarCarga(Math.random() * 500);
                    modelFactory.procesarEmbarque(carro);
                    actualizarTablas();
                } catch (IllegalArgumentException ex) {
                    mostrarAlerta("Error", "Error al asignar carga", ex.getMessage());
                }
            });
        });
    }

    @FXML
    private void salidaCarro() {
        CarroEmbarque carro = modelFactory.getCaribeAirlines().getEstacionamiento().salida();
        if (carro != null) {
            mostrarAlerta("Salida", "Salida de Carro", "Salida exitosa del carro: " + carro);
            actualizarTablas();
        } else {
            mostrarAlerta("Error", "No hay carros", "No hay carros disponibles para salir");
        }
    }

    @FXML
    private void retirarCarro() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retirar Carro");
        dialog.setHeaderText("Ingrese el ID del Carro a Retirar");
        dialog.setContentText("ID:");

        dialog.showAndWait().ifPresent(id -> {
            modelFactory.getCaribeAirlines().getEstacionamiento().retirar(id);
            actualizarTablas();
        });
    }

    private void actualizarTablas() {
        carrosBicola.clear();
        carrosEspera.clear();

        carrosBicola.addAll(modelFactory.getCaribeAirlines().getEstacionamiento().getCarrosEnBicola());
        carrosEspera.addAll(modelFactory.getCaribeAirlines().getEstacionamiento().getCarrosEnEspera());
    }

    private void mostrarAlerta(String titulo, String header, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
