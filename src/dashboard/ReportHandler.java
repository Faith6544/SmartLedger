package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.TransactionDAO;
import database.UserDAO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import model.*;

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
        double personal = transactionDAO.getTotalByType(uid, TransactionType.PERSONAL);
        double profit = sales - expenses - supplies;
        List<Transaction> allTxns = transactionDAO.getAllByUser(uid);

        StringBuilder h = new StringBuilder();
        h.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        h.append("<meta name='viewport' content='width=device-width,initial-scale=1.0'>");
        h.append("<title>SmartLedger Audit &amp; Statement - ").append(HtmlTemplates.escapeHtml(user.getUsername())).append("</title>");
        h.append("<link rel='preconnect' href='https://fonts.googleapis.com'>");
        h.append("<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>");
        h.append("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
        h.append("<style>");
        h.append("*{margin:0;padding:0;box-sizing:border-box;}");
        h.append("body{font-family:'Inter',-apple-system,sans-serif;color:#1f2937;padding:48px 40px;max-width:850px;margin:0 auto;background:#ffffff;line-height:1.5;}");
        h.append(".report-header{margin-bottom:32px;padding-bottom:24px;border-bottom:1px solid #e5e7eb;display:flex;justify-content:space-between;align-items:flex-end;}");
        h.append(".report-header img{width:36px;height:36px;}");
        h.append(".report-title{font-size:24px;font-weight:700;letter-spacing:-0.5px;color:#111827;}");
        h.append(".report-header .biz{font-size:14px;font-weight:600;color:#4b5563;margin-top:4px;}");
        h.append(".report-header .date{font-size:12px;font-weight:500;color:#6b7280;text-align:right;line-height:1.6;}");
        h.append(".charts{display:flex;justify-content:center;gap:24px;margin:32px 0;flex-wrap:wrap;}");
        h.append("table{width:100%;border-collapse:collapse;margin-bottom:32px;font-size:13px;}");
        h.append("th{background:#f9fafb;padding:12px 16px;text-align:left;font-size:11px;font-weight:600;text-transform:uppercase;letter-spacing:0.5px;color:#4b5563;border-top:1px solid #e5e7eb;border-bottom:1px solid #e5e7eb;}");
        h.append("td{padding:14px 16px;border-bottom:1px solid #f3f4f6;font-weight:400;color:#1f2937;}");
        h.append(".cat-title{font-size:14px;font-weight:600;color:#111827;margin:32px 0 16px;padding-bottom:8px;border-bottom:1px solid #e5e7eb;letter-spacing:0.5px;text-transform:uppercase;}");
        h.append(".summary{display:grid;grid-template-columns:repeat(auto-fit,minmax(140px,1fr));gap:16px;margin-bottom:32px;}");
        h.append(".sum-card{padding:16px 20px;background:#ffffff;border:1px solid #e5e7eb;border-radius:8px;box-shadow:0 1px 2px 0 rgba(0,0,0,0.05);}");
        h.append(".sum-card h4{font-size:12px;font-weight:500;color:#6b7280;margin-bottom:8px;}");
        h.append(".sum-card .val{font-size:22px;font-weight:700;letter-spacing:-0.5px;}");
        h.append(".print-btn{display:block;width:100%;padding:14px;background:#166534;color:#fff;border:none;border-radius:6px;font-size:13px;font-weight:600;cursor:pointer;margin-top:24px;transition:background 0.2s;box-shadow:0 1px 2px 0 rgba(0,0,0,0.05);}");
        h.append(".print-btn:hover{background:#14532d;}");
        h.append(".back-btn{display:inline-flex;align-items:center;padding:10px 20px;background:#ffffff;color:#4b5563;border:1px solid #e5e7eb;border-radius:6px;text-decoration:none;font-size:13px;font-weight:500;margin-bottom:24px;transition:all 0.2s;}");
        h.append(".back-btn:hover{background:#f9fafb;color:#111827;}");
        h.append("@media print{.print-btn,.back-btn{display:none!important;} body{padding:0;max-width:100%;}}");
        h.append("</style></head><body>");

        // Back button (hidden on print)
        h.append("<a href='/dashboard/").append(token).append("' class='back-btn'>&larr; Back to Dashboard</a>");

        // Header with logo and business name
        h.append("<div class='report-header'>");
        h.append("<div>");
        h.append("<div style='display:flex;align-items:center;gap:12px;margin-bottom:8px;'>");
        h.append("<div style='display:inline-flex;align-items:center;justify-content:center;width:40px;height:40px;background:#ffffff;border:1px solid #e5e7eb;border-radius:8px;box-shadow:0 1px 2px 0 rgba(0,0,0,0.05);'>");
        h.append("<img src='").append(HtmlTemplates.LOGO_DATA).append("' style='width:24px;height:24px;'></div>");
        h.append("<h1 class='report-title'>Statement of Account</h1></div>");
        if (!user.getBusinessName().isEmpty()) {
            h.append("<div class='biz'>").append(HtmlTemplates.escapeHtml(user.getBusinessName())).append("</div>");
        }
        h.append("</div>");
        h.append("<div class='date'>Generated: <strong>").append(java.time.LocalDate.now()).append("</strong><br>Merchant ID: <strong>").append(HtmlTemplates.escapeHtml(user.getUsername()).toUpperCase()).append("</strong></div>");
        h.append("</div>");

        // Summary cards
        h.append("<div class='summary'>");
        h.append(sumCard("Gross Sales", sales, "#15803d"));
        h.append(sumCard("Total Expenses", expenses, "#b91c1c"));
        h.append(sumCard("Cost of Supplies", supplies, "#c2410c"));
        h.append(sumCard("Net Margin", profit, profit >= 0 ? "#15803d" : "#b91c1c"));
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

            h.append("<h3 class='cat-title'>").append(type.name()).append(" Ledger (").append(typed.size()).append(" entries)</h3>");
            h.append("<table><tr><th>Date</th><th>Amount</th><th>Description</th><th>Counterparty</th></tr>");
            for (Transaction t : typed) {
                h.append("<tr>");
                h.append("<td>").append(t.getCreatedAt() != null ? t.getCreatedAt().toString().substring(0, 10) : "—").append("</td>");
                h.append("<td style='font-weight:600;font-variant-numeric:tabular-nums;'>&#8358;").append(HtmlTemplates.formatAmount(t.getAmount())).append("</td>");
                h.append("<td>").append(HtmlTemplates.escapeHtml(t.getDescription())).append("</td>");
                h.append("<td>").append(t.getCounterparty() != null ? HtmlTemplates.escapeHtml(t.getCounterparty()) : "—").append("</td>");
                h.append("</tr>");
            }
            h.append("</table>");
        }

        h.append("<button class='print-btn' onclick='window.print()'>Export Audit Statement to PDF</button>");
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