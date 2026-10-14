package vineria.modelo;

/*
 * VinoBlanco - MODELO (MVC).
 * Representa un vino de tipo blanco.
 *
 * HERENCIA: "extends Vino" -> hereda todos los atributos y métodos de Vino
 * (nombre, precio, stock, getters/setters...) sin tener que reescribirlos.
 * POLIMORFISMO: sobrescribe los métodos abstractos de Vino con su propia
 * versión (tipo "BLANCO" y 8 °C).
 */
public class VinoBlanco extends Vino {

    // HERENCIA: super(...) llama al constructor de la clase padre Vino.
    // Constructor para un vino nuevo (sin id).
    public VinoBlanco(String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(nombre, varietal, anio, precio, stock, idBodega);
    }

    // Constructor para un vino leído de la base (con id).
    public VinoBlanco(int id, String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(id, nombre, varietal, anio, precio, stock, idBodega);
    }

    // POLIMORFISMO: implementación propia del método abstracto de Vino.
    @Override
    public String getTipo() {
        return "BLANCO";
    }

    // POLIMORFISMO: el blanco se sirve frío.
    @Override
    public int getTemperaturaServicio() {
        return 8;
    }
}
