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

public class DaoParticipacion {
    private static Connection connection;
    public static boolean existeIdParticipacion(int idDeportista,int idEvento) {
		connection=ConexionBBDD.getConnection();
		String buscar="SELECT id_equipo FROM Participacion WHERE id_deportista=? AND id_evento=?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(buscar);
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
	

	public static void aniadirParticipacion(int idDeportista,int idEvento,int idEquipo,int edad,String medalla) {
		connection=ConexionBBDD.getConnection();
		String insertar="INSERT INTO Participacion (id_deportista,id_evento,id_equipo,edad,medalla) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(insertar);
			pstmt.setInt(1,idDeportista);
			pstmt.setInt(2, idEvento);
			pstmt.setInt(3,idEquipo);
			pstmt.setInt(4, edad);
			pstmt.setString(5, medalla);
			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<String> getIdDeportista(int idEvento) {
		connection=ConexionBBDD.getConnection();
		String select="SELECT id_deportista FROM Participacion WHERE id_evento=?";
		ArrayList<String> lst=new ArrayList<String>();
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
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



}
