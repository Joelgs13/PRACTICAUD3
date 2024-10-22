import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CrearTablaDocker {
    public static void main(String[] args) {
        // Datos de conexión
        String url = "jdbc:mysql://localhost:33066/olimpiadas"; // Cambia la URL si es necesario
        String usuario = "prof";
        String contraseña = "1234";

        try {
            // Conexión a la base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:33066/", usuario, contraseña);

            // Crear un Statement para ejecutar las consultas SQL
            Statement statement = conexion.createStatement();

            // 1. Crear el esquema primero
            String sqlCrearEsquema = "CREATE SCHEMA IF NOT EXISTS `olimpiadas` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;";
            statement.executeUpdate(sqlCrearEsquema);
            System.out.println("Esquema creado.");

            // 2. Cambiar el esquema actual a 'olimpiadas'
            String sqlUsarEsquema = "USE `olimpiadas`;";
            statement.executeUpdate(sqlUsarEsquema);
            System.out.println("Usando el esquema olimpiadas.");

            // 3. Crear tabla `Deporte`
            String sqlCrearTablaDeporte = 
                "CREATE TABLE IF NOT EXISTS `Deporte` (" +
                "`id_deporte` int(11) NOT NULL AUTO_INCREMENT, " +
                "`nombre` varchar(100) NOT NULL, " +
                "PRIMARY KEY (`id_deporte`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
            statement.executeUpdate(sqlCrearTablaDeporte);
            System.out.println("Tabla 'Deporte' creada.");

            // 4. Insertar valores en la tabla `Deporte`
            String sqlInsertarDeporte = 
                "INSERT INTO `Deporte` (`id_deporte`, `nombre`) " +
                "VALUES (1, 'Basketball'), (2, 'Judo'), (3, 'Football');";
            statement.executeUpdate(sqlInsertarDeporte);
            System.out.println("Valores insertados en 'Deporte'.");

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
            statement.executeUpdate(sqlCrearTablaDeportista);
            System.out.println("Tabla 'Deportista' creada.");

            // 6. Insertar valores en la tabla `Deportista`
            String sqlInsertarDeportista = 
                "INSERT INTO `Deportista` (`id_deportista`, `nombre`, `sexo`, `peso`, `altura`) " +
                "VALUES (1, 'A Dijiang', 'M', 80, 180), (2, 'A Lamusi', 'M', 60, 170);";
            statement.executeUpdate(sqlInsertarDeportista);
            System.out.println("Valores insertados en 'Deportista'.");

            // 7. Crear tabla `Equipo`
            String sqlCrearTablaEquipo = 
                "CREATE TABLE IF NOT EXISTS `Equipo` (" +
                "`id_equipo` int(11) NOT NULL AUTO_INCREMENT, " +
                "`nombre` varchar(50) NOT NULL, " +
                "`iniciales` varchar(3) NOT NULL, " +
                "PRIMARY KEY (`id_equipo`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
            statement.executeUpdate(sqlCrearTablaEquipo);
            System.out.println("Tabla 'Equipo' creada.");

            // 8. Insertar valores en la tabla `Equipo`
            String sqlInsertarEquipo = 
                "INSERT INTO `Equipo` (`id_equipo`, `nombre`, `iniciales`) " +
                "VALUES (1, 'China', 'CHN'), (2, 'Denmark', 'DEN');";
            statement.executeUpdate(sqlInsertarEquipo);
            System.out.println("Valores insertados en 'Equipo'.");

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
            statement.executeUpdate(sqlCrearTablaOlimpiada);
            System.out.println("Tabla 'Olimpiada' creada.");

            // 10. Insertar valores en la tabla `Olimpiada`
            String sqlInsertarOlimpiada = 
                "INSERT INTO `Olimpiada` (`id_olimpiada`, `nombre`, `anio`, `temporada`, `ciudad`) " +
                "VALUES (1, '1992 Summer', 1992, 'Summer', 'Barcelona'), " +
                "(2, '2012 Summer', 2012, 'Summer', 'London'), " +
                "(3, '1920 Summer', 1920, 'Summer', 'Antwerpen');";
            statement.executeUpdate(sqlInsertarOlimpiada);
            System.out.println("Valores insertados en 'Olimpiada'.");

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
            statement.executeUpdate(sqlCrearTablaEvento);
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
            statement.executeUpdate(sqlCrearTablaParticipacion);
            System.out.println("Tabla 'Participacion' creada.");

            // Cerrar la conexión
            statement.close();
            conexion.close();
            System.out.println("Tablas creadas e insertadas correctamente en Docker MySQL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}