package vineria.controlador;

import vineria.dao.VinoDAO;
import vineria.dao.BodegaDAO;
import vineria.modelo.*;
import java.util.List;

public class VinoControlador {

    private final VinoDAO vinoDAO = new VinoDAO();
    private final BodegaDAO bodegaDAO = new BodegaDAO();

    public List<Vino> listar() { return vinoDAO.listarTodos(); }
    public List<Bodega> listarBodegas() { return bodegaDAO.listarTodas(); }

    public void guardar(int id, String tipo, String nombre, String varietal,
                        int anio, double precio, int stock, int idBodega) {
        Vino vino = crearVino(tipo, nombre, varietal, anio, precio, stock, idBodega);
        vino.setId(id);
        if (id == 0) vinoDAO.insertar(vino);
        else vinoDAO.actualizar(vino);
    }

    public void eliminar(int id) { vinoDAO.eliminar(id); }

    // Según el tipo elegido, crea la subclase correcta
    private Vino crearVino(String tipo, String nombre, String varietal,
                           int anio, double precio, int stock, int idBodega) {
        switch (tipo) {
            case "TINTO":     return new VinoTinto(nombre, varietal, anio, precio, stock, idBodega);
            case "BLANCO":    return new VinoBlanco(nombre, varietal, anio, precio, stock, idBodega);
            case "ESPUMANTE": return new VinoEspumante(nombre, varietal, anio, precio, stock, idBodega);
            default:          return null;
        }
    }
    public List<Object[]> consultar(int idBodega) {
        return vinoDAO.consultarConBodega(idBodega);
    }
}