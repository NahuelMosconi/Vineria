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

public class VinoDAO {

    // ALTA
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
    public void actualizar(Vino vino) {
        String sql = "UPDATE vinos SET nombre = ?, tipo = ?, varietal = ?, anio = ?, "
                   + "precio = ?, stock = ?, id_bodega = ? WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vino.getNombre());
            ps.setString(2, vino.getTipo());
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
                return null;
        }
    }
    // Consulta con JOIN: vinos junto a su bodega. Si idBodega = 0, trae todos.
    public List<Object[]> consultarConBodega(int idBodega) {
        List<Object[]> filas = new ArrayList<>();
        String sql = "SELECT v.nombre, v.tipo, b.nombre, b.region, v.precio, v.stock "
                   + "FROM vinos v JOIN bodegas b ON v.id_bodega = b.id ";
        if (idBodega != 0) sql += "WHERE v.id_bodega = ? ";
        sql += "ORDER BY b.nombre, v.nombre";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (idBodega != 0) ps.setInt(1, idBodega);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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