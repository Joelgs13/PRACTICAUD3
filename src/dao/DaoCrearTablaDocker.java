package dao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import bbdd.ConexionBBDD;

public class DaoCrearTablaDocker {
    private static Connection connection;
    public static void crearLaBBDD(String pathString) {
        
		
        try {
            connection = ConexionBBDD.getConnection();
            File CSV=new File(pathString);
            // Separate the schema drop and creation into two statements
            String sqlDropEsquema = "DROP SCHEMA IF EXISTS `olimpiadas`;";
            Update(sqlDropEsquema);
            System.out.println("Esquema eliminado si existía.");
        
            String sqlCrearEsquema = "CREATE SCHEMA IF NOT EXISTS `olimpiadas` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;";
            Update(sqlCrearEsquema);
            System.out.println("Esquema creado.");
        
            // 2. Cambiar el esquema actual a 'olimpiadas'
            String sqlUsarEsquema = "USE `olimpiadas`;";
            Update(sqlUsarEsquema);
            System.out.println("Usando el esquema olimpiadas.");

            // 3. Crear tabla `Deporte`
            String sqlCrearTablaDeporte = 
                "CREATE TABLE IF NOT EXISTS `Deporte` (" +
                "`id_deporte` int(11) NOT NULL AUTO_INCREMENT, " +
                "`nombre` varchar(100) NOT NULL, " +
                "PRIMARY KEY (`id_deporte`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
            Update(sqlCrearTablaDeporte);
            System.out.println("Tabla 'Deporte' creada.");

            /*
            // 4. Insertar valores en la tabla `Deporte`
            String sqlInsertarDeporte = 
                "INSERT INTO `Deporte` (`id_deporte`, `nombre`) " +
                "VALUES (1, 'Basketball'), (2, 'Judo'), (3, 'Football');";
            statement.executeUpdate(sqlInsertarDeporte);
            System.out.println("Valores insertados en 'Deporte'.");
            */

            // 5. Crear tabla `Deportista`
            String sqlCrearTablaDeportista = 
                "CREATE TABLE IF NOT EXISTS `Deportista` (" +
                "`id_deportista` int(11) NOT NULL AUTO_INCREMENT, " +
                "`nombre` varchar(150) NOT NULL, " +
                "`sexo` enum('M', 'F') NOT NULL, " +
                "`peso` int(11) DEFAULT NULL, " +
                "`altura` int(11) DEFAULT NULL, " +
                "PRIMARY KEY (`id_deportista`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
            Update(sqlCrearTablaDeportista);
            System.out.println("Tabla 'Deportista' creada.");
            /*
            // 6. Insertar valores en la tabla `Deportista`
            String sqlInsertarDeportista = 
                "INSERT INTO `Deportista` (`id_deportista`, `nombre`, `sexo`, `peso`, `altura`) " +
                "VALUES (1, 'A Dijiang', 'M', 80, 180), (2, 'A Lamusi', 'M', 60, 170);";
            Update(sqlInsertarDeportista);
            System.out.println("Valores insertados en 'Deportista'.");
            */
            // 7. Crear tabla `Equipo`
            String sqlCrearTablaEquipo = 
                "CREATE TABLE IF NOT EXISTS `Equipo` (" +
                "`id_equipo` int(11) NOT NULL AUTO_INCREMENT, " +
                "`nombre` varchar(50) NOT NULL, " +
                "`iniciales` varchar(3) NOT NULL, " +
                "PRIMARY KEY (`id_equipo`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
            Update(sqlCrearTablaEquipo);
            System.out.println("Tabla 'Equipo' creada.");
            /* 
            // 8. Insertar valores en la tabla `Equipo`
            String sqlInsertarEquipo = 
                "INSERT INTO `Equipo` (`id_equipo`, `nombre`, `iniciales`) " +
                "VALUES (1, 'China', 'CHN'), (2, 'Denmark', 'DEN');";
            Update(sqlInsertarEquipo);
            System.out.println("Valores insertados en 'Equipo'.");
            */
            // 9. Crear tabla `Olimpiada`
            String sqlCrearTablaOlimpiada = 
                "CREATE TABLE IF NOT EXISTS `Olimpiada` (" +
                "`id_olimpiada` int(11) NOT NULL AUTO_INCREMENT, " +
                "`nombre` varchar(11) NOT NULL, " +
                "`anio` smallint(6) NOT NULL, " +
                "`temporada` enum('Summer', 'Winter') NOT NULL, " +
                "`ciudad` varchar(50) NOT NULL, " +
                "PRIMARY KEY (`id_olimpiada`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
            Update(sqlCrearTablaOlimpiada);
            System.out.println("Tabla 'Olimpiada' creada.");
            /*
            // 10. Insertar valores en la tabla `Olimpiada`
            String sqlInsertarOlimpiada = 
                "INSERT INTO `Olimpiada` (`id_olimpiada`, `nombre`, `anio`, `temporada`, `ciudad`) " +
                "VALUES (1, '1992 Summer', 1992, 'Summer', 'Barcelona'), " +
                "(2, '2012 Summer', 2012, 'Summer', 'London'), " +
                "(3, '1920 Summer', 1920, 'Summer', 'Antwerpen');";
            Update(sqlInsertarOlimpiada);
            System.out.println("Valores insertados en 'Olimpiada'.");
            */
            // 11. Crear tabla `Evento`
            String sqlCrearTablaEvento = 
                "CREATE TABLE IF NOT EXISTS `Evento` (" +
                "`id_evento` int(11) NOT NULL AUTO_INCREMENT, " +
                "`nombre` varchar(150) NOT NULL, " +
                "`id_olimpiada` int(11) NOT NULL, " +
                "`id_deporte` int(11) NOT NULL, " +
                "PRIMARY KEY (`id_evento`), " +
                "CONSTRAINT `FK_Evento_Deporte` FOREIGN KEY (`id_deporte`) REFERENCES `Deporte` (`id_deporte`), " +
                "CONSTRAINT `FK_Evento_Olimpiada` FOREIGN KEY (`id_olimpiada`) REFERENCES `Olimpiada` (`id_olimpiada`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
            Update(sqlCrearTablaEvento);
            System.out.println("Tabla 'Evento' creada.");
            
            // 12. Crear tabla `Participacion`
            String sqlCrearTablaParticipacion = 
                "CREATE TABLE IF NOT EXISTS `Participacion` (" +
                "`id_deportista` int(11) NOT NULL, " +
                "`id_evento` int(11) NOT NULL, " +
                "`id_equipo` int(11) NOT NULL, " +
                "`edad` tinyint(4) DEFAULT NULL, " +
                "`medalla` varchar(6) DEFAULT NULL, " +
                "PRIMARY KEY (`id_deportista`, `id_evento`), " +
                "CONSTRAINT `FK_Participacion_Deportista` FOREIGN KEY (`id_deportista`) REFERENCES `Deportista` (`id_deportista`), " +
                "CONSTRAINT `FK_Participacion_Equipo` FOREIGN KEY (`id_equipo`) REFERENCES `Equipo` (`id_equipo`), " +
                "CONSTRAINT `FK_Participacion_Evento` FOREIGN KEY (`id_evento`) REFERENCES `Evento` (`id_evento`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
            Update(sqlCrearTablaParticipacion);
            System.out.println("Tabla 'Participacion' creada.");
            System.out.println("Tablas creadas e insertadas correctamente en Docker MySQL");
            if(CSV.isFile()&&pathString.endsWith(".csv")) {
                //es csv
				try(BufferedReader br=new BufferedReader(new FileReader(CSV))){
                    //System.out.println("LLEGUE.");
					String linea=br.readLine();
					if(linea.equals("ID,Name,Sex,Age,Height,Weight,Team,NOC,Games,Year,Season,City,Sport,Event,Medal")) {
                        System.out.println("linea es correcta");
						linea=br.readLine();
						while(linea!=null) {
							String leido[]=linea.split(",");
							if(leido[3].equals("NA")){leido[3]="-1";}
							if(leido[4].equals("NA")){leido[4]="-1";}
							if(leido[5].equals("NA")){leido[5]="-1";}
							leido[5]=Math.round(Float.parseFloat(leido[5]))+"";
                            System.out.println("estoy llegando aqui");
							if(DaoDeportista.conseguirIdDeportista(leido[1], leido[2].charAt(0), Float.parseFloat(leido[5]), Integer.parseInt(leido[4]))==null){
								DaoDeportista.aniadirDeportista(leido[1], leido[2].charAt(0), Float.parseFloat(leido[5]), Integer.parseInt(leido[4]));
							}
							if(DaoDeporte.conseguirIdDeporte(leido[12])==null) {
								DaoDeporte.aniadirDeporte(leido[12]);
							}
							if(DaoEquipo.conseguirIdEquipo(leido[6], leido[7])==null) {
								DaoEquipo.aniadirEquipo(leido[6], leido[7]);
							}
							if(DaoOlimpiada.conseguirIdOlimpiada(leido[8],Integer.parseInt(leido[9]), leido[10], leido[11])==null) {
								DaoOlimpiada.aniadirOlimpiada(leido[8],Integer.parseInt(leido[9]), leido[10], leido[11]);
							}
							if(DaoEvento.conseguirIdEvento(leido[13], Integer.parseInt(DaoOlimpiada.conseguirIdOlimpiada(leido[8],Integer.parseInt(leido[9]), leido[10], leido[11])),
									Integer.parseInt(DaoDeporte.conseguirIdDeporte(leido[12])))==null){
								DaoEvento.aniadirEvento(leido[13], Integer.parseInt(DaoOlimpiada.conseguirIdOlimpiada(leido[8],Integer.parseInt(leido[9]), leido[10], leido[11])),Integer.parseInt(DaoDeporte.conseguirIdDeporte(leido[12])));
							}
							if(!DaoParticipacion.existeIdParticipacion(Integer.parseInt(DaoDeportista.conseguirIdDeportista(leido[1], leido[2].charAt(0), Float.parseFloat(leido[5]),Integer.parseInt(leido[4]))),
									Integer.parseInt(DaoEvento.conseguirIdEvento(leido[13], 
											Integer.parseInt(DaoOlimpiada.conseguirIdOlimpiada(leido[8],Integer.parseInt(leido[9]), leido[10], leido[11])),
											Integer.parseInt(DaoDeporte.conseguirIdDeporte(leido[12])))))) {
								DaoParticipacion.aniadirParticipacion(Integer.parseInt(DaoDeportista.conseguirIdDeportista(leido[1], leido[2].charAt(0), Float.parseFloat(leido[5]), 
										Integer.parseInt(leido[4]))),Integer.parseInt(DaoEvento.conseguirIdEvento(leido[13], 
												Integer.parseInt(DaoOlimpiada.conseguirIdOlimpiada(leido[8],Integer.parseInt(leido[9]), leido[10], leido[11])),
												Integer.parseInt(DaoDeporte.conseguirIdDeporte(leido[12])))),
										Integer.parseInt(DaoEquipo.conseguirIdEquipo(leido[6], leido[7])), Integer.parseInt(leido[3]), leido[14]);
							}
							linea=br.readLine();
						}
						System.out.println("La carga de la información se ha realizado correctamente");
					} else {
                        System.out.println("linea NO ES CORRECTA");
                    }
				} catch (FileNotFoundException e) {
					System.out.println("El archivo csv no existe");
				} catch (IOException e) {
					System.out.println("Ha ocurrido algún error en la carga");
				}
			}
			else {
				System.out.println("El archivo csv no existe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    static void Update(String sentencia) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement(sentencia);
		pstmt.executeUpdate();
	}
}
