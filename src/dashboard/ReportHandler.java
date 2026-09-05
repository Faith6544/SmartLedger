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
        h.append("<title>SmartLedger Report - ").append(HtmlTemplates.escapeHtml(user.getUsername())).append("</title>");
        h.append("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css'>");
        h.append("<style>");
        h.append("*{margin:0;padding:0;box-sizing:border-box;}");
        h.append("body{font-family:'Times New Roman',Georgia,serif;color:#1a1a2e;padding:32px 24px;max-width:900px;margin:0 auto;background:#f8f6f3;}");
        h.append(".report-wrap{background:#ffffff;border:1px solid #e0ddd5;padding:32px 36px;border-radius:4px;box-shadow:0 1px 3px rgba(0,0,0,0.04);}");
        h.append(".report-header{margin-bottom:28px;padding-bottom:16px;border-bottom:2px solid #1a1a2e;display:flex;justify-content:space-between;align-items:flex-end;flex-wrap:wrap;gap:12px;}");
        h.append(".report-brand{display:flex;align-items:center;gap:10px;}");
        h.append(".report-brand img{width:32px;height:32px;}");
        h.append(".report-title{font-family:'Times New Roman',serif;font-size:22px;font-weight:700;letter-spacing:-0.3px;color:#1a1a2e;}");
        h.append(".report-meta{text-align:right;font-family:'Times New Roman',serif;font-size:12px;color:#6b5f58;line-height:1.6;}");
        h.append(".report-meta strong{color:#1a1a2e;}");
        h.append(".summary-grid{display:grid;grid-template-columns:1fr 1fr 1fr 1fr;gap:12px;margin-bottom:24px;}");
        h.append(".sum-card{background:#faf8f6;border:1px solid #e0ddd5;border-radius:4px;padding:14px 16px;text-align:center;}");
        h.append(".sum-card .label{font-family:'Times New Roman',serif;font-style:italic;font-size:10px;text-transform:uppercase;letter-spacing:0.5px;color:#8a7f78;}");
        h.append(".sum-card .value{font-family:'Times New Roman',serif;font-size:18px;font-weight:700;color:#1a1a2e;margin-top:2px;}");
        h.append(".sum-card .value.positive{color:#2e7d32;}");
        h.append(".sum-card .value.negative{color:#c62828;}");
        h.append(".charts-row{display:flex;gap:24px;margin-bottom:24px;flex-wrap:wrap;justify-content:center;}");
        h.append(".charts-row > div{flex:1;min-width:200px;}");
        h.append(".section-title{font-family:'Times New Roman',serif;font-style:italic;font-size:14px;font-weight:700;color:#1a1a2e;margin:24px 0 10px;padding-bottom:4px;border-bottom:1px solid #e0ddd5;}");
        h.append("table{width:100%;border-collapse:collapse;font-family:'Times New Roman',serif;font-size:12px;}");
        h.append("th{background:#faf8f6;padding:8px 10px;text-align:left;font-family:'Times New Roman',serif;font-style:italic;font-size:10px;font-weight:700;text-transform:uppercase;letter-spacing:0.3px;color:#4b3f3a;border-bottom:1.5px solid #1a1a2e;}");
        h.append("td{padding:8px 10px;border-bottom:1px solid #f0ede8;color:#1a1a2e;}");
        h.append("tr:hover{background:#faf8f6;}");
        h.append(".btn-row{display:flex;gap:12px;margin-top:24px;flex-wrap:wrap;}");
        h.append(".btn{font-family:'Times New Roman',serif;padding:8px 20px;border:1px solid #1a1a2e;border-radius:4px;text-decoration:none;font-size:12px;font-weight:600;cursor:pointer;transition:all 0.15s;display:inline-flex;align-items:center;gap:6px;}");
        h.append(".btn-primary{background:#1a1a2e;color:#fff;}");
        h.append(".btn-primary:hover{background:#2d2d4a;}");
        h.append(".btn-secondary{background:#fff;color:#1a1a2e;}");
        h.append(".btn-secondary:hover{background:#faf8f6;}");
        h.append("@media print{.report-wrap{box-shadow:none;border:none;padding:20px;}.btn-row{display:none!important;}body{background:#fff;padding:15px;}}");
        h.append("@media(max-width:640px){.summary-grid{grid-template-columns:1fr 1fr;}.report-wrap{padding:16px;}}");
        h.append("</style></head><body>");

        h.append("<div class='report-wrap'>");

        // Header
        h.append("<div class='report-header'>");
        h.append("<div class='report-brand'>");
        h.append("<img src='").append(HtmlTemplates.LOGO_DATA).append("' alt='SmartLedger'>");
        h.append("<span class='report-title'>SmartLedger</span>");
        h.append("</div>");
        h.append("<div class='report-meta'>");
        h.append("<strong>STATEMENT DATE:</strong> ").append(java.time.LocalDate.now()).append("<br>");
        h.append("<strong>MERCHANT:</strong> ").append(HtmlTemplates.escapeHtml(user.getUsername()).toUpperCase());
        if (!user.getBusinessName().isEmpty()) {
            h.append("<br><strong>BUSINESS:</strong> ").append(HtmlTemplates.escapeHtml(user.getBusinessName()));
        }
        h.append("</div>");
        h.append("</div>");

        // Summary Cards
        h.append("<div class='summary-grid'>");
        h.append("<div class='sum-card'><div class='label'>Gross Sales</div><div class='value positive'>₦").append(HtmlTemplates.formatAmount(sales)).append("</div></div>");
        h.append("<div class='sum-card'><div class='label'>Expenses</div><div class='value negative'>₦").append(HtmlTemplates.formatAmount(expenses)).append("</div></div>");
        h.append("<div class='sum-card'><div class='label'>Supplies</div><div class='value' style='color:#e65100;'>₦").append(HtmlTemplates.formatAmount(supplies)).append("</div></div>");
        String profitClass = profit >= 0 ? "positive" : "negative";
        h.append("<div class='sum-card'><div class='label'>Net Margin</div><div class='value ").append(profitClass).append("'>₦").append(HtmlTemplates.formatAmount(profit)).append("</div></div>");
        h.append("</div>");

        // Charts
        h.append("<div class='charts-row'>");
        h.append("<div>").append(HtmlTemplates.barChart(sales, expenses, supplies, debts, payments)).append("</div>");
        h.append("<div>").append(HtmlTemplates.pieChart(sales, expenses, supplies)).append("</div>");
        h.append("</div>");

        // Transactions by category
        for (TransactionType type : TransactionType.values()) {
            if (type == TransactionType.PERSONAL) continue;
            java.util.List<Transaction> typed = new java.util.ArrayList<>();
            for (Transaction t : allTxns) { if (t.getType() == type) typed.add(t); }
            if (typed.isEmpty()) continue;

            h.append("<div class='section-title'>").append(type.name()).append(" ENTRIES (").append(typed.size()).append(")</div>");
            h.append("<table><tr><th>DATE</th><th>AMOUNT</th><th>DESCRIPTION</th><th>COUNTERPARTY</th></tr>");
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

        // Personal transactions (shown separately)
        java.util.List<Transaction> personalTxns = new java.util.ArrayList<>();
        for (Transaction t : allTxns) { if (t.getType() == TransactionType.PERSONAL) personalTxns.add(t); }
        if (!personalTxns.isEmpty()) {
            h.append("<div class='section-title' style='color:#6A1B9A;'>PERSONAL DRAWINGS (").append(personalTxns.size()).append(")</div>");
            h.append("<table><tr><th>DATE</th><th>AMOUNT</th><th>DESCRIPTION</th><th>COUNTERPARTY</th></tr>");
            for (Transaction t : personalTxns) {
                h.append("<tr>");
                h.append("<td>").append(t.getCreatedAt() != null ? t.getCreatedAt().toString().substring(0, 10) : "—").append("</td>");
                h.append("<td style='font-weight:600;color:#6A1B9A;'>₦").append(HtmlTemplates.formatAmount(t.getAmount())).append("</td>");
                h.append("<td>").append(HtmlTemplates.escapeHtml(t.getDescription())).append("</td>");
                h.append("<td>").append(t.getCounterparty() != null ? HtmlTemplates.escapeHtml(t.getCounterparty()) : "—").append("</td>");
                h.append("</tr>");
            }
            h.append("</table>");
        }

        // Buttons
        h.append("<div class='btn-row'>");
        h.append("<a href='/dashboard/").append(token).append("' class='btn btn-secondary'><i class='ti ti-arrow-left'></i> BACK</a>");
        h.append("<button class='btn btn-primary' onclick='window.print()'><i class='ti ti-printer'></i> PRINT / PDF</button>");
        h.append("</div>");

        h.append("</div></body></html>");
        return h.toString();
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