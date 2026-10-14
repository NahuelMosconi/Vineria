package vineria.modelo;

public abstract class Vino {

    private int id;
    private String nombre;
    private String varietal;
    private int anio;
    private double precio;
    private int stock;
    private int idBodega;

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
    public abstract String getTipo();
    public abstract int getTemperaturaServicio();

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

    @Override
    public String toString() {
        return nombre + " " + anio + " (" + getTipo() + ")";
    }
}