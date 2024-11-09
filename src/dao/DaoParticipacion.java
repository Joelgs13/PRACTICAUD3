package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bbdd.ConexionBBDD;
import model.ModeloDeportista;
import model.ModeloEquipo;
import model.ModeloEvento;
import model.ModeloParticipacion;

/**
 * Clase que maneja las operaciones relacionadas con la tabla de participaciones en la base de datos.
 * Proporciona métodos para consultar, insertar, actualizar y eliminar participaciones.
 */
public class DaoParticipacion {
    private static Connection connection;

    /**
     * Verifica si existe una participación en la base de datos con los IDs proporcionados.
     * 
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     * @return true si existe una participación, false si no existe.
     */
    public static boolean existeIdParticipacion(int idDeportista, int idEvento) {
        connection = ConexionBBDD.getConnection();
        String buscar = "SELECT id_equipo FROM Participacion WHERE id_deportista=? AND id_evento=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(buscar);
            pstmt.setInt(1, idDeportista);
            pstmt.setInt(2, idEvento);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina una participación de la base de datos.
     * 
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     */
    public static void eliminarParticipacion(int idDeportista, int idEvento) {
        connection = ConexionBBDD.getConnection();
        String delete = "DELETE FROM Participacion WHERE id_deportista=? AND id_evento=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(delete);
            pstmt.setInt(1, idDeportista);
            pstmt.setInt(2, idEvento);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la medalla de una participación en la base de datos.
     * 
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     * @param nuevaMedalla La nueva medalla obtenida.
     */
    public static void editMedal(int idDeportista, int idEvento, String nuevaMedalla) {
        connection = ConexionBBDD.getConnection();
        String update = "UPDATE Participacion SET medalla=? WHERE id_deportista=? AND id_evento=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(update);
            pstmt.setString(1, nuevaMedalla);
            pstmt.setInt(2, idDeportista);
            pstmt.setInt(3, idEvento);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene los IDs de los deportistas que participan en un evento específico.
     * 
     * @param idEvento El ID del evento.
     * @return Una lista con los IDs de los deportistas.
     */
    public static ArrayList<String> darIdDeportista(int idEvento) {
        connection = ConexionBBDD.getConnection();
        String select = "SELECT id_deportista FROM Participacion WHERE id_evento=?";
        ArrayList<String> lst = new ArrayList<String>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, idEvento);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                connection.commit();
                lst.add(rs.getString("id_deportista"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * Crea un objeto ModeloParticipacion a partir de los IDs proporcionados.
     * 
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     * @return Un objeto ModeloParticipacion que representa la participación, o null si no existe.
     */
    public static ModeloParticipacion crearModeloParticipacion(int idDeportista, int idEvento) {
        connection = ConexionBBDD.getConnection();
        String consulta = "SELECT id_equipo, edad, medalla FROM Participacion WHERE id_deportista=? AND id_evento=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
            pstmt.setInt(1, idDeportista);
            pstmt.setInt(2, idEvento);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                connection.commit();
                
                // Crear instancia de ModeloParticipacion con los datos obtenidos
                ModeloDeportista deportista = DaoDeportista.createDeportistaModel(String.valueOf(idDeportista));
                ModeloEvento evento = DaoEvento.createById(idEvento);
                ModeloEquipo equipo = DaoEquipo.createEquipoModel(rs.getInt("id_equipo"));
                
                return new ModeloParticipacion(deportista, evento, equipo, rs.getInt("edad"), rs.getString("medalla"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Obtiene los IDs de los eventos en los que un deportista ha participado.
     * 
     * @param idDeportista El ID del deportista.
     * @return Una lista con los IDs de los eventos en los que el deportista ha participado.
     */
    public static ArrayList<String> getIdEvento(int idDeportista) {
        ArrayList<String> listaEventos = new ArrayList<>();
        connection = ConexionBBDD.getConnection();
        String consulta = "SELECT id_evento FROM Participacion WHERE id_deportista=?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
            pstmt.setInt(1, idDeportista);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                listaEventos.add(rs.getString("id_evento"));
            }
            connection.commit();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return listaEventos;
    }

    /**
     * Inserta una nueva participación en la base de datos.
     * 
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     * @param idEquipo El ID del equipo.
     * @param edad La edad del deportista en el evento.
     * @param medalla La medalla obtenida en el evento.
     */
    public static void aniadirParticipacion(int idDeportista, int idEvento, int idEquipo, int edad, String medalla) {
        connection = ConexionBBDD.getConnection();
        String insertar = "INSERT INTO Participacion (id_deportista, id_evento, id_equipo, edad, medalla) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertar);
            pstmt.setInt(1, idDeportista);
            pstmt.setInt(2, idEvento);
            pstmt.setInt(3, idEquipo);
            pstmt.setInt(4, edad);
            pstmt.setString(5, medalla);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
