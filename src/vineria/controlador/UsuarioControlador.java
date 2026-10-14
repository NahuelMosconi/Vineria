package vineria.controlador;

import vineria.dao.UsuarioDAO;

public class UsuarioControlador {
    private final UsuarioDAO dao = new UsuarioDAO();

    public boolean login(String usuario, String contrasenia) {
        return dao.validar(usuario, contrasenia);
    }
}