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
        if (user == null) { exchange.getResponseHeaders().set("Location", "/auth/login"); exchange.sendResponseHeaders(302, -1); return; }
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
        h.append("<title>SmartLedger Audit &amp; Statement - ").append(HtmlTemplates.escapeHtml(user.getUsername())).append("</title>");
        h.append("<link rel='preconnect' href='https://fonts.googleapis.com'>");
        h.append("<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>");
        h.append("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800;900&display=swap' rel='stylesheet'>");
        h.append("<style>");
        h.append("*{margin:0;padding:0;box-sizing:border-box;}");
        h.append("body{font-family:'Inter',-apple-system,sans-serif;color:#111827;padding:40px 30px;max-width:800px;margin:0 auto;background:#ffffff;}");
        h.append(".report-header{margin-bottom:30px;padding-bottom:20px;border-bottom:2px solid #111827;display:flex;justify-content:space-between;align-items:flex-end;}");
        h.append(".report-header img{width:40px;height:40px;}");
        h.append(".report-title{font-size:26px;font-weight:900;letter-spacing:-0.5px;text-transform:uppercase;}");
        h.append(".report-header .biz{font-size:14px;font-weight:700;color:#4b5563;text-transform:uppercase;letter-spacing:0.5px;margin-top:4px;}");
        h.append(".report-header .date{font-size:11px;font-weight:700;color:#6b7280;text-transform:uppercase;letter-spacing:0.8px;}");
        h.append(".charts{display:flex;justify-content:center;gap:20px;margin:25px 0;flex-wrap:wrap;}");
        h.append("table{width:100%;border-collapse:collapse;margin-bottom:25px;font-size:12px;}");
        h.append("th{background:#f4f4f5;padding:8px 10px;text-align:left;font-size:10px;font-weight:900;text-transform:uppercase;letter-spacing:0.8px;color:#111827;border-top:1.5px solid #111827;border-bottom:1.5px solid #111827;}");
        h.append("td{padding:10px;border-bottom:1px solid #e5e7eb;font-weight:500;}");
        h.append(".cat-title{font-size:12px;font-weight:900;color:#111827;margin:24px 0 10px;padding-bottom:4px;border-bottom:1.5px solid #111827;letter-spacing:0.8px;text-transform:uppercase;}");
        h.append(".summary{display:grid;grid-template-columns:repeat(auto-fit,minmax(120px,1fr));gap:10px;margin-bottom:24px;}");
        h.append(".sum-card{padding:14px 12px;background:#ffffff;border:1.5px solid #111827;border-radius:2px;}");
        h.append(".sum-card h4{font-size:10px;font-weight:900;color:#6b7280;text-transform:uppercase;letter-spacing:0.8px;margin-bottom:4px;}");
        h.append(".sum-card .val{font-size:18px;font-weight:900;letter-spacing:-0.5px;}");
        h.append(".print-btn{display:block;width:100%;padding:12px;background:#2e7d32;color:#fff;border:1.5px solid #111827;border-radius:2px;font-size:12px;font-weight:900;letter-spacing:0.8px;text-transform:uppercase;cursor:pointer;margin-top:20px;}");
        h.append(".back-btn{display:inline-block;padding:8px 16px;background:#ffffff;color:#111827;border:1.5px solid #111827;border-radius:2px;text-decoration:none;font-size:11px;font-weight:800;letter-spacing:0.5px;text-transform:uppercase;margin-bottom:20px;}");
        h.append("@media print{.print-btn,.back-btn{display:none!important;} body{padding:15px;max-width:100%;}}");
        h.append("</style></head><body>");

        // Back button (hidden on print)
        h.append("<a href='/dashboard/").append(token).append("' class='back-btn'>&larr; BACK TO DASHBOARD</a>");

        // Header with logo and business name
        h.append("<div class='report-header'>");
        h.append("<div>");
        h.append("<div style='display:flex;align-items:center;gap:10px;margin-bottom:8px;'>");
        h.append("<div style='display:inline-flex;align-items:center;justify-content:center;width:36px;height:36px;background:#ffffff;border:1.5px solid #111827;border-radius:2px;'>");
        h.append("<img src='").append(HtmlTemplates.LOGO_DATA).append("' style='width:24px;height:24px;'></div>");
        h.append("<h1 class='report-title'>SMARTLEDGER</h1></div>");
        if (!user.getBusinessName().isEmpty()) {
            h.append("<div class='biz'>").append(HtmlTemplates.escapeHtml(user.getBusinessName())).append("</div>");
        }
        h.append("</div>");
        h.append("<div class='date'>STATEMENT DATE: ").append(java.time.LocalDate.now()).append("<br>MERCHANT: ").append(HtmlTemplates.escapeHtml(user.getUsername()).toUpperCase()).append("</div>");
        h.append("</div>");

        // Summary cards
        h.append("<div class='summary'>");
        h.append(sumCard("Gross Sales", sales, "#2e7d32"));
        h.append(sumCard("Expenses", expenses, "#c62828"));
        h.append(sumCard("Supplies", supplies, "#e65100"));
        h.append(sumCard("Net Margin", profit, profit >= 0 ? "#2e7d32" : "#c62828"));
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

            h.append("<h3 class='cat-title'>").append(type.name()).append(" ENTRIES (").append(typed.size()).append(")</h3>");
            h.append("<table><tr><th>DATE</th><th>AMOUNT</th><th>DESCRIPTION</th><th>COUNTERPARTY</th></tr>");
            for (Transaction t : typed) {
                h.append("<tr>");
                h.append("<td>").append(t.getCreatedAt() != null ? t.getCreatedAt().toString().substring(0, 10) : "—").append("</td>");
                h.append("<td style='font-weight:800;font-variant-numeric:tabular-nums;'>&#8358;").append(HtmlTemplates.formatAmount(t.getAmount())).append("</td>");
                h.append("<td>").append(HtmlTemplates.escapeHtml(t.getDescription())).append("</td>");
                h.append("<td>").append(t.getCounterparty() != null ? HtmlTemplates.escapeHtml(t.getCounterparty()) : "—").append("</td>");
                h.append("</tr>");
            }
            h.append("</table>");
        }

        h.append("<button class='print-btn' onclick='window.print()'>PRINT / EXPORT AUDIT STATEMENT (PDF)</button>");
        h.append("</body></html>");
        return h.toString();
    }

    private String sumCard(String label, double val, String color) {
        return "<div class='sum-card'><h4>" + label + "</h4><div class='val' style='color:" + color + ";'>&#8358;" + HtmlTemplates.formatAmount(val) + "</div></div>";
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
