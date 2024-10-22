import java.io.*;
import java.sql.*;
import java.util.Scanner;

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
                    crearBBDD(scanner);
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

    public static void crearBBDD(Scanner scanner) {
        String url = "jdbc:mysql://localhost:33066/olimpiadas"; 
        String usuario = "prof";
        String contraseña = "1234";

        System.out.print("Ingrese la ruta del archivo CSV: ");
        String rutaCSV = scanner.nextLine();

        File archivoCSV = new File(rutaCSV);
        if (!archivoCSV.exists()) {
            System.out.println("Error: El archivo CSV no existe.");
            return;
        }

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:33066/", usuario, contraseña);
             Statement statement = conexion.createStatement()) {

            // Desactivar auto-commit para manejar manualmente la transacción
            conexion.setAutoCommit(false);

            // 1. Borrar la base de datos si ya existe (eliminar tablas)
            System.out.println("Borrando la base de datos existente (si existe)...");
            statement.executeUpdate("DROP SCHEMA IF EXISTS `olimpiadas`;"); // Eliminar la base de datos
            System.out.println("Base de datos borrada.");

            // 2. Crear la base de datos y las tablas
            System.out.println("Creando la base de datos y las tablas...");
            String sqlCrearEsquema = "CREATE SCHEMA IF NOT EXISTS `olimpiadas` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;";
            statement.executeUpdate(sqlCrearEsquema);
            statement.executeUpdate("USE `olimpiadas`;");

            // Llamar a la función que crea las tablas
            crearTablas(statement);
            System.out.println("Base de datos y tablas creadas.");

            // 3. Cargar datos desde el archivo CSV
            System.out.println("Cargando datos desde el archivo CSV...");
            cargarDatosDesdeCSV(conexion, archivoCSV);
            System.out.println("Datos cargados correctamente.");

            // 4. Confirmar la transacción
            conexion.commit();
            System.out.println("Transacción completada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error durante la creación de la base de datos o la carga de datos.");
            e.printStackTrace();
        }
    }

    // Método para crear las tablas
    public static void crearTablas(Statement statement) throws SQLException {
        // Crear tabla `Deporte`
        String sqlCrearTablaDeporte =
            "CREATE TABLE IF NOT EXISTS `Deporte` (" +
            "`id_deporte` int(11) NOT NULL AUTO_INCREMENT, " +
            "`nombre` varchar(100) NOT NULL, " +
            "PRIMARY KEY (`id_deporte`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";

        statement.executeUpdate(sqlCrearTablaDeporte);

        // Crear tabla `Deportista`
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

        // Crear tabla `Equipo`
        String sqlCrearTablaEquipo =
            "CREATE TABLE IF NOT EXISTS `Equipo` (" +
            "`id_equipo` int(11) NOT NULL AUTO_INCREMENT, " +
            "`nombre` varchar(50) NOT NULL, " +
            "`iniciales` varchar(3) NOT NULL, " +
            "PRIMARY KEY (`id_equipo`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";

        statement.executeUpdate(sqlCrearTablaEquipo);

        // Crear tabla `Olimpiada`
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

        // Crear tabla `Evento`
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

        // Crear tabla `Participacion`
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
            ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";

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
            PreparedStatement psDeportista = conexion.prepareStatement(sqlInsertarDeportista);
            PreparedStatement psEquipo = conexion.prepareStatement(sqlInsertarEquipo);
            PreparedStatement psOlimpiada = conexion.prepareStatement(sqlInsertarOlimpiada);
            PreparedStatement psEvento = conexion.prepareStatement(sqlInsertarEvento);
            PreparedStatement psParticipacion = conexion.prepareStatement(sqlInsertarParticipacion);

            while ((line = br.readLine()) != null) {
                String[] campos = line.split(";");

                try {
                    if (campos.length == 4) { // Deportista
                        psDeportista.setString(1, campos[0]);
                        psDeportista.setString(2, campos[1]);
                        psDeportista.setInt(3, Integer.parseInt(campos[2]));
                        psDeportista.setInt(4, Integer.parseInt(campos[3]));
                        psDeportista.executeUpdate();
                        System.out.println("Deportista insertado: " + campos[0]);
                    } else if (campos.length == 3) { // Equipo
                        psEquipo.setString(1, campos[0]);
                        psEquipo.setString(2, campos[1]);
                        psEquipo.executeUpdate();
                        System.out.println("Equipo insertado: " + campos[0]);
                    } else if (campos.length == 4) { // Olimpiada
                        psOlimpiada.setString(1, campos[0]);
                        psOlimpiada.setInt(2, Integer.parseInt(campos[1]));
                        psOlimpiada.setString(3, campos[2]);
                        psOlimpiada.setString(4, campos[3]);
                        psOlimpiada.executeUpdate();
                        System.out.println("Olimpiada insertada: " + campos[0]);
                    } else if (campos.length == 3) { // Evento
                        psEvento.setString(1, campos[0]);
                        psEvento.setInt(2, Integer.parseInt(campos[1]));
                        psEvento.setInt(3, Integer.parseInt(campos[2]));
                        psEvento.executeUpdate();
                        System.out.println("Evento insertado: " + campos[0]);
                    } else if (campos.length == 5) { // Participación
                        psParticipacion.setInt(1, Integer.parseInt(campos[0]));
                        psParticipacion.setInt(2, Integer.parseInt(campos[1]));
                        psParticipacion.setInt(3, Integer.parseInt(campos[2]));
                        psParticipacion.setInt(4, Integer.parseInt(campos[3]));
                        psParticipacion.setString(5, campos[4]);
                        psParticipacion.executeUpdate();
                        System.out.println("Participación insertada para deportista ID: " + campos[0]);
                    }
                } catch (SQLException e) {
                    System.out.println("Error al insertar línea: " + line);
                    System.out.println("Mensaje de error: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Error de formato de número en línea: " + line);
                    System.out.println("Mensaje de error: " + e.getMessage());
                }
            }

            // No es necesario un commit aquí ya que cada inserción se maneja individualmente
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
}


