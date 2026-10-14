package vineria;

import com.formdev.flatlaf.FlatDarkLaf;
import vineria.dao.ConexionBD;
import vineria.vista.VistaLogin;

public static void main(String[] args) {
    FlatDarkLaf.setup();
    ConexionBD.inicializar();
    new VistaLogin().setVisible(true);
}

