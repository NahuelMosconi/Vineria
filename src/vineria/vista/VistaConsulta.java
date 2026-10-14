package vineria.vista;

import vineria.controlador.VinoControlador;
import vineria.modelo.Bodega;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/*
 * VistaConsulta - VISTA (MVC).
 * Pantalla de solo lectura: muestra los vinos junto con los datos de su
 * bodega, con la opción de filtrar por una bodega o ver todos.
 *
 * HERENCIA: "extends JFrame" -> es una ventana de Swing.
 * MVC: pide los datos a VinoControlador, que a su vez usa el JOIN del VinoDAO.
 */
public class VistaConsulta extends JFrame {

    // MVC: controlador que provee los datos de la consulta.
    private final VinoControlador controlador = new VinoControlador();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    // El combo guarda objetos Bodega; muestra el texto de su toString().
    private final JComboBox<Bodega> comboBodega = new JComboBox<>();

    // Constructor: arma la interfaz, conecta los botones y carga los datos.
    public VistaConsulta() {
        setTitle("Consulta de Vinos por Bodega");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con el filtro por bodega.
        JPanel arriba = new JPanel();
        arriba.add(new JLabel("Bodega:"));
        arriba.add(comboBodega);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnTodos = new JButton("Ver todos");
        arriba.add(btnBuscar);
        arriba.add(btnTodos);
        add(arriba, BorderLayout.NORTH);

        // Tabla con el resultado de la consulta.
        modeloTabla = new DefaultTableModel(
            new String[]{"Vino", "Tipo", "Bodega", "Región", "Precio", "Stock"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Buscar: filtra por la bodega elegida (0 si no hay ninguna = todas).
        btnBuscar.addActionListener(e -> {
            Bodega b = (Bodega) comboBodega.getSelectedItem();
            cargarTabla(b == null ? 0 : b.getId());
        });
        // Ver todos: idBodega = 0 significa "sin filtro".
        btnTodos.addActionListener(e -> cargarTabla(0));

        cargarBodegas();
        cargarTabla(0);
    }

    // Llena el combo con las bodegas que trae el controlador.
    private void cargarBodegas() {
        comboBodega.removeAllItems();
        for (Bodega b : controlador.listarBodegas()) comboBodega.addItem(b);
    }

    // Vacía la tabla y la llena con el resultado de la consulta.
    // Cada fila ya viene como Object[] desde el DAO, lista para agregar.
    private void cargarTabla(int idBodega) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : controlador.consultar(idBodega)) {
            modeloTabla.addRow(fila);
        }
    }
}
