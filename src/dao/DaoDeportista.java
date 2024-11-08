package dao;

import java.sql.*;

import bbdd.ConexionBBDD;

public class DaoDeportista {
    private static Connection connection;

    public static void insertDeportista(String nombre, String sexo, int edad) throws SQLException {
        String sql = "INSERT INTO `Deportista` (`nombre`, `sexo`, `edad`) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, sexo);
            pstmt.setInt(3, edad);
            pstmt.executeUpdate();
        }
    }

    public static String conseguirIdDeportista(String nombreDeportista,char sexo,float peso,int altura) {
		connection=ConexionBBDD.getConnection();
		String select="SELECT id_deportista FROM Deportista WHERE nombre=? AND sexo=? AND peso=? AND altura=?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			pstmt.setString(1,nombreDeportista);
			pstmt.setString(2,sexo+"");
			pstmt.setFloat(3,peso);
			pstmt.setInt(4,altura);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_deportista");
				connection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

    public static void aniadirDeportista(String nombreDeportista,char sexo,float peso,int altura) {
		connection=ConexionBBDD.getConnection();
		String insertar="INSERT INTO Deportista (nombre,sexo,peso,altura) VALUES (?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt=connection.prepareStatement(insertar);
			pstmt.setString(1, nombreDeportista);
			pstmt.setString(2, sexo+"");
			pstmt.setFloat(3, peso);
			pstmt.setInt(4, altura);
			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}