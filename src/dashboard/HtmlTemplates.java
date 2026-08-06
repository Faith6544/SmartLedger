package dashboard;

public class HtmlTemplates {

    public static final String LOGO_DATA = "data:image/png;base64,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";

    public static String head(String title) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width,initial-scale=1.0'>" +
            "<title>SmartLedger - " + title + "</title>" +
            "<link rel='icon' type='image/png' href='" + LOGO_DATA + "'>" +
            "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css'>" +
            "<style>" + CSS + "</style></head><body><div class='device-frame'><div class='app-shell'>";
    }

    public static String fullNav(String token, String active, String businessName) {
        String bizDisplay = (businessName != null && !businessName.isEmpty()) ? escapeHtml(businessName) : "";
        return "<div class='app-header'>" +
            "<div class='hdr-left'>" +
            "<button class='hamburger' onclick='toggleSidebar()'>&#9776;</button>" +
            "<a href='/dashboard/" + token + "' class='logo-link'>" +
            "<div class='logo-circle'><img src='" + LOGO_DATA + "' class='logo-img'></div>" +
            "<span class='logo-text'>SmartLedger</span></a></div>" +
            (bizDisplay.isEmpty() ? "" : "<div class='biz-name'>" + bizDisplay + "</div>") +
            "</div>" + sidebar(token, active);
    }

    private static String sidebar(String token, String active) {
        return "<div class='sidebar-overlay' id='sidebarOverlay' onclick='toggleSidebar()'></div>" +
            "<div class='sidebar' id='sidebar'>" +
            "<div class='sidebar-hdr'>" +
            "<div class='sidebar-logo-circle'><img src='" + LOGO_DATA + "' style='width:50px;height:50px;'></div>" +
            "<div class='sidebar-brand'>SmartLedger</div>" +
            "<button onclick='toggleSidebar()' class='sidebar-close'>&times;</button>" +
            "</div>" +
            "<nav class='sidebar-nav'>" +
            sideLink("/dashboard/" + token, "Overview", "ti-home", active.equals("overview")) +
            sideLink("/chat/" + token, "Chat", "ti-message", active.equals("chat")) +
            sideLink("/dashboard/" + token + "/transactions", "Transactions", "ti-list", active.equals("transactions")) +
            sideLink("/dashboard/" + token + "/debts", "Debts", "ti-cash", active.equals("debts")) +
            sideLink("/analysis/" + token, "Analysis", "ti-chart-bar", active.equals("analysis")) +
            sideLink("/report/" + token, "Report", "ti-file-text", active.equals("report")) +
            "<div class='sidebar-divider'></div>" +
            "<a href='/' class='side-link logout-link'><i class='ti ti-logout' aria-hidden='true'></i> Logout</a>" +
            "</nav></div>" +
            "<script>function toggleSidebar(){document.getElementById('sidebar').classList.toggle('open');" +
            "document.getElementById('sidebarOverlay').classList.toggle('open');}</script>";
    }

    private static String sideLink(String href, String label, String icon, boolean isActive) {
        return "<a href='" + href + "' class='side-link" + (isActive ? " active" : "") + "'><i class='ti " + icon + "' aria-hidden='true'></i> " + label + "</a>";
    }

    public static String footer() {
        return "</div></div>" +
            "<div style='text-align:center;padding:20px;color:#999;font-size:11px;'>SmartLedger &#169; 2026 &#8212; COS 202 Group 22</div>" +
            OBSERVER_JS +
            "</body></html>";
    }

    public static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public static String badge(String type) { return "<span class='badge badge-" + type + "'>" + type + "</span>"; }
    public static String formatAmount(double amount) { return String.format("%,.2f", amount); }

    public static String card(String title, double value, String cssClass) {
        return "<div class='card " + cssClass + " anim-on-scroll'><h3>" + title + "</h3>" +
            "<div class='value count-up' data-target='" + (long)value + "'>&#8358;0.00</div></div>";
    }

    public static String greeting(String username, double todaySales, String businessName) {
        int hour = java.time.LocalTime.now().getHour();
        String timeGreet = hour >= 5 && hour < 12 ? "Good morning" : hour < 17 ? "Good afternoon" : hour < 21 ? "Good evening" : "Hello";
        String salesMsg = todaySales > 0 ? "You've made &#8358;" + formatAmount(todaySales) + " in sales today." : "No sales recorded yet today.";
        return "<div class='greeting anim-on-scroll'><h2>" + timeGreet + ", " + escapeHtml(username) + "!</h2><p>" + salesMsg + "</p></div>";
    }

    public static String healthIndicator(double sales, double expenses, double supplies) {
        double profit = sales - expenses - supplies;
        double ratio = sales > 0 ? (expenses / sales) * 100 : 0;
        String color, label, tip;
        if (profit > 0 && ratio < 50) { color = "#4CAF50"; label = "Healthy"; tip = "Business is in good shape!"; }
        else if (profit > 0) { color = "#FF9800"; label = "Okay"; tip = "Expenses are creeping up."; }
        else { color = "#f44336"; label = "Needs Attention"; tip = "Spending exceeds earnings."; }
        return "<div class='health-card anim-on-scroll'><div class='health-dot' style='background:" + color + ";'></div>" +
            "<div><strong>" + label + "</strong><p style='margin:0;color:#888;font-size:12px;'>" + tip + "</p></div></div>";
    }

    public static String streakBanner(int streak) {
        if (streak < 2) return "";
        String emoji = streak >= 7 ? "&#128293;" : "&#9889;";
        return "<div class='streak-banner anim-on-scroll bounce-in'>" + emoji + " " + streak + "-day recording streak!</div>";
    }

    public static String pieChart(double sales, double expenses, double supplies) {
        double total = sales + expenses + supplies;
        if (total == 0) return "<p class='empty'>No data yet.</p>";
        double s1 = sales/total*360, s2 = expenses/total*360, s3 = supplies/total*360;
        double a1 = 0, a2 = s1, a3 = s1+s2;
        return "<div style='text-align:center;' class='anim-on-scroll'><svg width='180' height='180' viewBox='0 0 180 180'>" +
            pieSlice(90,90,70,a1,a1+s1,"#4CAF50") + pieSlice(90,90,70,a2,a2+s2,"#f44336") + pieSlice(90,90,70,a3,a3+s3,"#FF9800") +
            "<circle cx='90' cy='90' r='40' fill='white'/>" +
            "<text x='90' y='86' text-anchor='middle' font-size='11' fill='#888'>Total</text>" +
            "<text x='90' y='102' text-anchor='middle' font-size='13' font-weight='700' fill='#333'>&#8358;" + formatAmount(total) + "</text>" +
            "</svg>" +
            "<div style='display:flex;justify-content:center;gap:15px;margin-top:10px;font-size:11px;'>" +
            "<span><span style='display:inline-block;width:10px;height:10px;border-radius:50%;background:#4CAF50;margin-right:4px;'></span>Sales " + (int)(sales/total*100) + "%</span>" +
            "<span><span style='display:inline-block;width:10px;height:10px;border-radius:50%;background:#f44336;margin-right:4px;'></span>Expenses " + (int)(expenses/total*100) + "%</span>" +
            "<span><span style='display:inline-block;width:10px;height:10px;border-radius:50%;background:#FF9800;margin-right:4px;'></span>Supplies " + (int)(supplies/total*100) + "%</span>" +
            "</div></div>";
    }

    private static String pieSlice(int cx, int cy, int r, double startAngle, double endAngle, String color) {
        if (endAngle - startAngle >= 360) endAngle = startAngle + 359.99;
        if (endAngle - startAngle < 0.5) return "";
        double sr = Math.toRadians(startAngle - 90), er = Math.toRadians(endAngle - 90);
        int x1 = (int)(cx + r * Math.cos(sr)), y1 = (int)(cy + r * Math.sin(sr));
        int x2 = (int)(cx + r * Math.cos(er)), y2 = (int)(cy + r * Math.sin(er));
        int large = (endAngle - startAngle) > 180 ? 1 : 0;
        return "<path d='M" + cx + "," + cy + " L" + x1 + "," + y1 + " A" + r + "," + r + " 0 " + large + ",1 " + x2 + "," + y2 + " Z' fill='" + color + "'/>";
    }

    public static String barChart(double sales, double expenses, double supplies, double debts, double payments) {
        double max = Math.max(1, Math.max(sales, Math.max(expenses, Math.max(supplies, Math.max(debts, payments)))));
        StringBuilder svg = new StringBuilder();
        svg.append("<div class='anim-on-scroll chart-wrapper'><svg width='100%' viewBox='0 0 340 200' xmlns='http://www.w3.org/2000/svg'>");
        String[][] bars = {{"Sales",String.valueOf(sales),"#4CAF50"},{"Exp.",String.valueOf(expenses),"#f44336"},
            {"Supply",String.valueOf(supplies),"#FF9800"},{"Debts",String.valueOf(debts),"#e91e63"},{"Paid",String.valueOf(payments),"#2196F3"}};
        for (int i = 0; i < bars.length; i++) {
            double val = Double.parseDouble(bars[i][1]);
            int h = (int)(val / max * 140); if (h < 2 && val > 0) h = 2;
            int x = 25 + i * 65, y = 155 - h;
            svg.append("<rect x='").append(x).append("' y='").append(y).append("' width='40' height='").append(h)
               .append("' fill='").append(bars[i][2]).append("' rx='6' class='bar-el'/>");
            if (val > 0) svg.append("<text x='").append(x+20).append("' y='").append(y-4).append("' text-anchor='middle' font-size='9' fill='#888'>").append(formatAmount(val)).append("</text>");
            svg.append("<text x='").append(x+20).append("' y='175' text-anchor='middle' font-size='10' fill='#888'>").append(bars[i][0]).append("</text>");
        }
        svg.append("</svg></div>");
        return svg.toString();
    }

    public static String emptyState(String message, String ctaText, String ctaHref) {
        return "<div class='empty-state anim-on-scroll'>" +
            "<svg width='80' height='80' viewBox='0 0 80 80'><circle cx='40' cy='40' r='35' fill='#e8f5e9' stroke='#4CAF50' stroke-width='2'/>" +
            "<text x='40' y='45' text-anchor='middle' font-size='28' fill='#4CAF50'>+</text></svg>" +
            "<p style='margin:12px 0 16px;color:#999;font-size:13px;'>" + message + "</p>" +
            (ctaHref != null ? "<a href='" + ctaHref + "' class='btn btn-primary' style='text-decoration:none;font-size:12px;'>" + ctaText + "</a>" : "") +
            "</div>";
    }

    private static final String OBSERVER_JS =
        "<script>" +
        "document.addEventListener('DOMContentLoaded',function(){" +
        "var obs=new IntersectionObserver(function(entries){entries.forEach(function(e){if(e.isIntersecting){e.target.classList.add('in-view');obs.unobserve(e.target);}});},{threshold:0.1,rootMargin:'0px 0px -50px 0px'});" +
        "document.querySelectorAll('.anim-on-scroll').forEach(function(el){obs.observe(el);});" +
        "var progObs=new IntersectionObserver(function(entries){entries.forEach(function(e){if(e.isIntersecting){var bars=e.target.querySelectorAll('.progress-animate');bars.forEach(function(b){b.style.width=b.getAttribute('data-width')+'%';});progObs.unobserve(e.target);}});},{threshold:0.2});" +
        "document.querySelectorAll('.debt-card').forEach(function(el){progObs.observe(el);});" +
        "var countObs=new IntersectionObserver(function(entries){entries.forEach(function(e){if(e.isIntersecting){countUp(e.target);countObs.unobserve(e.target);}});},{threshold:0.3});" +
        "document.querySelectorAll('.count-up').forEach(function(el){countObs.observe(el);});" +
        "function countUp(el){var target=parseFloat(el.getAttribute('data-target'))||0;if(target===0){el.innerHTML='\u20A60.00';return;}var dur=2500,startTime=null;function step(ts){if(!startTime)startTime=ts;var p=Math.min((ts-startTime)/dur,1);p=1-Math.pow(1-p,3);var val=p*target;el.innerHTML='\u20A6'+val.toLocaleString('en-US',{minimumFractionDigits:2,maximumFractionDigits:2});if(p<1)requestAnimationFrame(step);}requestAnimationFrame(step);}" +
        "document.querySelectorAll('.stagger-children').forEach(function(parent){var children=parent.querySelectorAll('.anim-on-scroll,.card');children.forEach(function(c,i){c.style.transitionDelay=(i*0.15)+'s';});});" +
        "});</script>";

    private static final String CSS =
        "*{margin:0;padding:0;box-sizing:border-box;}" +
        "html{scroll-behavior:smooth;}" +
        "body{font-family:'Segoe UI',sans-serif;background:linear-gradient(135deg,#b8e6b3 0%,#d4edda 30%,#e8f5e9 60%,#f0f7f0 100%);background-size:400% 400%;animation:bgShift 12s ease infinite;min-height:100vh;color:#333;}" +
        "@keyframes bgShift{0%{background-position:0% 50%;}50%{background-position:100% 50%;}100%{background-position:0% 50%;}}" +
        ".device-frame{max-width:600px;margin:0 auto;min-height:100vh;}" +
        "@media(min-width:640px){.device-frame{margin:20px auto;min-height:calc(100vh - 40px);border-radius:24px;box-shadow:0 12px 50px rgba(0,0,0,0.18),0 4px 15px rgba(0,0,0,0.1);overflow:hidden;border:1px solid #ccc;}}" +
        ".app-shell{background:#fff;min-height:100vh;}" +
        ".app-header{background:#fff;border-bottom:2px solid #4CAF50;padding:0 16px;display:flex;justify-content:space-between;align-items:center;height:52px;position:sticky;top:0;z-index:100;}" +
        ".hdr-left{display:flex;align-items:center;gap:8px;}" +
        ".logo-link{display:flex;align-items:center;text-decoration:none;gap:6px;transition:transform 0.3s;} .logo-link:hover{transform:scale(1.08);}" +
        ".logo-circle{width:34px;height:34px;background:#c6edc3;border-radius:50%;display:flex;align-items:center;justify-content:center;transition:box-shadow 0.3s;} .logo-circle:hover{box-shadow:0 0 15px rgba(76,175,80,0.5);}" +
        ".logo-img{width:22px;height:22px;}" +
        ".logo-text{font-size:16px;font-weight:700;color:#2e7d32;}" +
        ".biz-name{font-size:12px;font-weight:600;color:#666;max-width:140px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;}" +
        ".hamburger{background:none;border:none;font-size:20px;cursor:pointer;color:#333;padding:4px 8px;transition:all 0.3s;border-radius:6px;} .hamburger:hover{background:#c6edc3;color:#2e7d32;transform:scale(1.15);}" +
        ".sidebar-overlay{display:none;position:fixed;inset:0;background:rgba(0,0,0,0.5);z-index:199;backdrop-filter:blur(2px);}" +
        ".sidebar-overlay.open{display:block;}" +
        ".sidebar{position:fixed;left:-300px;top:0;width:270px;height:100vh;background:linear-gradient(180deg,#1a1a2e 0%,#2e7d32 100%);z-index:200;transition:left 0.4s cubic-bezier(0.4,0,0.2,1);overflow-y:auto;}" +
        ".sidebar.open{left:0;}" +
        ".sidebar-hdr{padding:30px 20px 20px;text-align:center;border-bottom:1px solid rgba(255,255,255,0.15);}" +
        ".sidebar-logo-circle{width:80px;height:80px;background:#c6edc3;border-radius:50%;display:flex;align-items:center;justify-content:center;margin:0 auto 12px;}" +
        ".sidebar-brand{font-size:18px;font-weight:700;color:#fff;}" +
        ".sidebar-close{position:absolute;top:12px;right:12px;background:none;border:none;font-size:24px;color:rgba(255,255,255,0.7);cursor:pointer;transition:all 0.3s;} .sidebar-close:hover{color:#fff;transform:rotate(90deg);}" +
        ".sidebar-nav{padding:10px 0;}" +
        ".side-link{display:flex;align-items:center;gap:12px;padding:14px 24px;color:rgba(255,255,255,0.8);text-decoration:none;font-size:14px;font-weight:500;transition:all 0.3s;position:relative;overflow:hidden;}" +
        ".side-link::before{content:'';position:absolute;left:0;top:0;width:0;height:100%;background:rgba(255,255,255,0.1);transition:width 0.4s;}" +
        ".side-link:hover::before{width:100%;}" +
        ".side-link:hover{color:#fff;padding-left:32px;}" +
        ".side-link.active{background:rgba(255,255,255,0.15);color:#fff;border-right:3px solid #fff;}" +
        ".side-link i{font-size:18px;}" +
        ".sidebar-divider{border-top:1px solid rgba(255,255,255,0.15);margin:8px 0;}" +
        ".logout-link{color:rgba(255,200,200,0.9)!important;} .logout-link:hover{color:#ff6b6b!important;}" +
        ".container{padding:20px 16px;}" +

        // Scroll animations
        ".anim-on-scroll{opacity:0;transform:translateY(30px);transition:opacity 1.5s ease,transform 1.5s ease;}" +
        ".anim-on-scroll.in-view{opacity:1;transform:translateY(0);}" +
        "@keyframes bounceIn{0%{opacity:0;transform:translateY(30px) scale(0.9);}60%{transform:translateY(-5px) scale(1.02);}100%{opacity:1;transform:translateY(0) scale(1);}}" +
        ".bounce-in.in-view{animation:bounceIn 1.5s ease forwards;}" +

        // Bar chart - bars animate via parent .in-view
        ".chart-wrapper .bar-el{transform:scaleY(0);transform-origin:bottom;transition:transform 2.5s cubic-bezier(0.34,1.56,0.64,1);}" +
        ".chart-wrapper.in-view .bar-el{transform:scaleY(1);}" +

        ".greeting{margin-bottom:20px;} .greeting h2{font-size:20px;color:#1a1a2e;margin-bottom:3px;} .greeting p{color:#888;font-size:13px;}" +
        ".health-card{display:flex;align-items:center;gap:12px;background:#fff;border-radius:12px;padding:14px 16px;margin-bottom:16px;border:1px solid #e0e0e0;transition:all 0.4s;} .health-card:hover{box-shadow:0 6px 20px rgba(0,0,0,0.1);transform:translateY(-4px);}" +
        ".health-dot{width:16px;height:16px;border-radius:50%;flex-shrink:0;animation:pulse 3s ease-in-out infinite;}" +
        "@keyframes pulse{0%,100%{opacity:1;transform:scale(1);}50%{opacity:0.6;transform:scale(0.8);}}" +
        ".streak-banner{background:linear-gradient(135deg,#fff8e1,#fff3e0);border:1px solid #ffe082;border-radius:12px;padding:12px 16px;margin-bottom:16px;font-size:13px;font-weight:600;color:#e65100;text-align:center;}" +

        // Cards
        ".cards{display:grid;grid-template-columns:repeat(2,1fr);gap:10px;margin-bottom:20px;}" +
        ".card{background:linear-gradient(135deg,#fff 60%,#f8fff8);border-radius:12px;padding:16px;text-align:center;border:1px solid #e8e8e8;transition:all 0.4s cubic-bezier(0.4,0,0.2,1);cursor:default;}" +
        ".card:hover{transform:translateY(-8px) scale(1.03);}" +
        ".card.sales{border-left:4px solid #4CAF50;background:linear-gradient(135deg,#fff 50%,#e8f5e9);} .card.sales:hover{box-shadow:0 8px 30px rgba(76,175,80,0.3);} .card.sales .value{color:#2e7d32;}" +
        ".card.expenses{border-left:4px solid #f44336;background:linear-gradient(135deg,#fff 50%,#ffebee);} .card.expenses:hover{box-shadow:0 8px 30px rgba(244,67,54,0.25);} .card.expenses .value{color:#c62828;}" +
        ".card.supplies{border-left:4px solid #FF9800;background:linear-gradient(135deg,#fff 50%,#fff3e0);} .card.supplies:hover{box-shadow:0 8px 30px rgba(255,152,0,0.25);} .card.supplies .value{color:#e65100;}" +
        ".card.debts{border-left:4px solid #e91e63;background:linear-gradient(135deg,#fff 50%,#fce4ec);} .card.debts:hover{box-shadow:0 8px 30px rgba(233,30,99,0.25);} .card.debts .value{color:#ad1457;}" +
        ".card.payments{border-left:4px solid #2196F3;background:linear-gradient(135deg,#fff 50%,#e3f2fd);} .card.payments:hover{box-shadow:0 8px 30px rgba(33,150,243,0.25);} .card.payments .value{color:#1565c0;}" +
        ".card.profit{border-left:4px solid #4CAF50;background:linear-gradient(135deg,#fff 50%,#e8f5e9);} .card.profit:hover{box-shadow:0 8px 30px rgba(76,175,80,0.3);} .card.profit .value{color:#2e7d32;}" +
        ".card.profit.negative{border-left-color:#f44336;background:linear-gradient(135deg,#fff 50%,#ffebee);} .card.profit.negative:hover{box-shadow:0 8px 30px rgba(244,67,54,0.25);} .card.profit.negative .value{color:#c62828;}" +
        ".card h3{font-size:9px;color:#999;text-transform:uppercase;letter-spacing:1.5px;margin-bottom:6px;}" +
        ".card .value{font-size:22px;font-weight:800;letter-spacing:-0.5px;}" +

        // Sections
        ".section{background:#fff;border-radius:14px;padding:20px;margin-bottom:20px;border:1px solid #e8e8e8;}" +
        ".section.alt{background:#c6edc3;}" +
        ".section h2{font-size:15px;margin-bottom:14px;padding:6px 14px;display:inline-block;background:linear-gradient(135deg,#2e7d32,#4CAF50);color:#fff;border-radius:20px;}" +

        // Tables
        "table{width:100%;border-collapse:collapse;}" +
        "th{background:#f5f5f5;color:#888;padding:8px 10px;text-align:left;font-size:10px;text-transform:uppercase;border-bottom:1px solid #eee;}" +
        "td{padding:8px 10px;border-bottom:1px solid #f5f5f5;font-size:12px;color:#555;}" +
        "tr{transition:all 0.3s;} tr:hover{background:#f0fff0;}" +
        ".row-type-SALE:hover{background:#e8f5e9;} .row-type-EXPENSE:hover{background:#ffebee;} .row-type-SUPPLY:hover{background:#fff3e0;} .row-type-DEBT:hover{background:#fce4ec;} .row-type-PAYMENT:hover{background:#e3f2fd;}" +
        ".badge{padding:3px 10px;border-radius:10px;font-size:9px;font-weight:600;text-transform:uppercase;}" +
        ".badge-SALE{background:#e8f5e9;color:#2e7d32;} .badge-EXPENSE{background:#ffebee;color:#c62828;} .badge-SUPPLY{background:#fff3e0;color:#e65100;} .badge-DEBT{background:#fce4ec;color:#ad1457;} .badge-PAYMENT{background:#e3f2fd;color:#1565c0;}" +
        ".empty{color:#bbb;font-style:italic;padding:15px 0;font-size:13px;text-align:center;}" +
        ".empty-state{text-align:center;padding:30px 10px;}" +

        // Transaction cards
        ".txn-card{background:linear-gradient(135deg,#fff 70%,#fafafa);border-radius:12px;padding:14px;margin-bottom:10px;border:1px solid #e8e8e8;border-left:4px solid #ddd;transition:all 0.4s cubic-bezier(0.4,0,0.2,1);}" +
        ".txn-card:hover{transform:translateY(-8px) scale(1.02);border-left-width:6px;}" +
        ".txn-card.type-SALE{border-left-color:#4CAF50;} .txn-card.type-SALE:hover{box-shadow:0 8px 25px rgba(76,175,80,0.2);}" +
        ".txn-card.type-EXPENSE{border-left-color:#f44336;} .txn-card.type-EXPENSE:hover{box-shadow:0 8px 25px rgba(244,67,54,0.15);}" +
        ".txn-card.type-SUPPLY{border-left-color:#FF9800;} .txn-card.type-SUPPLY:hover{box-shadow:0 8px 25px rgba(255,152,0,0.15);}" +
        ".txn-card.type-DEBT{border-left-color:#e91e63;} .txn-card.type-DEBT:hover{box-shadow:0 8px 25px rgba(233,30,99,0.15);}" +
        ".txn-card.type-PAYMENT{border-left-color:#2196F3;} .txn-card.type-PAYMENT:hover{box-shadow:0 8px 25px rgba(33,150,243,0.15);}" +
        ".txn-top{display:flex;justify-content:space-between;align-items:center;margin-bottom:6px;}" +
        ".txn-amount{font-size:18px;font-weight:700;color:#1a1a2e;}" +
        ".txn-desc{font-size:12px;color:#888;margin-bottom:4px;}" +
        ".txn-bottom{display:flex;justify-content:space-between;align-items:center;}" +
        ".txn-meta{font-size:11px;color:#aaa;}" +
        ".txn-actions{display:flex;gap:6px;opacity:0;transition:opacity 0.3s;}" +
        ".txn-card:hover .txn-actions{opacity:1;}" +
        ".cat-tabs{display:flex;gap:4px;overflow-x:auto;margin-bottom:16px;padding-bottom:4px;}" +
        ".cat-tab{padding:7px 14px;border-radius:20px;font-size:11px;font-weight:600;text-decoration:none;color:#888;background:#f5f5f5;white-space:nowrap;transition:all 0.3s;border:1px solid transparent;}" +
        ".cat-tab:hover{transform:scale(1.1);box-shadow:0 4px 12px rgba(0,0,0,0.1);color:#333;}" +
        ".cat-tab.active{color:#fff;}" +
        ".cat-tab.active.t-ALL{background:#333;} .cat-tab.active.t-SALE{background:#4CAF50;} .cat-tab.active.t-EXPENSE{background:#f44336;} .cat-tab.active.t-SUPPLY{background:#FF9800;} .cat-tab.active.t-DEBT{background:#e91e63;} .cat-tab.active.t-PAYMENT{background:#2196F3;}" +
        ".btn{padding:6px 14px;border:none;border-radius:8px;font-size:11px;cursor:pointer;font-weight:600;transition:all 0.3s;}" +
        ".btn:hover{transform:translateY(-3px);box-shadow:0 5px 15px rgba(0,0,0,0.15);}" +
        ".btn:active{transform:scale(0.95);box-shadow:none;}" +
        ".btn-danger{background:linear-gradient(135deg,#ffebee,#ffcdd2);color:#c62828;} .btn-danger:hover{background:linear-gradient(135deg,#ffcdd2,#ef9a9a);}" +
        ".btn-primary{background:linear-gradient(135deg,#4CAF50,#66BB6A);color:#fff;padding:10px 18px;font-size:13px;} .btn-primary:hover{background:linear-gradient(135deg,#43A047,#4CAF50);box-shadow:0 6px 20px rgba(76,175,80,0.4);}" +

        // Debts
        ".debt-card{background:linear-gradient(135deg,#fff 70%,#fce4ec);border-radius:12px;padding:16px;margin-bottom:12px;border:1px solid #e8e8e8;border-left:4px solid #e91e63;transition:all 0.4s;}" +
        ".debt-card:hover{transform:translateY(-6px) scale(1.02);box-shadow:0 8px 25px rgba(233,30,99,0.2);border-left-width:6px;}" +
        ".debt-card h3{margin-bottom:8px;color:#333;font-size:14px;}" +
        ".progress-bar{height:8px;background:#f0f0f0;border-radius:4px;overflow:hidden;margin:10px 0;}" +
        ".progress-animate{height:100%;border-radius:4px;width:0;transition:width 2.5s cubic-bezier(0.4,0,0.2,1);}" +
        ".debt-amounts{display:flex;gap:12px;font-size:12px;flex-wrap:wrap;}" +
        ".status-badge{padding:3px 10px;border-radius:10px;font-size:10px;font-weight:600;}" +
        ".status-unpaid{background:#ffebee;color:#c62828;} .status-partial{background:#fff3e0;color:#e65100;} .status-paid{background:#e8f5e9;color:#2e7d32;}" +

        // Chat
        ".chat-container{padding:16px;}" +
        ".chat-messages{min-height:350px;max-height:450px;overflow-y:auto;margin-bottom:12px;}" +
        ".chat-msg{padding:10px 14px;margin:8px 0;border-radius:12px;font-size:13px;max-width:85%;}" +
        ".chat-msg.user{background:linear-gradient(135deg,#e8f5e9,#c8e6c9);border-bottom-right-radius:4px;margin-left:auto;animation:slideRight 1.8s ease forwards;}" +
        ".chat-msg.system{background:linear-gradient(135deg,#f5f5f5,#eee);border-bottom-left-radius:4px;animation:slideLeft 1.8s ease forwards;}" +
        "@keyframes slideRight{from{opacity:0;transform:translateX(30px);}to{opacity:1;transform:translateX(0);}}" +
        "@keyframes slideLeft{from{opacity:0;transform:translateX(-30px);}to{opacity:1;transform:translateX(0);}}" +
        ".typing{display:flex;gap:5px;padding:12px 16px;} .typing span{width:8px;height:8px;background:#aaa;border-radius:50%;animation:bounce 1.4s infinite;}" +
        ".typing span:nth-child(2){animation-delay:0.2s;} .typing span:nth-child(3){animation-delay:0.4s;}" +
        "@keyframes bounce{0%,80%,100%{transform:translateY(0);}40%{transform:translateY(-10px);}}" +
        ".chat-input-bar{display:flex;gap:8px;}" +
        ".chat-input-bar input{flex:1;padding:12px 14px;border:2px solid #e0e0e0;border-radius:12px;font-size:14px;transition:all 0.3s;} .chat-input-bar input:focus{outline:none;border-color:#4CAF50;box-shadow:0 0 15px rgba(76,175,80,0.25);}" +
        ".chat-input-bar button{border-radius:12px;}" +
        ".help-fab{position:fixed;bottom:80px;left:20px;width:42px;height:42px;border-radius:50%;background:linear-gradient(135deg,#2196F3,#42A5F5);color:#fff;border:none;font-size:18px;font-weight:700;cursor:pointer;box-shadow:0 4px 15px rgba(33,150,243,0.35);z-index:50;transition:all 0.3s;animation:fabGlow 3s ease infinite;}" +
        ".help-fab:hover{transform:scale(1.2);box-shadow:0 6px 25px rgba(33,150,243,0.5);}" +
        "@keyframes fabGlow{0%,100%{box-shadow:0 4px 15px rgba(33,150,243,0.3);}50%{box-shadow:0 4px 25px rgba(33,150,243,0.6);}}" +
        ".help-panel{position:fixed;right:-320px;top:0;width:300px;height:100vh;background:#fff;z-index:201;transition:right 0.4s cubic-bezier(0.4,0,0.2,1);box-shadow:-4px 0 20px rgba(0,0,0,0.15);overflow-y:auto;padding:20px;}" +
        ".help-panel.open{right:0;}" +
        ".help-example{padding:10px 14px;margin:6px 0;background:#f5f5f5;border-radius:8px;font-size:13px;cursor:pointer;transition:all 0.3s;border-left:3px solid transparent;}" +
        ".help-example:hover{background:#e8f5e9;border-left-color:#4CAF50;transform:translateX(6px) scale(1.03);box-shadow:0 2px 8px rgba(0,0,0,0.06);}" +
        ".confirm-card{background:linear-gradient(135deg,#fffde7,#fff9c4);border:1px solid #ffd54f;border-radius:12px;padding:14px;margin:8px 0;animation:confirmSlide 1.5s cubic-bezier(0.4,0,0.2,1) forwards;}" +
        "@keyframes confirmSlide{from{opacity:0;transform:translateY(-15px) scale(0.95);}to{opacity:1;transform:translateY(0) scale(1);}}" +
        ".confirm-card .actions{display:flex;gap:6px;margin-top:10px;flex-wrap:wrap;}" +
        ".confirm-card .actions button{padding:8px 14px;border:none;border-radius:8px;cursor:pointer;font-size:12px;font-weight:600;transition:all 0.3s;}" +
        ".confirm-card .actions button:hover{transform:translateY(-2px);box-shadow:0 4px 12px rgba(0,0,0,0.2);}" +
        ".confirm-card .actions button:active{transform:scale(0.95);}" +
        ".confirm-btn{background:linear-gradient(135deg,#4CAF50,#66BB6A);color:#fff;} .change-btn{background:linear-gradient(135deg,#FF9800,#FFB74D);color:#fff;} .cancel-btn{background:linear-gradient(135deg,#f44336,#ef5350);color:#fff;}" +
        ".category-select{padding:6px 10px;border:2px solid #e0e0e0;border-radius:8px;font-size:12px;margin-top:6px;transition:border-color 0.3s;} .category-select:focus{border-color:#4CAF50;outline:none;}" +
        ".toast{position:fixed;top:60px;right:20px;padding:14px 22px;border-radius:12px;color:#fff;font-size:13px;font-weight:600;z-index:300;opacity:0;transform:translateY(-20px) scale(0.9);transition:all 0.5s cubic-bezier(0.4,0,0.2,1);box-shadow:0 6px 20px rgba(0,0,0,0.2);max-width:280px;}" +
        ".toast.show{opacity:1;transform:translateY(0) scale(1);}" +
        ".toast.success{background:linear-gradient(135deg,#4CAF50,#66BB6A);}" +
        ".fab{position:fixed;bottom:24px;right:24px;width:54px;height:54px;border-radius:50%;background:linear-gradient(135deg,#4CAF50,#66BB6A);color:#fff;font-size:24px;border:none;cursor:pointer;box-shadow:0 6px 20px rgba(76,175,80,0.4);display:flex;align-items:center;justify-content:center;text-decoration:none;z-index:50;transition:all 0.3s;animation:fabGlow2 3s ease infinite;}" +
        ".fab:hover{transform:scale(1.15) rotate(90deg);box-shadow:0 8px 30px rgba(76,175,80,0.5);}" +
        "@keyframes fabGlow2{0%,100%{box-shadow:0 6px 20px rgba(76,175,80,0.3);}50%{box-shadow:0 6px 30px rgba(76,175,80,0.6);}}" +
        ".filter-bar{display:flex;gap:8px;flex-wrap:wrap;align-items:center;margin-bottom:16px;}" +
        ".filter-bar select,.filter-bar input{padding:7px 10px;border:2px solid #e0e0e0;border-radius:8px;font-size:12px;transition:all 0.3s;} .filter-bar select:focus,.filter-bar input:focus{border-color:#4CAF50;box-shadow:0 0 10px rgba(76,175,80,0.15);outline:none;}" +
        ".advice-card{background:linear-gradient(135deg,#fff 70%,#f8fff8);border-radius:12px;padding:16px;margin-bottom:12px;border:1px solid #e8e8e8;border-left:4px solid #4CAF50;transition:all 0.4s;}" +
        ".advice-card:hover{transform:translateY(-4px);box-shadow:0 6px 18px rgba(0,0,0,0.08);}" +
        ".advice-card.warning{border-left-color:#FF9800;background:linear-gradient(135deg,#fff 70%,#fff8f0);} .advice-card.danger{border-left-color:#f44336;background:linear-gradient(135deg,#fff 70%,#fff5f5);}" +
        ".advice-card h4{margin-bottom:4px;color:#333;font-size:14px;} .advice-card p{color:#666;font-size:12px;line-height:1.5;}" +
        ".period-bar{display:flex;gap:6px;flex-wrap:wrap;margin-bottom:20px;}" +
        ".period-btn{padding:7px 16px;border-radius:20px;text-decoration:none;font-size:12px;font-weight:500;border:1px solid #e0e0e0;color:#888;background:#fff;transition:all 0.3s;}" +
        ".period-btn:hover{transform:scale(1.1);box-shadow:0 4px 12px rgba(0,0,0,0.1);}" +
        ".period-btn.active{background:linear-gradient(135deg,#4CAF50,#66BB6A);color:#fff;border-color:#4CAF50;}" +
        ".chart-container{background:#fff;border-radius:12px;padding:16px;margin-bottom:20px;border:1px solid #e8e8e8;}" +
        ".chart-container h3{color:#333;font-size:14px;margin-bottom:12px;}" +
        ".carousel{position:relative;overflow:hidden;border-radius:14px;margin-bottom:20px;background:linear-gradient(135deg,#e8f5e9,#c6edc3);}" +
        ".carousel-track{display:flex;transition:transform 0.8s cubic-bezier(0.4,0,0.2,1);}" +
        ".carousel-slide{min-width:100%;padding:24px 20px;text-align:center;}" +
        ".carousel-slide h3{font-size:11px;color:#666;text-transform:uppercase;letter-spacing:1px;margin-bottom:8px;}" +
        ".carousel-slide .big-num{font-size:34px;font-weight:800;}" +
        ".carousel-dots{display:flex;justify-content:center;gap:8px;padding:10px;}" +
        ".carousel-dots span{width:8px;height:8px;border-radius:50%;background:rgba(0,0,0,0.2);cursor:pointer;transition:all 0.3s;}" +
        ".carousel-dots span:hover{transform:scale(1.4);}" +
        ".carousel-dots span.active{background:#2e7d32;transform:scale(1.2);}" +
        "a{transition:all 0.3s;position:relative;}" +
        "a.slide-link{text-decoration:none;} a.slide-link::after{content:'';position:absolute;bottom:-2px;left:0;width:0;height:2px;background:#4CAF50;transition:width 0.4s cubic-bezier(0.4,0,0.2,1);} a.slide-link:hover::after{width:100%;}" +
        "@media print{.app-header,.sidebar,.sidebar-overlay,.fab,.help-fab,.no-print{display:none!important;}.device-frame{max-width:100%;margin:0;box-shadow:none;border:none;border-radius:0;}.app-shell{min-height:auto;}}" +
        "";
}
