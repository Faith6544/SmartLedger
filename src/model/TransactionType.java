package model;

public enum TransactionType {
    SALE,
    EXPENSE,
    SUPPLY,
    DEBT,
    PAYMENT,
    DELIVERY,
    PERSONAL  // ← Personal spending (excluded from profit)
}