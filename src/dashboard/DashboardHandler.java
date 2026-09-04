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
        if (user == null) { exchange.getResponseHeaders().set("Location", "/auth/login"); exchange.sendResponseHeaders(302, -1); return; }

        String page = parts.length > 3 ? parts[3] : "overview";
        String query = exchange.getRequestURI().getQuery();

        String html;
        switch (page) {
            case "transactions": html = transactionsPage(user, token, query); break;
            case "debts": html = debtsPage(user, token); break;
            default: html = overviewPage(user, token, query); break;
        }

        send(exchange, 200, html);
    }

    // ===== OVERVIEW PAGE =====
    private String overviewPage(User user, String token, String query) {
        // Check for welcome flag
        String welcomeType = null;
        if (query != null) {
            for (String param : query.split("&")) {
                String[] kv = param.split("=", 2);
                if (kv.length == 2 && kv[0].equals("welcome")) welcomeType = kv[1];
            }
        }

        int uid = user.getId();
        double sales = transactionDAO.getTotalByType(uid, TransactionType.SALE);
        double expenses = transactionDAO.getTotalByType(uid, TransactionType.EXPENSE);
        double supplies = transactionDAO.getTotalByType(uid, TransactionType.SUPPLY);
        double debts = transactionDAO.getTotalByType(uid, TransactionType.DEBT);
        double payments = transactionDAO.getTotalByType(uid, TransactionType.PAYMENT);
        double deliveries = transactionDAO.getTotalByType(uid, TransactionType.DELIVERY);
        double profit = sales - expenses - supplies;

        double todaySales = transactionDAO.getTodayTotalByType(uid, TransactionType.SALE);
        double todayExpenses = transactionDAO.getTodayTotalByType(uid, TransactionType.EXPENSE);
        double todaySupplies = transactionDAO.getTodayTotalByType(uid, TransactionType.SUPPLY);

        List<Transaction> recent = transactionDAO.getRecent(uid, 10);
        int streak = transactionDAO.getStreak(uid);

        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head("Overview"));
        h.append(HtmlTemplates.fullNav(token, "overview", user.getBusinessName()));
        h.append("<div class='container'>");

        // Greeting
        h.append(HtmlTemplates.greeting(user.getUsername(), todaySales, user.getBusinessName()));

        // Health indicator
        h.append(HtmlTemplates.healthIndicator(sales, expenses, supplies));

        // Streak
        h.append(HtmlTemplates.streakBanner(streak));

        // Today's Activity Carousel
        h.append("<div class='carousel'><div class='carousel-track' id='carouselTrack'>");
        h.append("<div class='carousel-slide'><h3>Today's Sales</h3><div class='big-num' style='color:#2e7d32;'>&#8358;").append(HtmlTemplates.formatAmount(todaySales)).append("</div></div>");
        h.append("<div class='carousel-slide'><h3>Today's Expenses</h3><div class='big-num' style='color:#c62828;'>&#8358;").append(HtmlTemplates.formatAmount(todayExpenses)).append("</div></div>");
        h.append("<div class='carousel-slide'><h3>Today's Supplies</h3><div class='big-num' style='color:#e65100;'>&#8358;").append(HtmlTemplates.formatAmount(todaySupplies)).append("</div></div>");
        h.append("</div><div class='carousel-dots'><span class='active' onclick='goSlide(0)'></span><span onclick='goSlide(1)'></span><span onclick='goSlide(2)'></span></div></div>");
        h.append("<script>var ci=0;function goSlide(i){ci=i;document.getElementById('carouselTrack').style.transform='translateX(-'+i*100+'%)';");
        h.append("document.querySelectorAll('.carousel-dots span').forEach(function(d,j){d.className=j===i?'active':'';});}")
        .append("setInterval(function(){goSlide((ci+1)%3);},4000);</script>");

        // All-time summary
        h.append("<div class='cards stagger-children'>");
        h.append(HtmlTemplates.card("Total Sales", sales, "sales"));
        h.append(HtmlTemplates.card("Total Expenses", expenses, "expenses"));
        h.append(HtmlTemplates.card("Total Supplies", supplies, "supplies"));
        h.append(HtmlTemplates.card("Debts Owed", debts, "debts"));
        h.append(HtmlTemplates.card("Payments In", payments, "payments"));
        h.append(HtmlTemplates.card("Deliveries", deliveries, "deliveries"));
        String profitClass = profit >= 0 ? "profit" : "profit negative";
        h.append("<div class='card ").append(profitClass).append(" anim-on-scroll'>");
        h.append("<div class='card-header'><span class='card-label'>Net Margin / Profit</span></div>");
        h.append("<div class='value'>&#8358;").append(HtmlTemplates.formatAmount(profit)).append("</div></div>");
        h.append("</div>");

        // Charts
        h.append("<div class='section anim-on-scroll'><h2><i class='ti ti-chart-bar' style='color:var(--brand-primary);'></i> Performance Flow</h2>");
        h.append(HtmlTemplates.barChart(sales, expenses, supplies, debts, payments));
        h.append("</div>");
        h.append("<div class='section anim-on-scroll'><h2><i class='ti ti-chart-pie' style='color:var(--brand-primary);'></i> Outflow Breakdown</h2>");
        h.append(HtmlTemplates.pieChart(sales, expenses, supplies));
        h.append("</div>");

        // Recent transactions
        h.append("<div class='section anim-on-scroll'><h2><i class='ti ti-history' style='color:var(--brand-primary);'></i> Recent Transactions</h2>");
        if (recent.isEmpty()) {
            h.append("" + HtmlTemplates.emptyState("No transactions yet. Start recording!", "Record now", "/chat/" + token) + "");
        } else {
            h.append(transactionTable(recent, false, token));
        }
        h.append("<div style='margin-top:16px;'><a href='/dashboard/").append(token)
         .append("/transactions' class='btn btn-primary' style='text-decoration:none;'><i class='ti ti-list'></i> View All Transactions</a></div>");
        h.append("</div>");

        h.append("</div>");

        // FAB button
        h.append("<a href='/chat/").append(token).append("' class='fab' title='Record a transaction'><i class='ti ti-plus'></i></a>");

        // Welcome toast
        if (welcomeType != null) {
            String toastMsg;
            if (welcomeType.equals("new")) {
                if (!user.getBusinessName().isEmpty()) {
                    toastMsg = "Welcome! " + HtmlTemplates.escapeHtml(user.getBusinessName()) + " is all set up.";
                } else {
                    toastMsg = "Welcome to SmartLedger, " + HtmlTemplates.escapeHtml(user.getUsername()) + "!";
                }
            } else {
                toastMsg = "Welcome back, " + HtmlTemplates.escapeHtml(user.getUsername()) + "!";
            }
            h.append("<script>document.addEventListener('DOMContentLoaded',function(){");
            h.append("var t=document.createElement('div');");
            h.append("t.style.cssText='position:fixed;top:60px;left:50%;transform:translateX(-50%) translateY(-20px);background:linear-gradient(135deg,#4CAF50,#66BB6A);color:#fff;padding:14px 28px;border-radius:12px;font-size:14px;font-weight:600;z-index:999;opacity:0;transition:all 0.5s ease;box-shadow:0 6px 20px rgba(76,175,80,0.3);max-width:90%;text-align:center;';");
            h.append("t.textContent='").append(toastMsg).append("';");
            h.append("document.body.appendChild(t);");
            h.append("setTimeout(function(){t.style.opacity='1';t.style.transform='translateX(-50%) translateY(0)';},100);");
            h.append("setTimeout(function(){t.style.opacity='0';t.style.transform='translateX(-50%) translateY(-20px)';setTimeout(function(){t.remove();},500);},4000);");
            h.append("});</script>");
        }

        h.append(HtmlTemplates.footer());
        return h.toString();
    }

    // ===== TRANSACTIONS PAGE =====
    private String transactionsPage(User user, String token, String query) {
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
        h.append(HtmlTemplates.fullNav(token, "transactions", user.getBusinessName()));
        h.append("<div class='container'>");

        // Category tabs
        h.append("<div class='cat-tabs'>");
        String[] tabs = {"ALL", "SALE", "EXPENSE", "SUPPLY", "DEBT", "PAYMENT", "DELIVERY"};
        for (String tab : tabs) {
            String active = (tab.equals(typeFilter) || (tab.equals("ALL") && (typeFilter == null || typeFilter.equals("ALL")))) ? " active t-" + tab : " t-" + tab;
            String href = tab.equals("ALL") ? "/dashboard/" + token + "/transactions" : "/dashboard/" + token + "/transactions?type=" + tab;
            // fromDate/toDate come straight from the URL query string - escape before writing into an href attribute
            if (fromDate != null && !fromDate.isEmpty()) href += (href.contains("?") ? "&" : "?") + "from=" + HtmlTemplates.escapeHtml(fromDate);
            if (toDate != null && !toDate.isEmpty()) href += (href.contains("?") ? "&" : "?") + "to=" + HtmlTemplates.escapeHtml(toDate);
            h.append("<a href='").append(href).append("' class='cat-tab").append(active).append("'>").append(tab).append("</a>");
        }
        h.append("</div>");

        // Date filter
        h.append("<form class='filter-bar' method='GET' action='/dashboard/").append(token).append("/transactions'>");
        // type/from/to are user-controlled (URL query string) - must be escaped before landing in an HTML attribute,
        // otherwise ?type='><script>...</script> breaks out of the value='' attribute and runs in the trader's session
        if (typeFilter != null && !typeFilter.equals("ALL")) h.append("<input type='hidden' name='type' value='").append(HtmlTemplates.escapeHtml(typeFilter)).append("'>");
        h.append("<input type='date' name='from' value='").append(HtmlTemplates.escapeHtml(fromDate != null ? fromDate : "")).append("' placeholder='From'>");
        h.append("<input type='date' name='to' value='").append(HtmlTemplates.escapeHtml(toDate != null ? toDate : "")).append("' placeholder='To'>");
        h.append("<button type='submit' class='btn btn-primary' style='padding:7px 14px;font-size:12px;'>Filter</button>");
        h.append("<a href='/dashboard/").append(token).append("/transactions' style='font-size:12px;color:#888;text-decoration:none;'>Clear</a>");
        h.append("</form>");

        // Report button + View toggle
        h.append("<div style='margin-bottom:16px;display:flex;justify-content:space-between;align-items:center;'>");
        h.append("<a href='/report/").append(token).append("' class='btn btn-primary' style='text-decoration:none;font-size:12px;padding:8px 16px;'>Download Report</a>");
        h.append("<div style='display:flex;gap:4px;'>");
        h.append("<button class='btn' id='cardViewBtn' onclick='showCards()' style='background:#4CAF50;color:#fff;'>Cards</button>");
        h.append("<button class='btn' id='tableViewBtn' onclick='showTable()' style='background:#f5f5f5;color:#888;'>Table</button>");
        h.append("</div></div>");

        if (transactions.isEmpty()) {
            h.append(HtmlTemplates.emptyState("No transactions match your filters.", "Record now", "/chat/" + token));
        } else {
            // Card view
            h.append("<div id='cardView'>");
            for (Transaction txn : transactions) {
                h.append("<div class='txn-card anim-on-scroll type-").append(txn.getType().name()).append("' id='row-").append(txn.getId()).append("'>");
                h.append("<div class='txn-top'>");
                h.append(HtmlTemplates.badge(txn.getType().name()));
                h.append("<span class='txn-amount'>&#8358;").append(HtmlTemplates.formatAmount(txn.getAmount())).append("</span>");
                h.append("</div>");
                h.append("<div class='txn-desc'>").append(HtmlTemplates.escapeHtml(txn.getDescription())).append("</div>");
                h.append("<div class='txn-bottom'>");
                h.append("<span class='txn-meta'>");
                if (txn.getCounterparty() != null) h.append(HtmlTemplates.escapeHtml(txn.getCounterparty())).append(" &#183; ");
                h.append(txn.getCreatedAt() != null ? txn.getCreatedAt().toString().substring(0, 16) : "").append("</span>");
                h.append("<div class='txn-actions'>");
                h.append("<select id='edit-").append(txn.getId()).append("' class='category-select' onchange='editTxn(").append(txn.getId()).append(")'>");
                for (TransactionType type : TransactionType.values()) {
                    String sel = type == txn.getType() ? " selected" : "";
                    h.append("<option value='").append(type.name()).append("'").append(sel).append(">").append(type.name()).append("</option>");
                }
                h.append("</select>");
                h.append("<button class='btn btn-danger' onclick='deleteTxn(").append(txn.getId()).append(")'>Delete</button>");
                h.append("</div></div></div>");
            }
            h.append("</div>");

            // Table view (hidden by default)
            h.append("<div id='tableView' style='display:none;'>");
            h.append("<table><tr><th>Type</th><th>Amount</th><th>Description</th><th>Who</th><th>Date</th></tr>");
            for (Transaction txn : transactions) {
                h.append("<tr class='row-type-").append(txn.getType().name()).append("' id='trow-").append(txn.getId()).append("'>");
                h.append("<td>").append(HtmlTemplates.badge(txn.getType().name())).append("</td>");
                h.append("<td style='font-weight:600;'>&#8358;").append(HtmlTemplates.formatAmount(txn.getAmount())).append("</td>");
                h.append("<td>").append(HtmlTemplates.escapeHtml(txn.getDescription())).append("</td>");
                h.append("<td>").append(txn.getCounterparty() != null ? HtmlTemplates.escapeHtml(txn.getCounterparty()) : "-").append("</td>");
                h.append("<td style='color:#888;'>").append(txn.getCreatedAt() != null ? txn.getCreatedAt().toString().substring(0, 16) : "-").append("</td>");
                h.append("</tr>");
            }
            h.append("</table></div>");
        }
        h.append("</div>");

        h.append("<script>");
        h.append("function showCards(){document.getElementById('cardView').style.display='block';document.getElementById('tableView').style.display='none';document.getElementById('cardViewBtn').style.background='#4CAF50';document.getElementById('cardViewBtn').style.color='#fff';document.getElementById('tableViewBtn').style.background='#f5f5f5';document.getElementById('tableViewBtn').style.color='#888';}\n");
        h.append("function showTable(){document.getElementById('cardView').style.display='none';document.getElementById('tableView').style.display='block';document.getElementById('tableViewBtn').style.background='#4CAF50';document.getElementById('tableViewBtn').style.color='#fff';document.getElementById('cardViewBtn').style.background='#f5f5f5';document.getElementById('cardViewBtn').style.color='#888';}\n");
        h.append("function deleteTxn(id){if(!confirm('Delete this transaction?'))return;");
        h.append("fetch('/api/delete',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},");
        h.append("body:'id='+id+'&token=").append(token).append("'}).then(r=>r.json()).then(d=>{if(d.success){var e=document.getElementById('row-'+id);if(e)e.remove();var t=document.getElementById('trow-'+id);if(t)t.remove();}});}\n");
        h.append("function editTxn(id){var sel=document.getElementById('edit-'+id);var newType=sel.value;");
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
        h.append(HtmlTemplates.fullNav(token, "debts", user.getBusinessName()));
        h.append("<div class='container'>");

        h.append("<div class='section'><h2>Debtor Summary</h2>");

        if (debtSummary.isEmpty()) {
            h.append(HtmlTemplates.emptyState("No debts recorded yet.", "Record now", "/chat/" + token));
        } else {
            double totalRemaining = 0;
            for (Map.Entry<String, double[]> entry : debtSummary.entrySet()) {
                String name = entry.getKey();
                double[] vals = entry.getValue(); // [owed, paid, remaining]
                totalRemaining += vals[2];
                int paidPercent = vals[0] > 0 ? (int)(vals[1] / vals[0] * 100) : 0;
                String statusClass, statusText;
                if (vals[2] <= 0) { statusClass = "status-paid"; statusText = "Fully paid"; }
                else if (vals[1] > 0) { statusClass = "status-partial"; statusText = "Partially paid"; }
                else { statusClass = "status-unpaid"; statusText = "Unpaid"; }

                h.append("<div class='debt-card anim-on-scroll'>");
                h.append("<div style='display:flex;justify-content:space-between;align-items:center;'>");
                h.append("<h3>").append(HtmlTemplates.escapeHtml(name)).append("</h3>");
                h.append("<span class='status-badge ").append(statusClass).append("'>").append(statusText).append("</span>");
                h.append("</div>");

                // Progress bar - scroll triggered
                String barColor = vals[2] <= 0 ? "#4CAF50" : vals[1] > 0 ? "#FF9800" : "#f44336";
                h.append("<div class='progress-bar'><div class='progress-animate' data-width='").append(paidPercent).append("' style='background:").append(barColor).append(";'></div></div>");

                h.append("<div class='debt-amounts'>");
                h.append("<span style='color:#ad1457;'>Owed: &#8358;").append(HtmlTemplates.formatAmount(vals[0])).append("</span>");
                h.append("<span style='color:#2e7d32;'>Paid: &#8358;").append(HtmlTemplates.formatAmount(vals[1])).append("</span>");
                h.append("<span style='color:#c62828;font-weight:700;'>Left: <span class='count-up' data-target='").append((long)vals[2]).append("'>&#8358;0.00</span></span>");
                h.append("</div></div>");
            }

            h.append("<div style='margin-top:16px;padding:16px;background:var(--bg-surface);border:1px solid #c7d2fe;border-radius:var(--radius-lg);text-align:center;box-shadow:var(--shadow-card);' class='anim-on-scroll'>");
            h.append("<span style='font-size:11px;font-weight:700;text-transform:uppercase;letter-spacing:0.5px;color:var(--text-muted);'>Total Outstanding Debts</span><br>");
            h.append("<strong style='font-size:24px;font-weight:800;color:var(--debt-val);' class='count-up' data-target='").append((long)totalRemaining).append("'>&#8358;0.00</strong>");
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
            t.append("<td>").append(txn.getCounterparty() != null ? HtmlTemplates.escapeHtml(txn.getCounterparty()) : "-").append("</td>");
            t.append("<td style='color:#888;'>").append(txn.getCreatedAt() != null ? txn.getCreatedAt().toString().substring(0, 16) : "-").append("</td>");

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