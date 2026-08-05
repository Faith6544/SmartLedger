package parser;

import model.Transaction;
import model.TransactionType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {

    // Regex to match naira amounts: ₦100,000 or ₦100000 or 100,000 or 100000 or ₦5k or 5k
    private static final Pattern AMOUNT_PATTERN = Pattern.compile(
        "[₦N]?\\s?([\\d,]+(?:\\.\\d{1,2})?)\\s*(k|K|thousand)?|([\\d,]+(?:\\.\\d{1,2})?)\\s*(k|K|thousand)?"
    );

    // Keywords for each transaction type
    private static final String[] SALE_KEYWORDS = {
        "sold", "sell", "sale", "i sell", "i sold", "customer bought", "bought from me"
    };
    private static final String[] SUPPLY_KEYWORDS = {
        "bought", "buy", "purchased", "restock", "restocked", "supplied", "i bought", "i buy"
    };
    private static final String[] DEBT_KEYWORDS = {
        "owes", "owe", "on credit", "credit", "lend", "lent", "borrow", "not yet paid",
        "no pay", "e no pay", "never pay", "go pay later", "pay me later"
    };
    private static final String[] PAYMENT_KEYWORDS = {
        "received", "collected", "payment from", "paid me", "pay me", "returned",
        "gave me", "settled", "cleared"
    };
    private static final String[] EXPENSE_KEYWORDS = {
        "paid", "pay", "spent", "spend", "used", "transport", "fare", "rent",
        "electricity", "data", "airtime", "i paid", "i pay", "i spent"
    };

    /**
     * Parses a raw message and returns a Transaction if it matches a pattern,
     * or null if it's just a casual message.
     */
    public Transaction parse(String rawText, int userId) {
        if (rawText == null || rawText.trim().isEmpty()) {
            return null;
        }

        String lower = rawText.toLowerCase().trim();

        // Detect transaction type
        TransactionType type = detectType(lower);
        if (type == null) {
            return null; // Not a transaction — save as chat message
        }

        // Extract amount
        double amount = extractAmount(rawText);
        if (amount <= 0) {
            return null; // No valid amount found — treat as chat
        }

        // Extract counterparty name (if any)
        String counterparty = extractCounterparty(rawText);

        return new Transaction(userId, type, amount, rawText, counterparty);
    }

    /**
     * Detects the transaction type based on keywords.
     * Order matters — more specific keywords are checked first.
     */
    private TransactionType detectType(String lower) {
        // Check SALE first (sold, sell)
        for (String keyword : SALE_KEYWORDS) {
            if (lower.contains(keyword)) {
                return TransactionType.SALE;
            }
        }

        // Check DEBT (owes, credit, lend)
        for (String keyword : DEBT_KEYWORDS) {
            if (lower.contains(keyword)) {
                return TransactionType.DEBT;
            }
        }

        // Check PAYMENT received (received, collected, paid me)
        for (String keyword : PAYMENT_KEYWORDS) {
            if (lower.contains(keyword)) {
                return TransactionType.PAYMENT;
            }
        }

        // Check SUPPLY (bought, restock) — must come before EXPENSE
        for (String keyword : SUPPLY_KEYWORDS) {
            if (lower.contains(keyword)) {
                return TransactionType.SUPPLY;
            }
        }

        // Check EXPENSE last (paid, spent) — most general
        for (String keyword : EXPENSE_KEYWORDS) {
            if (lower.contains(keyword)) {
                return TransactionType.EXPENSE;
            }
        }

        return null; // No transaction keyword found
    }

    /**
     * Extracts the naira amount from the message.
     * Handles: ₦100,000 | 100000 | ₦5k | 5k | N50,000
     */
    private double extractAmount(String text) {
        // Clean up: replace common variations
        String cleaned = text.replace(",", "").replace("naira", "").replace("Naira", "");

        // Pattern: optional ₦ or N, then digits, optional k/K
        Pattern p = Pattern.compile("[₦N]?\\s?(\\d+(?:\\.\\d{1,2})?)\\s*(k|K)?");
        Matcher m = p.matcher(cleaned);

        double maxAmount = 0;
        while (m.find()) {
            try {
                double amount = Double.parseDouble(m.group(1));
                if (m.group(2) != null) {
                    amount *= 1000; // "5k" = 5000
                }
                if (amount > maxAmount) {
                    maxAmount = amount; // Take the largest amount found
                }
            } catch (NumberFormatException e) {
                // Skip invalid numbers
            }
        }
        return maxAmount;
    }

    /**
     * Tries to extract a counterparty name from the message.
     * Looks for names after keywords like "from", "to", "owes", etc.
     */
    private String extractCounterparty(String text) {
        String lower = text.toLowerCase();

        // Patterns: "Oga Musa owes...", "...from Mama Tope", "...to Chidi"
        String[][] patterns = {
            {"from ", " "},   // "received ₦5000 from Mama Tope"
            {"to ", " "},     // "paid ₦5000 to Oga Musa"
        };

        // Try "owes" pattern: "[Name] owes me"
        if (lower.contains("owes") || lower.contains("owe")) {
            int idx = lower.indexOf("owe");
            if (idx > 0) {
                String before = text.substring(0, idx).trim();
                // Take last 1-3 words before "owes" as the name
                String[] words = before.split("\\s+");
                int start = Math.max(0, words.length - 3);
                StringBuilder name = new StringBuilder();
                for (int i = start; i < words.length; i++) {
                    if (name.length() > 0) name.append(" ");
                    name.append(words[i]);
                }
                String result = name.toString().trim();
                if (!result.isEmpty() && !isCommonWord(result.toLowerCase())) {
                    return result;
                }
            }
        }

        // Try "from [Name]" pattern
        if (lower.contains(" from ")) {
            int idx = lower.indexOf(" from ") + 6;
            String after = text.substring(idx).trim();
            String[] words = after.split("\\s+");
            int end = Math.min(words.length, 3);
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < end; i++) {
                String word = words[i];
                // Stop at amount or common words
                if (word.matches(".*\\d.*") || word.equals("for") || word.equals("today")) break;
                if (name.length() > 0) name.append(" ");
                name.append(word);
            }
            String result = name.toString().trim();
            if (!result.isEmpty() && !isCommonWord(result.toLowerCase())) {
                return result;
            }
        }

        return null; // No counterparty found
    }

    private boolean isCommonWord(String word) {
        String[] common = {"i", "me", "my", "the", "a", "an", "for", "and", "to", "of", "it", "is", "was"};
        for (String c : common) {
            if (c.equals(word)) return true;
        }
        return false;
    }

    /**
     * Checks if a message is a command (not a transaction).
     */
    public boolean isCommand(String text) {
        String lower = text.toLowerCase().trim();
        return lower.contains("show") || lower.contains("dashboard") ||
               lower.contains("how much") || lower.contains("total") ||
               lower.contains("who owes") || lower.contains("profit") ||
               lower.contains("summary") || lower.contains("report") ||
               lower.contains("cancel") || lower.contains("undo") ||
               lower.contains("delete last") || lower.contains("help");
    }
}
