# Sistema de Gestión de Vinería

Aplicación de escritorio para administrar una vinería: permite gestionar
bodegas y sus vinos (stock, precios, tipos), con login de usuario, siguiendo
el patrón de arquitectura MVC.

## Características
- Login de usuario.
- ABM (Alta, Baja, Modificación) de bodegas.
- ABM de vinos, clasificados por tipo (tinto, blanco, espumante).
- Consulta de vinos por bodega (relación maestro-detalle).
- Interfaz moderna con FlatLaf.

## Tecnologías
- Lenguaje: Java
- Interfaz gráfica: Swing (look & feel FlatLaf)
- Base de datos: SQLite (embebida, vía JDBC)
- IDE: Apache NetBeans
- Arquitectura: MVC (Modelo - Vista - Controlador)

## Cómo ejecutar
1. Abrir el proyecto en NetBeans.
2. Ejecutar (F6). La base `vineria.db` se crea sola la primera vez, con datos de ejemplo.
3. Ingresar con:
   - Usuario: admin
   - Contraseña: admin

## Estructura del proyecto
- `vineria` → clase principal (inicia la aplicación).
- `vineria.modelo` → dominio: Bodega, Vino (abstracta) y sus subclases
  VinoTinto, VinoBlanco, VinoEspumante.
- `vineria.dao` → acceso a datos: ConexionBD, BodegaDAO, VinoDAO, UsuarioDAO.
- `vineria.controlador` → intermediarios: BodegaControlador, VinoControlador,
  UsuarioControlador.
- `vineria.vista` → ventanas Swing: VistaLogin, VistaPrincipal, VistaBodegas,
  VistaVinos, VistaConsulta.

## Base de datos
Dos tablas relacionadas:
- bodegas (id, nombre, region, pais)
- vinos (id, nombre, tipo, varietal, anio, precio, stock, id_bodega)

Un vino pertenece a una bodega (vinos.id_bodega → bodegas.id). La tabla
usuarios guarda las credenciales de acceso.

## Conceptos aplicados
- Encapsulamiento: atributos privados con getters/setters en el modelo.
- Herencia: Vino es la clase padre; VinoTinto, VinoBlanco y VinoEspumante heredan.
- Polimorfismo: cada tipo de vino resuelve a su manera getTipo() y
  getTemperaturaServicio().
- Abstracción: Vino es una clase abstracta (no se instancia directamente).
- MVC: separación en modelo, vista y controlador.
- Patrón DAO: cada tabla tiene su objeto de acceso a datos con el SQL.

## Autor
Nahuel Mosconi
