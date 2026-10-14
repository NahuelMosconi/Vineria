package vineria.vista;

import vineria.controlador.UsuarioControlador;
import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame {

    private final UsuarioControlador controlador = new UsuarioControlador();
    private final JTextField txtUsuario = new JTextField(15);
    private final JPasswordField txtContrasenia = new JPasswordField(15);

    public VistaLogin() {
        setTitle("Ingreso - Vinería");
        setSize(360, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Usuario:"));
        add(txtUsuario);
        add(new JLabel("Contraseña:"));
        add(txtContrasenia);

        JButton btnIngresar = new JButton("Ingresar");
        add(new JLabel());
        add(btnIngresar);

        btnIngresar.addActionListener(e -> ingresar());
        getRootPane().setDefaultButton(btnIngresar);   // Enter = Ingresar
    }

    private void ingresar() {
        String usuario = txtUsuario.getText().trim();
        String contrasenia = new String(txtContrasenia.getPassword());

        if (controlador.login(usuario, contrasenia)) {
            new VistaPrincipal().setVisible(true);
            dispose();   // cierra el login
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }
}