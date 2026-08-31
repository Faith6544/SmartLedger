package commands;

import database.TransactionDAO;
import model.Transaction;
import model.TransactionType;
import java.util.List;

public class CommandHandler {

    private TransactionDAO transactionDAO = new TransactionDAO();

    public String handle(String text, int userId, String dashboardToken) {
        String lower = text.toLowerCase().trim();

        if (lower.contains("dashboard") || lower.contains("show my") || lower.contains("send my link")) {
            return "Here's your dashboard link:\nhttp://localhost:8080/dashboard/" + dashboardToken +
                   "\nOpen it in your browser to see all your records.";
        }

        if (lower.contains("profit")) {
            double sales = transactionDAO.getTotalByType(userId, TransactionType.SALE);
            double expenses = transactionDAO.getTotalByType(userId, TransactionType.EXPENSE);
            double supplies = transactionDAO.getTotalByType(userId, TransactionType.SUPPLY);
            double profit = sales - expenses - supplies;
            return String.format("Profit Summary:\nTotal Sales: ₦%,.2f\nTotal Expenses: ₦%,.2f\nTotal Supplies: ₦%,.2f\n----------------\nProfit: ₦%,.2f",
                sales, expenses, supplies, profit);
        }

        if ((lower.contains("how much") && (lower.contains("sell") || lower.contains("sold"))) || lower.contains("total sale")) {
            double total = transactionDAO.getTotalByType(userId, TransactionType.SALE);
            return String.format("Total Sales: ₦%,.2f", total);
        }

        if ((lower.contains("how much") && (lower.contains("spent") || lower.contains("spend"))) || lower.contains("total expense")) {
            double total = transactionDAO.getTotalByType(userId, TransactionType.EXPENSE);
            return String.format("Total Expenses: ₦%,.2f", total);
        }

        if (lower.contains("who owes") || lower.contains("debtors")) {
            List<Transaction> debts = transactionDAO.getDebts(userId);
            if (debts.isEmpty()) return "No one owes you right now.";
            StringBuilder sb = new StringBuilder("People who owe you:\n\n");
            double totalDebt = 0;
            for (Transaction t : debts) {
                String name = t.getCounterparty() != null ? t.getCounterparty() : "Unknown";
                sb.append("  ").append(name).append(" - ₦").append(String.format("%,.2f", t.getAmount())).append("\n");
                totalDebt += t.getAmount();
            }
            sb.append("\n----------------\n");
            sb.append(String.format("Total Owed: ₦%,.2f", totalDebt));
            return sb.toString();
        }

        if (lower.contains("cancel") || lower.contains("undo") || lower.contains("delete last")) {
            boolean deleted = transactionDAO.deleteLastTransaction(userId);
            return deleted ? "Last transaction deleted." : "No transactions to delete.";
        }

        if (lower.contains("summary") || lower.contains("report") || lower.contains("overview")) {
            double sales = transactionDAO.getTotalByType(userId, TransactionType.SALE);
            double expenses = transactionDAO.getTotalByType(userId, TransactionType.EXPENSE);
            double supplies = transactionDAO.getTotalByType(userId, TransactionType.SUPPLY);
            double debts = transactionDAO.getTotalByType(userId, TransactionType.DEBT);
            double payments = transactionDAO.getTotalByType(userId, TransactionType.PAYMENT);
            double deliveries = transactionDAO.getTotalByType(userId, TransactionType.DELIVERY);
            double profit = sales - expenses - supplies;
            return String.format("Business Summary:\n\nSales:       ₦%,.2f\nExpenses:    ₦%,.2f\nSupplies:    ₦%,.2f\nDebts Owed:  ₦%,.2f\nPayments In: ₦%,.2f\nDeliveries:  ₦%,.2f\n----------------\nProfit:      ₦%,.2f",
                sales, expenses, supplies, debts, payments, deliveries, profit);
        }

        if (lower.contains("help")) {
            return "How to use SmartLedger:\n\n" +
                "Record transactions by typing naturally:\n" +
                "  \"Sold 5 bags of rice for ₦100,000\"\n" +
                "  \"Bought 2 cartons of Milo ₦35,000\"\n" +
                "  \"Oga Musa owes me ₦12,000\"\n" +
                "  \"Paid ₦5,000 for transport\"\n" +
                "  \"Received ₦6,000 from Mama Tope\"\n" +
                "  \"Delivered rice worth ₦20,000 to Musa\"\n\n" +
                "Or force a category with tags:\n" +
                "  \"[sale] Rice to Mama Tope ₦20,000\"\n" +
                "  \"[debt] Oga Musa 5 bags ₦45,000\"\n\n" +
                "Commands:\n" +
                "  \"show my dashboard\" - open your dashboard\n" +
                "  \"what's my profit\" - profit summary\n" +
                "  \"who owes me\" - list all debts\n" +
                "  \"summary\" - full business overview\n" +
                "  \"undo\" - delete last transaction\n" +
                "  \"help\" - show this message";
        }

        return "I didn't understand that command. Type \"help\" to see what I can do.";
    }
}