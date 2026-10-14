package vineria.vista;

import vineria.controlador.BodegaControlador;
import vineria.modelo.Bodega;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/*
 * VistaBodegas - VISTA (MVC).
 * Pantalla de ABM de bodegas: un formulario arriba (nombre, región, país),
 * botones Nuevo / Guardar / Eliminar y una tabla con todas las bodegas.
 *
 * HERENCIA: "extends JFrame" -> es una ventana de Swing.
 * MVC: la Vista solo muestra datos y captura acciones del usuario; todo lo
 * que toca la base pasa por BodegaControlador.
 */
public class VistaBodegas extends JFrame {

    // MVC: referencia al controlador de esta pantalla.
    private final BodegaControlador controlador = new BodegaControlador();
    // DefaultTableModel guarda las filas que muestra la JTable.
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    // ENCAPSULAMIENTO: componentes privados de la ventana.
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtRegion = new JTextField(15);
    private final JTextField txtPais = new JTextField(15);

    // Recuerda qué bodega se está editando. Lo usa el controlador para
    // decidir entre insertar (0) o actualizar (id > 0).
    private int idSeleccionado = 0; // 0 = bodega nueva

    // Constructor: arma la interfaz, conecta los eventos y carga la tabla.
    public VistaBodegas() {
        setTitle("Gestión de Bodegas");
        setSize(650, 450);
        setLocationRelativeTo(null);
        // DISPOSE_ON_CLOSE: cierra solo esta ventana, no todo el programa.
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

        // Panel superior: formulario + botones, ubicado al norte de la ventana.
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Región", "País"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);   // JScrollPane agrega barras de desplazamiento

        // Eventos
        // Cada botón llama a un método de esta clase (lambdas).
        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        // Al seleccionar una fila de la tabla, se cargan sus datos en el formulario.
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());

        cargarTabla();
    }

    // Vacía la tabla y la vuelve a llenar con las bodegas que trae el controlador.
    // MVC: la Vista recibe objetos Bodega (Modelo) y los muestra.
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Bodega b : controlador.listar()) {
            modeloTabla.addRow(new Object[]{b.getId(), b.getNombre(), b.getRegion(), b.getPais()});
        }
    }

    // Botón Guardar: valida el formulario, arma un objeto Bodega y se lo pasa
    // al controlador (que decide si es alta o modificación según el id).
    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String region = txtRegion.getText().trim();
        String pais = txtPais.getText().trim();

        // Validación: no se permiten campos vacíos.
        if (nombre.isEmpty() || region.isEmpty() || pais.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completá todos los campos.");
            return;
        }

        Bodega bodega = new Bodega(nombre, region, pais);
        bodega.setId(idSeleccionado);   // 0 si es nueva, o el id de la fila elegida
        controlador.guardar(bodega);    // MVC: Vista -> Controlador

        limpiarFormulario();
        cargarTabla();   // refresca la tabla para ver el cambio
    }

    // Botón Eliminar: toma el id de la fila seleccionada y pide borrarla.
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {   // -1 = no hay ninguna fila seleccionada
            JOptionPane.showMessageDialog(this, "Seleccioná una bodega de la tabla.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);   // columna 0 = ID
        controlador.eliminar(id);
        limpiarFormulario();
        cargarTabla();
    }

    // Copia los datos de la fila seleccionada al formulario para editarlos.
    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
        txtRegion.setText((String) modeloTabla.getValueAt(fila, 2));
        txtPais.setText((String) modeloTabla.getValueAt(fila, 3));
    }

    // Botón Nuevo: deja el formulario vacío y vuelve al modo "bodega nueva".
    private void limpiarFormulario() {
        idSeleccionado = 0;
        txtNombre.setText("");
        txtRegion.setText("");
        txtPais.setText("");
        tabla.clearSelection();
    }
}
