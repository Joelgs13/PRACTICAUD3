package dao;

import java.sql.*;
import java.util.ArrayList;

import bbdd.ConexionBBDD;
import model.ModeloDeportista;

public class DaoDeportista {
    private static Connection connection;

public static ModeloDeportista createDeportistaModel(String id) {
    // Conexión a la base de datos
    connection = ConexionBBDD.getConnection();
    String consultaSQL = "SELECT nombre, sexo, peso, altura FROM Deportista WHERE id_deportista = ?";
    
    try (PreparedStatement pstmt = connection.prepareStatement(consultaSQL)) {
		//System.out.println("prueba");
		
		pstmt.setString(1, id); // Asigna el ID del deportista a la consulta
        ResultSet resultado = pstmt.executeQuery();

		//System.out.println("prueba");

        if (resultado.next()) {
            connection.commit(); // Confirma la transacción si se encuentra un resultado
            String nombre = resultado.getString("nombre");
            char sexo = resultado.getString("sexo").charAt(0);
            int altura = resultado.getInt("altura");
            int peso = resultado.getInt("peso");
            
            return new ModeloDeportista(nombre, sexo, altura, peso);
        } else {
            System.out.println("No se encontró ningún deportista con el ID: " + id);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener el modelo del deportista: " + e.getMessage());
    }

    // Retorna null si no se encontró el deportista o ocurrió una excepción
    return null;
}


    public static ArrayList<ModeloDeportista> findDeportistaName(String cadena){
		connection=ConexionBBDD.getConnection();
		ArrayList<ModeloDeportista>lst=new ArrayList<ModeloDeportista>();
		String select="SELECT id_deportista FROM Deportista WHERE nombre LIKE ?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			pstmt.setString(1,"%"+cadena+"%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				connection.commit();
				lst.add(DaoDeportista.createDeportistaModel(rs.getInt("id_deportista")+""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;	
	}

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