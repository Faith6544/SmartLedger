package database;

import model.User;
import java.sql.*;

public class UserDAO {

    public UserDAO() { migrate(); }

    private void migrate() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(null, null, "users", "business_name");
            if (!rs.next()) {
                conn.createStatement().execute("ALTER TABLE users ADD COLUMN business_name VARCHAR(100) DEFAULT NULL");
                System.out.println("Added business_name column to users table");
            }
        } catch (SQLException e) { /* column might already exist */ }
    }

    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, password_hash, dashboard_token, business_name) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getDashboardToken());
            stmt.setString(4, user.getBusinessName().isEmpty() ? null : user.getBusinessName());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) user.setId(keys.getInt(1));
            return true;
        } catch (SQLException e) { return false; }
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = fromRS(rs);
                if (user.checkPassword(password)) return user;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public User getUserByToken(String token) {
        String sql = "SELECT * FROM users WHERE dashboard_token = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return fromRS(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private User fromRS(ResultSet rs) throws SQLException {
        String biz = null;
        try { biz = rs.getString("business_name"); } catch (SQLException e) { }
        return new User(rs.getInt("id"), rs.getString("username"),
            rs.getString("password_hash"), rs.getString("dashboard_token"),
            biz, rs.getTimestamp("created_at"));
    }
}
