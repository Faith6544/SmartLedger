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
import java.util.Map;

public class DashboardHandler implements HttpHandler {

    private UserDAO userDAO = new UserDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");
        // /dashboard/{token} or /dashboard/{token}/transactions or /dashboard/{token}/debts

        if (parts.length < 3) { send(exchange, 404, "Not found"); return; }

        String token = parts[2];
        User user = userDAO.getUserByToken(token);
        if (user == null) { send(exchange, 404, notFoundPage()); return; }

        String page = parts.length > 3 ? parts[3] : "overview";
        String query = exchange.getRequestURI().getQuery();

        String html;
        switch (page) {
            case "transactions": html = transactionsPage(user, token, query); break;
            case "debts": html = debtsPage(user, token); break;
            default: html = overviewPage(user, token); break;
        }

        send(exchange, 200, html);
    }

    // ===== OVERVIEW PAGE =====
    private String overviewPage(User user, String token) {
        int uid = user.getId();
        double sales = transactionDAO.getTotalByType(uid, TransactionType.SALE);
        double expenses = transactionDAO.getTotalByType(uid, TransactionType.EXPENSE);
        double supplies = transactionDAO.getTotalByType(uid, TransactionType.SUPPLY);
        double debts = transactionDAO.getTotalByType(uid, TransactionType.DEBT);
        double payments = transactionDAO.getTotalByType(uid, TransactionType.PAYMENT);
        double profit = sales - expenses - supplies;

        double todaySales = transactionDAO.getTodayTotalByType(uid, TransactionType.SALE);
        double todayExpenses = transactionDAO.getTodayTotalByType(uid, TransactionType.EXPENSE);
        double todaySupplies = transactionDAO.getTodayTotalByType(uid, TransactionType.SUPPLY);

        List<Transaction> recent = transactionDAO.getRecent(uid, 10);

        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head("Overview"));
        h.append(HtmlTemplates.nav(token, "overview"));
        h.append("<div class='container'>");

        // Today's summary
        h.append("<div class='section'><h2>Today's Activity</h2>");
        h.append("<div class='cards' style='margin-bottom:0;'>");
        h.append(HtmlTemplates.card("Today's Sales", todaySales, "sales"));
        h.append(HtmlTemplates.card("Today's Expenses", todayExpenses, "expenses"));
        h.append(HtmlTemplates.card("Today's Supplies", todaySupplies, "supplies"));
        h.append("</div></div>");

        // All-time summary
        h.append("<div class='cards'>");
        h.append(HtmlTemplates.card("Total Sales", sales, "sales"));
        h.append(HtmlTemplates.card("Total Expenses", expenses, "expenses"));
        h.append(HtmlTemplates.card("Total Supplies", supplies, "supplies"));
        h.append(HtmlTemplates.card("Debts Owed", debts, "debts"));
        h.append(HtmlTemplates.card("Payments In", payments, "payments"));
        String profitClass = profit >= 0 ? "profit" : "profit negative";
        h.append("<div class='card ").append(profitClass).append("'><h3>Profit</h3>");
        h.append("<div class='value'>&#8358;").append(HtmlTemplates.formatAmount(profit)).append("</div></div>");
        h.append("</div>");

        // Chart
        h.append("<div class='section'><h2>At a Glance</h2>");
        h.append(HtmlTemplates.barChart(sales, expenses, supplies, debts, payments));
        h.append("</div>");

        // Recent transactions
        h.append("<div class='section'><h2>Recent Transactions</h2>");
        if (recent.isEmpty()) {
            h.append("<p class='empty'>No transactions yet. Start recording in the chat!</p>");
        } else {
            h.append(transactionTable(recent, false, token));
        }
        h.append("<div style='margin-top:15px;'><a href='/dashboard/").append(token)
         .append("/transactions' class='btn btn-primary' style='text-decoration:none;'>View All Transactions</a></div>");
        h.append("</div>");

        h.append("</div>");

        // FAB button
        h.append("<a href='/chat/").append(token).append("' class='fab' title='Record a transaction'>+</a>");

        h.append(HtmlTemplates.footer());
        return h.toString();
    }

    // ===== TRANSACTIONS PAGE =====
    private String transactionsPage(User user, String token, String query) {
        // Parse filters from query string
        String typeFilter = null, fromDate = null, toDate = null;
        if (query != null) {
            for (String param : query.split("&")) {
                String[] kv = param.split("=", 2);
                if (kv.length == 2) {
                    switch (kv[0]) {
                        case "type": typeFilter = kv[1]; break;
                        case "from": fromDate = kv[1]; break;
                        case "to": toDate = kv[1]; break;
                    }
                }
            }
        }

        List<Transaction> transactions = transactionDAO.getFiltered(user.getId(), typeFilter, fromDate, toDate);

        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head("Transactions"));
        h.append(HtmlTemplates.nav(token, "transactions"));
        h.append("<div class='container'>");

        h.append("<div class='section'><h2>All Transactions</h2>");

        // Filter bar
        h.append("<form class='filter-bar' method='GET' action='/dashboard/").append(token).append("/transactions'>");
        h.append("<select name='type'><option value='ALL'>All Types</option>");
        for (TransactionType t : TransactionType.values()) {
            String sel = t.name().equals(typeFilter) ? " selected" : "";
            h.append("<option value='").append(t.name()).append("'").append(sel).append(">").append(t.name()).append("</option>");
        }
        h.append("</select>");
        h.append("<input type='date' name='from' value='").append(fromDate != null ? fromDate : "").append("' placeholder='From'>");
        h.append("<input type='date' name='to' value='").append(toDate != null ? toDate : "").append("' placeholder='To'>");
        h.append("<button type='submit' class='btn btn-primary' style='padding:8px 16px;'>Filter</button>");
        h.append("<a href='/dashboard/").append(token).append("/transactions' style='padding:8px;color:#666;text-decoration:none;'>Clear</a>");
        h.append("</form>");

        if (transactions.isEmpty()) {
            h.append("<p class='empty'>No transactions match your filters.</p>");
        } else {
            h.append(transactionTable(transactions, true, token));
        }
        h.append("</div></div>");

        // JavaScript for edit/delete
        h.append("<script>");
        h.append("function deleteTxn(id){if(!confirm('Delete this transaction?'))return;");
        h.append("fetch('/api/delete',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},");
        h.append("body:'id='+id+'&token=").append(token).append("'}).then(r=>r.json()).then(d=>{if(d.success)document.getElementById('row-'+id).remove();});}\n");

        h.append("function editTxn(id){var sel=document.getElementById('edit-'+id);");
        h.append("var newType=sel.value;");
        h.append("fetch('/api/edit',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},");
        h.append("body:'id='+id+'&type='+newType+'&token=").append(token).append("'}).then(r=>r.json()).then(d=>{if(d.success)location.reload();});}\n");
        h.append("</script>");

        h.append(HtmlTemplates.footer());
        return h.toString();
    }

    // ===== DEBTS PAGE =====
    private String debtsPage(User user, String token) {
        Map<String, double[]> debtSummary = transactionDAO.getDebtSummary(user.getId());

        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head("Debts"));
        h.append(HtmlTemplates.nav(token, "debts"));
        h.append("<div class='container'>");

        h.append("<div class='section'><h2>Debtor Summary</h2>");

        if (debtSummary.isEmpty()) {
            h.append("<p class='empty'>No debts recorded yet.</p>");
        } else {
            double totalRemaining = 0;
            for (Map.Entry<String, double[]> entry : debtSummary.entrySet()) {
                String name = entry.getKey();
                double[] vals = entry.getValue(); // [owed, paid, remaining]
                totalRemaining += vals[2];

                h.append("<div class='debt-card'>");
                h.append("<h3>").append(HtmlTemplates.escapeHtml(name)).append("</h3>");
                h.append("<div class='amounts'>");
                h.append("<span class='owed'>Owed: &#8358;").append(HtmlTemplates.formatAmount(vals[0])).append("</span>");
                h.append("<span class='paid'>Paid: &#8358;").append(HtmlTemplates.formatAmount(vals[1])).append("</span>");
                h.append("<span class='remaining'>Remaining: &#8358;").append(HtmlTemplates.formatAmount(vals[2])).append("</span>");
                h.append("</div></div>");
            }

            h.append("<div style='margin-top:20px;padding:15px;background:#fce4ec;border-radius:8px;'>");
            h.append("<strong style='color:#ad1457;'>Total Outstanding: &#8358;").append(HtmlTemplates.formatAmount(totalRemaining)).append("</strong>");
            h.append("</div>");
        }

        h.append("</div></div>");
        h.append(HtmlTemplates.footer());
        return h.toString();
    }

    // ===== HELPERS =====

    private String transactionTable(List<Transaction> transactions, boolean showActions, String token) {
        StringBuilder t = new StringBuilder();
        t.append("<table><tr><th>Type</th><th>Amount</th><th>Description</th><th>Who</th><th>Date</th>");
        if (showActions) t.append("<th>Actions</th>");
        t.append("</tr>");

        for (Transaction txn : transactions) {
            t.append("<tr id='row-").append(txn.getId()).append("'>");
            t.append("<td>").append(HtmlTemplates.badge(txn.getType().name())).append("</td>");
            t.append("<td style='font-weight:600;'>&#8358;").append(HtmlTemplates.formatAmount(txn.getAmount())).append("</td>");
            t.append("<td>").append(HtmlTemplates.escapeHtml(txn.getDescription())).append("</td>");
            t.append("<td>").append(txn.getCounterparty() != null ? HtmlTemplates.escapeHtml(txn.getCounterparty()) : "—").append("</td>");
            t.append("<td style='color:#888;'>").append(txn.getCreatedAt() != null ? txn.getCreatedAt().toString().substring(0, 16) : "—").append("</td>");

            if (showActions) {
                t.append("<td>");
                // Edit dropdown
                t.append("<select id='edit-").append(txn.getId()).append("' class='category-select' onchange='editTxn(").append(txn.getId()).append(")'>");
                for (TransactionType type : TransactionType.values()) {
                    String sel = type == txn.getType() ? " selected" : "";
                    t.append("<option value='").append(type.name()).append("'").append(sel).append(">").append(type.name()).append("</option>");
                }
                t.append("</select> ");
                // Delete button
                t.append("<button class='btn btn-danger' onclick='deleteTxn(").append(txn.getId()).append(")'>Delete</button>");
                t.append("</td>");
            }
            t.append("</tr>");
        }
        t.append("</table>");
        return t.toString();
    }

    private String notFoundPage() {
        return HtmlTemplates.head("Not Found") +
            "<div style='text-align:center;padding:80px 20px;'>" +
            "<h1 style='color:#ccc;font-size:60px;'>404</h1>" +
            "<p style='color:#888;'>Dashboard not found. Check your link.</p>" +
            "<a href='/auth/login' class='btn btn-primary' style='text-decoration:none;margin-top:20px;display:inline-block;'>Go to Login</a>" +
            "</div>" + HtmlTemplates.footer();
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
