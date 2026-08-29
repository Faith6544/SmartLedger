package database;

import model.Transaction;
import model.TransactionType;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransactionDAO {

    public void save(Transaction txn) {
        String sql = "INSERT INTO transactions (user_id, type, amount, description, counterparty) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, txn.getUserId());
            stmt.setString(2, txn.getType().name());
            stmt.setDouble(3, txn.getAmount());
            stmt.setString(4, txn.getDescription());
            stmt.setString(5, txn.getCounterparty());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) txn.setId(keys.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Transaction> getAllByUser(int userId) {
        return query("SELECT * FROM transactions WHERE user_id = ? ORDER BY created_at DESC", userId);
    }

    public List<Transaction> getRecent(int userId, int limit) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY created_at DESC LIMIT ?";
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(fromResultSet(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Transaction> getFiltered(int userId, String type, String from, String to) {
        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE user_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (type != null && !type.isEmpty() && !type.equals("ALL")) {
            sql.append(" AND type = ?");
            params.add(type);
        }
        if (from != null && !from.isEmpty()) {
            sql.append(" AND DATE(created_at) >= ?");
            params.add(from);
        }
        if (to != null && !to.isEmpty()) {
            sql.append(" AND DATE(created_at) <= ?");
            params.add(to);
        }
        sql.append(" ORDER BY created_at DESC");

        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(fromResultSet(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public double getTotalByType(int userId, TransactionType type) {
        String sql = "SELECT COALESCE(SUM(amount), 0) as total FROM transactions WHERE user_id = ? AND type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double getTodayTotalByType(int userId, TransactionType type) {
        String sql = "SELECT COALESCE(SUM(amount), 0) as total FROM transactions WHERE user_id = ? AND type = ? AND DATE(created_at) = CURDATE()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public List<Transaction> getDebts(int userId) {
        return query("SELECT * FROM transactions WHERE user_id = ? AND type = 'DEBT' ORDER BY created_at DESC", userId);
    }

    /**
     * Groups debts by counterparty. Returns map of name -> [total_debt, total_paid, remaining].
     */
    public Map<String, double[]> getDebtSummary(int userId) {
        Map<String, double[]> summary = new LinkedHashMap<>();

        // Get all debts grouped by counterparty
        String debtSql = "SELECT COALESCE(counterparty, 'Unknown') as name, SUM(amount) as total " +
                          "FROM transactions WHERE user_id = ? AND type = 'DEBT' GROUP BY counterparty ORDER BY total DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(debtSql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double total = rs.getDouble("total");
                summary.put(name, new double[]{total, 0, total}); // [owed, paid, remaining]
            }
        } catch (SQLException e) { e.printStackTrace(); }

        // Match payments by counterparty
        String paymentSql = "SELECT COALESCE(counterparty, 'Unknown') as name, SUM(amount) as total " +
                             "FROM transactions WHERE user_id = ? AND type = 'PAYMENT' AND counterparty IS NOT NULL GROUP BY counterparty";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(paymentSql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double paid = rs.getDouble("total");
                if (summary.containsKey(name)) {
                    double[] vals = summary.get(name);
                    vals[1] = paid;
                    vals[2] = Math.max(0, vals[0] - paid);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return summary;
    }

    public boolean deleteTransaction(int transactionId, int userId) {
        String sql = "DELETE FROM transactions WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateType(int transactionId, TransactionType newType, int userId) {
        String sql = "UPDATE transactions SET type = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newType.name());
            stmt.setInt(2, transactionId);
            stmt.setInt(3, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteLastTransaction(int userId) {
        String sql = "DELETE FROM transactions WHERE id = (SELECT max_id FROM (SELECT MAX(id) as max_id FROM transactions WHERE user_id = ?) as temp)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ===== Helpers =====

    private List<Transaction> query(String sql, int userId) {
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(fromResultSet(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private Transaction fromResultSet(ResultSet rs) throws SQLException {
        Transaction txn = new Transaction(
            rs.getInt("user_id"),
            TransactionType.valueOf(rs.getString("type")),
            rs.getDouble("amount"),
            rs.getString("description"),
            rs.getString("counterparty")
        );
        txn.setId(rs.getInt("id"));
        txn.setCreatedAt(rs.getTimestamp("created_at"));
        return txn;
    }

    // ===== ANALYSIS METHODS =====

    /**
     * Returns daily totals for a type within a date range.
     * Map key = "2026-08-05", value = total amount
     */
    public java.util.LinkedHashMap<String, Double> getDailyTotals(int userId, TransactionType type, String from, String to) {
        java.util.LinkedHashMap<String, Double> map = new java.util.LinkedHashMap<>();
        String sql = "SELECT DATE(created_at) as day, SUM(amount) as total FROM transactions " +
                     "WHERE user_id = ? AND type = ? AND DATE(created_at) BETWEEN ? AND ? " +
                     "GROUP BY DATE(created_at) ORDER BY day";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type.name());
            stmt.setString(3, from);
            stmt.setString(4, to);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("day"), rs.getDouble("total"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return map;
    }

    /**
     * Returns total for a type within a date range.
     */
    public double getPeriodTotal(int userId, TransactionType type, String from, String to) {
        String sql = "SELECT COALESCE(SUM(amount), 0) as total FROM transactions " +
                     "WHERE user_id = ? AND type = ? AND DATE(created_at) BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type.name());
            stmt.setString(3, from);
            stmt.setString(4, to);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    /**
     * Returns top debtors in a date range.
     */
    public java.util.LinkedHashMap<String, Double> getTopDebtors(int userId, String from, String to) {
        java.util.LinkedHashMap<String, Double> map = new java.util.LinkedHashMap<>();
        String sql = "SELECT COALESCE(counterparty, 'Unknown') as name, SUM(amount) as total FROM transactions " +
                     "WHERE user_id = ? AND type = 'DEBT' AND DATE(created_at) BETWEEN ? AND ? " +
                     "GROUP BY counterparty ORDER BY total DESC LIMIT 10";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, from);
            stmt.setString(3, to);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("name"), rs.getDouble("total"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return map;
    }

    /**
     * Returns the best sales day in a date range.
     */
    public String[] getBestDay(int userId, String from, String to) {
        String sql = "SELECT DATE(created_at) as day, SUM(amount) as total FROM transactions " +
                     "WHERE user_id = ? AND type = 'SALE' AND DATE(created_at) BETWEEN ? AND ? " +
                     "GROUP BY DATE(created_at) ORDER BY total DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, from);
            stmt.setString(3, to);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return new String[]{rs.getString("day"), String.valueOf(rs.getDouble("total"))};
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Count number of active days (days with at least one transaction).
     */
    public int getActiveDays(int userId, String from, String to) {
        String sql = "SELECT COUNT(DISTINCT DATE(created_at)) as days FROM transactions " +
                     "WHERE user_id = ? AND DATE(created_at) BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, from);
            stmt.setString(3, to);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("days");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    /**
     * Calculates the current recording streak (consecutive days with transactions, counting back from today).
     */
    public int getStreak(int userId) {
        String sql = "SELECT DISTINCT DATE(created_at) as day FROM transactions WHERE user_id = ? ORDER BY day DESC LIMIT 60";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            java.util.List<java.time.LocalDate> days = new java.util.ArrayList<>();
            while (rs.next()) days.add(rs.getDate("day").toLocalDate());

            if (days.isEmpty()) return 0;

            int streak = 0;
            java.time.LocalDate expected = java.time.LocalDate.now();

            for (java.time.LocalDate day : days) {
                if (day.equals(expected)) {
                    streak++;
                    expected = expected.minusDays(1);
                } else if (day.equals(expected.plusDays(1))) {
                    // Today hasn't been recorded yet, start from yesterday
                    if (streak == 0) {
                        expected = expected.minusDays(1);
                        if (day.equals(expected)) { streak++; expected = expected.minusDays(1); }
                    } else break;
                } else break;
            }
            return streak;
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    /**
     * Logs a parse correction for learning.
     */
    public void logCorrection(String originalText, String guessedType, String correctedType) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Create table if not exists
            conn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS parse_corrections (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "original_text TEXT, " +
                "guessed_type VARCHAR(20), " +
                "corrected_type VARCHAR(20), " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
            );
            try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO parse_corrections (original_text, guessed_type, corrected_type) VALUES (?, ?, ?)"
            )) {
                stmt.setString(1, originalText);
                stmt.setString(2, guessedType);
                stmt.setString(3, correctedType);
                stmt.executeUpdate();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}