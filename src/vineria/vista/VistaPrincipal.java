package vineria.vista;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {

    public VistaPrincipal() {
        setTitle("Vinería - Menú Principal");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnBodegas = new JButton("Gestión de Bodegas");
        JButton btnVinos = new JButton("Gestión de Vinos");
        JButton btnConsulta = new JButton("Consulta de Vinos");

        btnBodegas.addActionListener(e -> new VistaBodegas().setVisible(true));
        btnVinos.addActionListener(e -> new VistaVinos().setVisible(true));
        btnConsulta.addActionListener(e -> new VistaConsulta().setVisible(true));

        add(btnBodegas);
        add(btnVinos);
        add(btnConsulta);
    }
}