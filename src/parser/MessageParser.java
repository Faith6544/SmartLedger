package parser;

import model.TransactionType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {

    // ===== STAGE 1: Tag prefix patterns =====
    private static final Pattern TAG_PATTERN = Pattern.compile(
        "^\\[(sale|expense|supply|debt|payment)\\]\\s*(.+)", Pattern.CASE_INSENSITIVE
    );

    // ===== STAGE 2: Primary keywords =====
    private static final String[] SALE_KEYS = {"sold", "sell", "sale", "i sell", "i sold", "customer bought", "bought from me"};
    private static final String[] SUPPLY_KEYS = {"bought", "buy", "purchased", "restock", "restocked", "i bought", "i buy"};
    private static final String[] EXPENSE_KEYS = {"paid", "pay", "spent", "spend", "used", "transport", "fare", "rent", "electricity", "data", "airtime", "i paid", "i pay", "i spent"};
    private static final String[] PAYMENT_KEYS = {"received", "collected", "payment from", "paid me", "pay me", "gave me", "settled", "cleared"};
    private static final String[] DEBT_KEYS = {"owes me", "owe me", "on credit", "no pay", "e no pay", "never pay", "pay me later", "go pay later", "not yet paid"};
    private static final String[] OWE_KEYS = {"i owe", "i still owe", "we owe"};
    private static final String[] LEND_KEYS = {"i lent", "i lend", "lent"};
    private static final String[] BORROW_KEYS = {"i borrow", "i borrowed", "borrowed"};

    // ===== STAGE 3: Modifier keywords that override =====
    private static final String[] CREDIT_MODIFIERS = {"credit", "later", "on credit", "go pay", "no pay", "next time"};
    private static final String[] AMBIGUOUS_KEYS = {"transfer", "transferred", "sent", "gave", "moved", "put"};

    /**
     * Main parse method. Returns a ParseResult with confidence level.
     */
    public ParseResult parse(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) {
            return ParseResult.noMatch();
        }

        String trimmed = rawText.trim();

        // ===== STAGE 1: Check for [tag] prefix =====
        Matcher tagMatcher = TAG_PATTERN.matcher(trimmed);
        if (tagMatcher.matches()) {
            String tag = tagMatcher.group(1).toUpperCase();
            String rest = tagMatcher.group(2);
            TransactionType forcedType = TransactionType.valueOf(tag);
            double amount = extractAmount(rest);
            if (amount <= 0) return ParseResult.noMatch();
            String counterparty = extractCounterparty(rest);
            return new ParseResult(forcedType, amount, rest, counterparty, ParseResult.Confidence.HIGH);
        }

        String lower = trimmed.toLowerCase();

        // ===== STAGE 2: Detect primary keyword =====
        TransactionType primaryType = detectPrimaryType(lower);

        // ===== STAGE 3: Check modifiers that override =====
        TransactionType finalType = applyModifiers(lower, primaryType);

        // Determine confidence
        ParseResult.Confidence confidence;
        if (finalType == null) {
            // Check if it's an ambiguous keyword
            if (hasAny(lower, AMBIGUOUS_KEYS)) {
                double amount = extractAmount(trimmed);
                if (amount > 0) {
                    String counterparty = extractCounterparty(trimmed);
                    return new ParseResult(TransactionType.EXPENSE, amount, trimmed, counterparty, ParseResult.Confidence.LOW);
                }
            }
            return ParseResult.noMatch();
        }

        double amount = extractAmount(trimmed);
        if (amount <= 0) {
            return ParseResult.noMatch();
        }

        // If modifiers changed the type, confidence is still HIGH (we have a clear rule)
        confidence = ParseResult.Confidence.HIGH;

        String counterparty = extractCounterparty(trimmed);
        return new ParseResult(finalType, amount, trimmed, counterparty, confidence);
    }

    /**
     * Detects the primary transaction type from keywords.
     */
    private TransactionType detectPrimaryType(String lower) {
        // "I owe" = EXPENSE (you owe someone)
        if (hasAny(lower, OWE_KEYS)) return TransactionType.EXPENSE;

        // "owes me" = DEBT (someone owes you)
        if (hasAny(lower, DEBT_KEYS)) return TransactionType.DEBT;

        // "owes" without "me" — still likely debt (e.g. "Musa owes 5k")
        if (lower.contains("owes")) return TransactionType.DEBT;

        // "I lent" = DEBT (someone owes you)
        if (hasAny(lower, LEND_KEYS)) return TransactionType.DEBT;

        // "I borrowed" = EXPENSE
        if (hasAny(lower, BORROW_KEYS)) return TransactionType.EXPENSE;

        // SALE
        if (hasAny(lower, SALE_KEYS)) return TransactionType.SALE;

        // PAYMENT received
        if (hasAny(lower, PAYMENT_KEYS)) return TransactionType.PAYMENT;

        // SUPPLY (before expense — "bought" is supply, not expense)
        if (hasAny(lower, SUPPLY_KEYS)) return TransactionType.SUPPLY;

        // EXPENSE (last — most general)
        if (hasAny(lower, EXPENSE_KEYS)) return TransactionType.EXPENSE;

        return null;
    }

    /**
     * Applies modifier overrides to the primary type.
     * E.g., "sold on credit" → SALE becomes DEBT
     */
    private TransactionType applyModifiers(String lower, TransactionType primary) {
        if (primary == null) return null;

        boolean hasCreditModifier = hasAny(lower, CREDIT_MODIFIERS);

        // "Sold on credit" → DEBT (money hasn't come in)
        if (primary == TransactionType.SALE && hasCreditModifier) {
            return TransactionType.DEBT;
        }

        // "Bought on credit" → still SUPPLY but also creates debt awareness
        // For simplicity, keep as SUPPLY — the trader knows they owe
        // They can manually add a debt entry if needed

        return primary;
    }

    private boolean hasAny(String text, String... keywords) {
        for (String k : keywords) {
            if (text.contains(k)) return true;
        }
        return false;
    }

    /**
     * Extracts the naira amount. Handles ₦100,000 | 100000 | ₦5k | 5k | N50,000
     */
    public double extractAmount(String text) {
        String cleaned = text.replace(",", "").replace("naira", "").replace("Naira", "");
        Pattern p = Pattern.compile("[₦N]?\\s?(\\d+(?:\\.\\d{1,2})?)\\s*(k|K)?");
        Matcher m = p.matcher(cleaned);

        double maxAmount = 0;
        while (m.find()) {
            try {
                double amount = Double.parseDouble(m.group(1));
                if (m.group(2) != null) amount *= 1000;
                if (amount > maxAmount) maxAmount = amount;
            } catch (NumberFormatException e) { }
        }
        return maxAmount;
    }

    /**
     * Extracts counterparty name from the message.
     */
    public String extractCounterparty(String text) {
        String lower = text.toLowerCase();

        // "[Name] owes"
        if (lower.contains("owes") || lower.contains("owe")) {
            int idx = lower.indexOf("owe");
            if (idx > 0) {
                String before = text.substring(0, idx).trim();
                String[] words = before.split("\\s+");
                int start = Math.max(0, words.length - 3);
                StringBuilder name = new StringBuilder();
                for (int i = start; i < words.length; i++) {
                    String w = words[i].toLowerCase();
                    if (w.equals("i") || w.equals("we") || w.equals("still") || w.equals("also")) continue;
                    if (name.length() > 0) name.append(" ");
                    name.append(words[i]);
                }
                String result = name.toString().trim();
                if (!result.isEmpty() && !isCommon(result.toLowerCase())) return result;
            }
        }

        // "I owe [Name]"
        if (lower.contains("i owe ")) {
            int idx = lower.indexOf("i owe ") + 6;
            return extractNameAfter(text, idx);
        }

        // "from [Name]"
        if (lower.contains(" from ")) {
            int idx = lower.indexOf(" from ") + 6;
            return extractNameAfter(text, idx);
        }

        // "to [Name]"
        if (lower.contains(" to ") && !lower.contains("to buy") && !lower.contains("to pay") && !lower.contains("to get")) {
            int idx = lower.indexOf(" to ") + 4;
            return extractNameAfter(text, idx);
        }

        // "lent [Name]"
        if (lower.contains("lent ")) {
            int idx = lower.indexOf("lent ") + 5;
            return extractNameAfter(text, idx);
        }

        // "sold ... to [Name] on credit"
        if (lower.contains("sold") && lower.contains(" to ")) {
            int idx = lower.indexOf(" to ") + 4;
            return extractNameAfter(text, idx);
        }

        return null;
    }

    private String extractNameAfter(String text, int startIdx) {
        if (startIdx >= text.length()) return null;
        String after = text.substring(startIdx).trim();
        String[] words = after.split("\\s+");
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < Math.min(words.length, 3); i++) {
            String word = words[i];
            if (word.matches(".*\\d.*") || word.toLowerCase().matches("(for|today|on|credit|₦|naira|the|a)")) break;
            if (name.length() > 0) name.append(" ");
            name.append(word);
        }
        String result = name.toString().trim();
        if (!result.isEmpty() && !isCommon(result.toLowerCase())) return result;
        return null;
    }

    private boolean isCommon(String word) {
        String[] common = {"i", "me", "my", "the", "a", "an", "for", "and", "to", "of", "it", "is", "was", "someone", "somebody", "him", "her"};
        for (String c : common) { if (c.equals(word)) return true; }
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
