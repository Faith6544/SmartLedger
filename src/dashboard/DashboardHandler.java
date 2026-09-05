package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.TransactionDAO;
import database.UserDAO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import model.*;

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
        double personal = transactionDAO.getTotalByType(uid, TransactionType.PERSONAL);
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

        // Today's Activity Carousel (updated with brand colors)
        h.append("<div class='carousel'><div class='carousel-track' id='carouselTrack'>");
        h.append("<div class='carousel-slide'><h3>Today's Sales</h3><div class='big-num' style='color:var(--brand-primary);'>&#8358;").append(HtmlTemplates.formatAmount(todaySales)).append("</div></div>");
        h.append("<div class='carousel-slide'><h3>Today's Expenses</h3><div class='big-num' style='color:var(--expense-val);'>&#8358;").append(HtmlTemplates.formatAmount(todayExpenses)).append("</div></div>");
        h.append("<div class='carousel-slide'><h3>Today's Supplies</h3><div class='big-num' style='color:var(--supply-val);'>&#8358;").append(HtmlTemplates.formatAmount(todaySupplies)).append("</div></div>");
        h.append("</div><div class='carousel-dots'><span class='active' onclick='goSlide(0)'></span><span onclick='goSlide(1)'></span><span onclick='goSlide(2)'></span></div></div>");
        h.append("<script>var ci=0;function goSlide(i){ci=i;document.getElementById('carouselTrack').style.transform='translateX(-'+i*100+'%)';");
        h.append("document.querySelectorAll('.carousel-dots span').forEach(function(d,j){d.className=j===i?'active':'';});}")
        .append("setInterval(function(){goSlide((ci+1)%3);},4000);</script>");

        // ================================================================
        // ✅ NEW: All-time summary in a single vertical card
        // ================================================================
        h.append("<div class='metric-card anim-on-scroll'>");
        h.append(HtmlTemplates.metricList("Total Sales", sales, "sales", false));
        h.append(HtmlTemplates.metricList("Total Expenses", expenses, "expenses", false));
        h.append(HtmlTemplates.metricList("Total Supplies", supplies, "supplies", false));
        h.append(HtmlTemplates.metricList("Debts Owed", debts, "debts", false));
        h.append(HtmlTemplates.metricList("Payments In", payments, "payments", false));
        h.append(HtmlTemplates.metricList("Deliveries", deliveries, "deliveries", false));
        h.append(HtmlTemplates.metricList("Personal Drawings", personal, "personal", false));
        String profitClass = profit >= 0 ? "positive" : "negative";
        h.append(HtmlTemplates.metricList("Net Margin / Profit", profit, profitClass, false));
        h.append("</div>");

        // Charts
        h.append("<div class='section anim-on-scroll'><h2><i class='ti ti-chart-bar' style='color:var(--brand-primary);'></i> PERFORMANCE FLOW</h2>");
        h.append(HtmlTemplates.barChart(sales, expenses, supplies, debts, payments));
        h.append("</div>");
        h.append("<div class='section anim-on-scroll'><h2><i class='ti ti-chart-pie' style='color:var(--brand-primary);'></i> REVENUE & OUTFLOW RATIO</h2>");
        h.append(HtmlTemplates.pieChart(sales, expenses, supplies));
        h.append("</div>");

        // Recent transactions
        h.append("<div class='section anim-on-scroll'><h2><i class='ti ti-history' style='color:var(--brand-primary);'></i> RECENT POSTED TRANSACTIONS</h2>");
        if (recent.isEmpty()) {
            h.append("" + HtmlTemplates.emptyState("No transactions recorded yet today.", "Record Entry", "/chat/" + token) + "");
        } else {
            h.append(transactionTable(recent, false, token));
        }
        h.append("<div style='margin-top:16px;'><a href='/dashboard/").append(token)
         .append("/transactions' class='btn btn-primary' style='text-decoration:none;'><i class='ti ti-list'></i> VIEW FULL TRANSACTION LEDGER</a></div>");
        h.append("</div>");

        h.append("</div>");

        // FAB button
        h.append("<a href='/chat/").append(token).append("' class='fab' title='Record a transaction'><i class='ti ti-plus'></i></a>");

        // Welcome toast
        if (welcomeType != null) {
            String toastMsg;
            if (welcomeType.equals("new")) {
                if (!user.getBusinessName().isEmpty()) {
                    toastMsg = "Welcome! " + HtmlTemplates.escapeHtml(user.getBusinessName()) + " is active.";
                } else {
                    toastMsg = "Welcome to SmartLedger, " + HtmlTemplates.escapeHtml(user.getUsername()) + "!";
                }
            } else {
                toastMsg = "Welcome back, " + HtmlTemplates.escapeHtml(user.getUsername()) + "!";
            }
            h.append("<script>document.addEventListener('DOMContentLoaded',function(){");
            h.append("var t=document.createElement('div');");
            h.append("t.style.cssText='position:fixed;top:60px;left:50%;transform:translateX(-50%) translateY(-20px);background:#ffffff;color:#111827;border:1px solid #e0e0e0;padding:12px 24px;border-radius:8px;font-size:12px;font-weight:700;z-index:999;opacity:0;transition:all 0.3s ease;max-width:90%;text-align:center;';");
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

        // Category tabs (including PERSONAL)
        h.append("<div class='cat-tabs'>");
        String[] tabs = {"ALL", "SALE", "EXPENSE", "SUPPLY", "DEBT", "PAYMENT", "DELIVERY", "PERSONAL"};
        for (String tab : tabs) {
            String active = (tab.equals(typeFilter) || (tab.equals("ALL") && (typeFilter == null || typeFilter.equals("ALL")))) ? " active t-" + tab : " t-" + tab;
            String href = tab.equals("ALL") ? "/dashboard/" + token + "/transactions" : "/dashboard/" + token + "/transactions?type=" + tab;
            if (fromDate != null && !fromDate.isEmpty()) href += (href.contains("?") ? "&" : "?") + "from=" + HtmlTemplates.escapeHtml(fromDate);
            if (toDate != null && !toDate.isEmpty()) href += (href.contains("?") ? "&" : "?") + "to=" + HtmlTemplates.escapeHtml(toDate);
            h.append("<a href='").append(href).append("' class='cat-tab").append(active).append("'>").append(tab).append("</a>");
        }
        h.append("</div>");

        // Date filter
        h.append("<form class='filter-bar' method='GET' action='/dashboard/").append(token).append("/transactions'>");
        if (typeFilter != null && !typeFilter.equals("ALL")) h.append("<input type='hidden' name='type' value='").append(HtmlTemplates.escapeHtml(typeFilter)).append("'>");
        h.append("<input type='date' name='from' value='").append(HtmlTemplates.escapeHtml(fromDate != null ? fromDate : "")).append("' placeholder='From'>");
        h.append("<input type='date' name='to' value='").append(HtmlTemplates.escapeHtml(toDate != null ? toDate : "")).append("' placeholder='To'>");
        h.append("<button type='submit' class='btn btn-primary' style='padding:7px 14px;font-size:12px;'>Filter</button>");
        h.append("<a href='/dashboard/").append(token).append("/transactions' style='font-size:12px;color:#888;text-decoration:none;'>Clear</a>");
        h.append("</form>");

        // Report button + View toggle
        h.append("<div style='margin-bottom:20px;display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap;gap:10px;'>");
        h.append("<a href='/report/").append(token).append("' class='btn btn-primary' style='text-decoration:none;'><i class='ti ti-file-analytics'></i> STATEMENT &amp; AUDIT</a>");
        h.append("<div style='display:flex;gap:4px;'>");
        h.append("<button class='btn' id='cardViewBtn' onclick='showCards()' style='background:var(--brand-primary);color:#fff;'>CARDS</button>");
        h.append("<button class='btn' id='tableViewBtn' onclick='showTable()' style='background:#ffffff;color:var(--text-primary);'>TABLE</button>");
        h.append("</div></div>");

        if (transactions.isEmpty()) {
            h.append(HtmlTemplates.emptyState("No transactions match current filters.", "Record Entry", "/chat/" + token));
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
                if (txn.getCounterparty() != null) h.append(HtmlTemplates.escapeHtml(txn.getCounterparty())).append(" &middot; ");
                h.append(txn.getCreatedAt() != null ? txn.getCreatedAt().toString().substring(0, 16) : "").append("</span>");
                h.append("<div class='txn-actions'>");
                h.append("<select id='edit-").append(txn.getId()).append("' class='category-select' onchange='editTxn(").append(txn.getId()).append(")'>");
                for (TransactionType type : TransactionType.values()) {
                    String sel = type == txn.getType() ? " selected" : "";
                    h.append("<option value='").append(type.name()).append("'").append(sel).append(">").append(type.name()).append("</option>");
                }
                h.append("</select>");
                h.append("<button class='btn btn-danger' onclick='deleteTxn(").append(txn.getId()).append(")'>DELETE</button>");
                h.append("</div></div></div>");
            }
            h.append("</div>");

            // Table view (hidden by default)
            h.append("<div id='tableView' style='display:none;'>");
            h.append("<table><tr><th>TYPE</th><th>AMOUNT</th><th>DESCRIPTION</th><th>COUNTERPARTY</th><th>DATE</th></tr>");
            for (Transaction txn : transactions) {
                h.append("<tr class='row-type-").append(txn.getType().name()).append("' id='trow-").append(txn.getId()).append("'>");
                h.append("<td>").append(HtmlTemplates.badge(txn.getType().name())).append("</td>");
                h.append("<td style='font-weight:700;font-variant-numeric:tabular-nums;'>&#8358;").append(HtmlTemplates.formatAmount(txn.getAmount())).append("</td>");
                h.append("<td style='font-weight:600;'>").append(HtmlTemplates.escapeHtml(txn.getDescription())).append("</td>");
                h.append("<td>").append(txn.getCounterparty() != null ? HtmlTemplates.escapeHtml(txn.getCounterparty()) : "-").append("</td>");
                h.append("<td style='color:var(--text-muted);font-weight:600;'>").append(txn.getCreatedAt() != null ? txn.getCreatedAt().toString().substring(0, 16) : "-").append("</td>");
                h.append("</tr>");
            }
            h.append("</table></div>");
        }
        h.append("</div>");

        h.append("<script>");
        h.append("function showCards(){document.getElementById('cardView').style.display='block';document.getElementById('tableView').style.display='none';document.getElementById('cardViewBtn').style.background='var(--brand-primary)';document.getElementById('cardViewBtn').style.color='#fff';document.getElementById('tableViewBtn').style.background='#ffffff';document.getElementById('tableViewBtn').style.color='var(--text-primary)';}\n");
        h.append("function showTable(){document.getElementById('cardView').style.display='none';document.getElementById('tableView').style.display='block';document.getElementById('tableViewBtn').style.background='var(--brand-primary)';document.getElementById('tableViewBtn').style.color='#fff';document.getElementById('cardViewBtn').style.background='#ffffff';document.getElementById('cardViewBtn').style.color='var(--text-primary)';}\n");
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

        h.append("<div class='section'><h2><i class='ti ti-scale' style='color:var(--brand-primary);'></i> DEBTOR &amp; CREDIT LEDGER</h2>");

        if (debtSummary.isEmpty()) {
            h.append(HtmlTemplates.emptyState("No active debtor positions recorded.", "Record Debt", "/chat/" + token));
        } else {
            double totalRemaining = 0;
            for (Map.Entry<String, double[]> entry : debtSummary.entrySet()) {
                String name = entry.getKey();
                double[] vals = entry.getValue(); // [owed, paid, remaining]
                totalRemaining += vals[2];
                int paidPercent = vals[0] > 0 ? (int)(vals[1] / vals[0] * 100) : 0;
                String statusClass, statusText;
                if (vals[2] <= 0) { statusClass = "status-paid"; statusText = "Fully Paid"; }
                else if (vals[1] > 0) { statusClass = "status-partial"; statusText = "Partially Paid"; }
                else { statusClass = "status-unpaid"; statusText = "Unpaid"; }

                h.append("<div class='debt-card anim-on-scroll'>");
                h.append("<div style='display:flex;justify-content:space-between;align-items:center;'>");
                h.append("<h3>").append(HtmlTemplates.escapeHtml(name)).append("</h3>");
                h.append("<span class='status-badge ").append(statusClass).append("'>").append(statusText).append("</span>");
                h.append("</div>");

                // Progress bar - scroll triggered
                String barColor = vals[2] <= 0 ? "var(--sales-val)" : vals[1] > 0 ? "var(--supply-val)" : "var(--expense-val)";
                h.append("<div class='progress-bar'><div class='progress-animate' data-width='").append(paidPercent).append("' style='background:").append(barColor).append(";'></div></div>");

                h.append("<div class='debt-amounts'>");
                h.append("<span style='color:var(--debt-val);'>OWED: &#8358;").append(HtmlTemplates.formatAmount(vals[0])).append("</span>");
                h.append("<span style='color:var(--sales-val);'>PAID: &#8358;").append(HtmlTemplates.formatAmount(vals[1])).append("</span>");
                h.append("<span style='color:var(--expense-val);font-weight:800;'>OUTSTANDING: <span class='count-up' data-target='").append((long)vals[2]).append("'>&#8358;0.00</span></span>");
                h.append("</div></div>");
            }

            h.append("<div style='margin-top:20px;padding:20px;background:var(--bg-subtle);border:1px solid #e0e0e0;border-radius:8px;text-align:center;' class='anim-on-scroll'>");
            h.append("<span style='font-size:11px;font-weight:700;color:var(--text-primary);'>Total Outstanding Debt</span><br>");
            h.append("<strong style='font-size:28px;font-weight:900;color:var(--debt-val);letter-spacing:-0.5px;font-variant-numeric:tabular-nums;' class='count-up' data-target='").append((long)totalRemaining).append("'>&#8358;0.00</strong>");
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