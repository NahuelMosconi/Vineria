package vineria.modelo;

/*
 * Bodega - MODELO (MVC).
 * Representa una bodega productora de vinos (nombre, región y país).
 * Cada Vino guarda el id de su Bodega (relación uno a muchos: una bodega
 * tiene muchos vinos).
 */
public class Bodega {

    // ENCAPSULAMIENTO: atributos privados, solo accesibles con getters/setters.
    private int id;
    private String nombre;
    private String region;
    private String pais;

    // Constructor para una bodega NUEVA (sin id: lo asigna la base al guardarla)
    public Bodega(String nombre, String region, String pais) {
        this.nombre = nombre;
        this.region = region;
        this.pais = pais;
    }

    // Constructor para una bodega leída de la base (ya tiene id)
    public Bodega(int id, String nombre, String region, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.region = region;
        this.pais = pais;
    }

    // ENCAPSULAMIENTO: getters y setters públicos.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    // Cómo se muestra la bodega como texto (por ejemplo en un combo)
    // POLIMORFISMO: sobrescribe toString() heredado de Object. El JComboBox
    // lo llama automáticamente para mostrar cada bodega.
    @Override
    public String toString() {
        return nombre + " (" + region + ", " + pais + ")";
    }
}
