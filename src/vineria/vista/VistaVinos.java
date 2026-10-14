package vineria.vista;

import vineria.controlador.VinoControlador;
import vineria.modelo.Bodega;
import vineria.modelo.Vino;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/*
 * VistaVinos - VISTA (MVC).
 * Pantalla de ABM de vinos: formulario (nombre, tipo, varietal, año, precio,
 * stock y bodega), botones Nuevo / Guardar / Eliminar y una tabla con todos
 * los vinos.
 *
 * HERENCIA: "extends JFrame" -> es una ventana de Swing.
 * MVC: la Vista captura los datos y se los pasa a VinoControlador, que se
 * encarga de crear el objeto Vino correcto y guardarlo con el DAO.
 */
public class VistaVinos extends JFrame {

    // MVC: referencia al controlador de esta pantalla.
    private final VinoControlador controlador = new VinoControlador();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    // ENCAPSULAMIENTO: componentes privados del formulario.
    private final JTextField txtNombre = new JTextField(12);
    // El combo de tipo ofrece las tres subclases posibles de Vino.
    private final JComboBox<String> comboTipo = new JComboBox<>(new String[]{"TINTO", "BLANCO", "ESPUMANTE"});
    private final JTextField txtVarietal = new JTextField(10);
    private final JTextField txtAnio = new JTextField(5);
    private final JTextField txtPrecio = new JTextField(7);
    private final JTextField txtStock = new JTextField(5);
    private final JComboBox<Bodega> comboBodega = new JComboBox<>();

    // 0 = vino nuevo; distinto de 0 = id del vino que se está editando.
    private int idSeleccionado = 0;

    // Constructor: arma la interfaz, conecta los eventos y carga los datos.
    public VistaVinos() {
        setTitle("Gestión de Vinos");
        setSize(950, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Formulario: cada etiqueta seguida de su campo.
        JPanel form = new JPanel();
        form.add(new JLabel("Nombre:"));   form.add(txtNombre);
        form.add(new JLabel("Tipo:"));     form.add(comboTipo);
        form.add(new JLabel("Varietal:")); form.add(txtVarietal);
        form.add(new JLabel("Año:"));      form.add(txtAnio);
        form.add(new JLabel("Precio:"));   form.add(txtPrecio);
        form.add(new JLabel("Stock:"));    form.add(txtStock);
        form.add(new JLabel("Bodega:"));   form.add(comboBodega);

        // Botones
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JPanel botones = new JPanel();
        botones.add(btnNuevo); botones.add(btnGuardar); botones.add(btnEliminar);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(form, BorderLayout.CENTER);
        arriba.add(botones, BorderLayout.SOUTH);
        add(arriba, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Tipo", "Varietal", "Año", "Precio", "Stock", "IdBodega"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Eventos (lambdas): cada botón ejecuta un método de esta clase.
        btnNuevo.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());

        cargarBodegas();
        cargarTabla();
    }

    // Llena el combo de bodegas (se muestran con Bodega.toString()).
    private void cargarBodegas() {
        comboBodega.removeAllItems();
        for (Bodega b : controlador.listarBodegas()) comboBodega.addItem(b);
    }

    // Vacía la tabla y la vuelve a llenar con los vinos del controlador.
    // POLIMORFISMO: v es de tipo Vino, pero v.getTipo() devuelve "TINTO",
    // "BLANCO" o "ESPUMANTE" según la subclase real de cada objeto.
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Vino v : controlador.listar()) {
            modeloTabla.addRow(new Object[]{
                v.getId(), v.getNombre(), v.getTipo(), v.getVarietal(),
                v.getAnio(), v.getPrecio(), v.getStock(), v.getIdBodega()
            });
        }
    }

    // Botón Guardar: lee y valida el formulario, y le pasa los datos al
    // controlador (que decide si es alta o modificación según el id).
    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        String varietal = txtVarietal.getText().trim();
        Bodega bodega = (Bodega) comboBodega.getSelectedItem();

        // Validación 1: campos de texto obligatorios y bodega elegida.
        if (nombre.isEmpty() || varietal.isEmpty() || bodega == null) {
            JOptionPane.showMessageDialog(this, "Completá los campos y elegí una bodega.");
            return;
        }

        // Validación 2: año, precio y stock deben ser numéricos.
        // Si el texto no es un número, parseInt/parseDouble lanzan
        // NumberFormatException y se muestra un aviso.
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

        // MVC: Vista -> Controlador. La Vista no crea el Vino ni conoce el DAO.
        controlador.guardar(idSeleccionado, tipo, nombre, varietal, anio, precio, stock, bodega.getId());
        limpiar();
        cargarTabla();
    }

    // Botón Eliminar: borra el vino de la fila seleccionada.
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {   // ninguna fila seleccionada
            JOptionPane.showMessageDialog(this, "Seleccioná un vino de la tabla.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        controlador.eliminar(id);
        limpiar();
        cargarTabla();
    }

    // Copia los datos de la fila seleccionada al formulario para editarlos.
    // Los números se pasan a texto con toString() para los JTextField.
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

    // Busca en el combo la bodega con ese id y la deja seleccionada.
    private void seleccionarBodega(int idBodega) {
        for (int i = 0; i < comboBodega.getItemCount(); i++) {
            if (comboBodega.getItemAt(i).getId() == idBodega) {
                comboBodega.setSelectedIndex(i);
                return;
            }
        }
    }

    // Botón Nuevo: vacía el formulario y vuelve al modo "vino nuevo".
    private void limpiar() {
        idSeleccionado = 0;
        txtNombre.setText(""); txtVarietal.setText("");
        txtAnio.setText(""); txtPrecio.setText(""); txtStock.setText("");
        comboTipo.setSelectedIndex(0);
        tabla.clearSelection();
    }
}
