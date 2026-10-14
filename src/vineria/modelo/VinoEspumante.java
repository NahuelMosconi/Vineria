package vineria.modelo;

/*
 * VinoEspumante - MODELO (MVC).
 * Representa un vino espumante (con burbujas).
 *
 * HERENCIA: "extends Vino" -> hereda todos los atributos y métodos de Vino
 * (nombre, precio, stock, getters/setters...) sin tener que reescribirlos.
 * POLIMORFISMO: sobrescribe los métodos abstractos de Vino con su propia
 * versión (tipo "ESPUMANTE" y 6 °C).
 */
public class VinoEspumante extends Vino {

    // HERENCIA: super(...) llama al constructor de la clase padre Vino.
    // Constructor para un vino nuevo (sin id).
    public VinoEspumante(String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(nombre, varietal, anio, precio, stock, idBodega);
    }

    // Constructor para un vino leído de la base (con id).
    public VinoEspumante(int id, String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        super(id, nombre, varietal, anio, precio, stock, idBodega);
    }

    // POLIMORFISMO: implementación propia del método abstracto de Vino.
    @Override
    public String getTipo() {
        return "ESPUMANTE";
    }

    // POLIMORFISMO: el espumante es el que se sirve más frío.
    @Override
    public int getTemperaturaServicio() {
        return 6;
    }
}
