package model;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int userId;
    private TransactionType type;
    private double amount;
    private String description;
    private String counterparty;
    private Timestamp createdAt;

    public Transaction(int userId, TransactionType type, double amount, String description, String counterparty) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.counterparty = counterparty;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getCounterparty() { return counterparty; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setType(TransactionType type) { this.type = type; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return type + " | " + String.format("₦%,.2f", amount) + " | " + description;
    }
}
