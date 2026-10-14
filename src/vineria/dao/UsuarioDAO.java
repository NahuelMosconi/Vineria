package vineria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * UsuarioDAO - ACCESO A DATOS.
 *
 * DAO (Data Access Object): es la única clase que consulta la tabla
 * "usuarios". El resto del programa no escribe SQL; le pide a este objeto
 * que valide un usuario.
 */
public class UsuarioDAO {

    // Devuelve true si existe un usuario con ese nombre y contraseña.
    // Usa PreparedStatement con "?" para evitar inyección SQL: los valores
    // se pasan aparte y nunca se pegan dentro del texto de la consulta.
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
