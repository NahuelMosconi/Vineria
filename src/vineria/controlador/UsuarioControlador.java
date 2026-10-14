package vineria.controlador;

import vineria.dao.UsuarioDAO;

/*
 * UsuarioControlador - CONTROLADOR (MVC).
 * Atiende el pedido de login que hace la Vista (VistaLogin) y se lo
 * delega al DAO. La Vista nunca habla directo con la base de datos.
 */
public class UsuarioControlador {
    // DAO: el controlador usa el objeto de acceso a datos de usuarios.
    private final UsuarioDAO dao = new UsuarioDAO();

    // MVC: la Vista llama a login(); el controlador consulta al DAO
    // y devuelve true si las credenciales son correctas.
    public boolean login(String usuario, String contrasenia) {
        return dao.validar(usuario, contrasenia);
    }
}
