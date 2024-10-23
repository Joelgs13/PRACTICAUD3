package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBBDD {
    private Connection connection;
    
    // Parámetros de conexión
    private static final String DB_URL = "jdbc:mysql://localhost:33066";
    private static final String USER = "joel";
    private static final String PASSWORD = "1234";

    // Constructor para inicializar la conexión
    public ConexionBBDD() {
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver JDBC: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        return this.connection;
    }

    // Método para cerrar la conexión
    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                //System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    /*public static void main(String[] args) {
        ConexionBBDD a = new ConexionBBDD();
    }*/
}    
