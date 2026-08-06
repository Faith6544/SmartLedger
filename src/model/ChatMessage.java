package model;

import java.sql.Timestamp;

public class ChatMessage {
    private int id;
    private int userId;
    private String rawText;
    private boolean isTransaction;
    private Timestamp createdAt;

    public ChatMessage(int userId, String rawText, boolean isTransaction) {
        this.userId = userId;
        this.rawText = rawText;
        this.isTransaction = isTransaction;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getRawText() { return rawText; }
    public boolean isTransaction() { return isTransaction; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
