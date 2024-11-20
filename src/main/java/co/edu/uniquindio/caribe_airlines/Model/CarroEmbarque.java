package co.edu.uniquindio.caribe_airlines.Model;

public class CarroEmbarque {
    private String id;
    private double cargaActual;
    private String destinoAvion;
    private static final double CARGA_MAXIMA = 500.0;

    public CarroEmbarque(String id, String destinoAvion) {
        this.id = id;
        this.destinoAvion = destinoAvion;
        this.cargaActual = 0;
    }

    public void asignarCarga(double carga) {
        if (cargaActual + carga <= CARGA_MAXIMA) {
            cargaActual += carga;
        } else {
            throw new IllegalArgumentException("Carga excede el límite máximo");
        }
    }


    public boolean estaLleno() {
        return cargaActual >= CARGA_MAXIMA;
    }

    // Mantén estos métodos como estaban
    public String getId() { return id; }
    public String getDestinoAvion() { return destinoAvion; }
    public double getCargaActual() { return cargaActual; }

    // Agrega estos métodos para compatibilidad con PropertyValueFactory
    public void setId(String id) { this.id = id; }
    public void setDestinoAvion(String destinoAvion) { this.destinoAvion = destinoAvion; }
    public void setCargaActual(double cargaActual) { this.cargaActual = cargaActual; }

    @Override
    public String toString() {
        return "Carro " + id + " (Destino: " + destinoAvion + ")";
    }
}
