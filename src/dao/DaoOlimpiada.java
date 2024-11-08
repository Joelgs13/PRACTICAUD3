package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bbdd.ConexionBBDD;

import java.sql.Connection;

public class DaoOlimpiada {
    private static Connection connection;
    public static void aniadirOlimpiada(String nombre, int anio,String temporada,String ciudad) {
		connection=ConexionBBDD.getConnection();
		String insertar="INSERT INTO Olimpiada (nombre,anio,temporada,ciudad) VALUES (?,?,?,?)";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(insertar);
			pstmt.setString(1,nombre);
			pstmt.setInt(2, anio);
			pstmt.setString(3,temporada);
			pstmt.setString(4,ciudad);
			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String conseguirIdOlimpiada(String nombre, int anio,String temporada,String ciudad) {
		connection=ConexionBBDD.getConnection();
		String select="SELECT id_olimpiada FROM Olimpiada WHERE nombre=? AND anio=? AND temporada=? AND ciudad=?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			pstmt.setString(1,nombre);
			pstmt.setInt(2, anio);
			pstmt.setString(3, temporada);
			pstmt.setString(4,ciudad);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_olimpiada");
				connection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
