package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.TransactionDAO;
import database.UserDAO;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;
import model.*;

public class AnalysisHandler implements HttpHandler {

    private UserDAO userDAO = new UserDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String token = path.replace("/analysis/", "").replace("/", "");
        String query = exchange.getRequestURI().getQuery();

        User user = userDAO.getUserByToken(token);
        if (user == null) { exchange.getResponseHeaders().set("Location", "/auth/login"); exchange.sendResponseHeaders(302, -1); return; }

        String period = "week";
        String customFrom = null, customTo = null;
        if (query != null) {
            for (String param : query.split("&")) {
                String[] kv = param.split("=", 2);
                if (kv.length == 2) {
                    switch (kv[0]) {
                        case "period": period = kv[1]; break;
                        case "from": customFrom = kv[1]; break;
                        case "to": customTo = kv[1]; break;
                    }
                }
            }
        }

        LocalDate now = LocalDate.now();
        LocalDate from, to;
        String periodLabel;

        switch (period) {
            case "month":
                from = now.withDayOfMonth(1);
                to = now;
                periodLabel = "This Month";
                break;
            case "lastmonth":
                from = now.minusMonths(1).withDayOfMonth(1);
                to = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());
                periodLabel = "Last Month";
                break;
            case "custom":
                LocalDate parsedFrom = parseDateOrNull(customFrom);
                LocalDate parsedTo = parseDateOrNull(customTo);
                from = parsedFrom != null ? parsedFrom : now.minusDays(7);
                to = parsedTo != null ? parsedTo : now;
                if (from.isBefore(to.minusMonths(3))) from = to.minusMonths(3);
                periodLabel = from.toString() + " to " + to.toString();
                break;
            default:
                from = now.minusDays(6);
                to = now;
                periodLabel = "This Week";
                break;
        }

        String fromStr = from.toString();
        String toStr = to.toString();

        String html = buildAnalysis(user, token, period, fromStr, toStr, periodLabel);
        send(exchange, 200, html);
    }

    private String buildAnalysis(User user, String token, String activePeriod, String from, String to, String periodLabel) {
        int uid = user.getId();

        double sales = transactionDAO.getPeriodTotal(uid, TransactionType.SALE, from, to);
        double expenses = transactionDAO.getPeriodTotal(uid, TransactionType.EXPENSE, from, to);
        double supplies = transactionDAO.getPeriodTotal(uid, TransactionType.SUPPLY, from, to);
        double debts = transactionDAO.getPeriodTotal(uid, TransactionType.DEBT, from, to);
        double payments = transactionDAO.getPeriodTotal(uid, TransactionType.PAYMENT, from, to);
        double personal = transactionDAO.getPeriodTotal(uid, TransactionType.PERSONAL, from, to);
        double profit = sales - expenses - supplies;

        LinkedHashMap<String, Double> dailySales = transactionDAO.getDailyTotals(uid, TransactionType.SALE, from, to);
        LinkedHashMap<String, Double> dailyExpenses = transactionDAO.getDailyTotals(uid, TransactionType.EXPENSE, from, to);
        LinkedHashMap<String, Double> topDebtors = transactionDAO.getTopDebtors(uid, from, to);
        String[] bestDay = transactionDAO.getBestDay(uid, from, to);
        int activeDays = transactionDAO.getActiveDays(uid, from, to);

        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head("Analysis"));
        h.append(HtmlTemplates.fullNav(token, "analysis", user.getBusinessName()));
        
        h.append("<style>");
        h.append(".metric-grid{display:flex;flex-direction:column;gap:6px;margin-bottom:24px;}");
        h.append(".metric-row{display:flex;justify-content:space-between;align-items:center;padding:8px 0;border-bottom:1px solid var(--border-rule);}");
        h.append(".metric-row:last-child{border-bottom:none;}");
        h.append(".metric-label{font-size:13px;font-weight:600;color:var(--text-secondary);}");
        h.append(".metric-value{font-size:16px;font-weight:700;color:var(--text-primary);font-feature-settings:'tnum';}");
        h.append(".metric-value.positive{color:var(--sales-val);}");
        h.append(".metric-value.negative{color:var(--expense-val);}");
        h.append("</style>");

        h.append("<div class='container'>");

        // Title
        h.append("<div class='section'><h2>Business Analysis - ").append(periodLabel).append("</h2>");

        // Period buttons
        h.append("<div class='period-bar'>");
        h.append(periodBtn(token, "week", "THIS WEEK", activePeriod));
        h.append(periodBtn(token, "month", "THIS MONTH", activePeriod));
        h.append(periodBtn(token, "lastmonth", "LAST MONTH", activePeriod));
        h.append("</div>");

        // Custom range form
        h.append("<form class='filter-bar' style='margin-top:-10px;margin-bottom:24px;' method='GET' action='/analysis/").append(token).append("'>");
        h.append("<input type='hidden' name='period' value='custom'>");
        h.append("<input type='date' name='from' value='").append(from).append("'>");
        h.append("<span style='color:var(--text-muted);font-weight:700;font-size:11px;text-transform:uppercase;'>TO</span>");
        h.append("<input type='date' name='to' value='").append(to).append("'>");
        h.append("<button type='submit' class='btn btn-primary' style='padding:8px 16px;'>ANALYZE</button>");
        h.append("</form>");
        h.append("</div>");

        // Metric Grid
        h.append("<div class='metric-grid'>");
        h.append("<div class='metric-row'><span class='metric-label'>Gross Sales</span><span class='metric-value'>₦").append(HtmlTemplates.formatAmount(sales)).append("</span></div>");
        h.append("<div class='metric-row'><span class='metric-label'>Total Expenses</span><span class='metric-value'>₦").append(HtmlTemplates.formatAmount(expenses)).append("</span></div>");
        h.append("<div class='metric-row'><span class='metric-label'>Supplies Cost</span><span class='metric-value'>₦").append(HtmlTemplates.formatAmount(supplies)).append("</span></div>");
        h.append("<div class='metric-row'><span class='metric-label'>Debts Owed</span><span class='metric-value'>₦").append(HtmlTemplates.formatAmount(debts)).append("</span></div>");
        h.append("<div class='metric-row'><span class='metric-label'>Payments In</span><span class='metric-value'>₦").append(HtmlTemplates.formatAmount(payments)).append("</span></div>");
        String profitClass = profit >= 0 ? "positive" : "negative";
        h.append("<div class='metric-row'><span class='metric-label'>Net Margin</span><span class='metric-value ").append(profitClass).append("'>₦").append(HtmlTemplates.formatAmount(profit)).append("</span></div>");
        h.append("</div>");

        // Daily Sales vs Expenses Chart - COMPACT
        h.append("<div class='chart-container anim-on-scroll'>");
        h.append("<h3>DAILY SALES VS EXPENSES FLOW</h3>");
        h.append(buildDailyChart(dailySales, dailyExpenses, from, to));
        h.append("</div>");

        // Category breakdown + Pie chart side by side
        h.append("<div class='chart-container anim-on-scroll'>");
        h.append("<h3>OUTFLOW &amp; REVENUE ALLOCATION</h3>");
        h.append("<div style='display:flex;gap:24px;flex-wrap:wrap;align-items:center;justify-content:center;'>");
        h.append("<div style='flex:1;min-width:220px;'>").append(buildCategoryBars(sales, expenses, supplies)).append("</div>");
        h.append("<div style='flex-shrink:0;'>").append(HtmlTemplates.pieChart(sales, expenses, supplies)).append("</div>");
        h.append("</div></div>");

        // Top debtors
        if (!topDebtors.isEmpty()) {
            h.append("<div class='section anim-on-scroll'><h2><i class='ti ti-users' style='color:var(--brand-primary);'></i> TOP DEBTORS THIS PERIOD</h2>");
            h.append("<table><tr><th>COUNTERPARTY</th><th>OUTSTANDING BALANCE</th></tr>");
            for (Map.Entry<String, Double> entry : topDebtors.entrySet()) {
                h.append("<tr><td>").append(HtmlTemplates.escapeHtml(entry.getKey())).append("</td>");
                h.append("<td style='font-weight:700;color:var(--debt-val);font-variant-numeric:tabular-nums;'>&#8358;").append(HtmlTemplates.formatAmount(entry.getValue())).append("</td></tr>");
            }
            h.append("</table></div>");
        }

        // ADVICE SECTION
        h.append("<div class='section anim-on-scroll'><h2><i class='ti ti-bulb' style='color:var(--brand-primary);'></i> INTELLIGENCE &amp; RECOMMENDATIONS</h2>");
        h.append(generateAdvice(sales, expenses, supplies, debts, profit, activeDays, bestDay, topDebtors, from, to));
        h.append("</div>");

        h.append("</div>");
        h.append(HtmlTemplates.footer());
        return h.toString();
    }

    // ================================================================
    // ✅ COMPACT DAILY CHART - SMALLER AND CLEANER
    // ================================================================
    private String buildDailyChart(LinkedHashMap<String, Double> dailySales, LinkedHashMap<String, Double> dailyExpenses, String from, String to) {
        Set<String> allDates = new TreeSet<>();
        allDates.addAll(dailySales.keySet());
        allDates.addAll(dailyExpenses.keySet());

        if (allDates.isEmpty()) {
            return "<p class='empty'>No data for this period.</p>";
        }

        double max = 1;
        for (Double v : dailySales.values()) if (v > max) max = v;
        for (Double v : dailyExpenses.values()) if (v > max) max = v;

        // COMPACT PARAMETERS - MUCH SMALLER
        int barWidth = 10;
        int gap = 4;
        int groupGap = 10;
        int groupWidth = barWidth * 2 + gap;
        int chartHeight = 80;
        int totalWidth = allDates.size() * (groupWidth + groupGap) + 30;

        StringBuilder svg = new StringBuilder();
        svg.append("<div style='overflow-x:auto;text-align:center;max-width:100%;'>");
        svg.append("<svg width='100%' viewBox='0 0 ").append(totalWidth).append(" ").append(chartHeight + 35)
           .append("' xmlns='http://www.w3.org/2000/svg' style='display:block;margin:0 auto;'>");

        svg.append("<line x1='15' y1='").append(chartHeight).append("' x2='").append(totalWidth - 15)
           .append("' y2='").append(chartHeight).append("' stroke='var(--border-rule)' stroke-width='0.8'/>");

        int x = 20;
        for (String date : allDates) {
            double s = dailySales.getOrDefault(date, 0.0);
            double e = dailyExpenses.getOrDefault(date, 0.0);

            int sH = (int)(s / max * chartHeight);
            int eH = (int)(e / max * chartHeight);
            if (sH < 2 && s > 0) sH = 2;
            if (eH < 2 && e > 0) eH = 2;

            svg.append("<rect x='").append(x).append("' y='").append(chartHeight - sH)
               .append("' width='").append(barWidth).append("' height='").append(sH)
               .append("' fill='var(--sales-val)' class='bar-el' rx='1'/>");

            svg.append("<rect x='").append(x + barWidth + gap).append("' y='").append(chartHeight - eH)
               .append("' width='").append(barWidth).append("' height='").append(eH)
               .append("' fill='var(--expense-val)' class='bar-el' rx='1'/>");

            String shortDate = date.substring(5);
            svg.append("<text x='").append(x + groupWidth / 2).append("' y='").append(chartHeight + 12)
               .append("' text-anchor='middle' font-size='6' font-weight='600' fill='var(--text-muted)'>")
               .append(shortDate).append("</text>");

            x += groupWidth + groupGap;
        }

        int legendX = totalWidth - 120;
        svg.append("<rect x='").append(legendX).append("' y='4' width='6' height='6' fill='var(--sales-val)'/>");
        svg.append("<text x='").append(legendX + 10).append("' y='10' font-size='6' font-weight='700' fill='var(--text-primary)'>S</text>");
        svg.append("<rect x='").append(legendX + 30).append("' y='4' width='6' height='6' fill='var(--expense-val)'/>");
        svg.append("<text x='").append(legendX + 40).append("' y='10' font-size='6' font-weight='700' fill='var(--text-primary)'>E</text>");

        svg.append("</svg></div>");
        return svg.toString();
    }

    // ===== CATEGORY BARS =====
    private String buildCategoryBars(double sales, double expenses, double supplies) {
        double total = sales + expenses + supplies;
        if (total == 0) return "<p class='empty'>No spending data yet.</p>";

        StringBuilder h = new StringBuilder();
        h.append(horizontalBar("SALES", sales, total, "var(--sales-val)"));
        h.append(horizontalBar("EXPENSES", expenses, total, "var(--expense-val)"));
        h.append(horizontalBar("SUPPLIES", supplies, total, "var(--supply-val)"));
        return h.toString();
    }

    private String horizontalBar(String label, double value, double total, String color) {
        int percent = total > 0 ? (int)(value / total * 100) : 0;
        return "<div style='margin-bottom:14px;'>" +
            "<div style='display:flex;justify-content:space-between;margin-bottom:4px;'>" +
            "<span style='font-size:11px;font-weight:900;letter-spacing:0.5px;color:var(--text-primary);text-transform:uppercase;'>" + label + "</span>" +
            "<span style='font-size:12px;font-weight:800;font-variant-numeric:tabular-nums;'>&#8358;" + HtmlTemplates.formatAmount(value) + " (" + percent + "%)</span>" +
            "</div>" +
            "<div style='background:var(--bg-subtle);border:1px solid var(--border-rule);height:10px;overflow:hidden;'>" +
            "<div style='background:" + color + ";height:100%;width:" + percent + "%;'></div>" +
            "</div></div>";
    }

    // ===== ADVICE ENGINE =====
    private String generateAdvice(double sales, double expenses, double supplies, double debts, double profit,
                                   int activeDays, String[] bestDay, LinkedHashMap<String, Double> topDebtors, String from, String to) {
        StringBuilder advice = new StringBuilder();

        if (sales == 0 && expenses == 0 && supplies == 0) {
            advice.append(adviceCard("No Data Yet", "Start recording your transactions to see analysis and advice here.", ""));
            return advice.toString();
        }

        if (profit > 0) {
            advice.append(adviceCard("Profitable Period",
                "You made &#8358;" + HtmlTemplates.formatAmount(profit) + " in profit. Keep it up!", ""));
        } else if (profit < 0) {
            advice.append(adviceCard("Loss Alert",
                "You lost &#8358;" + HtmlTemplates.formatAmount(Math.abs(profit)) + " this period. Your spending exceeded your sales. Review your expenses and supply costs.",
                "danger"));
        } else {
            advice.append(adviceCard("Break Even",
                "You broke even - no profit, no loss. Look for ways to increase sales or reduce costs.", "warning"));
        }

        if (sales > 0) {
            double expenseRatio = (expenses / sales) * 100;
            if (expenseRatio > 70) {
                advice.append(adviceCard("High Expense Ratio",
                    "Your expenses are " + (int)expenseRatio + "% of your sales. Try to keep expenses below 50% of sales for a healthy margin.",
                    "danger"));
            } else if (expenseRatio > 50) {
                advice.append(adviceCard("Watch Your Expenses",
                    "Your expenses are " + (int)expenseRatio + "% of your sales. This is manageable but there's room to cut costs.",
                    "warning"));
            } else {
                advice.append(adviceCard("Healthy Spending",
                    "Your expenses are only " + (int)expenseRatio + "% of your sales. Great cost control!", ""));
            }
        }

        if (supplies > sales && supplies > 0) {
            advice.append(adviceCard("Supply Costs Exceed Sales",
                "You spent &#8358;" + HtmlTemplates.formatAmount(supplies) + " on supplies but only sold &#8358;" + HtmlTemplates.formatAmount(sales) +
                ". Are you pricing your goods correctly? Or is stock sitting unsold?",
                "danger"));
        }

        if (debts > 0) {
            advice.append(adviceCard("Outstanding Debts",
                "You have &#8358;" + HtmlTemplates.formatAmount(debts) + " in unpaid debts this period. Follow up with your debtors to improve your cash flow.",
                "warning"));

            if (!topDebtors.isEmpty()) {
                Map.Entry<String, Double> top = topDebtors.entrySet().iterator().next();
                advice.append(adviceCard("Biggest Debtor: " + HtmlTemplates.escapeHtml(top.getKey()),
                    top.getKey() + " owes you &#8358;" + HtmlTemplates.formatAmount(top.getValue()) + ". Consider following up or setting a payment deadline.",
                    "warning"));
            }
        }

        if (bestDay != null) {
            advice.append(adviceCard("Best Sales Day",
                "Your best day was " + bestDay[0] + " with &#8358;" + HtmlTemplates.formatAmount(Double.parseDouble(bestDay[1])) +
                " in sales. What did you do differently? Try to replicate it.", ""));
        }

        if (activeDays > 0 && sales > 0) {
            double dailyAvg = profit / activeDays;
            advice.append(adviceCard("Daily Average",
                "You're making about &#8358;" + HtmlTemplates.formatAmount(dailyAvg) + " per active day. " +
                "You recorded transactions on " + activeDays + " days this period.", ""));
        }

        if (activeDays < 3) {
            advice.append(adviceCard("Record More Often",
                "You only recorded on " + activeDays + " day(s). The more consistently you record, the more accurate your analysis becomes.",
                "warning"));
        }

        return advice.toString();
    }

    private String adviceCard(String title, String body, String type) {
        String cls = type.isEmpty() ? "advice-card" : "advice-card " + type;
        return "<div class='" + cls + "'><h4>" + title + "</h4><p>" + body + "</p></div>";
    }

    private String periodBtn(String token, String period, String label, String active) {
        String cls = period.equals(active) ? "period-btn active" : "period-btn";
        return "<a href='/analysis/" + token + "?period=" + period + "' class='" + cls + "'>" + label + "</a>";
    }

    private LocalDate parseDateOrNull(String s) {
        if (s == null || s.isEmpty()) return null;
        try { return LocalDate.parse(s); } catch (java.time.format.DateTimeParseException e) { return null; }
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