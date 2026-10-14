package vineria.vista;

import vineria.controlador.VinoControlador;
import vineria.modelo.Bodega;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaConsulta extends JFrame {

    private final VinoControlador controlador = new VinoControlador();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    private final JComboBox<Bodega> comboBodega = new JComboBox<>();

    public VistaConsulta() {
        setTitle("Consulta de Vinos por Bodega");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel arriba = new JPanel();
        arriba.add(new JLabel("Bodega:"));
        arriba.add(comboBodega);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnTodos = new JButton("Ver todos");
        arriba.add(btnBuscar);
        arriba.add(btnTodos);
        add(arriba, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
            new String[]{"Vino", "Tipo", "Bodega", "Región", "Precio", "Stock"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            Bodega b = (Bodega) comboBodega.getSelectedItem();
            cargarTabla(b == null ? 0 : b.getId());
        });
        btnTodos.addActionListener(e -> cargarTabla(0));

        cargarBodegas();
        cargarTabla(0);
    }

    private void cargarBodegas() {
        comboBodega.removeAllItems();
        for (Bodega b : controlador.listarBodegas()) comboBodega.addItem(b);
    }

    private void cargarTabla(int idBodega) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : controlador.consultar(idBodega)) {
            modeloTabla.addRow(fila);
        }
    }
}