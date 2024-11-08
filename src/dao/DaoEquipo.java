package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bbdd.ConexionBBDD;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoEquipo {
    private static Connection connection;

    public static void aniadirEquipo(String nombre, String iniciales) {
		connection=ConexionBBDD.getConnection();
		String insertar="INSERT INTO Equipo (nombre,iniciales) VALUES (?,?)";
		
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(insertar);
			pstmt.setString(1,nombre);
			pstmt.setString(2,iniciales);
			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String conseguirIdEquipo(String nombre, String iniciales) {
		connection=ConexionBBDD.getConnection();
		String select="SELECT id_equipo FROM Equipo WHERE nombre=? AND iniciales=?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			pstmt.setString(1,nombre);
			pstmt.setString(2, iniciales);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_equipo");
				connection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
