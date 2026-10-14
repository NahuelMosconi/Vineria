package vineria.dao;

import vineria.modelo.Bodega;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * BodegaDAO - ACCESO A DATOS.
 *
 * DAO (Data Access Object): concentra todo el SQL de la tabla "bodegas".
 * Implementa el ABM/CRUD: insertar (alta), listarTodas (lectura),
 * actualizar (modificación) y eliminar (baja).
 * Recibe y devuelve objetos Bodega del Modelo, así el Controlador trabaja
 * con objetos y nunca con SQL ni ResultSet.
 *
 * Todos los métodos usan try-with-resources: la conexión se cierra sola.
 */
public class BodegaDAO {

    // ALTA: inserta una bodega nueva
    // (el id no se envía: lo genera la base con AUTOINCREMENT).
    public void insertar(Bodega bodega) {
        String sql = "INSERT INTO bodegas (nombre, region, pais) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Cada "?" se reemplaza por un valor, en orden (1, 2, 3...).
            ps.setString(1, bodega.getNombre());
            ps.setString(2, bodega.getRegion());
            ps.setString(3, bodega.getPais());
            ps.executeUpdate();   // executeUpdate: para INSERT/UPDATE/DELETE
        } catch (SQLException e) {
            System.out.println("Error al insertar bodega: " + e.getMessage());
        }
    }

    // LECTURA: devuelve todas las bodegas
    // Recorre el ResultSet fila por fila y convierte cada una en un objeto Bodega.
    public List<Bodega> listarTodas() {
        List<Bodega> lista = new ArrayList<>();
        String sql = "SELECT * FROM bodegas ORDER BY nombre";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {   // rs.next() avanza a la siguiente fila
                Bodega b = new Bodega(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("region"),
                    rs.getString("pais"));
                lista.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar bodegas: " + e.getMessage());
        }
        return lista;
    }

    // MODIFICACIÓN: actualiza una bodega existente (por su id)
    public void actualizar(Bodega bodega) {
        String sql = "UPDATE bodegas SET nombre = ?, region = ?, pais = ? WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bodega.getNombre());
            ps.setString(2, bodega.getRegion());
            ps.setString(3, bodega.getPais());
            ps.setInt(4, bodega.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar bodega: " + e.getMessage());
        }
    }

    // BAJA: elimina una bodega por su id
    public void eliminar(int id) {
        String sql = "DELETE FROM bodegas WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar bodega: " + e.getMessage());
        }
    }
}
