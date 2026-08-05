package database;

import model.Transaction;
import model.TransactionType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            if (keys.next()) {
                txn.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllByUser(int userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY created_at DESC";
        List<Transaction> list = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction txn = new Transaction(
                    rs.getInt("user_id"),
                    TransactionType.valueOf(rs.getString("type")),
                    rs.getDouble("amount"),
                    rs.getString("description"),
                    rs.getString("counterparty")
                );
                txn.setId(rs.getInt("id"));
                txn.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(txn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Transaction> getByType(int userId, TransactionType type) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND type = ? ORDER BY created_at DESC";
        List<Transaction> list = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, type.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction txn = new Transaction(
                    rs.getInt("user_id"),
                    TransactionType.valueOf(rs.getString("type")),
                    rs.getDouble("amount"),
                    rs.getString("description"),
                    rs.getString("counterparty")
                );
                txn.setId(rs.getInt("id"));
                txn.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(txn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Transaction> getDebts(int userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND type = 'DEBT' ORDER BY created_at DESC";
        List<Transaction> list = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction txn = new Transaction(
                    rs.getInt("user_id"),
                    TransactionType.DEBT,
                    rs.getDouble("amount"),
                    rs.getString("description"),
                    rs.getString("counterparty")
                );
                txn.setId(rs.getInt("id"));
                txn.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(txn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteLastTransaction(int userId) {
        String sql = "DELETE FROM transactions WHERE id = (SELECT max_id FROM (SELECT MAX(id) as max_id FROM transactions WHERE user_id = ?) as temp)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
