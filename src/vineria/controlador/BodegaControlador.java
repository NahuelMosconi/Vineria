package vineria.controlador;

import vineria.dao.BodegaDAO;
import vineria.modelo.Bodega;
import java.util.List;

public class BodegaControlador {

    private final BodegaDAO dao = new BodegaDAO();

    public List<Bodega> listar() {
        return dao.listarTodas();
    }

    public void guardar(Bodega bodega) {
        if (bodega.getId() == 0) {
            dao.insertar(bodega);    // id 0 = bodega nueva
        } else {
            dao.actualizar(bodega);  // ya tiene id = existente
        }
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}