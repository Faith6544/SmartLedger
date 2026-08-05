package commands;

import database.TransactionDAO;
import model.Transaction;
import model.TransactionType;
import java.util.List;

public class CommandHandler {

    private TransactionDAO transactionDAO = new TransactionDAO();

    /**
     * Processes a command and returns the response text.
     */
    public String handle(String text, int userId, String dashboardToken) {
        String lower = text.toLowerCase().trim();

        // Dashboard command
        if (lower.contains("dashboard") || lower.contains("show my") || lower.contains("send my link")) {
            return "Here's your dashboard link:\nhttp://localhost:8080/dashboard/" + dashboardToken +
                   "\nOpen it in your browser to see all your records.";
        }

        // Profit command
        if (lower.contains("profit")) {
            double sales = transactionDAO.getTotalByType(userId, TransactionType.SALE);
            double expenses = transactionDAO.getTotalByType(userId, TransactionType.EXPENSE);
            double supplies = transactionDAO.getTotalByType(userId, TransactionType.SUPPLY);
            double profit = sales - expenses - supplies;
            return String.format("📊 Profit Summary:\n" +
                "Total Sales: ₦%,.2f\n" +
                "Total Expenses: ₦%,.2f\n" +
                "Total Supplies: ₦%,.2f\n" +
                "────────────────\n" +
                "Profit: ₦%,.2f", sales, expenses, supplies, profit);
        }

        // Sales total
        if (lower.contains("how much") && lower.contains("sell") || lower.contains("how much") && lower.contains("sold") || lower.contains("total sale")) {
            double total = transactionDAO.getTotalByType(userId, TransactionType.SALE);
            return String.format("Total Sales: ₦%,.2f", total);
        }

        // Expense total
        if (lower.contains("how much") && lower.contains("spent") || lower.contains("how much") && lower.contains("spend") || lower.contains("total expense")) {
            double total = transactionDAO.getTotalByType(userId, TransactionType.EXPENSE);
            return String.format("Total Expenses: ₦%,.2f", total);
        }

        // Who owes me
        if (lower.contains("who owes") || lower.contains("debt") || lower.contains("debtors")) {
            List<Transaction> debts = transactionDAO.getDebts(userId);
            if (debts.isEmpty()) {
                return "No one owes you right now. Business is clean! ✅";
            }
            StringBuilder sb = new StringBuilder("💰 People who owe you:\n\n");
            double totalDebt = 0;
            for (Transaction t : debts) {
                String name = t.getCounterparty() != null ? t.getCounterparty() : "Unknown";
                sb.append("• ").append(name).append(" — ₦").append(String.format("%,.2f", t.getAmount()));
                sb.append("\n");
                totalDebt += t.getAmount();
            }
            sb.append("\n────────────────\n");
            sb.append(String.format("Total Owed: ₦%,.2f", totalDebt));
            return sb.toString();
        }

        // Undo / cancel last
        if (lower.contains("cancel") || lower.contains("undo") || lower.contains("delete last")) {
            boolean deleted = transactionDAO.deleteLastTransaction(userId);
            if (deleted) {
                return "✅ Last transaction deleted.";
            } else {
                return "No transactions to delete.";
            }
        }

        // Summary
        if (lower.contains("summary") || lower.contains("report") || lower.contains("overview")) {
            double sales = transactionDAO.getTotalByType(userId, TransactionType.SALE);
            double expenses = transactionDAO.getTotalByType(userId, TransactionType.EXPENSE);
            double supplies = transactionDAO.getTotalByType(userId, TransactionType.SUPPLY);
            double debts = transactionDAO.getTotalByType(userId, TransactionType.DEBT);
            double payments = transactionDAO.getTotalByType(userId, TransactionType.PAYMENT);
            double profit = sales - expenses - supplies;

            return String.format("📋 Business Summary:\n\n" +
                "Sales:       ₦%,.2f\n" +
                "Expenses:    ₦%,.2f\n" +
                "Supplies:    ₦%,.2f\n" +
                "Debts Owed:  ₦%,.2f\n" +
                "Payments In: ₦%,.2f\n" +
                "────────────────\n" +
                "Profit:      ₦%,.2f", sales, expenses, supplies, debts, payments, profit);
        }

        // Help
        if (lower.contains("help")) {
            return "📝 How to use SmartLedger:\n\n" +
                "Record transactions by typing naturally:\n" +
                "• \"Sold 5 bags of rice for ₦100,000\"\n" +
                "• \"Bought 2 cartons of Milo ₦35,000\"\n" +
                "• \"Oga Musa owes me ₦12,000\"\n" +
                "• \"Paid ₦5,000 for transport\"\n" +
                "• \"Received ₦6,000 from Mama Tope\"\n\n" +
                "Commands you can use:\n" +
                "• \"show my dashboard\" — open your dashboard\n" +
                "• \"what's my profit\" — see profit summary\n" +
                "• \"who owes me\" — list all debts\n" +
                "• \"summary\" — full business overview\n" +
                "• \"undo\" — delete last transaction\n" +
                "• \"help\" — show this message";
        }

        return "I didn't understand that command. Type \"help\" to see what I can do.";
    }
}
