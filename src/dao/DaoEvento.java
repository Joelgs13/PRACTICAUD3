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
	
	public static ArrayList<ModeloEvento> crearListaModelosPorDeporteYOlimpiada(int idDeporte,int idOlimpiada) {
		connection=ConexionBBDD.getConnection();
		String select="SELECT nombre FROM Evento WHERE id_deporte=? AND id_olimpiada=?";
		ArrayList<ModeloEvento>lst=new ArrayList<ModeloEvento>();
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			pstmt.setInt(1, idDeporte);
			pstmt.setInt(2, idOlimpiada);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				connection.commit();
				ModeloEvento evento=new ModeloEvento(rs.getString("nombre"),DaoDeporte.createDeporteModel(idDeporte),DaoOlimpiada.createOlimpiadaModel(idOlimpiada));
				if(!lst.contains(evento)) {
					lst.add(evento);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;	
	}

	public static ArrayList<ModeloEvento> listaModelosPorId(ArrayList<String>lstId){
		connection=ConexionBBDD.getConnection();
		ArrayList<ModeloEvento>lst=new ArrayList<ModeloEvento>();
		String select="SELECT nombre,id_deporte,id_olimpiada FROM Evento where id_evento=?";
		try {
			PreparedStatement pstmt;
			pstmt=connection.prepareStatement(select);
			for(String id:lstId) {
				pstmt.setInt(1,Integer.parseInt(id));
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					connection.commit();
					ModeloEvento evento=new ModeloEvento(rs.getString("nombre"),DaoDeporte.createDeporteModel(rs.getInt("id_deporte")),DaoOlimpiada.createOlimpiadaModel(rs.getInt("id_olimpiada")));
					lst.add(evento);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
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
	public static ArrayList<ModeloEvento> modelosByDeporteOlimpiada(int idDeporte, int idOlimpiada) {
		ArrayList<ModeloEvento> lst = new ArrayList<>();
		
		String select = "SELECT nombre FROM Evento WHERE id_deporte=? AND id_olimpiada=?";
		
		try (Connection con = ConexionBBDD.getConnection();
			PreparedStatement pstmt = con.prepareStatement(select)) {
			
			pstmt.setInt(1, idDeporte);
			pstmt.setInt(2, idOlimpiada);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					// Creamos el modelo de evento
					ModeloEvento evento = new ModeloEvento(
							rs.getString("nombre"),
							DaoDeporte.createDeporteModel(idDeporte),
							DaoOlimpiada.createOlimpiadaModel(idOlimpiada)
					);
					
					// Añadimos el evento si no está ya en la lista (verificando por referencia)
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
