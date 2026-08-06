package dashboard;

public class HtmlTemplates {

    public static String head(String title) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
            "<title>SmartLedger - " + title + "</title>" +
            "<style>" + CSS + "</style></head><body>";
    }

    public static String nav(String token, String active) {
        return "<div class='header'><div class='container' style='padding:0;display:flex;justify-content:space-between;align-items:center;'>" +
            "<div><h1 class='logo'>SmartLedger</h1></div>" +
            "<nav>" +
            navLink("/dashboard/" + token, "Overview", active.equals("overview")) +
            navLink("/dashboard/" + token + "/transactions", "Transactions", active.equals("transactions")) +
            navLink("/dashboard/" + token + "/debts", "Debts", active.equals("debts")) +
            navLink("/chat/" + token, "Chat", active.equals("chat")) +
            "</nav></div></div>";
    }

    private static String navLink(String href, String label, boolean isActive) {
        String cls = isActive ? "nav-link active" : "nav-link";
        return "<a href='" + href + "' class='" + cls + "'>" + label + "</a>";
    }

    public static String footer() {
        return "<div style='text-align:center;padding:30px;color:#aaa;font-size:12px;'>SmartLedger &copy; 2026</div></body></html>";
    }

    public static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public static String badge(String type) {
        return "<span class='badge badge-" + type + "'>" + type + "</span>";
    }

    public static String formatAmount(double amount) {
        return String.format("%,.2f", amount);
    }

    public static String card(String title, double value, String cssClass) {
        return "<div class='card " + cssClass + "'>" +
            "<h3>" + title + "</h3>" +
            "<div class='value'>&#8358;" + formatAmount(value) + "</div></div>";
    }

    // ===== SVG Bar Chart =====
    public static String barChart(double sales, double expenses, double supplies, double debts, double payments) {
        double max = Math.max(1, Math.max(sales, Math.max(expenses, Math.max(supplies, Math.max(debts, payments)))));
        int chartWidth = 400, chartHeight = 180, barWidth = 50, gap = 30;
        int startX = 40;

        StringBuilder svg = new StringBuilder();
        svg.append("<svg width='100%' viewBox='0 0 ").append(chartWidth).append(" ").append(chartHeight + 40).append("' xmlns='http://www.w3.org/2000/svg'>");

        // Bars
        String[][] bars = {
            {"Sales", String.valueOf(sales), "#4CAF50"},
            {"Expenses", String.valueOf(expenses), "#f44336"},
            {"Supplies", String.valueOf(supplies), "#FF9800"},
            {"Debts", String.valueOf(debts), "#e91e63"},
            {"Payments", String.valueOf(payments), "#2196F3"}
        };

        for (int i = 0; i < bars.length; i++) {
            double val = Double.parseDouble(bars[i][1]);
            int barHeight = (int)(val / max * chartHeight);
            if (barHeight < 2 && val > 0) barHeight = 2;
            int x = startX + i * (barWidth + gap);
            int y = chartHeight - barHeight;

            svg.append("<rect x='").append(x).append("' y='").append(y).append("' width='").append(barWidth)
               .append("' height='").append(barHeight).append("' fill='").append(bars[i][2]).append("' rx='4'/>");

            // Value on top
            if (val > 0) {
                svg.append("<text x='").append(x + barWidth / 2).append("' y='").append(y - 5)
                   .append("' text-anchor='middle' font-size='10' fill='#666'>&#8358;").append(formatAmount(val)).append("</text>");
            }

            // Label below
            svg.append("<text x='").append(x + barWidth / 2).append("' y='").append(chartHeight + 15)
               .append("' text-anchor='middle' font-size='11' fill='#888'>").append(bars[i][0]).append("</text>");
        }

        svg.append("</svg>");
        return svg.toString();
    }

    // ===== CSS =====
    private static final String CSS =
        "* { margin:0; padding:0; box-sizing:border-box; }" +
        "body { font-family:'Segoe UI',sans-serif; background:#f5f5f5; color:#333; }" +
        ".header { background:#fff; border-bottom:2px solid #4CAF50; padding:15px 30px; }" +
        ".logo { color:#2e7d32; font-size:22px; margin:0; }" +
        "nav { display:flex; gap:5px; }" +
        ".nav-link { padding:8px 16px; border-radius:6px; text-decoration:none; color:#666; font-size:14px; font-weight:500; }" +
        ".nav-link:hover { background:#f0f0f0; color:#333; }" +
        ".nav-link.active { background:#e8f5e9; color:#2e7d32; }" +
        ".container { max-width:960px; margin:0 auto; padding:25px 20px; }" +
        ".cards { display:grid; grid-template-columns:repeat(auto-fit,minmax(140px,1fr)); gap:15px; margin-bottom:25px; }" +
        ".card { background:#fff; border-radius:10px; padding:18px; text-align:center; box-shadow:0 2px 8px rgba(0,0,0,0.06); border-top:3px solid #ddd; }" +
        ".card h3 { font-size:11px; color:#999; text-transform:uppercase; letter-spacing:1.5px; margin-bottom:8px; }" +
        ".card .value { font-size:20px; font-weight:700; }" +
        ".card.sales { border-top-color:#4CAF50; } .card.sales .value { color:#2e7d32; }" +
        ".card.expenses { border-top-color:#f44336; } .card.expenses .value { color:#c62828; }" +
        ".card.supplies { border-top-color:#FF9800; } .card.supplies .value { color:#e65100; }" +
        ".card.debts { border-top-color:#e91e63; } .card.debts .value { color:#ad1457; }" +
        ".card.payments { border-top-color:#2196F3; } .card.payments .value { color:#1565c0; }" +
        ".card.profit { border-top-color:#4CAF50; } .card.profit .value { color:#2e7d32; }" +
        ".card.profit.negative { border-top-color:#f44336; } .card.profit.negative .value { color:#c62828; }" +
        ".section { background:#fff; border-radius:10px; padding:25px; margin-bottom:25px; box-shadow:0 2px 8px rgba(0,0,0,0.06); }" +
        ".section h2 { color:#2e7d32; font-size:18px; margin-bottom:15px; padding-bottom:10px; border-bottom:1px solid #eee; }" +
        "table { width:100%; border-collapse:collapse; }" +
        "th { background:#fafafa; color:#666; padding:10px 12px; text-align:left; font-size:12px; text-transform:uppercase; border-bottom:2px solid #eee; }" +
        "td { padding:10px 12px; border-bottom:1px solid #f0f0f0; font-size:14px; color:#444; }" +
        "tr:hover { background:#f9f9f9; }" +
        ".badge { padding:3px 10px; border-radius:12px; font-size:11px; font-weight:600; text-transform:uppercase; }" +
        ".badge-SALE { background:#e8f5e9; color:#2e7d32; }" +
        ".badge-EXPENSE { background:#ffebee; color:#c62828; }" +
        ".badge-SUPPLY { background:#fff3e0; color:#e65100; }" +
        ".badge-DEBT { background:#fce4ec; color:#ad1457; }" +
        ".badge-PAYMENT { background:#e3f2fd; color:#1565c0; }" +
        ".empty { color:#bbb; font-style:italic; padding:20px 0; }" +
        ".btn { padding:6px 14px; border:none; border-radius:6px; font-size:12px; cursor:pointer; font-weight:500; }" +
        ".btn-danger { background:#ffebee; color:#c62828; } .btn-danger:hover { background:#ffcdd2; }" +
        ".btn-edit { background:#e3f2fd; color:#1565c0; } .btn-edit:hover { background:#bbdefb; }" +
        ".btn-primary { background:#4CAF50; color:#fff; padding:10px 20px; font-size:14px; } .btn-primary:hover { background:#43A047; }" +
        ".filter-bar { display:flex; gap:10px; flex-wrap:wrap; align-items:center; margin-bottom:20px; }" +
        ".filter-bar select, .filter-bar input { padding:8px 12px; border:1px solid #ddd; border-radius:6px; font-size:13px; }" +
        ".debt-card { background:#fff; border-radius:10px; padding:20px; margin-bottom:15px; box-shadow:0 2px 8px rgba(0,0,0,0.06); border-left:4px solid #e91e63; }" +
        ".debt-card h3 { margin-bottom:8px; color:#333; }" +
        ".debt-card .amounts { display:flex; gap:20px; font-size:14px; }" +
        ".debt-card .owed { color:#ad1457; } .debt-card .paid { color:#2e7d32; } .debt-card .remaining { color:#c62828; font-weight:700; }" +
        ".chat-container { max-width:600px; margin:0 auto; }" +
        ".chat-messages { background:#fff; border-radius:10px; padding:20px; min-height:400px; max-height:500px; overflow-y:auto; margin-bottom:15px; box-shadow:0 2px 8px rgba(0,0,0,0.06); }" +
        ".chat-msg { padding:10px 14px; margin:8px 0; border-radius:8px; font-size:14px; }" +
        ".chat-msg.user { background:#e8f5e9; border-left:3px solid #4CAF50; }" +
        ".chat-msg.system { background:#f5f5f5; border-left:3px solid #2196F3; }" +
        ".chat-msg .time { color:#aaa; font-size:11px; }" +
        ".chat-input-bar { display:flex; gap:10px; }" +
        ".chat-input-bar input { flex:1; padding:12px 16px; border:1px solid #ddd; border-radius:8px; font-size:14px; }" +
        ".confirm-card { background:#fffde7; border:1px solid #ffd54f; border-radius:8px; padding:15px; margin:8px 0; }" +
        ".confirm-card .actions { display:flex; gap:8px; margin-top:10px; }" +
        ".confirm-card .actions button { padding:8px 16px; border:none; border-radius:6px; cursor:pointer; font-size:13px; font-weight:500; }" +
        ".confirm-btn { background:#4CAF50; color:#fff; } .change-btn { background:#FF9800; color:#fff; } .cancel-btn { background:#f44336; color:#fff; }" +
        ".category-select { padding:8px; border:1px solid #ddd; border-radius:6px; font-size:13px; margin-top:8px; }" +

        "@media(max-width:600px) { .cards { grid-template-columns:repeat(2,1fr); } .header { padding:10px 15px; } nav { gap:2px; } .nav-link { padding:6px 10px; font-size:12px; } .container { padding:15px 10px; } }";
}
