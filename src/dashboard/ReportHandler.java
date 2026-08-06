package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.TransactionDAO;
import database.UserDAO;
import model.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ReportHandler implements HttpHandler {

    private UserDAO userDAO = new UserDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String token = path.replace("/report/", "").replace("/", "");
        User user = userDAO.getUserByToken(token);
        if (user == null) { send(exchange, 404, "Not found"); return; }
        send(exchange, 200, buildReport(user, token));
    }

    private String buildReport(User user, String token) {
        int uid = user.getId();
        double sales = transactionDAO.getTotalByType(uid, TransactionType.SALE);
        double expenses = transactionDAO.getTotalByType(uid, TransactionType.EXPENSE);
        double supplies = transactionDAO.getTotalByType(uid, TransactionType.SUPPLY);
        double debts = transactionDAO.getTotalByType(uid, TransactionType.DEBT);
        double payments = transactionDAO.getTotalByType(uid, TransactionType.PAYMENT);
        double profit = sales - expenses - supplies;

        List<Transaction> allTxns = transactionDAO.getAllByUser(uid);

        StringBuilder h = new StringBuilder();
        h.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        h.append("<meta name='viewport' content='width=device-width,initial-scale=1.0'>");
        h.append("<title>SmartLedger Report - ").append(HtmlTemplates.escapeHtml(user.getUsername())).append("</title>");
        h.append("<style>");
        h.append("*{margin:0;padding:0;box-sizing:border-box;}");
        h.append("body{font-family:'Segoe UI',sans-serif;color:#333;padding:30px;max-width:700px;margin:0 auto;}");
        h.append(".report-header{text-align:center;margin-bottom:30px;padding-bottom:20px;border-bottom:2px solid #4CAF50;}");
        h.append(".report-header img{width:50px;height:50px;}");
        h.append(".report-header h1{color:#2e7d32;font-size:24px;margin-top:8px;}");
        h.append(".report-header .biz{font-size:16px;color:#666;margin-top:4px;}");
        h.append(".report-header .date{font-size:12px;color:#aaa;margin-top:4px;}");
        h.append(".charts{display:flex;justify-content:center;gap:30px;margin:25px 0;flex-wrap:wrap;}");
        h.append("table{width:100%;border-collapse:collapse;margin-bottom:25px;}");
        h.append("th{background:#f5f5f5;padding:8px 10px;text-align:left;font-size:11px;text-transform:uppercase;color:#888;border-bottom:2px solid #eee;}");
        h.append("td{padding:8px 10px;border-bottom:1px solid #f0f0f0;font-size:12px;}");
        h.append(".cat-title{font-size:15px;color:#2e7d32;margin:20px 0 10px;padding-bottom:5px;border-bottom:1px solid #eee;}");
        h.append(".summary{display:flex;flex-wrap:wrap;gap:10px;margin-bottom:20px;}");
        h.append(".sum-card{flex:1;min-width:100px;text-align:center;padding:12px;background:#f9f9f9;border-radius:8px;}");
        h.append(".sum-card h4{font-size:9px;color:#999;text-transform:uppercase;margin-bottom:4px;}");
        h.append(".sum-card .val{font-size:16px;font-weight:700;}");
        h.append(".print-btn{display:block;width:100%;padding:12px;background:#4CAF50;color:#fff;border:none;border-radius:8px;font-size:15px;font-weight:600;cursor:pointer;margin-top:20px;}");
        h.append("@media print{.print-btn{display:none!important;} body{padding:10px;}}");
        h.append("</style></head><body>");

        // Back button (hidden on print)
        h.append("<div class='print-btn' style='margin-bottom:20px;'><a href='/dashboard/").append(token).append("' style='display:inline-block;padding:8px 16px;background:#4CAF50;color:#fff;border-radius:8px;text-decoration:none;font-size:13px;font-weight:600;'>&#8592; Back to Dashboard</a></div>");

        // Header with logo and business name
        h.append("<div class='report-header' style='background:linear-gradient(135deg,#e8f5e9,#c6edc3);border-radius:12px;padding:30px;border-bottom:none;'>");
        h.append("<div style='display:inline-block;width:60px;height:60px;background:#c6edc3;border-radius:50%;padding:10px;'>");
        h.append("<img src='").append(HtmlTemplates.LOGO_DATA).append("' style='width:40px;height:40px;'></div>");
        h.append("<h1>SmartLedger</h1>");
        if (!user.getBusinessName().isEmpty()) {
            h.append("<div class='biz'>").append(HtmlTemplates.escapeHtml(user.getBusinessName())).append("</div>");
        }
        h.append("<div class='date'>Report generated: ").append(java.time.LocalDate.now()).append("</div>");
        h.append("</div>");

        // Summary cards
        h.append("<div class='summary'>");
        h.append(sumCard("Sales", sales, "#2e7d32"));
        h.append(sumCard("Expenses", expenses, "#c62828"));
        h.append(sumCard("Supplies", supplies, "#e65100"));
        h.append(sumCard("Profit", profit, profit >= 0 ? "#2e7d32" : "#c62828"));
        h.append("</div>");

        // Charts
        h.append("<div class='charts'>");
        h.append(HtmlTemplates.barChart(sales, expenses, supplies, debts, payments));
        h.append(HtmlTemplates.pieChart(sales, expenses, supplies));
        h.append("</div>");

        // Transactions by category
        for (TransactionType type : TransactionType.values()) {
            java.util.List<Transaction> typed = new java.util.ArrayList<>();
            for (Transaction t : allTxns) { if (t.getType() == type) typed.add(t); }
            if (typed.isEmpty()) continue;

            h.append("<h3 class='cat-title'>").append(type.name()).append(" (").append(typed.size()).append(")</h3>");
            h.append("<table><tr><th>Date</th><th>Amount</th><th>Description</th><th>Who</th></tr>");
            for (Transaction t : typed) {
                h.append("<tr>");
                h.append("<td>").append(t.getCreatedAt() != null ? t.getCreatedAt().toString().substring(0, 10) : "—").append("</td>");
                h.append("<td style='font-weight:600;'>₦").append(HtmlTemplates.formatAmount(t.getAmount())).append("</td>");
                h.append("<td>").append(HtmlTemplates.escapeHtml(t.getDescription())).append("</td>");
                h.append("<td>").append(t.getCounterparty() != null ? HtmlTemplates.escapeHtml(t.getCounterparty()) : "—").append("</td>");
                h.append("</tr>");
            }
            h.append("</table>");
        }

        h.append("<button class='print-btn' onclick='window.print()'>Save as PDF</button>");
        h.append("</body></html>");
        return h.toString();
    }

    private String sumCard(String label, double val, String color) {
        return "<div class='sum-card'><h4>" + label + "</h4><div class='val' style='color:" + color + ";'>₦" + HtmlTemplates.formatAmount(val) + "</div></div>";
    }

    private void send(HttpExchange exchange, int code, String html) throws IOException {
        byte[] bytes = html.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
