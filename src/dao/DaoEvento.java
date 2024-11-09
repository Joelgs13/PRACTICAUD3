package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bbdd.ConexionBBDD;
import model.ModeloDeporte;
import model.ModeloEvento;
import model.ModeloOlimpiada;

import java.sql.Connection;

/**
 * Clase que maneja las operaciones relacionadas con la tabla de Eventos en la base de datos.
 * Proporciona métodos para insertar, consultar y obtener eventos relacionados con olimpiadas y deportes.
 */
public class DaoEvento {
    private static Connection connection;

    /**
     * Inserta un nuevo evento en la base de datos.
     * 
     * @param nombreEvento El nombre del evento.
     * @param idOlimpiada El ID de la olimpiada asociada al evento.
     * @param idDeporte El ID del deporte asociado al evento.
     */
    public static void aniadirEvento(String nombreEvento, int idOlimpiada, int idDeporte) {
        connection = ConexionBBDD.getConnection();
        String insertar = "INSERT INTO Evento (nombre, id_olimpiada, id_deporte) VALUES (?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertar);
            pstmt.setString(1, nombreEvento);
            pstmt.setInt(2, idOlimpiada);
            pstmt.setInt(3, idDeporte);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea una lista de objetos `ModeloEvento` basados en el deporte y la olimpiada proporcionados.
     * 
     * @param idDeporte El ID del deporte.
     * @param idOlimpiada El ID de la olimpiada.
     * @return Una lista de eventos correspondientes al deporte y la olimpiada dados.
     */
    public static ArrayList<ModeloEvento> crearListaModelosPorDeporteYOlimpiada(int idDeporte, int idOlimpiada) {
        connection = ConexionBBDD.getConnection();
        String select = "SELECT nombre FROM Evento WHERE id_deporte=? AND id_olimpiada=?";
        ArrayList<ModeloEvento> lst = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setInt(1, idDeporte);
            pstmt.setInt(2, idOlimpiada);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                connection.commit();
                ModeloEvento evento = new ModeloEvento(
                        rs.getString("nombre"),
                        DaoDeporte.createDeporteModel(idDeporte),
                        DaoOlimpiada.createOlimpiadaModel(idOlimpiada)
                );
                if (!lst.contains(evento)) {
                    lst.add(evento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * Obtiene una lista de eventos a partir de una lista de IDs de eventos.
     * 
     * @param lstId Una lista de IDs de eventos.
     * @return Una lista de objetos `ModeloEvento` correspondientes a los IDs dados.
     */
    public static ArrayList<ModeloEvento> listaModelosPorId(ArrayList<String> lstId) {
        connection = ConexionBBDD.getConnection();
        ArrayList<ModeloEvento> lst = new ArrayList<>();
        String select = "SELECT nombre, id_deporte, id_olimpiada FROM Evento where id_evento=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(select);
            for (String id : lstId) {
                pstmt.setInt(1, Integer.parseInt(id));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    connection.commit();
                    ModeloEvento evento = new ModeloEvento(
                            rs.getString("nombre"),
                            DaoDeporte.createDeporteModel(rs.getInt("id_deporte")),
                            DaoOlimpiada.createOlimpiadaModel(rs.getInt("id_olimpiada"))
                    );
                    lst.add(evento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * Crea un objeto `ModeloEvento` a partir de un ID de evento.
     * 
     * @param id El ID del evento.
     * @return Un objeto `ModeloEvento` correspondiente al evento con el ID proporcionado.
     */
    public static ModeloEvento createById(int id) {
        connection = ConexionBBDD.getConnection();
        String consulta = "SELECT nombre, id_deporte, id_olimpiada FROM Evento WHERE id_evento=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                connection.commit();

                String nombreEvento = rs.getString("nombre");
                ModeloDeporte deporte = DaoDeporte.createDeporteModel(rs.getInt("id_deporte"));
                ModeloOlimpiada olimpiada = DaoOlimpiada.createOlimpiadaModel(rs.getInt("id_olimpiada"));
                
                return new ModeloEvento(nombreEvento, deporte, olimpiada);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Obtiene el ID de un evento a partir de su nombre, ID de olimpiada e ID de deporte.
     * 
     * @param nombreEvento El nombre del evento.
     * @param idOlimpiada El ID de la olimpiada asociada.
     * @param idDeporte El ID del deporte asociado.
     * @return El ID del evento si existe, o null si no se encuentra.
     */
    public static String conseguirIdEvento(String nombreEvento, int idOlimpiada, int idDeporte) {
        connection = ConexionBBDD.getConnection();
        String select = "SELECT id_evento FROM Evento WHERE nombre=? AND id_olimpiada=? AND id_deporte=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setString(1, nombreEvento);
            pstmt.setInt(2, idOlimpiada);
            pstmt.setInt(3, idDeporte);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id_evento");
                connection.commit();
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene una lista de objetos `ModeloEvento` basados en el deporte y la olimpiada proporcionados.
     * 
     * @param idDeporte El ID del deporte.
     * @param idOlimpiada El ID de la olimpiada.
     * @return Una lista de eventos correspondientes al deporte y la olimpiada dados.
     */
    public static ArrayList<ModeloEvento> modelosByDeporteOlimpiada(int idDeporte, int idOlimpiada) {
        ArrayList<ModeloEvento> lst = new ArrayList<>();
        
        String select = "SELECT nombre FROM Evento WHERE id_deporte=? AND id_olimpiada=?";
        
        try (Connection con = ConexionBBDD.getConnection();
             PreparedStatement pstmt = con.prepareStatement(select)) {
            
            pstmt.setInt(1, idDeporte);
            pstmt.setInt(2, idOlimpiada);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Crear un objeto ModeloEvento y añadirlo a la lista
                    ModeloEvento evento = new ModeloEvento(
                            rs.getString("nombre"),
                            DaoDeporte.createDeporteModel(idDeporte),
                            DaoOlimpiada.createOlimpiadaModel(idOlimpiada)
                    );
                    
                    // Añadir el evento solo si no está ya en la lista
                    if (!lst.contains(evento)) {
                        lst.add(evento);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lst;
    }
}
