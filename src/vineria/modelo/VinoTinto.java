package vineria.modelo;

/*
 * VinoTinto - MODELO (MVC).
 * Representa un vino de tipo tinto.
 *
 * HERENCIA: "extends Vino" -> hereda todos los atributos y métodos de Vino
 * (nombre, precio, stock, getters/setters...) sin tener que reescribirlos.
 * POLIMORFISMO: sobrescribe los métodos abstractos de Vino con su propia
 * versión (tipo "TINTO" y 18 °C).
 */
public class VinoTinto extends Vino {

    // HERENCIA: super(...) llama al constructor de la clase padre Vino.
    // Constructor para un vino nuevo (sin id).
    public VinoTinto(String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(nombre, varietal, anio, precio, stock, idBodega);
    }

    // Constructor para un vino leído de la base (con id).
    public VinoTinto(int id, String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(id, nombre, varietal, anio, precio, stock, idBodega);
    }

    // POLIMORFISMO: implementación propia del método abstracto de Vino.
    @Override
    public String getTipo() {
        return "TINTO";
    }

    // POLIMORFISMO: el tinto se sirve más templado que los demás.
    @Override
    public int getTemperaturaServicio() {
        return 18;
    }
}
