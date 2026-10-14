package vineria.modelo;

public class VinoTinto extends Vino {

    public VinoTinto(String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(nombre, varietal, anio, precio, stock, idBodega);
    }

    public VinoTinto(int id, String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(id, nombre, varietal, anio, precio, stock, idBodega);
    }

    @Override
    public String getTipo() {
        return "TINTO";
    }

    @Override
    public int getTemperaturaServicio() {
        return 18;
    }
}