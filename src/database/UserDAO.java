package database;

import model.User;
import java.sql.*;

public class UserDAO {

    public UserDAO() { migrate(); }

    private void migrate() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(null, null, "users", "business_name");
            if (!rs.next()) {
                conn.createStatement().execute("ALTER TABLE users ADD COLUMN business_name VARCHAR(100) DEFAULT NULL");
                System.out.println("Added business_name column to users table");
            }
        } catch (SQLException e) {
            // Print this one instead of swallowing it silently - if the DB connection
            // itself is broken, this is where you'd first see why.
            System.out.println("UserDAO startup check failed: " + e.getMessage());
        }
    }

    // Returns true on success, false ONLY if the username is genuinely already taken
    // (MySQL duplicate-key error, code 1062). Any other problem (bad password, DB down,
    // missing table) is thrown instead of silently reported as "username taken" -
    // that was hiding the real error before.
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, password_hash, dashboard_token, business_name) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getDashboardToken());
            stmt.setString(4, user.getBusinessName().isEmpty() ? null : user.getBusinessName());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) user.setId(keys.getInt(1));
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // genuine duplicate username
        } catch (SQLException e) {
            throw new RuntimeException(realCause(e), e);
        }
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = fromRS(rs);
                if (user.checkPassword(password)) return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(realCause(e), e);
        }
        return null;
    }

    public User getUserByToken(String token) {
        String sql = "SELECT * FROM users WHERE dashboard_token = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return fromRS(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Turns MySQL's raw exception into a message that actually says what's wrong
    private String realCause(SQLException e) {
        String msg = e.getMessage() == null ? "" : e.getMessage();
        if (msg.contains("Access denied")) return "Access denied for that MySQL user/password - check DB_PASS matches your MySQL root password";
        if (msg.contains("Unknown database")) return "Database 'smartledger' doesn't exist yet - run schema.sql first";
        if (msg.contains("Communications link failure") || msg.contains("Connection refused")) return "Can't reach MySQL - is the MySQL server actually running?";
        if (msg.contains("Unknown column")) return "Database table is missing a column - re-run schema.sql (" + msg + ")";
        return msg;
    }

    private User fromRS(ResultSet rs) throws SQLException {
        String biz = null;
        try { biz = rs.getString("business_name"); } catch (SQLException e) { }
        return new User(rs.getInt("id"), rs.getString("username"),
            rs.getString("password_hash"), rs.getString("dashboard_token"),
            biz, rs.getTimestamp("created_at"));
    }
}