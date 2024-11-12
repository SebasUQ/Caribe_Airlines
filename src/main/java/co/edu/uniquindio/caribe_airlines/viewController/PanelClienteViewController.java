package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Model.Cliente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PanelClienteViewController {
    public AnchorPane panelCliente;
    public Tab tabTickets, tabPerfil;
    private Cliente cliente;

//----------------------------------------------------------------------------------------------------//
    public void cambiarContenido (Node pane){
        panelCliente.getChildren().setAll(pane);
    }


    public void setCliente(Cliente c) {
        this.cliente = c;
        initialize();
    }
    public void initialize(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelTickets.fxml"));
            AnchorPane nuevoPanel = loader.load();
            PanelTicketsViewController controller = loader.getController();
            controller.setObjetos(null, cliente);
            tabTickets.setContent(nuevoPanel);

            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/caribe_airlines/View/panelPerfilCliente.fxml"));
            AnchorPane nuevoPanel1 = loader1.load();
            PanelPerfilClienteViewController controller1 = loader1.getController();
            controller1.setObjetos(cliente,this);
            tabPerfil.setContent(nuevoPanel1);


        }catch (IOException e) {
            e.printStackTrace();
        }

    }


}
