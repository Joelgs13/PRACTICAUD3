package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bbdd.ConexionBBDD;
import model.ModeloDeporte;
import model.ModeloEvento;
import model.ModeloOlimpiada;

import java.sql.Connection;

public class DaoEvento {
    private static Connection connection;

    public static void aniadirEvento(String nombreEvento,int idOlimpiada,int idDeporte) {
		connection=ConexionBBDD.getConnection();
		String insertar="INSERT INTO Evento (nombre,id_olimpiada,id_deporte) VALUES (?,?,?)";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(insertar);
			pstmt.setString(1,nombreEvento);
			pstmt.setInt(2, idOlimpiada);
			pstmt.setInt(3, idDeporte);
			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
public static ModeloEvento createById(int id) {
    connection = ConexionBBDD.getConnection();
    String consulta = "SELECT nombre, id_deporte, id_olimpiada FROM Evento WHERE id_evento=?";
    
    try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            connection.commit();

            // Crear instancias para los objetos asociados antes de construir ModeloEvento
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


	public static String conseguirIdEvento(String nombreEvento,int idOlimpiada,int idDeporte) {
		connection=ConexionBBDD.getConnection();
		String select="SELECT id_evento FROM Evento WHERE nombre=? AND id_olimpiada=? AND id_deporte=?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			pstmt.setString(1,nombreEvento);
			pstmt.setInt(2, idOlimpiada);
			pstmt.setInt(3, idDeporte);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_evento");
				connection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
