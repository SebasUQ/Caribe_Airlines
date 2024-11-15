package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.Cliente;
import co.edu.uniquindio.caribe_airlines.Model.Equipaje;
import co.edu.uniquindio.caribe_airlines.Model.Ticket;
import co.edu.uniquindio.caribe_airlines.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;

public class PanelTicketsViewController2 {

    public AnchorPane panelTickets2, panelPrimeraPieza, panelSegundaPieza, panelEquipajeMano, panelMascota;
    public Button btnSiguiente, btnCancelar;
    public TextField alturaP1, largoP1, anchoP1, pesoP1, altura2, largo2, ancho2, peso2, pesoM;
    public TextField alturaEMano, largoEMano, AnchoEMano;
    public RadioButton opcionSIMascota, opcionNOMascota, opcionSIEquipaje, opcionNOEquipaje;
    private Ticket ticketCliente;
    private Cliente cliente;

//----------------------------------------------------------------------------------------------------//

    public void setObjetos(Ticket ticketCliente, Cliente c) {
        this.ticketCliente = ticketCliente;
        this.cliente = c;
        initialize();
    }

    private void initialize(){
        bloquearEntradas();
        panelEquipajeMano.setVisible(false);
        panelMascota.setVisible(false);

        opcionNOMascota.setSelected(true);
        opcionNOEquipaje.setSelected(true);

        if (ticketCliente != null){
            definirPaneles();
        }
    }

    public void nextPanel(ActionEvent actionEvent) {
        if (verificarDatos()){
            if (verificarDimensiones()){
                definirPrimeraPieza();
                if (panelSegundaPieza.isVisible()){
                    definirSegundaPieza();
                }
                if (panelEquipajeMano.isVisible()){
                    definirEquipajeM();
                }
                if (panelMascota.isVisible()){
                    definirTarifaM();
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets3.fxml"));
                    AnchorPane nuevoPanel = loader.load();

                    PanelTicketsViewController3 controller3 = loader.getController();
                    controller3.setObjetos(ticketCliente, cliente);
                    panelTickets2.getChildren().setAll(nuevoPanel);

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Por favor diligencie los espacios necesarios primero");
        }

    }

    public void cancelarReserva(ActionEvent actionEvent) {

        String msj = "¿Esta seguro de que desea cancelar la reserva?";

        if (Utils.verificarDesicion(msj)){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets.fxml"));
                AnchorPane primerPanel = loader.load();

                PanelTicketsViewController controller = loader.getController();
                controller.setObjetos(null, cliente);
                panelTickets2.getChildren().setAll(primerPanel);

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean verificarDimensiones(){
        boolean cent = true;
        double sumaE1 = Double.parseDouble(alturaP1.getText())+Double.parseDouble(largoP1.getText())+Double.parseDouble(anchoP1.getText());
        double sumaE2 = 0;
        double sumaEM = 0;
        if (panelSegundaPieza.isVisible()){
            sumaE2 = Double.parseDouble(altura2.getText())+Double.parseDouble(largo2.getText())+Double.parseDouble(ancho2.getText());
        }
        if (panelEquipajeMano.isVisible()){
            sumaEM = Double.parseDouble(alturaEMano.getText())+Double.parseDouble(largoEMano.getText())+Double.parseDouble(AnchoEMano.getText());
        }

        if (sumaE1 > 170 || sumaE2 > 170){
            cent = false;
            JOptionPane.showMessageDialog(null, "La suma de las dimenciones de cada equipaje debe de sumar como maximo 170cm");
        }
        if (sumaEM > 110){
            cent = false;
            JOptionPane.showMessageDialog(null,"La suma de las dimenciones del equoiaje de mano debe ser de maximo 110cm");
        }
        return cent;
    }

    private void definirPrimeraPieza() {
        double peso1 = Double.parseDouble(pesoP1.getText());
        double suma1 = Double.parseDouble(alturaP1.getText())+Double.parseDouble(largoP1.getText())+Double.parseDouble(anchoP1.getText());

        if (ticketCliente.getTipoServicio().equals("Economica")){
            if (peso1 > 24){
                double extra = 0;
                for (int i = 24; i <= peso1; i++){
                    extra+=8;
                }
                extra = extra + (extra*0.0675);
                double total = ticketCliente.getValorPagado()+ extra;
                ticketCliente.setValorPagado(total);
            }
            Equipaje equipaje = new Equipaje();
            equipaje.setTipo("Comun");
            equipaje.setPeso(peso1);
            equipaje.setSumDimensiones(suma1);
            ticketCliente.getEquipajes().add(equipaje);
        }
        if (ticketCliente.getTipoServicio().equals("Ejecutiva")){
            if (peso1 > 34){
                double extra = 0;
                for (int i = 34; i <= peso1; i++){
                    extra+=8;
                }
                extra = extra + (extra*0.0675);
                double total = ticketCliente.getValorPagado()+ extra;
                ticketCliente.setValorPagado(total);
            }
            Equipaje equipaje = new Equipaje();
            equipaje.setTipo("Comun");
            equipaje.setPeso(peso1);
            equipaje.setSumDimensiones(suma1);
            ticketCliente.getEquipajes().add(equipaje);
        }
    }

    private void definirSegundaPieza(){
        double peso = Double.parseDouble(peso2.getText());
        double suma = Double.parseDouble(altura2.getText())+Double.parseDouble(largo2.getText())+Double.parseDouble(ancho2.getText());

        if (ticketCliente.getTipoServicio().equals("Economica")){
            if (peso > 24){
                double extra = 0;
                for (int i = 24; i <= peso; i++){
                    extra+=8;
                }
                extra = extra + (extra*0.0675);
                double total = ticketCliente.getValorPagado()+ extra;
                ticketCliente.setValorPagado(total);
            }
            Equipaje equipaje = new Equipaje();
            equipaje.setTipo("Comun");
            equipaje.setPeso(peso);
            equipaje.setSumDimensiones(suma);
            ticketCliente.getEquipajes().add(equipaje);
        }
        if (ticketCliente.getTipoServicio().equals("Ejecutiva")){
            if (peso > 34){
                double extra = 0;
                for (int i = 34; i <= peso; i++){
                    extra+=8;
                }
                extra = extra + (extra*0.0675);
                double total = ticketCliente.getValorPagado()+ extra;
                ticketCliente.setValorPagado(total);
            }
            Equipaje equipaje = new Equipaje();
            equipaje.setTipo("Comun");
            equipaje.setPeso(peso);
            equipaje.setSumDimensiones(suma);
            ticketCliente.getEquipajes().add(equipaje);
        }

    }

    private void definirEquipajeM(){
        Equipaje equipaje = new Equipaje();
        equipaje.setTipo("Equipiaje de mano");
        double suma = Double.parseDouble(alturaEMano.getText())+Double.parseDouble(largoEMano.getText())+Double.parseDouble(AnchoEMano.getText());
        equipaje.setSumDimensiones(suma);
        equipaje.setPeso(0);
        ticketCliente.getEquipajes().add(equipaje);
    }

    private void definirTarifaM (){
        double pesoMascota = Double.parseDouble(pesoM.getText());
        double tarifa = 48;
        if (pesoMascota > 9){
            for (int i = 9; i < pesoMascota; i++){
                tarifa+=2;
            }
        }
        double total = ticketCliente.getValorPagado()+tarifa;
        ticketCliente.setValorPagado(total);
    }

    private boolean verificarDatos(){
        boolean cent = true;
        if (alturaP1.getText().isEmpty() || largoP1.getText().isEmpty() || anchoP1.getText().isEmpty() || pesoP1.getText().isEmpty()){
            cent = false;
        }
        if (panelSegundaPieza.isVisible()){
            if(altura2.getText().isEmpty() || largo2.getText().isEmpty() || ancho2.getText().isEmpty() || peso2.getText().isEmpty()){
                cent= false;
            }
        }
        if (panelEquipajeMano.isVisible()){
            if (alturaEMano.getText().isEmpty() || largoEMano.getText().isEmpty() || AnchoEMano.getText().isEmpty()){
                cent = false;
            }
        }
        if (panelMascota.isVisible()){
            if (pesoM.getText().isEmpty()){
                cent = false;
            }
        }
        return cent;
    }

    public void mostrarPesoM(ActionEvent actionEvent) {
        if (opcionSIMascota.isSelected()){
            panelMascota.setVisible(true);
            opcionNOMascota.setSelected(false);
        }else{
            opcionNOMascota.setSelected(true);
            panelMascota.setVisible(false);
        }
    }

    public void esconderPesoM(ActionEvent actionEvent) {
        if(opcionNOMascota.isSelected()){
            panelMascota.setVisible(false);
            opcionSIMascota.setSelected(false);
        }else{
            opcionSIMascota.setSelected(true);
            panelMascota.setVisible(true);
        }
    }

    public void MostrarPanelE(ActionEvent actionEvent) {
        if(opcionSIEquipaje.isSelected()){
            panelEquipajeMano.setVisible(true);
            opcionNOEquipaje.setSelected(false);
        }
        else{
            opcionNOEquipaje.setSelected(true);
            panelEquipajeMano.setVisible(false);
        }
    }

    public void esconderPanelE(ActionEvent actionEvent) {
        if (opcionNOEquipaje.isSelected()){
            panelEquipajeMano.setVisible(false);
            opcionSIEquipaje.setSelected(false);
        }
        else{
            opcionSIEquipaje.setSelected(true);
            panelEquipajeMano.setVisible(true);
        }
    }

    private void definirPaneles(){
        if(ticketCliente.getTipoVuelo().equals("Nacional") && ticketCliente.getTipoServicio().equals("Economica")){
            panelSegundaPieza.setVisible(false);
        }
    }

    private void bloquearEntradas(){
        alturaP1.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        largoP1.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        anchoP1.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        pesoP1.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        altura2.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        largo2.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        ancho2.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        peso2.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        alturaEMano.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        largoEMano.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        AnchoEMano.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
        pesoM.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));

    }
}
