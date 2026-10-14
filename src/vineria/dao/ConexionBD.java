package vineria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * ConexionBD - ACCESO A DATOS (capa DAO).
 * Centraliza la conexión a la base SQLite y la creación inicial de tablas.
 *
 * DAO: los demás DAO (BodegaDAO, VinoDAO, UsuarioDAO) piden su conexión acá,
 * así la dirección de la base está escrita en un solo lugar.
 * ABSTRACCION: el resto del programa no necesita saber cómo se conecta a
 * SQLite; solo llama a ConexionBD.conectar().
 *
 * Sus métodos son "static": se usan sin crear un objeto (ConexionBD.conectar()).
 */
public class ConexionBD {

    // Dirección de la base: un archivo "vineria.db" en la carpeta del proyecto.
    // ENCAPSULAMIENTO: es private, nadie de afuera puede leerla ni cambiarla.
    private static final String URL = "jdbc:sqlite:vineria.db";

    // Abre la conexión. Si el archivo no existe, SQLite lo crea solo.
    // Quien la usa debe cerrarla (los DAO lo hacen con try-with-resources).
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Crea las tablas y carga los datos iniciales si no existen.
    // Se llama una sola vez, desde main, al arrancar el programa.
    public static void inicializar() {
        // Tabla de bodegas. "AUTOINCREMENT": la base asigna el id sola.
        String sqlBodegas =
            "CREATE TABLE IF NOT EXISTS bodegas (" +
            "  id     INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nombre TEXT NOT NULL," +
            "  region TEXT NOT NULL," +
            "  pais   TEXT NOT NULL" +
            ")";

        // Tabla de vinos. La columna "tipo" guarda TINTO/BLANCO/ESPUMANTE
        // para saber qué subclase de Vino crear al leerla.
        // FOREIGN KEY: cada vino apunta a una bodega existente.
        String sqlVinos =
            "CREATE TABLE IF NOT EXISTS vinos (" +
            "  id        INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nombre    TEXT NOT NULL," +
            "  tipo      TEXT NOT NULL," +
            "  varietal  TEXT NOT NULL," +
            "  anio      INTEGER NOT NULL," +
            "  precio    REAL NOT NULL," +
            "  stock     INTEGER NOT NULL," +
            "  id_bodega INTEGER NOT NULL," +
            "  FOREIGN KEY (id_bodega) REFERENCES bodegas(id)" +
            ")";

        // Tabla de usuarios para el login. UNIQUE: no se repiten nombres.
        String sqlUsuarios =
            "CREATE TABLE IF NOT EXISTS usuarios (" +
            "  id          INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  usuario     TEXT NOT NULL UNIQUE," +
            "  contrasenia TEXT NOT NULL" +
            ")";

        // try-with-resources: la conexión y el Statement se cierran solos
        // al terminar el bloque, aunque ocurra un error.
        try (Connection con = conectar();
             Statement st = con.createStatement()) {
            st.execute(sqlBodegas);
            st.execute(sqlVinos);
            st.execute(sqlUsuarios);

            // "INSERT OR IGNORE": si el registro ya existe, no lo duplica.
            // Así se puede ejecutar en cada arranque sin problemas.

            // Usuario para el login
            st.execute("INSERT OR IGNORE INTO usuarios (usuario, contrasenia) VALUES ('admin', 'admin')");

            // Bodegas de ejemplo
            st.execute("INSERT OR IGNORE INTO bodegas (id, nombre, region, pais) VALUES "
                + "(1,'Catena Zapata','Mendoza','Argentina'),"
                + "(2,'Bodega Norton','Mendoza','Argentina'),"
                + "(3,'Trapiche','Mendoza','Argentina'),"
                + "(4,'Luigi Bosca','Mendoza','Argentina'),"
                + "(5,'El Esteco','Cafayate, Salta','Argentina')");

            // Vinos de ejemplo
            st.execute("INSERT OR IGNORE INTO vinos (id, nombre, tipo, varietal, anio, precio, stock, id_bodega) VALUES "
                + "(1,'Malbec Argentino','TINTO','Malbec',2021,12500.0,30,1),"
                + "(2,'Catena Chardonnay','BLANCO','Chardonnay',2022,9800.0,20,1),"
                + "(3,'Catena Cabernet','TINTO','Cabernet Sauvignon',2020,11000.0,15,1),"
                + "(4,'Norton Reserva','TINTO','Malbec',2021,7200.0,40,2),"
                + "(5,'Norton Torrontés','BLANCO','Torrontés',2022,6500.0,25,2),"
                + "(6,'Norton Brut','ESPUMANTE','Chardonnay-Pinot Noir',2021,8900.0,18,2),"
                + "(7,'Medalla Malbec','TINTO','Malbec',2019,9500.0,22,3),"
                + "(8,'Trapiche Bonarda','TINTO','Bonarda',2020,5400.0,35,3),"
                + "(9,'Extravaganza Blanco','BLANCO','Chardonnay',2022,7800.0,16,3),"
                + "(10,'De Sangre Malbec','TINTO','Malbec',2020,10200.0,20,4),"
                + "(11,'Luigi Bosca Riesling','BLANCO','Riesling',2022,6900.0,14,4),"
                + "(12,'Bohème Nature','ESPUMANTE','Pinot Noir-Chardonnay',2019,13500.0,10,4),"
                + "(13,'Old Vines Malbec','TINTO','Malbec',2018,14500.0,12,5),"
                + "(14,'Esteco Torrontés','BLANCO','Torrontés',2023,5200.0,40,5),"
                + "(15,'Don David Cabernet','TINTO','Cabernet Sauvignon',2021,7600.0,28,5)");

            System.out.println("Base de datos lista: tablas creadas.");
        } catch (SQLException e) {
            System.out.println("Error al inicializar la base: " + e.getMessage());
        }
    }
}
