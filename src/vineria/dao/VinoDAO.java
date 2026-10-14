package vineria.dao;

import vineria.modelo.Vino;
import vineria.modelo.VinoTinto;
import vineria.modelo.VinoBlanco;
import vineria.modelo.VinoEspumante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * VinoDAO - ACCESO A DATOS.
 *
 * DAO (Data Access Object): concentra todo el SQL de la tabla "vinos".
 * Implementa el ABM/CRUD (alta, lectura, modificación, baja) y además una
 * consulta con JOIN entre vinos y bodegas.
 *
 * POLIMORFISMO: trabaja siempre con el tipo general Vino (List<Vino>,
 * insertar(Vino)...), sin importar si el objeto real es Tinto, Blanco o
 * Espumante.
 */
public class VinoDAO {

    // ALTA
    // Recibe un Vino (de cualquier subclase) y lo guarda en la tabla.
    public void insertar(Vino vino) {
        String sql = "INSERT INTO vinos (nombre, tipo, varietal, anio, precio, stock, id_bodega) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vino.getNombre());
            ps.setString(2, vino.getTipo());      // polimorfismo: cada subclase da su tipo
            ps.setString(3, vino.getVarietal());
            ps.setInt(4, vino.getAnio());
            ps.setDouble(5, vino.getPrecio());
            ps.setInt(6, vino.getStock());
            ps.setInt(7, vino.getIdBodega());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar vino: " + e.getMessage());
        }
    }

    // LECTURA
    // Devuelve todos los vinos. Cada fila se transforma en la subclase
    // correcta gracias a crearVino(rs).
    public List<Vino> listarTodos() {
        List<Vino> lista = new ArrayList<>();
        String sql = "SELECT * FROM vinos ORDER BY nombre";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearVino(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar vinos: " + e.getMessage());
        }
        return lista;
    }

    // MODIFICACIÓN
    // Actualiza todos los campos del vino cuyo id coincide (WHERE id = ?).
    public void actualizar(Vino vino) {
        String sql = "UPDATE vinos SET nombre = ?, tipo = ?, varietal = ?, anio = ?, "
                   + "precio = ?, stock = ?, id_bodega = ? WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vino.getNombre());
            ps.setString(2, vino.getTipo());      // POLIMORFISMO
            ps.setString(3, vino.getVarietal());
            ps.setInt(4, vino.getAnio());
            ps.setDouble(5, vino.getPrecio());
            ps.setInt(6, vino.getStock());
            ps.setInt(7, vino.getIdBodega());
            ps.setInt(8, vino.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar vino: " + e.getMessage());
        }
    }

    // BAJA
    // Elimina el vino con ese id.
    public void eliminar(int id) {
        String sql = "DELETE FROM vinos WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar vino: " + e.getMessage());
        }
    }

    // Según el "tipo" de la fila, crea el Vino de la subclase correcta
    // La base guarda texto ("TINTO", "BLANCO"...), pero en Java necesitamos
    // un objeto concreto: Vino es abstracta y no se puede instanciar.
    // HERENCIA / POLIMORFISMO: devuelve un VinoTinto, VinoBlanco o
    // VinoEspumante, pero el tipo de retorno es el padre Vino.
    private Vino crearVino(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String varietal = rs.getString("varietal");
        int anio = rs.getInt("anio");
        double precio = rs.getDouble("precio");
        int stock = rs.getInt("stock");
        int idBodega = rs.getInt("id_bodega");

        switch (tipo) {
            case "TINTO":
                return new VinoTinto(id, nombre, varietal, anio, precio, stock, idBodega);
            case "BLANCO":
                return new VinoBlanco(id, nombre, varietal, anio, precio, stock, idBodega);
            case "ESPUMANTE":
                return new VinoEspumante(id, nombre, varietal, anio, precio, stock, idBodega);
            default:
                return null;   // tipo desconocido
        }
    }
    // Consulta con JOIN: vinos junto a su bodega. Si idBodega = 0, trae todos.
    // Devuelve cada fila como Object[] (vino, tipo, bodega, región, precio,
    // stock), listo para agregarse directamente a la tabla de la Vista.
    public List<Object[]> consultarConBodega(int idBodega) {
        List<Object[]> filas = new ArrayList<>();
        // JOIN: une cada vino con la bodega cuyo id coincide con id_bodega.
        String sql = "SELECT v.nombre, v.tipo, b.nombre, b.region, v.precio, v.stock "
                   + "FROM vinos v JOIN bodegas b ON v.id_bodega = b.id ";
        // El filtro WHERE solo se agrega si se eligió una bodega.
        if (idBodega != 0) sql += "WHERE v.id_bodega = ? ";
        sql += "ORDER BY b.nombre, v.nombre";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (idBodega != 0) ps.setInt(1, idBodega);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Se leen las columnas por posición (1..6), porque hay
                    // dos columnas llamadas "nombre" (v.nombre y b.nombre).
                    filas.add(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDouble(5), rs.getInt(6)
                    });
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en consulta: " + e.getMessage());
        }
        return filas;
    }
}
