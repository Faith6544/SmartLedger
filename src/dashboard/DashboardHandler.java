package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.TransactionDAO;
import database.ChatMessageDAO;
import database.UserDAO;
import model.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DashboardHandler implements HttpHandler {

    private UserDAO userDAO = new UserDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();
    private ChatMessageDAO chatMessageDAO = new ChatMessageDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract token from URL: /dashboard/{token}
        String path = exchange.getRequestURI().getPath();
        String token = path.replace("/dashboard/", "").replace("/", "");

        User user = userDAO.getUserByToken(token);
        if (user == null) {
            String error = "<html><body style='font-family:sans-serif;text-align:center;padding:50px;'>" +
                "<h1>404</h1><p>Dashboard not found. Check your link.</p></body></html>";
            sendResponse(exchange, 404, error);
            return;
        }

        // Build the dashboard HTML
        String html = buildDashboard(user);
        sendResponse(exchange, 200, html);
    }

    private String buildDashboard(User user) {
        int userId = user.getId();

        // Get totals
        double totalSales = transactionDAO.getTotalByType(userId, TransactionType.SALE);
        double totalExpenses = transactionDAO.getTotalByType(userId, TransactionType.EXPENSE);
        double totalSupplies = transactionDAO.getTotalByType(userId, TransactionType.SUPPLY);
        double totalDebts = transactionDAO.getTotalByType(userId, TransactionType.DEBT);
        double totalPayments = transactionDAO.getTotalByType(userId, TransactionType.PAYMENT);
        double profit = totalSales - totalExpenses - totalSupplies;

        // Get all transactions
        List<Transaction> transactions = transactionDAO.getAllByUser(userId);
        List<ChatMessage> chatMessages = chatMessageDAO.getAllByUser(userId);

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("<title>SmartLedger — ").append(user.getUsername()).append("</title>");
        html.append("<style>");
        html.append("* { margin: 0; padding: 0; box-sizing: border-box; }");
        html.append("body { font-family: 'Segoe UI', sans-serif; background: #1a1a2e; color: #e0e0e0; padding: 20px; }");
        html.append(".container { max-width: 900px; margin: 0 auto; }");
        html.append("h1 { color: #4CAF50; font-size: 28px; margin-bottom: 5px; }");
        html.append(".subtitle { color: #888; margin-bottom: 30px; }");
        html.append(".cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(160px, 1fr)); gap: 15px; margin-bottom: 30px; }");
        html.append(".card { background: #16213e; border-radius: 12px; padding: 20px; text-align: center; }");
        html.append(".card h3 { font-size: 13px; color: #888; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 8px; }");
        html.append(".card .value { font-size: 22px; font-weight: bold; }");
        html.append(".card.sales .value { color: #4CAF50; }");
        html.append(".card.expenses .value { color: #f44336; }");
        html.append(".card.supplies .value { color: #FF9800; }");
        html.append(".card.debts .value { color: #e91e63; }");
        html.append(".card.payments .value { color: #2196F3; }");
        html.append(".card.profit .value { color: ").append(profit >= 0 ? "#4CAF50" : "#f44336").append("; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 15px; }");
        html.append("th { background: #16213e; color: #4CAF50; padding: 12px 15px; text-align: left; font-size: 13px; text-transform: uppercase; }");
        html.append("td { padding: 10px 15px; border-bottom: 1px solid #2a2a4a; font-size: 14px; }");
        html.append("tr:hover { background: #16213e; }");
        html.append(".type-badge { padding: 3px 10px; border-radius: 12px; font-size: 11px; font-weight: bold; text-transform: uppercase; }");
        html.append(".type-SALE { background: #1b5e20; color: #4CAF50; }");
        html.append(".type-EXPENSE { background: #b71c1c33; color: #f44336; }");
        html.append(".type-SUPPLY { background: #e6510033; color: #FF9800; }");
        html.append(".type-DEBT { background: #88006633; color: #e91e63; }");
        html.append(".type-PAYMENT { background: #0d47a133; color: #2196F3; }");
        html.append(".section { background: #0f3460; border-radius: 12px; padding: 20px; margin-bottom: 25px; }");
        html.append(".section h2 { color: #4CAF50; font-size: 18px; margin-bottom: 10px; }");
        html.append(".chat-msg { padding: 8px 12px; margin: 5px 0; background: #16213e; border-radius: 8px; font-size: 13px; }");
        html.append(".chat-time { color: #666; font-size: 11px; }");
        html.append("</style></head><body>");

        html.append("<div class='container'>");
        html.append("<h1>SmartLedger</h1>");
        html.append("<p class='subtitle'>Dashboard for ").append(user.getUsername()).append("</p>");

        // Summary cards
        html.append("<div class='cards'>");
        html.append(card("Sales", totalSales, "sales"));
        html.append(card("Expenses", totalExpenses, "expenses"));
        html.append(card("Supplies", totalSupplies, "supplies"));
        html.append(card("Debts Owed", totalDebts, "debts"));
        html.append(card("Payments In", totalPayments, "payments"));
        html.append(card("Profit", profit, "profit"));
        html.append("</div>");

        // Transactions table
        html.append("<div class='section'>");
        html.append("<h2>All Transactions</h2>");
        if (transactions.isEmpty()) {
            html.append("<p style='color:#888;'>No transactions yet. Start typing in the chat!</p>");
        } else {
            html.append("<table><tr><th>Type</th><th>Amount</th><th>Description</th><th>Who</th><th>Date</th></tr>");
            for (Transaction t : transactions) {
                html.append("<tr>");
                html.append("<td><span class='type-badge type-").append(t.getType()).append("'>").append(t.getType()).append("</span></td>");
                html.append("<td>₦").append(String.format("%,.2f", t.getAmount())).append("</td>");
                html.append("<td>").append(escapeHtml(t.getDescription())).append("</td>");
                html.append("<td>").append(t.getCounterparty() != null ? escapeHtml(t.getCounterparty()) : "—").append("</td>");
                html.append("<td>").append(t.getCreatedAt() != null ? t.getCreatedAt().toString().substring(0, 16) : "—").append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
        }
        html.append("</div>");

        // Chat history
        html.append("<div class='section'>");
        html.append("<h2>Chat History</h2>");
        if (chatMessages.isEmpty()) {
            html.append("<p style='color:#888;'>No messages yet.</p>");
        } else {
            for (ChatMessage msg : chatMessages) {
                String icon = msg.isTransaction() ? "💰" : "💬";
                String time = msg.getCreatedAt() != null ? msg.getCreatedAt().toString().substring(0, 16) : "";
                html.append("<div class='chat-msg'>")
                    .append(icon).append(" ").append(escapeHtml(msg.getRawText()))
                    .append(" <span class='chat-time'>").append(time).append("</span>")
                    .append("</div>");
            }
        }
        html.append("</div>");

        html.append("</div></body></html>");
        return html.toString();
    }

    private String card(String title, double value, String cssClass) {
        return "<div class='card " + cssClass + "'>" +
            "<h3>" + title + "</h3>" +
            "<div class='value'>₦" + String.format("%,.2f", value) + "</div>" +
            "</div>";
    }

    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private void sendResponse(HttpExchange exchange, int code, String html) throws IOException {
        byte[] bytes = html.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
