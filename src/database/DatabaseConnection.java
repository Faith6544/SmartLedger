package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Check for cloud environment variables first, fall back to localhost
            String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
            String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
            String name = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "smartledger";
            String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
            String pass = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "";
            String ssl = System.getenv("DB_SSL") != null ? "&sslMode=REQUIRED" : "";

            String url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useUnicode=true&characterEncoding=UTF-8" + ssl;
            connection = DriverManager.getConnection(url, user, pass);
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
