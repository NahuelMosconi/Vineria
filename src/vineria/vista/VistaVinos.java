package vineria.vista;

import vineria.controlador.VinoControlador;
import vineria.modelo.Bodega;
import vineria.modelo.Vino;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaVinos extends JFrame {

    private final VinoControlador controlador = new VinoControlador();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private final JTextField txtNombre = new JTextField(12);
    private final JComboBox<String> comboTipo = new JComboBox<>(new String[]{"TINTO", "BLANCO", "ESPUMANTE"});
    private final JTextField txtVarietal = new JTextField(10);
    private final JTextField txtAnio = new JTextField(5);
    private final JTextField txtPrecio = new JTextField(7);
    private final JTextField txtStock = new JTextField(5);
    private final JComboBox<Bodega> comboBodega = new JComboBox<>();

    private int idSeleccionado = 0;

    public VistaVinos() {
        setTitle("Gestión de Vinos");
        setSize(950, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.add(new JLabel("Nombre:"));   form.add(txtNombre);
        form.add(new JLabel("Tipo:"));     form.add(comboTipo);
        form.add(new JLabel("Varietal:")); form.add(txtVarietal);
        form.add(new JLabel("Año:"));      form.add(txtAnio);
        form.add(new JLabel("Precio:"));   form.add(txtPrecio);
        form.add(new JLabel("Stock:"));    form.add(txtStock);
        form.add(new JLabel("Bodega:"));   form.add(comboBodega);

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JPanel botones = new JPanel();
        botones.add(btnNuevo); botones.add(btnGuardar); botones.add(btnEliminar);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(form, BorderLayout.CENTER);
        arriba.add(botones, BorderLayout.SOUTH);
        add(arriba, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Tipo", "Varietal", "Año", "Precio", "Stock", "IdBodega"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnNuevo.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());

        cargarBodegas();
        cargarTabla();
    }

    private void cargarBodegas() {
        comboBodega.removeAllItems();
        for (Bodega b : controlador.listarBodegas()) comboBodega.addItem(b);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Vino v : controlador.listar()) {
            modeloTabla.addRow(new Object[]{
                v.getId(), v.getNombre(), v.getTipo(), v.getVarietal(),
                v.getAnio(), v.getPrecio(), v.getStock(), v.getIdBodega()
            });
        }
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        String varietal = txtVarietal.getText().trim();
        Bodega bodega = (Bodega) comboBodega.getSelectedItem();

        if (nombre.isEmpty() || varietal.isEmpty() || bodega == null) {
            JOptionPane.showMessageDialog(this, "Completá los campos y elegí una bodega.");
            return;
        }

        int anio, stock;
        double precio;
        try {
            anio = Integer.parseInt(txtAnio.getText().trim());
            precio = Double.parseDouble(txtPrecio.getText().trim());
            stock = Integer.parseInt(txtStock.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año, precio y stock deben ser números.");
            return;
        }

        controlador.guardar(idSeleccionado, tipo, nombre, varietal, anio, precio, stock, bodega.getId());
        limpiar();
        cargarTabla();
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un vino de la tabla.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        controlador.eliminar(id);
        limpiar();
        cargarTabla();
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
        comboTipo.setSelectedItem(modeloTabla.getValueAt(fila, 2));
        txtVarietal.setText((String) modeloTabla.getValueAt(fila, 3));
        txtAnio.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtPrecio.setText(modeloTabla.getValueAt(fila, 5).toString());
        txtStock.setText(modeloTabla.getValueAt(fila, 6).toString());
        seleccionarBodega((int) modeloTabla.getValueAt(fila, 7));
    }

    private void seleccionarBodega(int idBodega) {
        for (int i = 0; i < comboBodega.getItemCount(); i++) {
            if (comboBodega.getItemAt(i).getId() == idBodega) {
                comboBodega.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtNombre.setText(""); txtVarietal.setText("");
        txtAnio.setText(""); txtPrecio.setText(""); txtStock.setText("");
        comboTipo.setSelectedIndex(0);
        tabla.clearSelection();
    }
}