package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Was returning one shared static Connection for the whole app — fine when only
    // one request thread existed, but MySQL Connections aren't safe to use from
    // multiple threads at once. Now every call gets its own fresh connection, and
    // callers are responsible for closing it (use try-with-resources).
    public static Connection getConnection() throws SQLException {
        // Check for cloud environment variables first, fall back to localhost
        String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
        String name = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "smartledger";
        String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
        String pass = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "Skemzy@6544@";
        String ssl = System.getenv("DB_SSL") != null ? "&sslMode=REQUIRED" : "";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useUnicode=true&characterEncoding=UTF-8" + ssl;
        return DriverManager.getConnection(url, user, pass);
    }
}