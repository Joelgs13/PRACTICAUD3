package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bbdd.ConexionBBDD;
import model.ModeloDeporte;

public class DaoDeporte {
    private static Connection connection;

public static ArrayList<ModeloDeporte> listaDeportesPorOlimpiada(int idOlimpiada){
		connection=ConexionBBDD.getConnection();
		ArrayList<ModeloDeporte>lst=new ArrayList<ModeloDeporte>();
		String select="SELECT id_deporte FROM Evento WHERE id_olimpiada=?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			pstmt.setInt(1, idOlimpiada);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				connection.commit();
				ModeloDeporte deporte=DaoDeporte.createDeporteModel(rs.getInt("id_deporte"));
				if(!lst.contains(deporte)) {
					lst.add(deporte);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

    public static int getDeporteId(String nombreDeporte) throws SQLException {
        String query = "SELECT id_deporte FROM Deporte WHERE nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nombreDeporte);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_deporte");
            } else {
                // If not found, insert it
                String insert = "INSERT INTO Deporte (nombre) VALUES (?)";
                try (PreparedStatement insertPstmt = connection.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertPstmt.setString(1, nombreDeporte);
                    insertPstmt.executeUpdate();
                    ResultSet keys = insertPstmt.getGeneratedKeys();
                    if (keys.next()) return keys.getInt(1);
                }
            }
        }
        return -1; // Indicate an error if ID cannot be retrieved or inserted
    }

    public static void aniadirDeporte(String nombreDeporte) {
		connection=ConexionBBDD.getConnection();
		String insertar="INSERT INTO Deporte (nombre) VALUES (?)";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(insertar);
			pstmt.setString(1, nombreDeporte);
			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Conseguir id deporte.
	 *
	 * @param nombre the nombre
	 * @return the string
	 */
	public static String conseguirIdDeporte(String nombre) {
		connection=ConexionBBDD.getConnection();
		String select="SELECT id_deporte FROM Deporte WHERE nombre=?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			pstmt.setString(1,nombre);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_deporte");
				connection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

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
