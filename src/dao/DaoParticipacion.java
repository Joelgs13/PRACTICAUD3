package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bbdd.ConexionBBDD;

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
}
