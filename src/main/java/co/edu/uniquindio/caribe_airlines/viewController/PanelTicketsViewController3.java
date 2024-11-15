package co.edu.uniquindio.caribe_airlines.viewController;

import co.edu.uniquindio.caribe_airlines.Controller.ModelFactoryController;
import co.edu.uniquindio.caribe_airlines.Model.Cliente;
import co.edu.uniquindio.caribe_airlines.Model.Ticket;
import javafx.scene.layout.AnchorPane;

public class PanelTicketsViewController3 {
    public AnchorPane panelTicket3;

    private Cliente cliente;
    private Ticket ticketCliente;
    private ModelFactoryController controller;

    public void setObjetos(Ticket ticket, Cliente c){
        this.ticketCliente = ticket;
        this.cliente = c;
    }

    public void initialize(){
        this.controller = ModelFactoryController.getInstance();
    }
}
