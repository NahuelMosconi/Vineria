package vineria.modelo;

/*
 * Vino - MODELO (MVC).
 * Representa un vino de la vinería: nombre, varietal, año, precio, stock y
 * la bodega a la que pertenece.
 *
 * ABSTRACCION: es una clase abstracta. No existe "un vino" a secas: siempre
 * es Tinto, Blanco o Espumante. Por eso no se puede hacer "new Vino(...)";
 * esta clase solo reúne lo que TODOS los vinos tienen en común y deja
 * declarado lo que cada tipo debe resolver por su cuenta.
 *
 * HERENCIA: es la clase padre de VinoTinto, VinoBlanco y VinoEspumante.
 */
public abstract class Vino {

    // ENCAPSULAMIENTO: los atributos son privados; desde afuera solo se
    // accede a ellos mediante los getters y setters públicos.
    private int id;
    private String nombre;
    private String varietal;
    private int anio;
    private double precio;
    private int stock;
    private int idBodega;   // clave foránea: id de la Bodega a la que pertenece

    // Constructor para un vino nuevo (sin id)
    public Vino(String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        this.nombre = nombre;
        this.varietal = varietal;
        this.anio = anio;
        this.precio = precio;
        this.stock = stock;
        this.idBodega = idBodega;
    }

    // Constructor para un vino leído de la base (con id)
    public Vino(int id, String nombre, String varietal, int anio, double precio, int stock, int idBodega) {
        this.id = id;
        this.nombre = nombre;
        this.varietal = varietal;
        this.anio = anio;
        this.precio = precio;
        this.stock = stock;
        this.idBodega = idBodega;
    }

    // MÉTODOS ABSTRACTOS: cada hijo los resuelve a su manera (polimorfismo)
    // ABSTRACCION: se declara QUÉ debe saber hacer un vino, sin decir CÓMO.
    // POLIMORFISMO: al llamar vino.getTipo() se ejecuta la versión de la
    // subclase real (Tinto, Blanco o Espumante).
    public abstract String getTipo();
    public abstract int getTemperaturaServicio();   // temperatura ideal en °C

    // ENCAPSULAMIENTO: getters y setters, la única puerta de acceso a los atributos.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getVarietal() { return varietal; }
    public void setVarietal(String varietal) { this.varietal = varietal; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getIdBodega() { return idBodega; }
    public void setIdBodega(int idBodega) { this.idBodega = idBodega; }

    // POLIMORFISMO: sobrescribe toString() de Object. Además usa getTipo(),
    // que devuelve un texto distinto según la subclase del objeto.
    @Override
    public String toString() {
        return nombre + " " + anio + " (" + getTipo() + ")";
    }
}
