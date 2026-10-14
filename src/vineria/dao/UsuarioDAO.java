package vineria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean validar(String usuario, String contrasenia) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasenia = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();   // true si encontró un usuario que coincide
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
            return false;
        }
    }
}