package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.Cliente;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;

public class PanelIniciarSesionViewController {
    public TextField txtID;
    public PasswordField txtCON;
    public Button btnAcceder;
    public AnchorPane panelIniciar;
    private VentanaInicialController ventanaI;
    private ModelFactoryController controller;
    private Cliente cliente;

//----------------------------------------------------------------------------------------------------//

    public void setVentanaI(VentanaInicialController ventanaInicialController){
        this.ventanaI = ventanaInicialController;
        initialize();
        txtID.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")){
                return change;
            }
            return null;
        }));
    }

    public void initialize(){
        controller = ModelFactoryController.getInstance();
    }

    public void iniciarSesion() {
        if (verificarEspacios()){
            if (txtID.getText().equals("123") && txtCON.getText().equals("123")){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelEmpleado.fxml"));
                    AnchorPane nuevoPanel = loader.load();
                    ventanaI.cambiarContenido(nuevoPanel);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                cliente = controller.estaRegistrado(txtID.getText());
                if (cliente != null){
                    if (controller.verificarContrasena(cliente,txtCON.getText())){
                        try {
                            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelCliente.fxml"));
                            AnchorPane nuevoPanel = loader1.load();
                            PanelClienteViewController controller1 = loader1.getController();
                            controller1.setCliente(cliente);
                            ventanaI.cambiarContenido(nuevoPanel);

                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Cliente no registrado");
                }
            }
        }else {
            JOptionPane.showMessageDialog(null,"Por favor rellenar los espacios primero");
        }
    }

    private boolean verificarEspacios() {
        boolean cent = true;
        if (txtID.getText().isEmpty() || txtCON.getText().isEmpty()){
            cent = false;
        }
        return cent;
    }
}
