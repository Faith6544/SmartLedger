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
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
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
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
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
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, type.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double getTodayTotalByType(int userId, TransactionType type) {
        String sql = "SELECT COALESCE(SUM(amount), 0) as total FROM transactions WHERE user_id = ? AND type = ? AND DATE(created_at) = CURDATE()";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
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
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(debtSql);
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
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(paymentSql);
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
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transactionId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateType(int transactionId, TransactionType newType, int userId) {
        String sql = "UPDATE transactions SET type = ? WHERE id = ? AND user_id = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newType.name());
            stmt.setInt(2, transactionId);
            stmt.setInt(3, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteLastTransaction(int userId) {
        String sql = "DELETE FROM transactions WHERE id = (SELECT max_id FROM (SELECT MAX(id) as max_id FROM transactions WHERE user_id = ?) as temp)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ===== Helpers =====

    private List<Transaction> query(String sql, int userId) {
        List<Transaction> list = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
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
}
