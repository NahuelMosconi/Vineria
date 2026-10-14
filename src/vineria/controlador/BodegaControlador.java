package vineria.controlador;

import vineria.dao.BodegaDAO;
import vineria.modelo.Bodega;
import java.util.List;

/*
 * BodegaControlador - CONTROLADOR (MVC).
 * Intermediario entre la Vista (VistaBodegas) y el acceso a datos
 * (BodegaDAO). Recibe los pedidos de la ventana, decide qué hacer y le pide
 * al DAO que lo ejecute. Así la Vista no conoce SQL y el DAO no conoce Swing.
 */
public class BodegaControlador {

    // DAO: objeto encargado de leer/escribir bodegas en la base.
    private final BodegaDAO dao = new BodegaDAO();

    // MVC: devuelve la lista de bodegas (objetos del Modelo) para la Vista.
    public List<Bodega> listar() {
        return dao.listarTodas();
    }

    // MVC: lógica de decisión del controlador.
    // Un mismo botón "Guardar" sirve para alta y modificación: si la
    // bodega no tiene id (0) se inserta; si tiene id se actualiza.
    public void guardar(Bodega bodega) {
        if (bodega.getId() == 0) {
            dao.insertar(bodega);    // id 0 = bodega nueva
        } else {
            dao.actualizar(bodega);  // ya tiene id = existente
        }
    }

    // MVC: pide al DAO que elimine la bodega con ese id.
    public void eliminar(int id) {
        dao.eliminar(id);
    }
}
