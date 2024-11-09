package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBBDD {
    private static Connection connection;
    
    // Parámetros de conexión
    private static final String DB_URL = "jdbc:mysql://localhost:33066/olimpiadas";
    private static final String USER = "joel";
    private static final String PASSWORD = "1234";

    // Constructor para inicializar la conexión
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

    // Método para obtener la conexión
    public static Connection getConnection() {
		return connection;
	}

    // Método para cerrar la conexión
    public Connection CloseConexion() throws SQLException{
		connection.close();
        return connection;
    }
    /*public static void main(String[] args) {
        ConexionBBDD a = new ConexionBBDD();
    }*/
}    
