package dashboard;

public class HtmlTemplates {

    public static final String LOGO_DATA = "data:image/svg+xml;utf8,%3Csvg%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20viewBox%3D%220%200%2036%2036%22%20width%3D%2232%22%20height%3D%2232%22%20fill%3D%22none%22%3E%3Cpath%20d%3D%22M22%2010h-8a4%204%200%200%200-4%204v0a4%204%200%200%200%204%204h8a4%204%200%200%201%204%204v0a4%204%200%200%201-4%204h-9%22%20stroke%3D%22%233b82f6%22%20stroke-width%3D%223.2%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22/%3E%3Cpath%20d%3D%22M22%2016l8-8m0%200h-5.5m5.5%200v5.5%22%20stroke%3D%22%230f172a%22%20stroke-width%3D%223%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22/%3E%3Ccircle%20cx%3D%2230%22%20cy%3D%228%22%20r%3D%221.5%22%20fill%3D%22%233b82f6%22/%3E%3C/svg%3E";

    public static String head(String title) {
        return "<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width,initial-scale=1.0'>" +
            "<title>SmartLedger - " + title + "</title>" +
            "<link rel='icon' type='image/png' href='" + LOGO_DATA + "'><link rel='shortcut icon' type='image/png' href='" + LOGO_DATA + "'>" +
            "<link rel='preconnect' href='https://fonts.googleapis.com'>" +
            "<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>" +
            "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800;900&display=swap' rel='stylesheet'>" +
            "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css'>" +
            "<style>" + CSS + "</style></head><body><div class='app'>";
    }

    public static String fullNav(String token, String active, String businessName) {
        String bizDisplay = (businessName != null && !businessName.isEmpty()) ? escapeHtml(businessName) : "";
        return "<header class='app-header'>" +
            "<div class='hdr-left'>" +
            "<button class='hamburger' onclick='toggleSidebar()' aria-label='Open Navigation'><i class='ti ti-menu-2'></i></button>" +
            "<a href='/dashboard/" + token + "' class='logo-link'>" +
            "<div class='logo-badge'><img src='" + LOGO_DATA + "' class='logo-img' alt='SmartLedger'></div>" +
            "<div class='logo-meta'><span class='logo-text'>SmartLedger</span>" +
            (bizDisplay.isEmpty() ? "" : "<span class='biz-badge'>" + bizDisplay + "</span>") +
            "</div></a></div>" +
            "<div class='hdr-right'>" +
            "<a href='/chat/" + token + "' class='nav-action-btn' title='New Transaction'><i class='ti ti-plus'></i><span>Record</span></a>" +
            "</div>" +
            "</header>" + sidebar(token, active);
    }

    private static String sidebar(String token, String active) {
        return "<div class='sidebar-overlay' id='sidebarOverlay' onclick='toggleSidebar()'></div>" +
            "<aside class='sidebar' id='sidebar'>" +
            "<div class='sidebar-hdr'>" +
            "<div class='sidebar-brand-box'>" +
            "<div class='sidebar-logo-badge'><img src='" + LOGO_DATA + "' style='width:32px;height:32px;' alt='Logo'></div>" +
            "<div><div class='sidebar-brand'>SmartLedger</div><div class='sidebar-tagline'>Bookkeeping for traders</div></div>" +
            "</div>" +
            "<button onclick='toggleSidebar()' class='sidebar-close' aria-label='Close Navigation'><i class='ti ti-x'></i></button>" +
            "</div>" +
            "<nav class='sidebar-nav'>" +
            "<div class='nav-section-label'>Main</div>" +
            sideLink("/dashboard/" + token, "Overview", "ti-layout-grid", active.equals("overview")) +
            sideLink("/chat/" + token, "Chat Assistant", "ti-message-circle", active.equals("chat")) +
            sideLink("/dashboard/" + token + "/transactions", "Transactions", "ti-receipt", active.equals("transactions")) +
            sideLink("/dashboard/" + token + "/debts", "Debts & Credit", "ti-scale", active.equals("debts")) +
            "<div class='nav-section-label'>Reports</div>" +
            sideLink("/analysis/" + token, "Analytics", "ti-chart-pie", active.equals("analysis")) +
            sideLink("/report/" + token, "Financial Report", "ti-file-analytics", active.equals("report")) +
            "<div class='sidebar-divider'></div>" +
            "<a href='/' class='side-link logout-link'><i class='ti ti-logout'></i> Log Out</a>" +
            "</nav></aside>" +
            "<script>function toggleSidebar(){document.getElementById('sidebar').classList.toggle('open');" +
            "document.getElementById('sidebarOverlay').classList.toggle('open');}</script>";
    }

    private static String sideLink(String href, String label, String icon, boolean isActive) {
        return "<a href='" + href + "' class='side-link" + (isActive ? " active" : "") + "'>" +
            "<i class='ti " + icon + "' aria-hidden='true'></i><span>" + label + "</span></a>";
    }

    public static String footer() {
        return "</div>" +
            "<footer class='app-footer'>" +
            "<div class='footer-inner'>" +
            "<div>SmartLedger © 2026 — COS 202 Group 22</div>" +
            "<div class='footer-sub'>Aligned with UN SDG 8 (Decent Work) and SDG 9 (Industry, Innovation)</div>" +
            "</div></footer>" + OBSERVER_JS + "</body></html>";
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

    public static String metricList(String title, double value, String cssClass, boolean isToday) {
        String displayValue;
        if (value == 0) {
            displayValue = "<span style='color:var(--text-muted);'>—</span>";
        } else {
            displayValue = "<span class='count-up' data-target='" + (long)value + "'>₦0.00</span>";
        }
        String style = isToday ? "color:var(--brand-primary); font-size:22px; font-weight:800;" : "";
        return "<div class='metric-row " + cssClass + "'>" +
            "<span class='metric-label'>" + title + "</span>" +
            "<span class='metric-value' style='" + style + "'>" + displayValue + "</span>" +
            "</div>";
    }

    public static String greeting(String username, double todaySales, String businessName) {
        int hour = java.time.LocalTime.now().getHour();
        String timeGreet = hour >= 5 && hour < 12 ? "Good morning" : 
                           hour < 17 ? "Good afternoon" : 
                           hour < 21 ? "Good evening" : "Hello";
        String salesMsg = todaySales > 0 ? 
            "You've made <strong style='color:var(--brand-primary);'>₦" + formatAmount(todaySales) + "</strong> in sales today." : 
            "No sales recorded yet today.";
        return "<div class='greeting anim-on-scroll'>" +
            "<h2>" + timeGreet + ", " + escapeHtml(username) + "</h2>" +
            "<p class='greeting-sub'>" + salesMsg + "</p></div>";
    }

    public static String healthIndicator(double sales, double expenses, double supplies) {
        double profit = sales - expenses - supplies;
        double ratio = sales > 0 ? (expenses / sales) * 100 : 0;
        String color, label, tip;
        if (profit > 0 && ratio < 50) {
            color = "var(--brand-primary)"; label = "Healthy"; tip = "Business in good shape";
        } else if (profit > 0) {
            color = "var(--brand-secondary)"; label = "Okay"; tip = "Expenses creeping up";
        } else {
            color = "var(--brand-dark)"; label = "Needs Attention"; tip = "Spending exceeds earnings";
        }
        return "<div class='health-pill anim-on-scroll'>" +
            "<span class='health-dot' style='background:" + color + ";'></span>" +
            "<span class='health-label'>" + label + "</span>" +
            "<span class='health-tip'>" + tip + "</span></div>";
    }

    public static String streakBanner(int streak) {
        if (streak < 1) return "";
        return "<div class='streak-pill anim-on-scroll'>" +
            "<span class='streak-icon'>🔥</span>" +
            "<span class='streak-text'><strong>" + streak + " Day Streak</strong> — Keep it going!</span></div>";
    }

    public static String pieChart(double sales, double expenses, double supplies) {
        double total = sales + expenses + supplies;
        if (total == 0) return "<p class='empty'>No data yet.</p>";
        double s1 = sales/total*360, s2 = expenses/total*360, s3 = supplies/total*360;
        double a1 = 0, a2 = s1, a3 = s1+s2;
        return "<div class='donut-container anim-on-scroll'>" +
            "<svg width='140' height='140' viewBox='0 0 180 180' class='donut-svg'>" +
            pieSlice(90,90,72,a1,a1+s1,"var(--sales-val)") + 
            pieSlice(90,90,72,a2,a2+s2,"var(--expense-val)") + 
            pieSlice(90,90,72,a3,a3+s3,"var(--supply-val)") +
            "<circle cx='90' cy='90' r='45' fill='var(--bg-surface)'/>" +
            "<text x='90' y='78' text-anchor='middle' font-size='8' fill='var(--text-muted)'>Total</text>" +
            "<text x='90' y='98' text-anchor='middle' font-size='11' font-weight='700' fill='var(--text-primary)'>₦" + formatAmount(total) + "</text>" +
            "</svg>" +
            "<div class='chart-legend'>" +
            "<span class='legend-item'><span class='legend-dot' style='background:var(--sales-val);'></span>Sales " + (int)(sales/total*100) + "%</span>" +
            "<span class='legend-item'><span class='legend-dot' style='background:var(--expense-val);'></span>Expenses " + (int)(expenses/total*100) + "%</span>" +
            "<span class='legend-item'><span class='legend-dot' style='background:var(--supply-val);'></span>Supplies " + (int)(supplies/total*100) + "%</span>" +
            "</div></div>";
    }

    private static String pieSlice(int cx, int cy, int r, double startAngle, double endAngle, String color) {
        if (endAngle - startAngle >= 360) endAngle = startAngle + 359.99;
        if (endAngle - startAngle < 0.5) return "";
        double sr = Math.toRadians(startAngle - 90), er = Math.toRadians(endAngle - 90);
        int x1 = (int)(cx + r * Math.cos(sr)), y1 = (int)(cy + r * Math.sin(sr));
        int x2 = (int)(cx + r * Math.cos(er)), y2 = (int)(cy + r * Math.sin(er));
        int large = (endAngle - startAngle) > 180 ? 1 : 0;
        return "<path d='M" + cx + "," + cy + " L" + x1 + "," + y1 + " A" + r + "," + r + " 0 " + large + ",1 " + x2 + "," + y2 + " Z' fill='" + color + "' stroke='var(--bg-surface)' stroke-width='2'/>";
    }

    public static String barChart(double sales, double expenses, double supplies, double debts, double payments) {
    double max = Math.max(1, Math.max(sales, Math.max(expenses, Math.max(supplies, Math.max(debts, payments)))));
    StringBuilder svg = new StringBuilder();
    svg.append("<div class='chart-wrapper anim-on-scroll' style='max-width:100%;overflow-x:auto;padding:4px 0;'>");
    svg.append("<svg width='100%' viewBox='0 0 500 150' xmlns='http://www.w3.org/2000/svg' style='display:block;'>");

    // Y-axis line
    svg.append("<line x1='50' y1='20' x2='50' y2='115' stroke='var(--border-rule)' stroke-width='1'/>");
    
    // X-axis line
    svg.append("<line x1='50' y1='115' x2='470' y2='115' stroke='var(--border-rule)' stroke-width='1'/>");

    // Y-axis labels (small, clean)
    int maxVal = (int)Math.ceil(max / 1000) * 1000;
    int steps = 5;
    if (maxVal < 1000) { maxVal = (int)Math.ceil(max / 100) * 100; }
    if (maxVal < 100) { maxVal = (int)Math.ceil(max / 10) * 10; }
    if (maxVal < 10) { maxVal = 10; }
    
    for (int i = 0; i <= steps; i++) {
        double val = maxVal * (1.0 - (double)i / steps);
        int yPos = 20 + (int)((115 - 20) * (double)i / steps);
        svg.append("<text x='45' y='").append(yPos + 4).append("' text-anchor='end' font-size='7' font-weight='500' fill='var(--text-muted)'>");
        if (val >= 1000) {
            svg.append(String.format("%.0fk", val/1000));
        } else if (val >= 100) {
            svg.append(String.format("%.0f", val));
        } else {
            svg.append(String.format("%.0f", val));
        }
        svg.append("</text>");
        // Grid line
        svg.append("<line x1='50' y1='").append(yPos).append("' x2='470' y2='").append(yPos)
           .append("' stroke='var(--border-rule)' stroke-width='0.5' stroke-dasharray='3 3'/>");
    }

    String[][] bars = {
        {"Sales", String.valueOf(sales), "var(--sales-val)"},
        {"Expense", String.valueOf(expenses), "var(--expense-val)"},
        {"Supply", String.valueOf(supplies), "var(--supply-val)"},
        {"Debt", String.valueOf(debts), "var(--debt-val)"},
        {"Paid", String.valueOf(payments), "var(--payment-val)"}
    };

    // SHARP CORNERS - no rx/ry
    int barWidth = 24;
    int gap = 30;
    int totalWidth = bars.length * (barWidth + gap) - gap;
    int startX = 50 + (400 - totalWidth) / 2;

    for (int i = 0; i < bars.length; i++) {
        double val = Double.parseDouble(bars[i][1]);
        int barHeight = (int)(val / max * 85);
        if (barHeight < 2 && val > 0) barHeight = 2;
        
        int x = startX + i * (barWidth + gap);
        int y = 115 - barHeight;
        
        // SHARP CORNERS - no rx
        svg.append("<rect x='").append(x).append("' y='").append(y).append("' width='").append(barWidth)
           .append("' height='").append(barHeight).append("' fill='").append(bars[i][2])
           .append("' class='bar-el'/>");
        
        // Value above bar (smaller font)
        if (val > 0) {
            svg.append("<text x='").append(x + barWidth/2).append("' y='").append(Math.max(12, y - 4))
               .append("' text-anchor='middle' font-size='7' font-weight='600' fill='var(--text-primary)'>")
               .append(formatAmount(val)).append("</text>");
        }
        
        // Category label below (smaller font)
        svg.append("<text x='").append(x + barWidth/2).append("' y='132' text-anchor='middle' font-size='8' font-weight='600' fill='var(--text-muted)'>")
           .append(bars[i][0]).append("</text>");
    }

    svg.append("</svg></div>");
    return svg.toString();
}

    public static String emptyState(String message, String ctaText, String ctaHref) {
        return "<div class='empty-state anim-on-scroll'>" +
            "<div class='empty-icon-wrap'><i class='ti ti-receipt-off'></i></div>" +
            "<h4>No transactions yet</h4>" +
            "<p>" + message + "</p>" +
            (ctaHref != null ? "<a href='" + ctaHref + "' class='btn btn-primary'><i class='ti ti-plus'></i> " + ctaText + "</a>" : "") +
            "</div>";
    }

    private static final String OBSERVER_JS =
        "<script>" +
        "document.addEventListener('DOMContentLoaded',function(){" +
        "var obs=new IntersectionObserver(function(entries){entries.forEach(function(e){if(e.isIntersecting){e.target.classList.add('in-view');obs.unobserve(e.target);}});},{threshold:0.1});" +
        "document.querySelectorAll('.anim-on-scroll').forEach(function(el){obs.observe(el);});" +
        "var countObs=new IntersectionObserver(function(entries){entries.forEach(function(e){if(e.isIntersecting){countUp(e.target);countObs.unobserve(e.target);}});},{threshold:0.1});" +
        "document.querySelectorAll('.count-up').forEach(function(el){countObs.observe(el);});" +
        "function countUp(el){var target=parseFloat(el.getAttribute('data-target'))||0;if(target===0){el.innerHTML='₦0.00';return;}var dur=800,start=null;function step(t){if(!start)start=t;var p=Math.min((t-start)/dur,1);p=1-Math.pow(1-p,3);var val=p*target;el.innerHTML='₦'+val.toLocaleString('en-US',{minFrac:2,maxFrac:2});if(p<1)requestAnimationFrame(step);}requestAnimationFrame(step);}" +
        "});</script>";

    private static final String CSS =
        ":root{" +
        "--bg-canvas:#e8ecf1;" +
        "--bg-surface:#ffffff;" +
        "--bg-subtle:#d5dce4;" +
        "--border-rule:#c5cdd8;" +
        "--border-light:#b0b8c4;" +
        "--text-primary:#1a1a2e;" +
        "--text-secondary:#2c3e50;" +
        "--text-muted:#5b6f84;" +
        "--brand-primary:#2563eb;" +
        "--brand-secondary:#3b82f6;" +
        "--brand-dark:#1d4ed8;" +
        "--brand-light:#dbeafe;" +
        "--sales-val:#3b82f6;" +
        "--expense-val:#2563eb;" +
        "--supply-val:#1d4ed8;" +
        "--debt-val:#4f46e5;" +
        "--payment-val:#0ea5e9;" +
        "--personal-val:#6366f1;" +
        "}" +
        "*{margin:0;padding:0;box-sizing:border-box;}" +
        "html{scroll-behavior:smooth;background:var(--bg-canvas);}" +
        "body{font-family:'Inter',sans-serif;background:var(--bg-canvas);color:var(--text-primary);min-height:100vh;line-height:1.5;}" +
        ".app{min-height:100vh;display:flex;flex-direction:column;}" +
        ".app-header{background:var(--bg-surface);border-bottom:1px solid var(--border-rule);padding:0 24px;display:flex;justify-content:space-between;align-items:center;height:56px;position:sticky;top:0;z-index:100;}" +
        ".hdr-left{display:flex;align-items:center;gap:12px;}" +
        ".logo-link{display:flex;align-items:center;text-decoration:none;gap:8px;}" +
        ".logo-badge{display:flex;align-items:center;justify-content:center;}" +
        ".logo-img{width:28px;height:28px;object-fit:contain;}" +
        ".logo-text{font-size:16px;font-weight:800;color:var(--text-primary);}" +
        ".biz-badge{font-size:11px;font-weight:600;color:#fff;background:var(--brand-primary);padding:2px 10px;border-radius:4px;max-width:160px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;}" +
        ".hdr-right{display:flex;align-items:center;gap:8px;}" +
        ".nav-action-btn{display:inline-flex;align-items:center;gap:6px;background:var(--brand-primary);color:#fff;text-decoration:none;font-size:12px;font-weight:600;padding:6px 14px;border-radius:6px;}" +
        ".nav-action-btn:hover{background:var(--brand-dark);}" +
        ".theme-toggle{background:transparent;border:1px solid var(--border-rule);font-size:16px;cursor:pointer;color:var(--text-primary);width:34px;height:34px;display:flex;align-items:center;justify-content:center;border-radius:6px;}" +
        ".theme-toggle:hover{background:var(--bg-subtle);}" +
        ".hamburger{background:transparent;border:1px solid var(--border-rule);font-size:16px;cursor:pointer;color:var(--text-primary);width:34px;height:34px;display:flex;align-items:center;justify-content:center;border-radius:6px;}" +
        ".hamburger:hover{background:var(--bg-subtle);}" +
        ".sidebar-overlay{display:none;position:fixed;inset:0;background:rgba(0,0,0,0.5);z-index:199;}" +
        ".sidebar-overlay.open{display:block;}" +
        ".sidebar{position:fixed;left:-260px;top:0;width:240px;height:100vh;background:var(--bg-surface);border-right:1px solid var(--border-rule);z-index:200;transition:left 0.25s ease;overflow-y:auto;}" +
        ".sidebar.open{left:0;}" +
        ".sidebar-hdr{padding:16px 18px;display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid var(--border-rule);background:var(--bg-subtle);}" +
        ".sidebar-brand-box{display:flex;align-items:center;gap:10px;}" +
        ".sidebar-logo-badge{display:flex;align-items:center;justify-content:center;}" +
        ".sidebar-brand{font-size:15px;font-weight:800;color:var(--text-primary);}" +
        ".sidebar-tagline{font-size:10px;color:var(--text-muted);font-weight:500;}" +
        ".sidebar-close{background:none;border:1px solid var(--border-rule);font-size:16px;color:var(--text-primary);cursor:pointer;width:28px;height:28px;display:flex;align-items:center;justify-content:center;border-radius:6px;}" +
        ".sidebar-close:hover{background:var(--bg-subtle);}" +
        ".sidebar-nav{padding:12px 12px;}" +
        ".nav-section-label{font-size:10px;font-weight:700;color:var(--text-muted);padding:10px 8px 4px;text-transform:uppercase;letter-spacing:0.5px;}" +
        ".side-link{display:flex;align-items:center;gap:10px;padding:8px 10px;color:var(--text-secondary);text-decoration:none;font-size:13px;font-weight:500;border-radius:6px;margin-bottom:1px;}" +
        ".side-link:hover{color:var(--text-primary);background:var(--bg-subtle);}" +
        ".side-link.active{background:var(--brand-light);color:var(--brand-primary);font-weight:600;}" +
        ".side-link i{font-size:16px;}" +
        ".sidebar-divider{border-top:1px solid var(--border-rule);margin:10px 0;}" +
        ".logout-link{color:var(--brand-dark)!important;}" +
        ".logout-link:hover{background:var(--bg-subtle)!important;}" +
        ".container{padding:28px 36px;flex:1;width:100%;}" +
        ".anim-on-scroll{opacity:0;transform:translateY(6px);transition:opacity 0.3s ease,transform 0.3s ease;}" +
        ".anim-on-scroll.in-view{opacity:1;transform:translateY(0);}" +
        ".chart-wrapper .bar-el{transform:scaleY(0);transform-origin:bottom;transition:transform 0.5s cubic-bezier(0.34,1.56,0.64,1);}" +
        ".chart-wrapper.in-view .bar-el{transform:scaleY(1);}" +
        ".greeting{margin-bottom:18px;padding-bottom:12px;border-bottom:1px solid var(--border-rule);}" +
        ".greeting h2{font-size:22px;font-weight:800;color:var(--text-primary);}" +
        ".greeting-sub{color:var(--text-secondary);font-size:14px;margin-top:2px;}" +
        ".health-pill{display:inline-flex;align-items:center;gap:6px;padding:6px 14px;border-radius:20px;background:var(--bg-surface);border:1px solid var(--border-rule);font-size:12px;font-weight:500;color:var(--text-secondary);margin-right:8px;margin-bottom:12px;}" +
        ".health-dot{width:8px;height:8px;border-radius:50%;display:inline-block;}" +
        ".health-label{font-weight:600;color:var(--text-primary);}" +
        ".health-tip{color:var(--text-muted);}" +
        ".streak-pill{display:inline-flex;align-items:center;gap:6px;padding:6px 14px;border-radius:20px;background:var(--bg-surface);border:1px solid var(--border-rule);font-size:12px;font-weight:500;color:var(--text-secondary);margin-bottom:12px;}" +
        ".streak-icon{font-size:14px;}" +
        ".streak-text{color:var(--text-primary);}" +
        ".metric-card{background:var(--bg-surface);border:1px solid var(--border-rule);border-radius:8px;padding:8px 0;margin-bottom:24px;}" +
        ".metric-row{display:flex;justify-content:space-between;align-items:center;padding:12px 20px;border-bottom:1px solid var(--border-rule);}" +
        ".metric-row:last-child{border-bottom:none;}" +
        ".metric-label{font-size:13px;font-weight:600;color:var(--text-secondary);text-transform:uppercase;letter-spacing:0.3px;}" +
        ".metric-value{font-size:18px;font-weight:700;color:var(--text-primary);}" +
        ".metric-value.positive{color:var(--sales-val);}" +
        ".metric-value.negative{color:var(--expense-val);}" +
        ".section{background:var(--bg-surface);border:1px solid var(--border-rule);border-radius:8px;padding:20px;margin-bottom:20px;}" +
        ".section.alt{background:var(--bg-subtle);}" +
        ".section h2{font-size:14px;font-weight:700;color:var(--text-primary);margin-bottom:14px;display:flex;align-items:center;gap:8px;border-bottom:1px solid var(--border-rule);padding-bottom:8px;}" +
        "table{width:100%;border-collapse:collapse;font-size:13px;}" +
        "th{background:var(--bg-subtle);color:var(--text-secondary);padding:8px 10px;text-align:left;font-size:11px;font-weight:600;text-transform:uppercase;letter-spacing:0.3px;border-bottom:1px solid var(--border-rule);}" +
        "td{padding:10px;border-bottom:1px solid var(--border-rule);color:var(--text-primary);}" +
        "tr:hover{background:var(--bg-subtle);}" +
        ".badge{padding:2px 8px;border-radius:4px;font-size:10px;font-weight:600;text-transform:uppercase;letter-spacing:0.3px;}" +
        ".badge-SALE{background:var(--brand-light);color:var(--sales-val);}" +
        ".badge-EXPENSE{background:var(--bg-subtle);color:var(--expense-val);}" +
        ".badge-SUPPLY{background:var(--bg-subtle);color:var(--supply-val);}" +
        ".badge-DEBT{background:var(--bg-subtle);color:var(--debt-val);}" +
        ".badge-PAYMENT{background:var(--bg-subtle);color:var(--payment-val);}" +
        ".badge-PERSONAL{background:var(--bg-subtle);color:var(--personal-val);}" +
        ".empty{font-style:italic;color:var(--text-muted);padding:20px 0;font-size:14px;text-align:center;}" +
        ".empty-state{text-align:center;padding:30px 20px;border:1px dashed var(--border-rule);background:var(--bg-subtle);border-radius:8px;}" +
        ".empty-icon-wrap{width:40px;height:40px;border:1px solid var(--border-rule);background:var(--bg-surface);color:var(--text-primary);display:flex;align-items:center;justify-content:center;margin:0 auto 10px;font-size:18px;border-radius:8px;}" +
        ".empty-state h4{font-size:15px;font-weight:700;color:var(--text-primary);margin-bottom:2px;}" +
        ".empty-state p{font-size:13px;color:var(--text-secondary);margin-bottom:14px;}" +
        ".txn-card{background:var(--bg-surface);border:1px solid var(--border-rule);border-radius:8px;padding:12px 16px;margin-bottom:10px;}" +
        ".txn-card:hover{background:var(--bg-subtle);}" +
        ".txn-top{display:flex;justify-content:space-between;align-items:flex-start;margin-bottom:4px;}" +
        ".txn-amount{font-size:17px;font-weight:700;color:var(--text-primary);}" +
        ".txn-desc{font-size:14px;color:var(--text-secondary);margin-bottom:4px;}" +
        ".txn-bottom{display:flex;justify-content:space-between;align-items:center;margin-top:6px;border-top:1px solid var(--border-rule);padding-top:6px;}" +
        ".txn-meta{font-size:11px;color:var(--text-muted);}" +
        ".txn-actions{display:flex;gap:6px;}" +
        ".cat-tabs{display:flex;gap:6px;overflow-x:auto;margin-bottom:16px;}" +
        ".cat-tab{padding:6px 14px;border-radius:6px;font-size:11px;font-weight:600;text-decoration:none;color:var(--text-secondary);background:var(--bg-surface);border:1px solid var(--border-rule);white-space:nowrap;}" +
        ".cat-tab:hover{background:var(--bg-subtle);color:var(--text-primary);}" +
        ".cat-tab.active{background:var(--text-primary);color:var(--bg-surface);border-color:var(--text-primary);}" +
        ".btn{display:inline-flex;align-items:center;justify-content:center;gap:6px;padding:7px 14px;border:1px solid var(--border-rule);border-radius:6px;font-size:12px;cursor:pointer;font-weight:600;text-decoration:none;background:var(--bg-surface);color:var(--text-primary);}" +
        ".btn:hover{background:var(--bg-subtle);}" +
        ".btn-primary{background:var(--brand-primary);color:#fff;border-color:var(--brand-primary);}" +
        ".btn-primary:hover{background:var(--brand-dark);}" +
        ".btn-danger{background:var(--bg-subtle);color:var(--expense-val);border-color:var(--expense-val);}" +
        ".btn-danger:hover{background:var(--bg-subtle);}" +
        ".debt-card{background:var(--bg-surface);border:1px solid var(--border-rule);border-radius:8px;padding:16px;margin-bottom:12px;}" +
        ".debt-card:hover{background:var(--bg-subtle);}" +
        ".debt-card h3{font-size:15px;font-weight:700;color:var(--text-primary);margin-bottom:6px;}" +
        ".progress-bar{height:6px;background:var(--bg-subtle);border-radius:4px;overflow:hidden;margin:10px 0 8px;}" +
        ".progress-animate{height:100%;border-radius:4px;width:0;background:var(--brand-primary);transition:width 0.8s ease;}" +
        ".debt-amounts{display:flex;gap:14px;font-size:12px;flex-wrap:wrap;color:var(--text-secondary);}" +
        ".status-badge{padding:2px 8px;border-radius:4px;font-size:10px;font-weight:600;}" +
        ".status-unpaid{background:var(--bg-subtle);color:var(--expense-val);}" +
        ".status-partial{background:var(--bg-subtle);color:var(--supply-val);}" +
        ".status-paid{background:var(--brand-light);color:var(--sales-val);}" +
        ".chat-container{padding:16px 0;}" +
        ".chat-messages{min-height:340px;max-height:440px;overflow-y:auto;padding:12px 4px;margin-bottom:12px;display:flex;flex-direction:column;gap:8px;}" +
        ".chat-msg{padding:10px 16px;border-radius:8px;font-size:14px;line-height:1.5;max-width:85%;border:1px solid var(--border-rule);}" +
        ".chat-msg.user{background:var(--brand-primary);color:#fff;align-self:flex-end;border-color:var(--brand-primary);}" +
        ".chat-msg.system{background:var(--bg-subtle);border:1px solid var(--border-rule);color:var(--text-primary);align-self:flex-start;}" +
        ".typing{display:flex;gap:5px;padding:10px 16px;align-self:flex-start;background:var(--bg-subtle);border:1px solid var(--border-rule);border-radius:8px;}" +
        ".typing span{width:6px;height:6px;background:var(--text-primary);border-radius:50%;animation:typingBounce 1.2s infinite ease-in-out;}" +
        ".typing span:nth-child(2){animation-delay:0.15s;}" +
        ".typing span:nth-child(3){animation-delay:0.3s;}" +
        "@keyframes typingBounce{0%,80%,100%{transform:scale(0.6);opacity:0.3;}40%{transform:scale(1.2);opacity:1;}}" +
        ".chat-input-bar{display:flex;gap:8px;background:var(--bg-surface);padding:8px;border:1px solid var(--border-rule);border-radius:8px;}" +
        ".chat-input-bar input{flex:1;padding:8px 14px;border:1px solid var(--border-rule);border-radius:6px;font-size:14px;color:var(--text-primary);background:var(--bg-surface);outline:none;transition:border 0.2s;}" +
        ".chat-input-bar input:focus{border-color:var(--brand-primary);}" +
        ".chat-input-bar button{padding:8px 16px;border-radius:6px;}" +
        ".help-fab{position:fixed;bottom:80px;left:24px;width:38px;height:38px;border-radius:50%;background:var(--bg-surface);color:var(--text-primary);border:1px solid var(--border-rule);display:flex;align-items:center;justify-content:center;cursor:pointer;z-index:50;}" +
        ".help-fab:hover{background:var(--bg-subtle);transform:scale(1.05);}" +
        ".help-panel{position:fixed;right:-340px;top:0;width:320px;height:100vh;background:var(--bg-surface);z-index:201;transition:right 0.25s ease;overflow-y:auto;padding:24px;border-left:1px solid var(--border-rule);}" +
        ".help-panel.open{right:0;}" +
        ".help-example{padding:8px 12px;margin:6px 0;background:var(--bg-subtle);border:1px solid var(--border-rule);border-radius:6px;font-size:13px;cursor:pointer;}" +
        ".help-example:hover{background:var(--brand-light);border-color:var(--brand-primary);}" +
        ".confirm-card{background:var(--bg-subtle);border:1px solid var(--border-rule);border-radius:8px;padding:14px 16px;margin:8px 0;}" +
        ".confirm-card .actions{display:flex;gap:8px;margin-top:10px;flex-wrap:wrap;}" +
        ".confirm-btn{background:var(--brand-primary);color:#fff;border:1px solid var(--brand-primary);padding:6px 14px;border-radius:6px;font-weight:600;font-size:12px;cursor:pointer;}" +
        ".change-btn{background:var(--bg-surface);color:var(--text-primary);border:1px solid var(--border-rule);padding:6px 14px;border-radius:6px;font-weight:600;font-size:12px;cursor:pointer;}" +
        ".cancel-btn{background:var(--bg-subtle);color:var(--expense-val);border:1px solid var(--expense-val);padding:6px 14px;border-radius:6px;font-weight:600;font-size:12px;cursor:pointer;}" +
        ".category-select{padding:4px 10px;border:1px solid var(--border-rule);border-radius:6px;font-size:12px;font-weight:500;margin-top:6px;background:var(--bg-surface);color:var(--text-primary);}" +
        ".toast{position:fixed;top:20px;right:20px;padding:12px 20px;border-radius:8px;color:#fff;font-size:13px;font-weight:600;z-index:300;opacity:0;transform:translateY(-10px);transition:all 0.25s ease;border:1px solid var(--border-rule);background:var(--text-primary);}" +
        ".toast.show{opacity:1;transform:translateY(0);}" +
        ".toast.success{background:var(--brand-primary);}" +
        ".fab{position:fixed;bottom:24px;right:24px;width:48px;height:48px;border-radius:50%;background:var(--brand-primary);color:#fff;font-size:20px;border:1px solid var(--border-rule);cursor:pointer;display:flex;align-items:center;justify-content:center;text-decoration:none;z-index:50;}" +
        ".fab:hover{background:var(--brand-dark);transform:scale(1.05);}" +
        ".donut-container{display:flex;flex-direction:column;align-items:center;padding:8px 0;}" +
        ".donut-svg{margin-bottom:6px;}" +
        ".chart-legend{display:flex;justify-content:center;gap:14px;flex-wrap:wrap;}" +
        ".legend-item{display:flex;align-items:center;gap:6px;font-size:12px;font-weight:500;color:var(--text-secondary);}" +
        ".legend-dot{width:10px;height:10px;border-radius:4px;display:inline-block;}" +
        ".filter-bar{display:flex;gap:8px;flex-wrap:wrap;align-items:center;margin-bottom:16px;}" +
        ".filter-bar select,.filter-bar input{padding:6px 12px;border:1px solid var(--border-rule);border-radius:6px;font-size:12px;font-weight:500;background:var(--bg-surface);color:var(--text-primary);transition:border 0.2s;}" +
        ".filter-bar select:focus,.filter-bar input:focus{border-color:var(--brand-primary);outline:none;}" +
        ".advice-card{background:var(--bg-surface);border-radius:8px;padding:14px 16px;margin-bottom:12px;border:1px solid var(--border-rule);border-left:4px solid var(--brand-primary);}" +
        ".advice-card.warning{border-left-color:var(--supply-val);}" +
        ".advice-card.danger{border-left-color:var(--expense-val);}" +
        ".advice-card h4{font-size:13px;font-weight:700;color:var(--text-primary);margin-bottom:4px;}" +
        ".advice-card p{font-size:13px;color:var(--text-secondary);line-height:1.5;}" +
        ".period-bar{display:flex;gap:6px;flex-wrap:wrap;margin-bottom:16px;}" +
        ".period-btn{padding:6px 16px;border-radius:6px;text-decoration:none;font-size:12px;font-weight:600;border:1px solid var(--border-rule);color:var(--text-secondary);background:var(--bg-surface);}" +
        ".period-btn:hover{background:var(--bg-subtle);color:var(--text-primary);}" +
        ".period-btn.active{background:var(--text-primary);color:var(--bg-surface);border-color:var(--text-primary);}" +
        ".chart-container{background:var(--bg-surface);border-radius:8px;padding:18px;margin-bottom:20px;border:1px solid var(--border-rule);}" +
        ".chart-container h3{font-size:14px;font-weight:700;color:var(--text-primary);margin-bottom:12px;border-bottom:1px solid var(--border-rule);padding-bottom:6px;}" +
        ".carousel{position:relative;overflow:hidden;border-radius:8px;margin-bottom:20px;background:var(--bg-subtle);border:1px solid var(--border-rule);}" +
        ".carousel-track{display:flex;transition:transform 0.4s ease;}" +
        ".carousel-slide{min-width:100%;padding:20px;text-align:center;}" +
        ".carousel-slide h3{font-size:11px;color:var(--text-muted);font-weight:600;margin-bottom:4px;text-transform:uppercase;letter-spacing:0.5px;}" +
        ".carousel-slide .big-num{font-size:28px;font-weight:700;color:var(--brand-primary);}" +
        ".carousel-dots{display:flex;justify-content:center;gap:6px;padding:8px;border-top:1px solid var(--border-rule);}" +
        ".carousel-dots span{width:8px;height:8px;border-radius:50%;border:1px solid var(--border-rule);background:var(--bg-surface);cursor:pointer;}" +
        ".carousel-dots span.active{background:var(--text-primary);width:20px;border-radius:4px;}" +
        ".app-footer{text-align:center;padding:16px;color:var(--text-muted);font-size:11px;font-weight:500;border-top:1px solid var(--border-rule);background:var(--bg-subtle);}" +
        ".footer-inner{max-width:1200px;margin:0 auto;display:flex;flex-direction:column;gap:2px;}" +
        ".footer-sub{font-size:10px;color:var(--text-muted);font-weight:400;" +
        "}" +
        "@media print{.app-header,.sidebar,.sidebar-overlay,.fab,.help-fab,.no-print,.app-footer{display:none!important;}.container{padding:0;}}" +
        "@media (min-width: 768px){.app-header{display:none;}.sidebar{position:fixed;top:0;left:0!important;width:240px;height:100vh;border-right:1px solid var(--border-rule);}.sidebar-close{display:none;}.app{margin-left:240px;width:calc(100% - 240px);}.container{padding:32px 40px;}}" +
        "@media(max-width:640px){.cards{grid-template-columns:1fr 1fr;}.container{padding:16px;}.greeting h2{font-size:20px;}}";
}