package vineria.modelo;

public class VinoBlanco extends Vino {

    public VinoBlanco(String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(nombre, varietal, anio, precio, stock, idBodega);
    }

    public VinoBlanco(int id, String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(id, nombre, varietal, anio, precio, stock, idBodega);
    }

    @Override
    public String getTipo() {
        return "BLANCO";
    }

    @Override
    public int getTemperaturaServicio() {
        return 8;
    }
}