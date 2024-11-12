package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.Cliente;
import co.edu.uniquindio.caribe_airlines.dataStructures.MiListaEnlazada;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;

public class PanelRegistrarseViewController {
    public TextField txtID, txtNom, txtApe, txtDirec, txtCorreo, txtContra;
    public DatePicker fNaci;
    public Button btnRegistrarse;
    public AnchorPane panelRegistrarse;
    private ModelFactoryController controller;

//----------------------------------------------------------------------------------------------------//

    public void initialize(){
        controller = ModelFactoryController.getInstance();
        txtID.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")){
                return change;
            }
            return null;
        }));

    }

    public void RegistrarCliente() {
        if (datosCompletos()){
            if( controller.estaRegistrado(txtID.getText()) == null){
                Cliente c = recopilarInfo();
                controller.registrarCliente(c);
                clear();
                JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");
            }
            else {
                JOptionPane.showMessageDialog(null, "Cliente ya existente");
            }

        }else {
            JOptionPane.showMessageDialog(null, "Datos incompletos");
        }
    }

    private Cliente recopilarInfo() {
        Cliente cliente = new Cliente(txtID.getText(),
                txtContra.getText(),
                txtNom.getText(),
                txtApe.getText(),
                txtDirec.getText(),
                txtCorreo.getText(),
                fNaci.getValue().toString(),
                new MiListaEnlazada<>());
        return cliente;
    }

    private boolean datosCompletos() {
        boolean cent = true;
        if (txtID.getText().isEmpty() || txtNom.getText().isEmpty() || txtApe.getText().isEmpty()){
            cent = false;
        }
        if (txtCorreo.getText().isEmpty() || txtDirec.getText().isEmpty() || fNaci.getValue() == null){
            cent = false;
        }
        return cent;
    }

    private void clear(){
        txtID.setText("");
        txtNom.setText("");
        txtApe.setText("");
        txtDirec.setText("");
        txtCorreo.setText("");
        txtContra.setText("");
        fNaci.setValue(null);
    }


}
