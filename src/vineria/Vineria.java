/*
 * Vineria.java - PUNTO DE ENTRADA de la aplicación.
 *
 * Arquitectura general (MVC):
 *   - Modelo      (vineria.modelo):      clases que representan los datos (Vino, Bodega...).
 *   - Vista       (vineria.vista):       ventanas Swing que ve el usuario.
 *   - Controlador (vineria.controlador): intermediario entre la Vista y el acceso a datos.
 *   - DAO         (vineria.dao):         clases que hablan con la base SQLite.
 *
 * Flujo: Vista -> Controlador -> DAO -> Base de datos, y los datos vuelven
 * como objetos del Modelo.
 */
package vineria;

import com.formdev.flatlaf.FlatDarkLaf;
import vineria.dao.ConexionBD;
import vineria.vista.VistaLogin;

// Método main: lo primero que se ejecuta al iniciar el programa.
// (Está escrito como "archivo fuente compacto": el main va sin una clase
// explícita alrededor; Java la genera automáticamente).
public static void main(String[] args) {
    // 1) Aplica el tema visual oscuro FlatLaf a todos los componentes Swing.
    FlatDarkLaf.setup();
    // 2) Crea las tablas y los datos de ejemplo en SQLite si todavía no existen.
    ConexionBD.inicializar();
    // 3) Muestra la primera Vista (MVC): la ventana de login.
    new VistaLogin().setVisible(true);
}
