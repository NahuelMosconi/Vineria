package vineria.vista;

import vineria.controlador.BodegaControlador;
import vineria.modelo.Bodega;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaBodegas extends JFrame {

    private final BodegaControlador controlador = new BodegaControlador();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtRegion = new JTextField(15);
    private final JTextField txtPais = new JTextField(15);

    private int idSeleccionado = 0; // 0 = bodega nueva

    public VistaBodegas() {
        setTitle("Gestión de Bodegas");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelForm = new JPanel();
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Región:"));
        panelForm.add(txtRegion);
        panelForm.add(new JLabel("País:"));
        panelForm.add(txtPais);

        // Botones
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Región", "País"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Eventos
        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());

        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Bodega b : controlador.listar()) {
            modeloTabla.addRow(new Object[]{b.getId(), b.getNombre(), b.getRegion(), b.getPais()});
        }
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String region = txtRegion.getText().trim();
        String pais = txtPais.getText().trim();

        if (nombre.isEmpty() || region.isEmpty() || pais.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completá todos los campos.");
            return;
        }

        Bodega bodega = new Bodega(nombre, region, pais);
        bodega.setId(idSeleccionado);
        controlador.guardar(bodega);

        limpiarFormulario();
        cargarTabla();
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná una bodega de la tabla.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        controlador.eliminar(id);
        limpiarFormulario();
        cargarTabla();
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
        txtRegion.setText((String) modeloTabla.getValueAt(fila, 2));
        txtPais.setText((String) modeloTabla.getValueAt(fila, 3));
    }

    private void limpiarFormulario() {
        idSeleccionado = 0;
        txtNombre.setText("");
        txtRegion.setText("");
        txtPais.setText("");
        tabla.clearSelection();
    }
}