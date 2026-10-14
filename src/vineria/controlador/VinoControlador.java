package vineria.controlador;

import vineria.dao.VinoDAO;
import vineria.dao.BodegaDAO;
import vineria.modelo.*;
import java.util.List;

/*
 * VinoControlador - CONTROLADOR (MVC).
 * Intermediario entre las Vistas de vinos (VistaVinos y VistaConsulta) y el
 * acceso a datos (VinoDAO y BodegaDAO).
 * La Vista le pasa datos sueltos (textos y números del formulario) y el
 * controlador arma el objeto del Modelo correcto antes de mandarlo al DAO.
 */
public class VinoControlador {

    // DAO: usa dos objetos de acceso a datos, uno por cada tabla.
    private final VinoDAO vinoDAO = new VinoDAO();
    private final BodegaDAO bodegaDAO = new BodegaDAO();

    // MVC: listas para llenar la tabla de vinos y el combo de bodegas.
    public List<Vino> listar() { return vinoDAO.listarTodos(); }
    public List<Bodega> listarBodegas() { return bodegaDAO.listarTodas(); }

    // MVC: alta o modificación de un vino.
    // 1) crea la subclase de Vino según el tipo elegido en la Vista;
    // 2) le asigna el id (0 = nuevo);
    // 3) si es nuevo lo inserta, si no lo actualiza.
    public void guardar(int id, String tipo, String nombre, String varietal,
                        int anio, double precio, int stock, int idBodega) {
        Vino vino = crearVino(tipo, nombre, varietal, anio, precio, stock, idBodega);
        vino.setId(id);
        if (id == 0) vinoDAO.insertar(vino);
        else vinoDAO.actualizar(vino);
    }

    // MVC: baja de un vino por id.
    public void eliminar(int id) { vinoDAO.eliminar(id); }

    // Según el tipo elegido, crea la subclase correcta
    // HERENCIA / POLIMORFISMO: la variable es de tipo Vino (el padre), pero
    // el objeto creado es VinoTinto, VinoBlanco o VinoEspumante.
    private Vino crearVino(String tipo, String nombre, String varietal,
                           int anio, double precio, int stock, int idBodega) {
        switch (tipo) {
            case "TINTO":     return new VinoTinto(nombre, varietal, anio, precio, stock, idBodega);
            case "BLANCO":    return new VinoBlanco(nombre, varietal, anio, precio, stock, idBodega);
            case "ESPUMANTE": return new VinoEspumante(nombre, varietal, anio, precio, stock, idBodega);
            default:          return null;
        }
    }
    // MVC: consulta de vinos con su bodega (JOIN). idBodega = 0 trae todos.
    public List<Object[]> consultar(int idBodega) {
        return vinoDAO.consultarConBodega(idBodega);
    }
}
