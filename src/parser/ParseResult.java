package parser;

import model.TransactionType;

public class ParseResult {

    public enum Confidence { HIGH, LOW, NONE }

    private TransactionType type;
    private double amount;
    private String description;
    private String counterparty;
    private Confidence confidence;

    public ParseResult(TransactionType type, double amount, String description, String counterparty, Confidence confidence) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.counterparty = counterparty;
        this.confidence = confidence;
    }

    public static ParseResult noMatch() {
        return new ParseResult(null, 0, null, null, Confidence.NONE);
    }

    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getCounterparty() { return counterparty; }
    public Confidence getConfidence() { return confidence; }

    public void setType(TransactionType type) { this.type = type; }
    public void setConfidence(Confidence confidence) { this.confidence = confidence; }

    public boolean isTransaction() { return confidence != Confidence.NONE && type != null && amount > 0; }
}
