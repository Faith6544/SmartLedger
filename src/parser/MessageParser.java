package parser;

import model.TransactionType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {

    private static final Pattern TAG_PATTERN = Pattern.compile(
        "^\\[(sale|expense|supply|debt|payment)\\]\\s*(.+)", Pattern.CASE_INSENSITIVE
    );

    private static final String[] SALE_KEYS = {"sold", "sell", "sale", "i sell", "i sold", "customer bought", "bought from me"};
    private static final String[] SUPPLY_KEYS = {"bought", "buy", "purchased", "restock", "restocked", "i bought", "i buy"};
    private static final String[] EXPENSE_KEYS = {"paid", "pay", "spent", "spend", "used", "transport", "fare", "rent", "electricity", "data", "airtime", "i paid", "i pay", "i spent"};
    private static final String[] PAYMENT_KEYS = {"received", "collected", "payment from", "paid me", "pay me", "gave me", "settled", "cleared"};
    private static final String[] DEBT_KEYS = {"owes me", "owe me", "on credit", "no pay", "e no pay", "never pay", "pay me later", "go pay later", "not yet paid"};
    private static final String[] OWE_KEYS = {"i owe", "i still owe", "we owe"};
    private static final String[] CREDIT_MODIFIERS = {"credit", "later", "on credit", "go pay", "no pay", "next time"};
    private static final String[] AMBIGUOUS_KEYS = {"transfer", "transferred", "sent", "moved", "put"};
    // Kept separate from AMBIGUOUS_KEYS/"sent" above since "sent" alone is ambiguous (could be a
    // money transfer) - these words are unambiguously about physically delivering goods
    private static final String[] DELIVERY_KEYS = {"deliver", "delivered", "delivery", "dispatch", "dispatched", "drop off", "dropped off", "drop-off"};

    public ParseResult parse(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) return ParseResult.noMatch();

        String trimmed = rawText.trim();

        // STAGE 1: [tag] prefix
        Matcher tagMatcher = TAG_PATTERN.matcher(trimmed);
        if (tagMatcher.matches()) {
            String tag = tagMatcher.group(1).toUpperCase();
            String rest = tagMatcher.group(2);
            TransactionType forcedType = TransactionType.valueOf(tag);
            double amount = extractAmount(rest);
            if (amount <= 0) return ParseResult.noMatch();
            return new ParseResult(forcedType, amount, rest, extractCounterparty(rest), ParseResult.Confidence.HIGH);
        }

        String lower = trimmed.toLowerCase();

        // STAGE 2: Detect primary keyword
        TransactionType primaryType = detectPrimaryType(lower);

        // STAGE 3: Modifier overrides
        TransactionType finalType = applyModifiers(lower, primaryType);

        if (finalType == null) {
            double amount = extractAmount(trimmed);
            if (amount > 0) {
                // Check for "I borrowed" - ambiguous in Nigerian English
                if (lower.contains("i borrowed") || lower.contains("i borrow")) {
                    return new ParseResult(TransactionType.DEBT, amount, trimmed, extractCounterparty(trimmed), ParseResult.Confidence.LOW);
                }
                // Check for other ambiguous keywords
                if (hasAny(lower, AMBIGUOUS_KEYS)) {
                    return new ParseResult(TransactionType.EXPENSE, amount, trimmed, extractCounterparty(trimmed), ParseResult.Confidence.LOW);
                }
            }
            return ParseResult.noMatch();
        }

        double amount = extractAmount(trimmed);
        if (amount <= 0) return ParseResult.noMatch();

        return new ParseResult(finalType, amount, trimmed, extractCounterparty(trimmed), ParseResult.Confidence.HIGH);
    }

    private TransactionType detectPrimaryType(String lower) {
        // DELIVERY - check first since it's unambiguous and specific
        if (hasAnyFuzzy(lower, DELIVERY_KEYS)) return TransactionType.DELIVERY;

        // "I owe" = EXPENSE
        if (hasAny(lower, OWE_KEYS)) return TransactionType.EXPENSE;

        // "owes me" = DEBT
        if (hasAny(lower, DEBT_KEYS)) return TransactionType.DEBT;

        // "owes" without "me" - still DEBT ("Musa owes 5k")
        if (lower.contains("owes")) return TransactionType.DEBT;

        // === BORROWED FIX ===
        // "I borrowed" is ambiguous in Nigerian English - could mean "I lent" (DEBT) or "I took a loan" (EXPENSE)
        // Mark as null here, handle as LOW confidence in parse() method
        if (lower.contains("i borrowed") || lower.contains("i borrow")) return null;
        // "[Name] borrowed" = DEBT (they owe you)
        if (lower.contains("borrowed") || lower.contains("borrow")) return TransactionType.DEBT;

        // === LENT FIX ===
        // "I lent [Name]" = DEBT (someone owes you)
        if (lower.contains("i lent") || lower.contains("i lend") || lower.contains("lent")) return TransactionType.DEBT;

        // "gave" - directional check
        if (lower.contains("i gave") || lower.contains("gave")) {
            // "gave me" = PAYMENT (someone paid you)
            if (lower.contains("gave me")) return TransactionType.PAYMENT;
            // "I gave" = EXPENSE (you spent)
            if (lower.contains("i gave")) return TransactionType.EXPENSE;
        }

        // SALE
        if (hasAnyFuzzy(lower, SALE_KEYS)) return TransactionType.SALE;

        // PAYMENT received
        if (hasAnyFuzzy(lower, PAYMENT_KEYS)) return TransactionType.PAYMENT;

        // SUPPLY
        if (hasAnyFuzzy(lower, SUPPLY_KEYS)) return TransactionType.SUPPLY;

        // EXPENSE (last - most general)
        if (hasAnyFuzzy(lower, EXPENSE_KEYS)) return TransactionType.EXPENSE;

        return null;
    }

    private TransactionType applyModifiers(String lower, TransactionType primary) {
        if (primary == null) return null;
        boolean hasCreditModifier = hasAny(lower, CREDIT_MODIFIERS);
        if (primary == TransactionType.SALE && hasCreditModifier) return TransactionType.DEBT;
        return primary;
    }

    private boolean hasAny(String text, String... keywords) {
        for (String k : keywords) { if (text.contains(k)) return true; }
        return false;
    }

    // Same as hasAny() but also catches a single-letter typo (missing, extra, or swapped letter) -
    // "soold" still matches "sold". Only applied to keywords 4+ letters with no spaces, checked
    // against words 4+ letters in the message, to avoid short words like "buy"/"pay" false-matching
    // on unrelated common words ("but", "day", etc.)
    private boolean hasAnyFuzzy(String text, String... keywords) {
        if (hasAny(text, keywords)) return true;
        String[] words = text.split("\\s+");
        for (String keyword : keywords) {
            if (keyword.contains(" ") || keyword.length() < 4) continue;
            for (String word : words) {
                String cleanWord = word.replaceAll("[^a-z]", "");
                if (cleanWord.length() < 4) continue;
                if (levenshtein(cleanWord, keyword) <= 1) return true;
            }
        }
        return false;
    }

    private int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }
        return dp[a.length()][b.length()];
    }

    // Words that usually mean the number right before them is a QUANTITY, not a price
    // (e.g. "5 bags of water" - the 5 is how many, not how much it cost)
    private static final String[] UNIT_WORDS = {
        "bag", "bags", "carton", "cartons", "piece", "pieces", "pcs", "pc", "kg", "kilogram", "kilograms",
        "litre", "litres", "liter", "liters", "sack", "sacks", "dozen", "basket", "baskets", "tin", "tins",
        "can", "cans", "plate", "plates", "cup", "cups", "bottle", "bottles", "pack", "packs", "pair", "pairs",
        "roll", "rolls", "crate", "crates", "bundle", "bundles", "yard", "yards", "meter", "meters"
    };
    // Words that usually introduce the actual price
    private static final String[] PRICE_INTRO_WORDS = {"for", "at", "cost", "costs", "worth", "price", "priced"};

    public double extractAmount(String text) {
        String cleaned = text.replace(",", "").replace("naira", "").replace("Naira", "");
        Pattern p = Pattern.compile("([₦N]?)\\s?(\\d+(?:\\.\\d{1,2})?)\\s*(k|K)?");
        Matcher m = p.matcher(cleaned);

        double bestMarked = 0;   // has ₦/N/naira/k - clearly a currency amount
        double bestUnmarked = 0; // plain number, no currency marker, not next to a unit word

        while (m.find()) {
            String currencyMark = m.group(1);
            String thousandsMark = m.group(3);
            try {
                double amount = Double.parseDouble(m.group(2));
                if (thousandsMark != null) amount *= 1000;

                boolean hasCurrencyMark = (currencyMark != null && !currencyMark.isEmpty()) || thousandsMark != null;
                boolean precededByPriceWord = precededByAny(cleaned, m.start(), PRICE_INTRO_WORDS);
                boolean followedByUnitWord = followedByAny(cleaned, m.end(), UNIT_WORDS);

                if (hasCurrencyMark || precededByPriceWord) {
                    if (amount > bestMarked) bestMarked = amount;
                } else if (!followedByUnitWord) {
                    // Plain number with no marker and not immediately followed by a unit word
                    // ("5" in "5 bags" is skipped; "15000" with nothing after it still counts)
                    if (amount > bestUnmarked) bestUnmarked = amount;
                }
            } catch (NumberFormatException e) { }
        }

        // Prefer a clearly-marked amount (₦, naira, k, or after "for"/"at"/"worth") over a guess
        return bestMarked > 0 ? bestMarked : bestUnmarked;
    }

    private boolean precededByAny(String text, int pos, String[] words) {
        String before = text.substring(0, pos).toLowerCase();
        for (String w : words) {
            if (before.endsWith(w + " ") || before.endsWith(w)) return true;
        }
        return false;
    }

    private boolean followedByAny(String text, int pos, String[] words) {
        String after = text.substring(Math.min(pos, text.length())).toLowerCase().trim();
        for (String w : words) {
            if (after.startsWith(w)) return true;
        }
        return false;
    }

    public String extractCounterparty(String text) {
        String lower = text.toLowerCase();

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

        // "[Name] borrowed" - name before "borrowed"
        if (lower.contains("borrowed") || lower.contains("borrow")) {
            int idx = lower.indexOf("borrow");
            if (idx > 0) {
                String before = text.substring(0, idx).trim();
                String[] words = before.split("\\s+");
                int start = Math.max(0, words.length - 3);
                StringBuilder name = new StringBuilder();
                for (int i = start; i < words.length; i++) {
                    String w = words[i].toLowerCase();
                    if (w.equals("i") || w.equals("we") || w.equals("my")) continue;
                    if (name.length() > 0) name.append(" ");
                    name.append(words[i]);
                }
                String result = name.toString().trim();
                if (!result.isEmpty() && !isCommon(result.toLowerCase())) return result;
            }
        }

        if (lower.contains("i owe ")) { int idx = lower.indexOf("i owe ") + 6; return extractNameAfter(text, idx); }
        if (lower.contains(" from ")) { int idx = lower.indexOf(" from ") + 6; return extractNameAfter(text, idx); }
        if (lower.contains(" to ") && !lower.contains("to buy") && !lower.contains("to pay") && !lower.contains("to get")) { int idx = lower.indexOf(" to ") + 4; return extractNameAfter(text, idx); }
        if (lower.contains("lent ")) { int idx = lower.indexOf("lent ") + 5; return extractNameAfter(text, idx); }
        if (lower.contains("sold") && lower.contains(" to ")) { int idx = lower.indexOf(" to ") + 4; return extractNameAfter(text, idx); }

        return null;
    }

    private String extractNameAfter(String text, int startIdx) {
        if (startIdx >= text.length()) return null;
        String after = text.substring(startIdx).trim();
        String[] words = after.split("\\s+");
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < Math.min(words.length, 3); i++) {
            String word = words[i];
            if (word.matches(".*\\d.*") || word.toLowerCase().matches("(for|today|on|credit|naira|the|a)")) break;
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

    public boolean isCommand(String text) {
        String lower = text.toLowerCase().trim();
        return lower.contains("show") || lower.contains("dashboard") || lower.contains("how much") || lower.contains("total") ||
               lower.contains("who owes") || lower.contains("profit") || lower.contains("summary") || lower.contains("report") ||
               lower.contains("cancel") || lower.contains("undo") || lower.contains("delete last") || lower.contains("help");
    }
}