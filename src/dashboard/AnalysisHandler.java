package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.TransactionDAO;
import database.UserDAO;
import model.*;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

        // Parse period from query
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

        // Calculate date range
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
                // customFrom/customTo come from the URL query string - a malformed date here
                // used to throw an uncaught DateTimeParseException and crash the whole page.
                LocalDate parsedFrom = parseDateOrNull(customFrom);
                LocalDate parsedTo = parseDateOrNull(customTo);
                from = parsedFrom != null ? parsedFrom : now.minusDays(7);
                to = parsedTo != null ? parsedTo : now;
                // Cap at 3 months
                if (from.isBefore(to.minusMonths(3))) from = to.minusMonths(3);
                periodLabel = from.toString() + " to " + to.toString();
                break;
            default: // week
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

        // Get period totals
        double sales = transactionDAO.getPeriodTotal(uid, TransactionType.SALE, from, to);
        double expenses = transactionDAO.getPeriodTotal(uid, TransactionType.EXPENSE, from, to);
        double supplies = transactionDAO.getPeriodTotal(uid, TransactionType.SUPPLY, from, to);
        double debts = transactionDAO.getPeriodTotal(uid, TransactionType.DEBT, from, to);
        double payments = transactionDAO.getPeriodTotal(uid, TransactionType.PAYMENT, from, to);
        double profit = sales - expenses - supplies;

        // Get daily breakdowns for chart
        LinkedHashMap<String, Double> dailySales = transactionDAO.getDailyTotals(uid, TransactionType.SALE, from, to);
        LinkedHashMap<String, Double> dailyExpenses = transactionDAO.getDailyTotals(uid, TransactionType.EXPENSE, from, to);

        // Get top debtors
        LinkedHashMap<String, Double> topDebtors = transactionDAO.getTopDebtors(uid, from, to);

        // Get best day
        String[] bestDay = transactionDAO.getBestDay(uid, from, to);

        // Active days
        int activeDays = transactionDAO.getActiveDays(uid, from, to);

        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head("Analysis"));
        h.append(HtmlTemplates.fullNav(token, "analysis", user.getBusinessName()));
        h.append("<div class='container'>");

        // Title
        h.append("<div class='section'><h2>Business Analysis - ").append(periodLabel).append("</h2>");

        // Period selector
        h.append("<div class='period-bar'>");
        h.append(periodBtn(token, "week", "This Week", activePeriod));
        h.append(periodBtn(token, "month", "This Month", activePeriod));
        h.append(periodBtn(token, "lastmonth", "Last Month", activePeriod));
        h.append("</div>");

        // Custom range form
        h.append("<form class='filter-bar' style='margin-top:-10px;margin-bottom:20px;' method='GET' action='/analysis/").append(token).append("'>");
        h.append("<input type='hidden' name='period' value='custom'>");
        h.append("<input type='date' name='from' value='").append(from).append("'>");
        h.append("<span style='color:#888;'>to</span>");
        h.append("<input type='date' name='to' value='").append(to).append("'>");
        h.append("<button type='submit' class='btn btn-primary' style='padding:8px 16px;'>Analyze</button>");
        h.append("</form>");
        h.append("</div>");

        // Period summary cards
        h.append("<div class='cards'>");
        h.append(HtmlTemplates.card("Sales", sales, "sales"));
        h.append(HtmlTemplates.card("Expenses", expenses, "expenses"));
        h.append(HtmlTemplates.card("Supplies", supplies, "supplies"));
        h.append(HtmlTemplates.card("Debts Owed", debts, "debts"));
        h.append(HtmlTemplates.card("Payments In", payments, "payments"));
        String profitClass = profit >= 0 ? "profit" : "profit negative";
        h.append("<div class='card ").append(profitClass).append("'><h3>Profit</h3>");
        h.append("<div class='value'>&#8358;").append(HtmlTemplates.formatAmount(profit)).append("</div></div>");
        h.append("</div>");

        // Daily Sales vs Expenses Chart
        h.append("<div class='chart-container'>");
        h.append("<h3>Daily Sales vs Expenses</h3>");
        h.append(buildDailyChart(dailySales, dailyExpenses, from, to));
        h.append("</div>");

        // Category breakdown + Pie chart side by side
        h.append("<div class='chart-container anim-on-scroll'>");
        h.append("<h3>Spending Breakdown</h3>");
        h.append("<div style='display:flex;gap:20px;flex-wrap:wrap;align-items:center;justify-content:center;'>");
        h.append("<div style='flex:1;min-width:200px;'>").append(buildCategoryBars(sales, expenses, supplies)).append("</div>");
        h.append("<div style='flex-shrink:0;'>").append(HtmlTemplates.pieChart(sales, expenses, supplies)).append("</div>");
        h.append("</div></div>");

        // Top debtors
        if (!topDebtors.isEmpty()) {
            h.append("<div class='section'><h2>Top Debtors This Period</h2>");
            h.append("<table><tr><th>Name</th><th>Amount Owed</th></tr>");
            for (Map.Entry<String, Double> entry : topDebtors.entrySet()) {
                h.append("<tr><td>").append(HtmlTemplates.escapeHtml(entry.getKey())).append("</td>");
                h.append("<td style='font-weight:600;color:#ad1457;'>&#8358;").append(HtmlTemplates.formatAmount(entry.getValue())).append("</td></tr>");
            }
            h.append("</table></div>");
        }

        // ADVICE SECTION
        h.append("<div class='section'><h2>Advice</h2>");
        h.append(generateAdvice(sales, expenses, supplies, debts, profit, activeDays, bestDay, topDebtors, from, to));
        h.append("</div>");

        h.append("</div>");
        h.append(HtmlTemplates.footer());
        return h.toString();
    }

    // ===== DAILY CHART =====
    private String buildDailyChart(LinkedHashMap<String, Double> dailySales, LinkedHashMap<String, Double> dailyExpenses, String from, String to) {
        // Merge all dates
        Set<String> allDates = new TreeSet<>();
        allDates.addAll(dailySales.keySet());
        allDates.addAll(dailyExpenses.keySet());

        if (allDates.isEmpty()) {
            return "<p class='empty'>No data for this period.</p>";
        }

        double max = 1;
        for (Double v : dailySales.values()) if (v > max) max = v;
        for (Double v : dailyExpenses.values()) if (v > max) max = v;

        int barWidth = 20, gap = 8, groupGap = 20;
        int groupWidth = barWidth * 2 + gap;
        int chartHeight = 160;
        int totalWidth = allDates.size() * (groupWidth + groupGap) + 60;
        // Dynamic width - no forced minimum, chart fits the data

        StringBuilder svg = new StringBuilder();
        svg.append("<div style='overflow-x:auto;text-align:center;'>");
        svg.append("<svg width='100%' viewBox='0 0 ").append(totalWidth).append(" ").append(chartHeight + 50).append("' xmlns='http://www.w3.org/2000/svg'>");

        int x = 40;
        for (String date : allDates) {
            double s = dailySales.getOrDefault(date, 0.0);
            double e = dailyExpenses.getOrDefault(date, 0.0);

            int sH = (int)(s / max * chartHeight);
            int eH = (int)(e / max * chartHeight);
            if (sH < 2 && s > 0) sH = 2;
            if (eH < 2 && e > 0) eH = 2;

            // Sales bar (emerald)
            svg.append("<rect x='").append(x).append("' y='").append(chartHeight - sH)
               .append("' width='").append(barWidth).append("' height='").append(sH)
               .append("' fill='#10b981' rx='4'/>");

            // Expense bar (rose)
            svg.append("<rect x='").append(x + barWidth + gap).append("' y='").append(chartHeight - eH)
               .append("' width='").append(barWidth).append("' height='").append(eH)
               .append("' fill='#f43f5e' rx='4'/>");

            // Date label
            String shortDate = date.substring(5); // "08-05"
            svg.append("<text x='").append(x + groupWidth / 2).append("' y='").append(chartHeight + 15)
               .append("' text-anchor='middle' font-size='10' font-weight='600' fill='#64748b'>").append(shortDate).append("</text>");

            x += groupWidth + groupGap;
        }

        // Legend
        svg.append("<rect x='").append(totalWidth - 150).append("' y='5' width='10' height='10' fill='#10b981' rx='2'/>");
        svg.append("<text x='").append(totalWidth - 133).append("' y='14' font-size='11' font-weight='500' fill='#475569'>Sales</text>");
        svg.append("<rect x='").append(totalWidth - 80).append("' y='5' width='10' height='10' fill='#f43f5e' rx='2'/>");
        svg.append("<text x='").append(totalWidth - 63).append("' y='14' font-size='11' font-weight='500' fill='#475569'>Expenses</text>");

        svg.append("</svg></div>");
        return svg.toString();
    }

    // ===== CATEGORY BARS =====
    private String buildCategoryBars(double sales, double expenses, double supplies) {
        double total = sales + expenses + supplies;
        if (total == 0) return "<p class='empty'>No spending data yet.</p>";

        StringBuilder h = new StringBuilder();
        h.append(horizontalBar("Sales", sales, total, "#4CAF50"));
        h.append(horizontalBar("Expenses", expenses, total, "#f44336"));
        h.append(horizontalBar("Supplies", supplies, total, "#FF9800"));
        return h.toString();
    }

    private String horizontalBar(String label, double value, double total, String color) {
        int percent = total > 0 ? (int)(value / total * 100) : 0;
        return "<div style='margin-bottom:12px;'>" +
            "<div style='display:flex;justify-content:space-between;margin-bottom:4px;'>" +
            "<span style='font-size:13px;color:#666;'>" + label + "</span>" +
            "<span style='font-size:13px;font-weight:600;'>&#8358;" + HtmlTemplates.formatAmount(value) + " (" + percent + "%)</span>" +
            "</div>" +
            "<div style='background:#f0f0f0;border-radius:6px;height:10px;overflow:hidden;'>" +
            "<div style='background:" + color + ";height:100%;width:" + percent + "%;border-radius:6px;'></div>" +
            "</div></div>";
    }

    // ===== ADVICE ENGINE =====
    private String generateAdvice(double sales, double expenses, double supplies, double debts, double profit,
                                   int activeDays, String[] bestDay, LinkedHashMap<String, Double> topDebtors, String from, String to) {
        StringBuilder advice = new StringBuilder();

        // No data
        if (sales == 0 && expenses == 0 && supplies == 0) {
            advice.append(adviceCard("No Data Yet", "Start recording your transactions to see analysis and advice here.", ""));
            return advice.toString();
        }

        // Profit status
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

        // Expense ratio
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

        // Supply vs sales
        if (supplies > sales && supplies > 0) {
            advice.append(adviceCard("Supply Costs Exceed Sales",
                "You spent &#8358;" + HtmlTemplates.formatAmount(supplies) + " on supplies but only sold &#8358;" + HtmlTemplates.formatAmount(sales) +
                ". Are you pricing your goods correctly? Or is stock sitting unsold?",
                "danger"));
        }

        // Outstanding debts
        if (debts > 0) {
            advice.append(adviceCard("Outstanding Debts",
                "You have &#8358;" + HtmlTemplates.formatAmount(debts) + " in unpaid debts this period. Follow up with your debtors to improve your cash flow.",
                "warning"));

            // Top debtor
            if (!topDebtors.isEmpty()) {
                Map.Entry<String, Double> top = topDebtors.entrySet().iterator().next();
                advice.append(adviceCard("Biggest Debtor: " + HtmlTemplates.escapeHtml(top.getKey()),
                    top.getKey() + " owes you &#8358;" + HtmlTemplates.formatAmount(top.getValue()) + ". Consider following up or setting a payment deadline.",
                    "warning"));
            }
        }

        // Best day
        if (bestDay != null) {
            advice.append(adviceCard("Best Sales Day",
                "Your best day was " + bestDay[0] + " with &#8358;" + HtmlTemplates.formatAmount(Double.parseDouble(bestDay[1])) +
                " in sales. What did you do differently? Try to replicate it.", ""));
        }

        // Daily average
        if (activeDays > 0 && sales > 0) {
            double dailyAvg = profit / activeDays;
            advice.append(adviceCard("Daily Average",
                "You're making about &#8358;" + HtmlTemplates.formatAmount(dailyAvg) + " per active day. " +
                "You recorded transactions on " + activeDays + " days this period.", ""));
        }

        // Recording consistency
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