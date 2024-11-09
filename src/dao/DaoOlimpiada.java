package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bbdd.ConexionBBDD;
import model.ModeloOlimpiada;

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

	public static ModeloOlimpiada createOlimpiadaModel(int id) {
		connection = ConexionBBDD.getConnection();
		String consulta = "SELECT nombre, anio, temporada, ciudad FROM Olimpiada WHERE id_olimpiada=?";

		try (PreparedStatement pstmt = connection.prepareStatement(consulta)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				connection.commit();
				String nombre = rs.getString("nombre");
				int anio = rs.getInt("anio");
				String temporada = rs.getString("temporada");
				String ciudad = rs.getString("ciudad");
				
				return new ModeloOlimpiada(nombre, anio, temporada, ciudad);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static ArrayList<ModeloOlimpiada> listOlimpiadasByTemp(int temp) {
		ArrayList<ModeloOlimpiada> lst = new ArrayList<>();

		// Usamos un operador ternario para asignar la temporada
		String temporada = (temp == 2) ? "Summer" : "Winter";
		
		// Consulta SQL para obtener los id de las olimpiadas de la temporada seleccionada
		String select = "SELECT id_olimpiada FROM Olimpiada WHERE temporada=?";
		
		try (Connection con = ConexionBBDD.getConnection();
			PreparedStatement pstmt = con.prepareStatement(select)) {
			
			pstmt.setString(1, temporada);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					// Añadir la olimpiada a la lista
					lst.add(DaoOlimpiada.createOlimpiadaModel(rs.getInt("id_olimpiada")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lst;
	}


}
