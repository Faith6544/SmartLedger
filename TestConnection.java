import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smartledger", "root", ""
            );
            System.out.println("Connected to SmartLedger database!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}