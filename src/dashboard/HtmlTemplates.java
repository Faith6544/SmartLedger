package dashboard;

public class HtmlTemplates {

    public static final String LOGO_DATA = "data:image/png;base64,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";

    public static String head(String title) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
            "<title>SmartLedger - " + title + "</title>" +
            "<link rel='icon' type='image/png' href='" + LOGO_DATA + "'>" +
            "<style>" + CSS + "</style></head><body>";
    }

    public static String nav(String token, String active) {
        return "<div class='header'><div class='hdr-inner'>" +
            "<div class='hdr-left'>" +
            "<button class='hamburger' onclick='toggleSidebar()'>&#9776;</button>" +
            "<a href='/dashboard/" + token + "' class='logo-link'><img src='" + LOGO_DATA + "' class='logo-img' alt='SmartLedger'><span class='logo-text'>SmartLedger</span></a>" +
            "</div>" +
            "<nav class='desktop-nav'>" +
            navLink("/dashboard/" + token, "Overview", active.equals("overview")) +
            navLink("/chat/" + token, "Chat", active.equals("chat")) +
            navLink("/dashboard/" + token + "/transactions", "Transactions", active.equals("transactions")) +
            navLink("/dashboard/" + token + "/debts", "Debts", active.equals("debts")) +
            navLink("/analysis/" + token, "Analysis", active.equals("analysis")) +
            "</nav></div></div>" +
            sidebar(token, active);
    }

    private static String sidebar(String token, String active) {
        return "<div class='sidebar-overlay' id='sidebarOverlay' onclick='toggleSidebar()'></div>" +
            "<div class='sidebar' id='sidebar'>" +
            "<div class='sidebar-hdr'>" +
            "<img src='" + LOGO_DATA + "' style='width:40px;height:40px;'>" +
            "<span style='font-size:18px;font-weight:700;color:#2e7d32;margin-left:10px;'>SmartLedger</span>" +
            "<button onclick='toggleSidebar()' style='background:none;border:none;font-size:22px;cursor:pointer;color:#666;margin-left:auto;'>&times;</button>" +
            "</div>" +
            "<nav class='sidebar-nav'>" +
            sideLink("/dashboard/" + token, "Overview", active.equals("overview")) +
            sideLink("/chat/" + token, "Chat", active.equals("chat")) +
            sideLink("/dashboard/" + token + "/transactions", "Transactions", active.equals("transactions")) +
            sideLink("/dashboard/" + token + "/debts", "Debts", active.equals("debts")) +
            sideLink("/analysis/" + token, "Analysis", active.equals("analysis")) +
            "<div style='border-top:1px solid #eee;margin:15px 0;'></div>" +
            "<a href='/' class='side-link' style='color:#c62828;'>Logout</a>" +
            "</nav></div>" +
            "<script>function toggleSidebar(){document.getElementById('sidebar').classList.toggle('open');" +
            "document.getElementById('sidebarOverlay').classList.toggle('open');}</script>";
    }

    private static String navLink(String href, String label, boolean isActive) {
        return "<a href='" + href + "' class='nav-link" + (isActive ? " active" : "") + "'>" + label + "</a>";
    }

    private static String sideLink(String href, String label, boolean isActive) {
        return "<a href='" + href + "' class='side-link" + (isActive ? " active" : "") + "'>" + label + "</a>";
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
        return "<div class='card " + cssClass + "'><h3>" + title + "</h3>" +
            "<div class='value'>&#8358;" + formatAmount(value) + "</div></div>";
    }

    public static String barChart(double sales, double expenses, double supplies, double debts, double payments) {
        double max = Math.max(1, Math.max(sales, Math.max(expenses, Math.max(supplies, Math.max(debts, payments)))));
        int chartWidth = 400, chartHeight = 180, barWidth = 50, gap = 30;
        int startX = 40;
        StringBuilder svg = new StringBuilder();
        svg.append("<svg width='100%' viewBox='0 0 ").append(chartWidth).append(" ").append(chartHeight + 40).append("' xmlns='http://www.w3.org/2000/svg'>");
        String[][] bars = {{"Sales", String.valueOf(sales), "#4CAF50"}, {"Expenses", String.valueOf(expenses), "#f44336"},
            {"Supplies", String.valueOf(supplies), "#FF9800"}, {"Debts", String.valueOf(debts), "#e91e63"}, {"Payments", String.valueOf(payments), "#2196F3"}};
        for (int i = 0; i < bars.length; i++) {
            double val = Double.parseDouble(bars[i][1]);
            int barHeight = (int)(val / max * chartHeight);
            if (barHeight < 2 && val > 0) barHeight = 2;
            int x = startX + i * (barWidth + gap);
            int y = chartHeight - barHeight;
            svg.append("<rect x='").append(x).append("' y='").append(y).append("' width='").append(barWidth)
               .append("' height='").append(barHeight).append("' fill='").append(bars[i][2]).append("' rx='4'/>");
            if (val > 0) svg.append("<text x='").append(x + barWidth / 2).append("' y='").append(y - 5)
               .append("' text-anchor='middle' font-size='10' fill='#666'>&#8358;").append(formatAmount(val)).append("</text>");
            svg.append("<text x='").append(x + barWidth / 2).append("' y='").append(chartHeight + 15)
               .append("' text-anchor='middle' font-size='11' fill='#888'>").append(bars[i][0]).append("</text>");
        }
        svg.append("</svg>");
        return svg.toString();
    }

    public static String greeting(String username, double todaySales) {
        int hour = java.time.LocalTime.now().getHour();
        String timeGreet;
        if (hour >= 5 && hour < 12) timeGreet = "Good morning";
        else if (hour >= 12 && hour < 17) timeGreet = "Good afternoon";
        else if (hour >= 17 && hour < 21) timeGreet = "Good evening";
        else timeGreet = "Hello";

        String salesMsg;
        if (todaySales > 0) salesMsg = "You've made &#8358;" + formatAmount(todaySales) + " in sales today.";
        else salesMsg = "No sales recorded today yet — let's change that!";

        return "<div class='greeting'><h2>" + timeGreet + ", " + escapeHtml(username) + "!</h2><p>" + salesMsg + "</p></div>";
    }

    public static String healthIndicator(double sales, double expenses, double supplies) {
        double profit = sales - expenses - supplies;
        double expenseRatio = sales > 0 ? (expenses / sales) * 100 : 0;
        String color, label, tip;
        if (profit > 0 && expenseRatio < 50) { color = "#4CAF50"; label = "Healthy"; tip = "Business is in good shape!"; }
        else if (profit > 0 && expenseRatio <= 70) { color = "#FF9800"; label = "Okay"; tip = "Expenses are creeping up. Keep an eye on costs."; }
        else if (profit <= 0) { color = "#f44336"; label = "Needs Attention"; tip = "Spending exceeds earnings. Review your costs."; }
        else { color = "#FF9800"; label = "Caution"; tip = "High expense ratio. Try to reduce costs."; }

        return "<div class='health-card'><div class='health-dot' style='background:" + color + ";'></div>" +
            "<div><strong>" + label + "</strong><p style='margin:0;color:#888;font-size:13px;'>" + tip + "</p></div></div>";
    }

    public static String streakBanner(int streak) {
        if (streak < 2) return "";
        String emoji = streak >= 7 ? "&#128293;" : "&#9889;";
        return "<div class='streak-banner'>" + emoji + " " + streak + "-day recording streak! Keep it going!</div>";
    }

    private static final String CSS =
        "*{margin:0;padding:0;box-sizing:border-box;}" +
        "body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;color:#333;}" +
        // Header
        ".header{background:#fff;border-bottom:2px solid #4CAF50;padding:0 20px;position:sticky;top:0;z-index:100;}" +
        ".hdr-inner{max-width:960px;margin:0 auto;display:flex;justify-content:space-between;align-items:center;height:56px;}" +
        ".hdr-left{display:flex;align-items:center;gap:10px;}" +
        ".logo-link{display:flex;align-items:center;text-decoration:none;gap:8px;}" +
        ".logo-img{width:32px;height:32px;}" +
        ".logo-text{font-size:20px;font-weight:700;color:#2e7d32;}" +
        ".hamburger{display:none;background:none;border:none;font-size:24px;cursor:pointer;color:#333;padding:4px 8px;}" +
        ".desktop-nav{display:flex;gap:4px;}" +
        ".nav-link{padding:8px 14px;border-radius:6px;text-decoration:none;color:#666;font-size:13px;font-weight:500;}" +
        ".nav-link:hover{background:#f0f0f0;color:#333;}" +
        ".nav-link.active{background:#e8f5e9;color:#2e7d32;}" +
        // Sidebar
        ".sidebar-overlay{display:none;position:fixed;inset:0;background:rgba(0,0,0,0.5);z-index:199;}" +
        ".sidebar-overlay.open{display:block;}" +
        ".sidebar{position:fixed;left:-300px;top:0;width:280px;height:100vh;background:#fff;z-index:200;transition:left 0.3s ease;overflow-y:auto;box-shadow:2px 0 15px rgba(0,0,0,0.1);}" +
        ".sidebar.open{left:0;}" +
        ".sidebar-hdr{display:flex;align-items:center;padding:20px;border-bottom:1px solid #eee;}" +
        ".sidebar-nav{padding:10px 0;}" +
        ".side-link{display:block;padding:14px 24px;color:#333;text-decoration:none;font-size:15px;font-weight:500;}" +
        ".side-link:hover{background:#f5f5f5;}" +
        ".side-link.active{color:#2e7d32;background:#e8f5e9;border-right:3px solid #4CAF50;}" +
        // Container
        ".container{max-width:960px;margin:0 auto;padding:25px 20px;}" +
        // Greeting
        ".greeting{margin-bottom:25px;} .greeting h2{font-size:24px;color:#1a1a2e;margin-bottom:4px;} .greeting p{color:#888;font-size:15px;}" +
        // Health
        ".health-card{display:flex;align-items:center;gap:15px;background:#fff;border-radius:10px;padding:16px 20px;margin-bottom:20px;box-shadow:0 2px 8px rgba(0,0,0,0.06);}" +
        ".health-dot{width:18px;height:18px;border-radius:50%;flex-shrink:0;}" +
        // Streak
        ".streak-banner{background:linear-gradient(135deg,#fff8e1,#fff3e0);border:1px solid #ffe082;border-radius:10px;padding:12px 20px;margin-bottom:20px;font-size:14px;font-weight:600;color:#e65100;text-align:center;}" +
        // Cards
        ".cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(140px,1fr));gap:15px;margin-bottom:25px;}" +
        ".card{background:#fff;border-radius:10px;padding:18px;text-align:center;box-shadow:0 2px 8px rgba(0,0,0,0.06);border-top:3px solid #ddd;}" +
        ".card h3{font-size:10px;color:#999;text-transform:uppercase;letter-spacing:1.5px;margin-bottom:8px;}" +
        ".card .value{font-size:22px;font-weight:800;letter-spacing:-0.5px;}" +
        ".card.sales{border-top-color:#4CAF50;} .card.sales .value{color:#2e7d32;}" +
        ".card.expenses{border-top-color:#f44336;} .card.expenses .value{color:#c62828;}" +
        ".card.supplies{border-top-color:#FF9800;} .card.supplies .value{color:#e65100;}" +
        ".card.debts{border-top-color:#e91e63;} .card.debts .value{color:#ad1457;}" +
        ".card.payments{border-top-color:#2196F3;} .card.payments .value{color:#1565c0;}" +
        ".card.profit{border-top-color:#4CAF50;} .card.profit .value{color:#2e7d32;}" +
        ".card.profit.negative{border-top-color:#f44336;} .card.profit.negative .value{color:#c62828;}" +
        // Sections
        ".section{background:#fff;border-radius:10px;padding:20px;margin-bottom:25px;box-shadow:0 2px 8px rgba(0,0,0,0.06);}" +
        ".section h2{color:#2e7d32;font-size:17px;margin-bottom:15px;padding-bottom:10px;border-bottom:1px solid #eee;}" +
        // Tables
        "table{width:100%;border-collapse:collapse;}" +
        "th{background:#fafafa;color:#666;padding:10px 12px;text-align:left;font-size:11px;text-transform:uppercase;border-bottom:2px solid #eee;}" +
        "td{padding:10px 12px;border-bottom:1px solid #f0f0f0;font-size:13px;color:#444;}" +
        "tr:hover{background:#f9f9f9;}" +
        ".badge{padding:3px 10px;border-radius:12px;font-size:10px;font-weight:600;text-transform:uppercase;}" +
        ".badge-SALE{background:#e8f5e9;color:#2e7d32;} .badge-EXPENSE{background:#ffebee;color:#c62828;} .badge-SUPPLY{background:#fff3e0;color:#e65100;} .badge-DEBT{background:#fce4ec;color:#ad1457;} .badge-PAYMENT{background:#e3f2fd;color:#1565c0;}" +
        ".empty{color:#bbb;font-style:italic;padding:20px 0;}" +
        // Buttons
        ".btn{padding:6px 14px;border:none;border-radius:6px;font-size:12px;cursor:pointer;font-weight:500;}" +
        ".btn-danger{background:#ffebee;color:#c62828;} .btn-danger:hover{background:#ffcdd2;}" +
        ".btn-edit{background:#e3f2fd;color:#1565c0;} .btn-edit:hover{background:#bbdefb;}" +
        ".btn-primary{background:#4CAF50;color:#fff;padding:10px 20px;font-size:14px;} .btn-primary:hover{background:#43A047;}" +
        // Filters
        ".filter-bar{display:flex;gap:10px;flex-wrap:wrap;align-items:center;margin-bottom:20px;}" +
        ".filter-bar select,.filter-bar input{padding:8px 12px;border:1px solid #ddd;border-radius:6px;font-size:13px;}" +
        // Debts
        ".debt-card{background:#fff;border-radius:10px;padding:20px;margin-bottom:15px;box-shadow:0 2px 8px rgba(0,0,0,0.06);border-left:4px solid #e91e63;}" +
        ".debt-card h3{margin-bottom:8px;color:#333;} .debt-card .amounts{display:flex;gap:20px;font-size:14px;flex-wrap:wrap;}" +
        ".debt-card .owed{color:#ad1457;} .debt-card .paid{color:#2e7d32;} .debt-card .remaining{color:#c62828;font-weight:700;}" +
        // Chat
        ".chat-container{max-width:600px;margin:0 auto;}" +
        ".chat-messages{background:#fff;border-radius:10px;padding:20px;min-height:400px;max-height:500px;overflow-y:auto;margin-bottom:15px;box-shadow:0 2px 8px rgba(0,0,0,0.06);}" +
        ".chat-msg{padding:10px 14px;margin:8px 0;border-radius:8px;font-size:14px;}" +
        ".chat-msg.user{background:#e8f5e9;border-left:3px solid #4CAF50;}" +
        ".chat-msg.system{background:#f5f5f5;border-left:3px solid #2196F3;}" +
        ".chat-msg .time{color:#aaa;font-size:11px;}" +
        ".chat-input-bar{display:flex;gap:10px;}" +
        ".chat-input-bar input{flex:1;padding:12px 16px;border:1px solid #ddd;border-radius:8px;font-size:14px;}" +
        ".confirm-card{background:#fffde7;border:1px solid #ffd54f;border-radius:8px;padding:15px;margin:8px 0;}" +
        ".confirm-card .actions{display:flex;gap:8px;margin-top:10px;flex-wrap:wrap;}" +
        ".confirm-card .actions button{padding:8px 16px;border:none;border-radius:6px;cursor:pointer;font-size:13px;font-weight:500;}" +
        ".confirm-btn{background:#4CAF50;color:#fff;} .change-btn{background:#FF9800;color:#fff;} .cancel-btn{background:#f44336;color:#fff;}" +
        ".category-select{padding:8px;border:1px solid #ddd;border-radius:6px;font-size:13px;margin-top:8px;}" +
        // Toast
        ".toast{position:fixed;top:20px;right:20px;padding:14px 24px;border-radius:10px;color:#fff;font-size:14px;font-weight:600;z-index:300;opacity:0;transform:translateY(-20px);transition:all 0.4s ease;box-shadow:0 4px 15px rgba(0,0,0,0.15);}" +
        ".toast.show{opacity:1;transform:translateY(0);}" +
        ".toast.success{background:#4CAF50;} .toast.info{background:#2196F3;} .toast.warning{background:#FF9800;}" +
        // FAB
        ".fab{position:fixed;bottom:30px;right:30px;width:60px;height:60px;border-radius:50%;background:#4CAF50;color:#fff;font-size:28px;border:none;cursor:pointer;box-shadow:0 4px 15px rgba(76,175,80,0.4);display:flex;align-items:center;justify-content:center;text-decoration:none;z-index:99;transition:transform 0.2s;} .fab:hover{transform:scale(1.1);background:#43A047;}" +
        // Analysis
        ".advice-card{background:#fff;border-radius:10px;padding:20px;margin-bottom:15px;box-shadow:0 2px 8px rgba(0,0,0,0.06);border-left:4px solid #4CAF50;}" +
        ".advice-card.warning{border-left-color:#FF9800;} .advice-card.danger{border-left-color:#f44336;}" +
        ".advice-card h4{margin-bottom:5px;color:#333;font-size:15px;} .advice-card p{color:#666;font-size:13px;line-height:1.5;}" +
        ".period-bar{display:flex;gap:8px;flex-wrap:wrap;margin-bottom:25px;}" +
        ".period-btn{padding:8px 18px;border-radius:20px;text-decoration:none;font-size:13px;font-weight:500;border:1px solid #ddd;color:#666;background:#fff;}" +
        ".period-btn.active{background:#4CAF50;color:#fff;border-color:#4CAF50;}" +
        ".chart-container{background:#fff;border-radius:10px;padding:20px;margin-bottom:25px;box-shadow:0 2px 8px rgba(0,0,0,0.06);}" +
        ".chart-container h3{color:#333;font-size:16px;margin-bottom:15px;}" +
        // Mobile
        "@media(max-width:768px){" +
        ".hamburger{display:block;}" +
        ".desktop-nav{display:none;}" +
        ".logo-text{font-size:17px;}" +
        ".cards{grid-template-columns:repeat(2,1fr);gap:10px;}" +
        ".card{padding:14px;} .card .value{font-size:18px;} .card h3{font-size:9px;}" +
        ".container{padding:15px 12px;}" +
        ".section{padding:15px;}" +
        ".header{padding:0 12px;}" +
        ".greeting h2{font-size:20px;}" +
        ".fab{bottom:20px;right:20px;width:50px;height:50px;font-size:22px;}" +
        "table{font-size:12px;} th,td{padding:8px 6px;}" +
        ".filter-bar{gap:6px;} .filter-bar select,.filter-bar input{padding:6px 8px;font-size:12px;}" +
        ".debt-card .amounts{flex-direction:column;gap:4px;}" +
        ".chat-input-bar{flex-direction:column;} .chat-input-bar input{font-size:16px;}" +
        "}";
}
