package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bbdd.ConexionBBDD;
import model.ModeloEquipo;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que maneja las operaciones relacionadas con la tabla de Equipos en la base de datos.
 * Proporciona métodos para insertar, consultar y obtener información sobre equipos.
 */
public class DaoEquipo {
    private static Connection connection;

    /**
     * Inserta un nuevo equipo en la base de datos.
     * 
     * @param nombre El nombre del equipo.
     * @param iniciales Las iniciales del equipo.
     */
    public static void aniadirEquipo(String nombre, String iniciales) {
        connection = ConexionBBDD.getConnection();
        String insertar = "INSERT INTO Equipo (nombre, iniciales) VALUES (?,?)";
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertar);
            pstmt.setString(1, nombre);
            pstmt.setString(2, iniciales);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de un equipo a partir de su nombre y sus iniciales.
     * 
     * @param nombre El nombre del equipo.
     * @param iniciales Las iniciales del equipo.
     * @return El ID del equipo si existe, o null si no se encuentra.
     */
    public static String conseguirIdEquipo(String nombre, String iniciales) {
        connection = ConexionBBDD.getConnection();
        String select = "SELECT id_equipo FROM Equipo WHERE nombre=? AND iniciales=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setString(1, nombre);
            pstmt.setString(2, iniciales);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id_equipo");
                connection.commit();
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Crea un objeto `ModeloEquipo` a partir del ID de un equipo.
     * 
     * @param id El ID del equipo.
     * @return Un objeto `ModeloEquipo` correspondiente al equipo con el ID proporcionado.
     */
    public static ModeloEquipo createEquipoModel(int id) {
        connection = ConexionBBDD.getConnection();
        String consulta = "SELECT nombre, iniciales FROM Equipo WHERE id_equipo=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                connection.commit();
                String nombre = rs.getString("nombre");
                String iniciales = rs.getString("iniciales");

                return new ModeloEquipo(nombre, iniciales);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
