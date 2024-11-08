import java.io.*;
import java.sql.*;
import java.util.Scanner;

import bbdd.ConexionBBDD;
import dao.DaoCrearTablaDocker;

public class MenuOlimpiadas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            // Mostrar el menú
            System.out.println("Menú:");
            System.out.println("1. Crear BBDD MySQL");
            System.out.println("2. Opción 2 (Pendiente)");
            System.out.println("3. Opción 3 (Pendiente)");
            System.out.println("4. Opción 4 (Pendiente)");
            System.out.println("5. Opción 5 (Pendiente)");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    DaoCrearTablaDocker.crearLaBBDD();
                     try {
                        ConexionBBDD conexion = new ConexionBBDD();
                        connection = conexion.getConnection();
                        
                        // Existing table creation code here...

                        // After creating tables, insert data from CSV
                        insertDataFromCSV("athlete_events-sort.csv");
                        conexion.closeConnection();
                        System.out.println("Datos insertados correctamente.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no disponible.");
                    break;
            }
        } while (opcion != 6);

        scanner.close();
    }



    public static void crearTablas(Statement statement) throws SQLException {
        String sqlCrearTablaDeporte =
            "CREATE TABLE IF NOT EXISTS `Deporte` (" +
            "`id_deporte` int(11) NOT NULL AUTO_INCREMENT, " +
            "`nombre` varchar(100) NOT NULL, " +
            "PRIMARY KEY (`id_deporte`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
        statement.executeUpdate(sqlCrearTablaDeporte);

        String sqlCrearTablaDeportista =
            "CREATE TABLE IF NOT EXISTS `Deportista` (" +
            "`id_deportista` int(11) NOT NULL AUTO_INCREMENT, " +
            "`nombre` varchar(150) NOT NULL, " +
            "`sexo` enum('M', 'F') NOT NULL, " +
            "`peso` int(11) DEFAULT NULL, " +
            "`altura` int(11) DEFAULT NULL, " +
            "PRIMARY KEY (`id_deportista`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
        statement.executeUpdate(sqlCrearTablaDeportista);

        String sqlCrearTablaEquipo =
            "CREATE TABLE IF NOT EXISTS `Equipo` (" +
            "`id_equipo` int(11) NOT NULL AUTO_INCREMENT, " +
            "`nombre` varchar(50) NOT NULL, " +
            "`iniciales` varchar(3) NOT NULL, " +
            "PRIMARY KEY (`id_equipo`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
        statement.executeUpdate(sqlCrearTablaEquipo);

        String sqlCrearTablaOlimpiada =
            "CREATE TABLE IF NOT EXISTS `Olimpiada` (" +
            "`id_olimpiada` int(11) NOT NULL AUTO_INCREMENT, " +
            "`nombre` varchar(11) NOT NULL, " +
            "`anio` smallint(6) NOT NULL, " +
            "`temporada` enum('Summer', 'Winter') NOT NULL, " +
            "`ciudad` varchar(50) NOT NULL, " +
            "PRIMARY KEY (`id_olimpiada`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
        statement.executeUpdate(sqlCrearTablaOlimpiada);

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
        statement.executeUpdate(sqlCrearTablaEvento);

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
        statement.executeUpdate(sqlCrearTablaParticipacion);
    }

    public static void cargarDatosDesdeCSV(Connection conexion, File archivoCSV) {
        String sqlInsertarDeportista = "INSERT INTO Deportista (nombre, sexo, peso, altura) VALUES (?, ?, ?, ?)";
        String sqlInsertarEquipo = "INSERT INTO Equipo (nombre, iniciales) VALUES (?, ?)";
        String sqlInsertarOlimpiada = "INSERT INTO Olimpiada (nombre, anio, temporada, ciudad) VALUES (?, ?, ?, ?)";
        String sqlInsertarEvento = "INSERT INTO Evento (nombre, id_olimpiada, id_deporte) VALUES (?, ?, ?)";
        String sqlInsertarParticipacion = "INSERT INTO Participacion (id_deportista, id_evento, id_equipo, edad, medalla) VALUES (?, ?, ?, ?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String line;
            br.readLine(); // Leer la línea de encabezado para obtener la estructura del archivo
            PreparedStatement psDeportista = conexion.prepareStatement(sqlInsertarDeportista);
            PreparedStatement psEquipo = conexion.prepareStatement(sqlInsertarEquipo);
            PreparedStatement psOlimpiada = conexion.prepareStatement(sqlInsertarOlimpiada);
            PreparedStatement psEvento = conexion.prepareStatement(sqlInsertarEvento);
            PreparedStatement psParticipacion = conexion.prepareStatement(sqlInsertarParticipacion);

            while ((line = br.readLine()) != null) {
                String[] campos = line.split(",");

                // Validar y procesar las columnas, insertar en la tabla correcta
                if (campos.length > 0) {
                    
                    if (campos[5].equals("NA")){
                        campos[5]="0";
                    }
                    if (campos[4].equals("NA")){
                        campos[4]="0";
                    }

                    campos[5]=Math.round(Float.parseFloat(campos[5]))+"";
                    psDeportista.setString(1, campos[1]); // Nombres
                    psDeportista.setString(2, campos[2]); // Sexo
                    psDeportista.setInt(3, Integer.parseInt(campos[5])); // Peso
                    psDeportista.setInt(4, Integer.parseInt(campos[4])); // Altura

                    
                    //System.out.println(campos[2]);
                    psDeportista.executeUpdate();
                    conexion.commit();
                }
            }

        } catch (IOException | SQLException e) {
            System.out.println("Error al procesar el archivo CSV o realizar operaciones en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}
