package vineria.vista;

import javax.swing.*;
import java.awt.*;

/*
 * VistaPrincipal - VISTA (MVC).
 * Menú principal que aparece después del login. Tiene un botón por cada
 * pantalla del sistema: bodegas, vinos y consulta.
 *
 * HERENCIA: "extends JFrame" -> es una ventana de Swing.
 * MVC: esta Vista solo navega entre ventanas; no tiene controlador propio
 * porque no maneja datos.
 */
public class VistaPrincipal extends JFrame {

    public VistaPrincipal() {
        setTitle("Vinería - Menú Principal");
        setSize(400, 250);
        setLocationRelativeTo(null);   // centra la ventana
        // EXIT_ON_CLOSE: cerrar el menú principal termina el programa.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));   // 3 botones en columna

        JButton btnBodegas = new JButton("Gestión de Bodegas");
        JButton btnVinos = new JButton("Gestión de Vinos");
        JButton btnConsulta = new JButton("Consulta de Vinos");

        // Eventos: cada botón abre una ventana nueva (lambdas).
        btnBodegas.addActionListener(e -> new VistaBodegas().setVisible(true));
        btnVinos.addActionListener(e -> new VistaVinos().setVisible(true));
        btnConsulta.addActionListener(e -> new VistaConsulta().setVisible(true));

        add(btnBodegas);
        add(btnVinos);
        add(btnConsulta);
    }
}
