package co.edu.uniquindio.caribe_airlines.Utils;

import co.edu.uniquindio.caribe_airlines.Model.Ticket;

import javax.swing.*;
import java.security.SecureRandom;
import java.text.DecimalFormat;

public class Utils {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LONGITUD_CODIGO = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generarID(){
        StringBuilder codigo = new StringBuilder(LONGITUD_CODIGO);

        for (int i = 0; i < LONGITUD_CODIGO; i++) {
            int index = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(index));
        }

        return codigo.toString();
    }

    public static boolean verificarDesicion(String msj){
        boolean centinela = false;
        int result = JOptionPane.showConfirmDialog(null,
                msj,null, JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION) {
            centinela=true;
        }
        return centinela;
    }

    public static double calcularCostosEco(Ticket ticketCliente) {
        double costoP = 0;
        if (ticketCliente.getDestino().equals("Monterrey") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 37.27;
        }
        if (ticketCliente.getDestino().equals("Monterrey") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 79.50;
        }
        if (ticketCliente.getDestino().equals("Cancun") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 25.84;
        }
        if (ticketCliente.getDestino().equals("Cancun") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 58.14;
        }
        if (ticketCliente.getDestino().equals("Buenos Aires") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 270.81;
        }
        if (ticketCliente.getDestino().equals("Buenos Aires") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 591.30;
        }
        if (ticketCliente.getDestino().equals("Los Angeles") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 81.49;
        }
        if (ticketCliente.getDestino().equals("Los Angeles") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 150.06;
        }
        if (ticketCliente.getDestino().equals("Bogota") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 163.48;
        }
        if (ticketCliente.getDestino().equals("Bogota") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 320.96;
        }
        if (ticketCliente.getDestino().equals("Panama") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 168.05;
        }
        if (ticketCliente.getDestino().equals("Panama") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 351;
        }
        return costoP;
    }

    public static double calcularCostosEje(Ticket ticketCliente) {
        double costoP = 0;
        if (ticketCliente.getDestino().equals("Monterrey") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 89.27;
        }
        if (ticketCliente.getDestino().equals("Monterrey") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 182.50;
        }
        if (ticketCliente.getDestino().equals("Cancun") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 77.84;
        }
        if (ticketCliente.getDestino().equals("Cancun") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 161.14;
        }
        if (ticketCliente.getDestino().equals("Buenos Aires") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 322.81;
        }
        if (ticketCliente.getDestino().equals("Buenos Aires") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 653.30;
        }
        if (ticketCliente.getDestino().equals("Los Angeles") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 133.49;
        }
        if (ticketCliente.getDestino().equals("Los Angeles") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 267.06;
        }
        if (ticketCliente.getDestino().equals("Bogota") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 215.48;
        }
        if (ticketCliente.getDestino().equals("Bogota") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 428.96;
        }
        if (ticketCliente.getDestino().equals("Panama") && ticketCliente.getModalidad().equals("Solo Ida")){
            costoP = 230.05;
        }
        if (ticketCliente.getDestino().equals("Panama") && ticketCliente.getModalidad().equals("Ida y Vuelta")){
            costoP = 471.9;
        }
        return costoP;
    }
}
