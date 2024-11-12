package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;

public class PanelPerfilClienteViewController {
    public AnchorPane panelPerfilC;
    public Label lblUsuario, lblID, lblCorreo, lblDireccion;
    public Button btnCerrarSesion;
    private Cliente cliente;
    private PanelClienteViewController controller;

//----------------------------------------------------------------------------------------------------//

    public void setObjetos(Cliente c, PanelClienteViewController panelClienteViewController){
        this.cliente = c;
        this.controller = panelClienteViewController;
        initialize();
    }

    public void initialize(){
        if (cliente != null){
            lblUsuario.setText(cliente.getNombre());
            lblID.setText(cliente.getIdentificacion());
            lblCorreo.setText(cliente.getCorreoElectronico());
            lblDireccion.setText(cliente.getDireccion());
        }
    }

    public void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/ventanaInicial.fxml"));
            AnchorPane nuevoPanel = loader.load();
            controller.cambiarContenido(nuevoPanel);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
