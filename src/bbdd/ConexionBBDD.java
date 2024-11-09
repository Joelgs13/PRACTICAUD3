package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase {@code ConexionBBDD} gestiona la conexión a la base de datos MySQL
 * para la aplicación. Proporciona métodos para establecer la conexión, 
 * obtenerla y cerrarla de manera segura.
 * 
 * <p>Esta clase se conecta a una base de datos MySQL utilizando JDBC, donde
 * los parámetros de conexión (URL, usuario, contraseña) son especificados
 * dentro de la clase.</p>
 * 
 * <p>El controlador JDBC se carga dinámicamente al iniciar la conexión.</p>
 * 
 * @author Joel
 */
public class ConexionBBDD {
    private static Connection connection;

    // Parámetros de conexión
    private static final String DB_URL = "jdbc:mysql://localhost:33066/olimpiadas";
    private static final String USER = "joel";
    private static final String PASSWORD = "1234";

    /**
     * Constructor que establece la conexión con la base de datos MySQL.
     * 
     * <p>Este constructor carga el driver JDBC y establece una conexión con
     * la base de datos usando los parámetros proporcionados. Si la conexión
     * falla, se captura una excepción {@code SQLException} o 
     * {@code ClassNotFoundException} y se imprime un mensaje de error.</p>
     */
    public ConexionBBDD() {
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver JDBC: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la conexión a la base de datos.
     * 
     * @return La conexión activa a la base de datos.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos.
     * 
     * <p>Este método cierra la conexión activa. Si la conexión ya está cerrada,
     * puede generar una excepción {@code SQLException}.</p>
     * 
     * @return La conexión cerrada.
     * @throws SQLException Si ocurre un error al intentar cerrar la conexión.
     */
    public Connection CloseConexion() throws SQLException {
        connection.close();
        return connection;
    }

    /*
    // Método para probar la conexión (comentado para desactivarlo)
    public static void main(String[] args) {
        ConexionBBDD a = new ConexionBBDD();
    }
    */
}
