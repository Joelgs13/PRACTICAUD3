package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bbdd.ConexionBBDD;
import model.ModeloDeporte;

/**
 * Clase que gestiona las operaciones relacionadas con la tabla Deporte en la base de datos.
 * Proporciona métodos para insertar, consultar y obtener información sobre los deportes.
 */
public class DaoDeporte {
    private static Connection connection;

    /**
     * Obtiene una lista de deportes asociados a una olimpiada específica.
     * 
     * @param idOlimpiada El ID de la olimpiada.
     * @return Una lista de objetos `ModeloDeporte` asociados a la olimpiada.
     */
    public static ArrayList<ModeloDeporte> listaDeportesPorOlimpiada(int idOlimpiada) {
        connection = ConexionBBDD.getConnection();
        ArrayList<ModeloDeporte> lst = new ArrayList<>();
        String select = "SELECT id_deporte FROM Evento WHERE id_olimpiada=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, idOlimpiada);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                connection.commit();
                ModeloDeporte deporte = DaoDeporte.createDeporteModel(rs.getInt("id_deporte"));
                if (!lst.contains(deporte)) {
                    lst.add(deporte);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * Obtiene el ID de un deporte a partir de su nombre. Si el deporte no existe, lo inserta en la base de datos.
     * 
     * @param nombreDeporte El nombre del deporte.
     * @return El ID del deporte.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public static int getDeporteId(String nombreDeporte) throws SQLException {
        String query = "SELECT id_deporte FROM Deporte WHERE nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nombreDeporte);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_deporte");
            } else {
                // Si no se encuentra el deporte, lo inserta
                String insert = "INSERT INTO Deporte (nombre) VALUES (?)";
                try (PreparedStatement insertPstmt = connection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertPstmt.setString(1, nombreDeporte);
                    insertPstmt.executeUpdate();
                    ResultSet keys = insertPstmt.getGeneratedKeys();
                    if (keys.next()) return keys.getInt(1);
                }
            }
        }
        return -1; // Indica un error si no se pudo obtener o insertar el ID
    }

    /**
     * Inserta un nuevo deporte en la base de datos.
     * 
     * @param nombreDeporte El nombre del deporte.
     */
    public static void aniadirDeporte(String nombreDeporte) {
        connection = ConexionBBDD.getConnection();
        String insertar = "INSERT INTO Deporte (nombre) VALUES (?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertar);
            pstmt.setString(1, nombreDeporte);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de un deporte a partir de su nombre.
     * 
     * @param nombre El nombre del deporte.
     * @return El ID del deporte, o null si no se encuentra.
     */
    public static String conseguirIdDeporte(String nombre) {
        connection = ConexionBBDD.getConnection();
        String select = "SELECT id_deporte FROM Deporte WHERE nombre=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id_deporte");
                connection.commit();
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Crea un objeto `ModeloDeporte` a partir del ID de un deporte.
     * 
     * @param id El ID del deporte.
     * @return Un objeto `ModeloDeporte` correspondiente al deporte con el ID proporcionado,
     *         o null si no se encuentra en la base de datos.
     */
    public static ModeloDeporte createDeporteModel(int id) {
        connection = ConexionBBDD.getConnection();
        String consulta = "SELECT nombre FROM Deporte WHERE id_deporte=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                connection.commit();
                String nombreDeporte = rs.getString("nombre");
                return new ModeloDeporte(nombreDeporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
