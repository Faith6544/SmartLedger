package database;

import model.ChatMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDAO {

    public void save(ChatMessage msg) {
        String sql = "INSERT INTO chat_messages (user_id, raw_text, is_transaction) VALUES (?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, msg.getUserId());
            stmt.setString(2, msg.getRawText());
            stmt.setBoolean(3, msg.isTransaction());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                msg.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ChatMessage> getAllByUser(int userId) {
        String sql = "SELECT * FROM chat_messages WHERE user_id = ? ORDER BY created_at DESC";
        List<ChatMessage> list = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChatMessage msg = new ChatMessage(
                    rs.getInt("user_id"),
                    rs.getString("raw_text"),
                    rs.getBoolean("is_transaction")
                );
                msg.setId(rs.getInt("id"));
                msg.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
