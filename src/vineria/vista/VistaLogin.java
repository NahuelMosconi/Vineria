package vineria.vista;

import vineria.controlador.UsuarioControlador;
import javax.swing.*;
import java.awt.*;

/*
 * VistaLogin - VISTA (MVC).
 * Ventana de ingreso: pide usuario y contraseña. Es la primera ventana que
 * se abre (desde main).
 *
 * HERENCIA: "extends JFrame" -> hereda todo el comportamiento de una
 * ventana de Swing (título, tamaño, setVisible, add...).
 * MVC: la Vista solo muestra y captura datos; la validación la resuelve
 * el UsuarioControlador.
 */
public class VistaLogin extends JFrame {

    // MVC: la Vista conoce a su controlador, nunca al DAO directamente.
    private final UsuarioControlador controlador = new UsuarioControlador();
    // ENCAPSULAMIENTO: los componentes son privados de esta ventana.
    private final JTextField txtUsuario = new JTextField(15);
    private final JPasswordField txtContrasenia = new JPasswordField(15);

    // Constructor: arma la ventana y conecta el botón con su acción.
    public VistaLogin() {
        setTitle("Ingreso - Vinería");
        setSize(360, 180);
        setLocationRelativeTo(null);   // centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));   // grilla de 3 filas x 2 columnas

        add(new JLabel("Usuario:"));
        add(txtUsuario);
        add(new JLabel("Contraseña:"));
        add(txtContrasenia);

        JButton btnIngresar = new JButton("Ingresar");
        add(new JLabel());   // celda vacía para alinear el botón a la derecha
        add(btnIngresar);

        // Evento: al hacer clic se ejecuta ingresar() (expresión lambda).
        btnIngresar.addActionListener(e -> ingresar());
        getRootPane().setDefaultButton(btnIngresar);   // Enter = Ingresar
    }

    // Toma los datos del formulario y le pide al controlador que los valide.
    // Si son correctos abre el menú principal; si no, muestra un aviso.
    private void ingresar() {
        String usuario = txtUsuario.getText().trim();
        // getPassword() devuelve char[] (más seguro que String); se convierte.
        String contrasenia = new String(txtContrasenia.getPassword());

        // MVC: Vista -> Controlador -> DAO.
        if (controlador.login(usuario, contrasenia)) {
            new VistaPrincipal().setVisible(true);
            dispose();   // cierra el login
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }
}
