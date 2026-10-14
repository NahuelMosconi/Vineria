package vineria.modelo;

public class VinoEspumante extends Vino {

    public VinoEspumante(String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(nombre, varietal, anio, precio, stock, idBodega);
    }

    public VinoEspumante(int id, String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(id, nombre, varietal, anio, precio, stock, idBodega);
    }

    @Override
    public String getTipo() {
        return "ESPUMANTE";
    }

    @Override
    public int getTemperaturaServicio() {
        return 6;
    }
}
