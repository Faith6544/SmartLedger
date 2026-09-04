package dashboard;

public class HtmlTemplates {

    public static final String LOGO_DATA = "data:image/png;base64,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";

    public static String head(String title) {
        return "<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width,initial-scale=1.0'>" +
            "<title>SmartLedger - " + title + "</title>" +
            "<link rel='icon' type='image/png' href='" + LOGO_DATA + "'><link rel='shortcut icon' type='image/png' href='" + LOGO_DATA + "'>" +
            "<link rel='preconnect' href='https://fonts.googleapis.com'>" +
            "<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>" +
            "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800;900&family=JetBrains+Mono:wght@500;700;800&display=swap' rel='stylesheet'>" +
            "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css'>" +
            "<style>" + CSS + "</style></head><body><div class='device-frame'><div class='app-shell'>";
    }

    public static String fullNav(String token, String active, String businessName) {
        String bizDisplay = (businessName != null && !businessName.isEmpty()) ? escapeHtml(businessName) : "";
        return "<header class='app-header'>" +
            "<div class='hdr-left'>" +
            "<button class='hamburger' onclick='toggleSidebar()' aria-label='Open Navigation'><i class='ti ti-menu-2'></i></button>" +
            "<a href='/dashboard/" + token + "' class='logo-link'>" +
            "<div class='logo-badge'><img src='" + LOGO_DATA + "' class='logo-img' alt='SmartLedger'></div>" +
            "<div class='logo-meta'><span class='logo-text'>SMARTLEDGER</span>" +
            (bizDisplay.isEmpty() ? "" : "<span class='biz-badge'>" + bizDisplay + "</span>") +
            "</div></a></div>" +
            "<div class='hdr-right'>" +
            "<a href='/chat/" + token + "' class='nav-action-btn' title='New Transaction'><i class='ti ti-plus'></i><span>RECORD</span></a>" +
            "</div>" +
            "</header>" + sidebar(token, active);
    }

    private static String sidebar(String token, String active) {
        return "<div class='sidebar-overlay' id='sidebarOverlay' onclick='toggleSidebar()'></div>" +
            "<aside class='sidebar' id='sidebar'>" +
            "<div class='sidebar-hdr'>" +
            "<div class='sidebar-brand-box'>" +
            "<div class='sidebar-logo-badge'><img src='" + LOGO_DATA + "' style='width:32px;height:32px;' alt='Logo'></div>" +
            "<div><div class='sidebar-brand'>SMARTLEDGER</div><div class='sidebar-tagline'>FINANCIAL SYSTEM &middot; COS 202</div></div>" +
            "</div>" +
            "<button onclick='toggleSidebar()' class='sidebar-close' aria-label='Close Navigation'><i class='ti ti-x'></i></button>" +
            "</div>" +
            "<nav class='sidebar-nav'>" +
            "<div class='nav-section-label'>CORE LEDGER</div>" +
            sideLink("/dashboard/" + token, "Overview", "ti-layout-grid", active.equals("overview")) +
            sideLink("/chat/" + token, "Chat & Record", "ti-message-circle", active.equals("chat")) +
            sideLink("/dashboard/" + token + "/transactions", "Transactions", "ti-receipt", active.equals("transactions")) +
            sideLink("/dashboard/" + token + "/debts", "Debts & Balances", "ti-scale", active.equals("debts")) +
            "<div class='nav-section-label'>INTELLIGENCE</div>" +
            sideLink("/analysis/" + token, "Analytics & Trends", "ti-chart-pie", active.equals("analysis")) +
            sideLink("/report/" + token, "Statement & Audit", "ti-file-analytics", active.equals("report")) +
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
        return "</div></div>" +
            "<footer class='app-footer'>" +
            "<div class='footer-inner'>" +
            "<div>SMARTLEDGER SYSTEM &middot; BROWSER DESKTOP & MOBILE EDITION</div>" +
            "<div class='footer-sub'>DESIGNED WITH SWISS TYPOGRAPHIC GRID PRECISION &middot; COS 202</div>" +
            "</div>" +
            "</footer>" +
            OBSERVER_JS +
            "</body></html>";
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
        return "<div class='card " + cssClass + " anim-on-scroll'>" +
            "<div class='card-header'><span class='card-label'>" + title + "</span></div>" +
            "<div class='value count-up' data-target='" + (long)value + "'>&#8358;0.00</div></div>";
    }

    public static String greeting(String username, double todaySales, String businessName) {
        int hour = java.time.LocalTime.now().getHour();
        String timeGreet = hour >= 5 && hour < 12 ? "GOOD MORNING" : hour < 17 ? "GOOD AFTERNOON" : hour < 21 ? "GOOD EVENING" : "HELLO";
        String salesMsg = todaySales > 0 ? "You have recorded <strong style='color:var(--sales-val);'>&#8358;" + formatAmount(todaySales) + "</strong> in gross sales today." : "No transactions posted yet today.";
        return "<div class='greeting anim-on-scroll'>" +
            "<div class='greeting-main'><h2>" + timeGreet + ", " + escapeHtml(username).toUpperCase() + "</h2>" +
            "<p class='greeting-sub'>" + salesMsg + "</p></div>" +
            "</div>";
    }

    public static String healthIndicator(double sales, double expenses, double supplies) {
        double profit = sales - expenses - supplies;
        double ratio = sales > 0 ? (expenses / sales) * 100 : 0;
        String color, label, tip, statusClass;
        if (profit > 0 && ratio < 50) {
            color = "var(--brand-primary)"; label = "HEALTHY CASHFLOW"; tip = "Operational margins are strong and spending is within budget."; statusClass = "healthy";
        } else if (profit > 0) {
            color = "var(--supply-val)"; label = "MODERATE MARGIN"; tip = "Operational expenses and restocking are rising relative to revenue."; statusClass = "moderate";
        } else {
            color = "var(--expense-val)"; label = "DEFICIT STATE"; tip = "Outflows currently exceed inflows. Review recent expense line items."; statusClass = "warning";
        }
        return "<div class='health-card " + statusClass + " anim-on-scroll'>" +
            "<div class='health-dot' style='background:" + color + ";'></div>" +
            "<div class='health-content'><strong>" + label + "</strong><p>" + tip + "</p></div>" +
            "</div>";
    }

    public static String streakBanner(int streak) {
        if (streak < 2) return "";
        return "<div class='streak-banner anim-on-scroll'>" +
            "<span class='streak-tag'>CONSISTENCY</span>" +
            "<span><strong>" + streak + "-DAY ACTIVE RECORDING STREAK</strong> &mdash; Continuous daily ledger entries maintained</span></div>";
    }

    public static String pieChart(double sales, double expenses, double supplies) {
        double total = sales + expenses + supplies;
        if (total == 0) return "<p class='empty'>No data available for breakdown.</p>";
        double s1 = sales/total*360, s2 = expenses/total*360, s3 = supplies/total*360;
        double a1 = 0, a2 = s1, a3 = s1+s2;
        return "<div class='donut-container anim-on-scroll'>" +
            "<svg width='180' height='180' viewBox='0 0 180 180' class='donut-svg'>" +
            pieSlice(90,90,75,a1,a1+s1,"#2e7d32") + pieSlice(90,90,75,a2,a2+s2,"#c62828") + pieSlice(90,90,75,a3,a3+s3,"#e65100") +
            "<circle cx='90' cy='90' r='50' fill='#ffffff' stroke='#111827' stroke-width='1.5'/>" +
            "<text x='90' y='82' text-anchor='middle' font-size='9' font-weight='800' fill='#666' letter-spacing='1'>TOTAL FLOW</text>" +
            "<text x='90' y='102' text-anchor='middle' font-size='12' font-weight='800' fill='#111827'>&#8358;" + formatAmount(total) + "</text>" +
            "</svg>" +
            "<div class='chart-legend'>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#2e7d32;'></span><span class='legend-text'>SALES (" + (int)(sales/total*100) + "%)</span></div>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#c62828;'></span><span class='legend-text'>EXPENSES (" + (int)(expenses/total*100) + "%)</span></div>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#e65100;'></span><span class='legend-text'>SUPPLIES (" + (int)(supplies/total*100) + "%)</span></div>" +
            "</div></div>";
    }

    private static String pieSlice(int cx, int cy, int r, double startAngle, double endAngle, String color) {
        if (endAngle - startAngle >= 360) endAngle = startAngle + 359.99;
        if (endAngle - startAngle < 0.5) return "";
        double sr = Math.toRadians(startAngle - 90), er = Math.toRadians(endAngle - 90);
        int x1 = (int)(cx + r * Math.cos(sr)), y1 = (int)(cy + r * Math.sin(sr));
        int x2 = (int)(cx + r * Math.cos(er)), y2 = (int)(cy + r * Math.sin(er));
        int large = (endAngle - startAngle) > 180 ? 1 : 0;
        return "<path d='M" + cx + "," + cy + " L" + x1 + "," + y1 + " A" + r + "," + r + " 0 " + large + ",1 " + x2 + "," + y2 + " Z' fill='" + color + "' stroke='#111827' stroke-width='1.5'/>";
    }

    public static String barChart(double sales, double expenses, double supplies, double debts, double payments) {
        double max = Math.max(1, Math.max(sales, Math.max(expenses, Math.max(supplies, Math.max(debts, payments)))));
        StringBuilder svg = new StringBuilder();
        svg.append("<div class='anim-on-scroll chart-wrapper'><svg width='100%' viewBox='0 0 360 200' xmlns='http://www.w3.org/2000/svg'>");
        svg.append("<line x1='30' y1='155' x2='340' y2='155' stroke='#111827' stroke-width='2'/>");
        svg.append("<line x1='30' y1='85' x2='340' y2='85' stroke='#e5e7eb' stroke-dasharray='2 2' stroke-width='1'/>");
        svg.append("<line x1='30' y1='15' x2='340' y2='15' stroke='#e5e7eb' stroke-dasharray='2 2' stroke-width='1'/>");

        String[][] bars = {
            {"SALES", String.valueOf(sales), "#2e7d32"},
            {"EXPENSE", String.valueOf(expenses), "#c62828"},
            {"SUPPLY", String.valueOf(supplies), "#e65100"},
            {"DEBT", String.valueOf(debts), "#6a1b9a"},
            {"PAID", String.valueOf(payments), "#1565c0"}
        };

        for (int i = 0; i < bars.length; i++) {
            double val = Double.parseDouble(bars[i][1]);
            int h = (int)(val / max * 135);
            if (h < 4 && val > 0) h = 4;
            int x = 42 + i * 62, y = 155 - h;
            // Track bg
            svg.append("<rect x='").append(x).append("' y='20' width='36' height='135' fill='#f4f4f5' stroke='#e5e7eb' stroke-width='1'/>");
            // Value bar
            svg.append("<rect x='").append(x).append("' y='").append(y).append("' width='36' height='").append(h)
               .append("' fill='").append(bars[i][2]).append("' stroke='#111827' stroke-width='1.5' class='bar-el'/>");
            if (val > 0) {
                svg.append("<text x='").append(x+18).append("' y='").append(Math.max(14, y-6))
                   .append("' text-anchor='middle' font-size='9' font-weight='800' fill='#111827'>")
                   .append(formatAmount(val)).append("</text>");
            }
            svg.append("<text x='").append(x+18).append("' y='175' text-anchor='middle' font-size='10' font-weight='800' fill='#475569'>")
               .append(bars[i][0]).append("</text>");
        }
        svg.append("</svg></div>");
        return svg.toString();
    }

    public static String emptyState(String message, String ctaText, String ctaHref) {
        return "<div class='empty-state anim-on-scroll'>" +
            "<div class='empty-icon-wrap'><i class='ti ti-receipt-off'></i></div>" +
            "<h4>NO TRANSACTIONS RECORDED</h4>" +
            "<p>" + message + "</p>" +
            (ctaHref != null ? "<a href='" + ctaHref + "' class='btn btn-primary'><i class='ti ti-plus'></i> " + ctaText + "</a>" : "") +
            "</div>";
    }

    private static final String OBSERVER_JS =
        "<script>" +
        "document.addEventListener('DOMContentLoaded',function(){" +
        "var obs=new IntersectionObserver(function(entries){entries.forEach(function(e){if(e.isIntersecting){e.target.classList.add('in-view');obs.unobserve(e.target);}});},{threshold:0.05,rootMargin:'0px 0px -20px 0px'});" +
        "document.querySelectorAll('.anim-on-scroll').forEach(function(el){obs.observe(el);});" +
        "var progObs=new IntersectionObserver(function(entries){entries.forEach(function(e){if(e.isIntersecting){var bars=e.target.querySelectorAll('.progress-animate');bars.forEach(function(b){b.style.width=b.getAttribute('data-width')+'%';});progObs.unobserve(e.target);}});},{threshold:0.15});" +
        "document.querySelectorAll('.debt-card').forEach(function(el){progObs.observe(el);});" +
        "var countObs=new IntersectionObserver(function(entries){entries.forEach(function(e){if(e.isIntersecting){countUp(e.target);countObs.unobserve(e.target);}});},{threshold:0.1});" +
        "document.querySelectorAll('.count-up').forEach(function(el){countObs.observe(el);});" +
        "function countUp(el){var target=parseFloat(el.getAttribute('data-target'))||0;if(target===0){el.innerHTML='&#8358;0.00';return;}var dur=1000,startTime=null;function step(ts){if(!startTime)startTime=ts;var p=Math.min((ts-startTime)/dur,1);p=1-Math.pow(1-p,3);var val=p*target;el.innerHTML='&#8358;'+val.toLocaleString('en-US',{minimumFractionDigits:2,maximumFractionDigits:2});if(p<1)requestAnimationFrame(step);}requestAnimationFrame(step);}" +
        "document.querySelectorAll('.stagger-children').forEach(function(parent){var children=parent.querySelectorAll('.anim-on-scroll,.card');children.forEach(function(c,i){c.style.transitionDelay=(i*0.04)+'s';});});" +
        "});</script>";

    private static final String CSS =
        ":root{" +
        "--bg-canvas:#ffffff;" +
        "--bg-surface:#ffffff;" +
        "--bg-subtle:#f4f4f5;" +
        "--border-rule:#111827;" +
        "--border-light:#e5e7eb;" +
        "--text-primary:#111827;" +
        "--text-secondary:#4b5563;" +
        "--text-muted:#6b7280;" +
        "--brand-primary:#2e7d32;" +
        "--brand-dark:#1b5e20;" +
        "--brand-light:#e8f5e9;" +
        "--sales-val:#2e7d32;" +
        "--expense-val:#c62828;" +
        "--supply-val:#e65100;" +
        "--debt-val:#6a1b9a;" +
        "--payment-val:#1565c0;" +
        "--shadow-hairline:none;" +
        "--radius-none:0px;" +
        "--radius-sm:2px;" +
        "--radius-md:4px;" +
        "}" +
        "*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;}" +
        "html{scroll-behavior:smooth;background-color:var(--bg-canvas);}" +
        "body{background:var(--bg-canvas);min-height:100vh;color:var(--text-primary);-webkit-font-smoothing:antialiased;line-height:1.45;}" +
        ".device-frame{max-width:980px;margin:0 auto;min-height:100vh;border-left:1.5px solid var(--border-rule);border-right:1.5px solid var(--border-rule);background:#ffffff;}" +
        ".app-shell{background:transparent;min-height:100vh;display:flex;flex-direction:column;}" +
        
        // Header
        ".app-header{background:#ffffff;border-bottom:2px solid var(--border-rule);padding:0 24px;display:flex;justify-content:space-between;align-items:center;height:64px;position:sticky;top:0;z-index:100;}" +
        ".hdr-left{display:flex;align-items:center;gap:14px;}" +
        ".logo-link{display:flex;align-items:center;text-decoration:none;gap:12px;}" +
        ".logo-badge{width:36px;height:36px;background:#ffffff;border:1.5px solid var(--border-rule);border-radius:2px;display:flex;align-items:center;justify-content:center;transition:transform 0.1s;}" +
        ".logo-link:hover .logo-badge{transform:translate(-1px,-1px);}" +
        ".logo-img{width:24px;height:24px;}" +
        ".logo-meta{display:flex;align-items:center;gap:10px;}" +
        ".logo-text{font-size:16px;font-weight:900;color:var(--text-primary);letter-spacing:1px;text-transform:uppercase;}" +
        ".biz-badge{font-size:11px;font-weight:800;color:#ffffff;background:var(--brand-primary);padding:3px 8px;border-radius:2px;border:1px solid var(--border-rule);max-width:180px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;letter-spacing:0.5px;text-transform:uppercase;}" +
        ".hdr-right{display:flex;align-items:center;gap:10px;}" +
        ".nav-action-btn{display:inline-flex;align-items:center;gap:6px;background:var(--brand-primary);color:#fff;text-decoration:none;font-size:12px;font-weight:800;letter-spacing:0.8px;padding:8px 16px;border:1.5px solid var(--border-rule);border-radius:2px;transition:all 0.1s;}" +
        ".nav-action-btn:hover{background:var(--brand-dark);transform:translate(-1px,-1px);}" +
        ".hamburger{background:transparent;border:1.5px solid var(--border-rule);font-size:18px;cursor:pointer;color:var(--text-primary);width:36px;height:36px;display:flex;align-items:center;justify-content:center;border-radius:2px;transition:all 0.1s;}" +
        ".hamburger:hover{background:var(--bg-subtle);}" +
        
        // Sidebar
        ".sidebar-overlay{display:none;position:fixed;inset:0;background:rgba(0,0,0,0.5);z-index:199;}" +
        ".sidebar-overlay.open{display:block;}" +
        ".sidebar{position:fixed;left:-320px;top:0;width:290px;height:100vh;background:#ffffff;border-right:2px solid var(--border-rule);z-index:200;transition:left 0.25s ease;overflow-y:auto;}" +
        ".sidebar.open{left:0;}" +
        ".sidebar-hdr{padding:24px 20px;display:flex;align-items:center;justify-content:space-between;border-bottom:2px solid var(--border-rule);background:var(--bg-subtle);}" +
        ".sidebar-brand-box{display:flex;align-items:center;gap:12px;}" +
        ".sidebar-logo-badge{width:38px;height:38px;background:#ffffff;border:1.5px solid var(--border-rule);border-radius:2px;display:flex;align-items:center;justify-content:center;}" +
        ".sidebar-brand{font-size:16px;font-weight:900;color:var(--text-primary);letter-spacing:0.8px;}" +
        ".sidebar-tagline{font-size:10px;color:var(--text-muted);font-weight:700;letter-spacing:0.5px;}" +
        ".sidebar-close{background:none;border:1.5px solid var(--border-rule);font-size:16px;color:var(--text-primary);cursor:pointer;width:30px;height:30px;display:flex;align-items:center;justify-content:center;border-radius:2px;}" +
        ".sidebar-close:hover{background:#ffffff;}" +
        ".sidebar-nav{padding:16px 14px;}" +
        ".nav-section-label{font-size:11px;font-weight:900;color:var(--text-primary);letter-spacing:1px;padding:12px 10px 6px;text-transform:uppercase;border-bottom:1.5px solid var(--border-rule);margin-bottom:6px;}" +
        ".side-link{display:flex;align-items:center;gap:12px;padding:10px 12px;color:var(--text-secondary);text-decoration:none;font-size:13px;font-weight:700;border:1px solid transparent;border-radius:2px;transition:all 0.1s;margin-bottom:3px;}" +
        ".side-link:hover{color:var(--text-primary);background:var(--bg-subtle);border-color:var(--border-light);}" +
        ".side-link.active{background:var(--brand-light);color:var(--brand-primary);font-weight:800;border-color:var(--brand-primary);border-left:4px solid var(--brand-primary);}" +
        ".side-link i{font-size:18px;}" +
        ".sidebar-divider{border-top:1.5px solid var(--border-rule);margin:14px 0;}" +
        ".logout-link{color:var(--expense-val)!important;}" +
        ".logout-link:hover{background:#fee2e2!important;border-color:var(--expense-val)!important;}" +
        
        // Container
        ".container{padding:28px 24px;flex:1;}" +

        // Animations (crisp & immediate)
        ".anim-on-scroll{opacity:0;transform:translateY(6px);transition:opacity 0.25s ease,transform 0.25s ease;}" +
        ".anim-on-scroll.in-view{opacity:1;transform:translateY(0);}" +
        ".chart-wrapper .bar-el{transform:scaleY(0);transform-origin:bottom;transition:transform 0.6s ease;}" +
        ".chart-wrapper.in-view .bar-el{transform:scaleY(1);}" +

        // Greeting & Status Cards
        ".greeting{margin-bottom:24px;border-bottom:2px solid var(--border-rule);padding-bottom:16px;display:flex;justify-content:space-between;align-items:flex-end;flex-wrap:wrap;gap:12px;}" +
        ".greeting h2{font-size:26px;font-weight:900;color:var(--text-primary);letter-spacing:-0.5px;line-height:1.1;}" +
        ".greeting-sub{color:var(--text-secondary);font-size:13px;font-weight:500;margin-top:4px;}" +
        ".health-card{display:flex;align-items:center;gap:14px;background:#ffffff;border:1.5px solid var(--border-rule);border-radius:2px;padding:14px 18px;margin-bottom:22px;}" +
        ".health-dot{width:12px;height:12px;border:1.5px solid var(--border-rule);border-radius:0;flex-shrink:0;}" +
        ".health-content strong{font-size:12px;font-weight:900;color:var(--text-primary);letter-spacing:0.5px;text-transform:uppercase;}" +
        ".health-content p{margin:2px 0 0;color:var(--text-secondary);font-size:12px;line-height:1.4;}" +
        ".streak-banner{background:var(--bg-subtle);border:1.5px solid var(--border-rule);border-radius:2px;padding:12px 16px;margin-bottom:22px;font-size:12px;color:var(--text-primary);display:flex;align-items:center;gap:10px;}" +
        ".streak-tag{background:var(--text-primary);color:#fff;font-size:10px;font-weight:900;padding:2px 6px;letter-spacing:0.8px;}" +

        // Stat Cards (Swiss Grid Layout)
        ".cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(140px,1fr));gap:12px;margin-bottom:24px;}" +
        ".card{background:#ffffff;border:1.5px solid var(--border-rule);border-radius:2px;padding:16px 14px;transition:all 0.1s;position:relative;}" +
        ".card:hover{background:var(--bg-subtle);transform:translate(-1px,-1px);}" +
        ".card-header{display:flex;justify-content:space-between;align-items:center;margin-bottom:8px;border-bottom:1px solid var(--border-light);padding-bottom:4px;}" +
        ".card h3,.card-label{font-size:11px;font-weight:900;color:var(--text-primary);text-transform:uppercase;letter-spacing:0.8px;}" +
        ".card .value{font-size:22px;font-weight:900;letter-spacing:-0.5px;color:var(--text-primary);font-feature-settings:'tnum';font-variant-numeric:tabular-nums;}" +
        ".card.sales .value{color:var(--sales-val);}" +
        ".card.expenses .value{color:var(--expense-val);}" +
        ".card.supplies .value{color:var(--supply-val);}" +
        ".card.debts .value{color:var(--debt-val);}" +
        ".card.payments .value{color:var(--payment-val);}" +
        ".card.deliveries .value{color:#00796b;}" +
        ".card.profit .value{color:var(--sales-val);}" +
        ".card.profit.negative .value{color:var(--expense-val);}" +

        // Section Containers
        ".section{background:#ffffff;border:1.5px solid var(--border-rule);border-radius:2px;padding:20px;margin-bottom:24px;}" +
        ".section.alt{background:var(--bg-subtle);}" +
        ".section h2{font-size:13px;font-weight:900;color:var(--text-primary);margin-bottom:18px;display:flex;align-items:center;gap:8px;text-transform:uppercase;letter-spacing:1px;border-bottom:1.5px solid var(--border-rule);padding-bottom:8px;}" +

        // Tables (Swiss Grid Accounting Table)
        "table{width:100%;border-collapse:collapse;font-size:13px;}" +
        "th{background:var(--bg-subtle);color:var(--text-primary);padding:10px 12px;text-align:left;font-size:11px;font-weight:900;text-transform:uppercase;letter-spacing:0.8px;border-top:1.5px solid var(--border-rule);border-bottom:1.5px solid var(--border-rule);}" +
        "td{padding:12px;border-bottom:1px solid var(--border-light);color:var(--text-primary);font-weight:500;}" +
        "tr{transition:background-color 0.1s;}" +
        "tr:hover{background:var(--bg-subtle);}" +
        ".badge{padding:2px 6px;border:1px solid var(--border-rule);border-radius:2px;font-size:10px;font-weight:800;text-transform:uppercase;letter-spacing:0.5px;display:inline-block;}" +
        ".badge-SALE{background:var(--brand-light);color:var(--sales-val);border-color:var(--sales-val);}" +
        ".badge-EXPENSE{background:#ffebee;color:var(--expense-val);border-color:var(--expense-val);}" +
        ".badge-SUPPLY{background:#fff3e0;color:var(--supply-val);border-color:var(--supply-val);}" +
        ".badge-DEBT{background:#f3e5f5;color:var(--debt-val);border-color:var(--debt-val);}" +
        ".badge-PAYMENT{background:#e3f2fd;color:var(--payment-val);border-color:var(--payment-val);}" +
        ".badge-DELIVERY{background:#e0f2f1;color:#00796b;border-color:#00796b;}" +
        ".empty{color:var(--text-muted);padding:24px 0;font-size:13px;text-align:center;font-weight:600;}" +
        ".empty-state{text-align:center;padding:40px 20px;border:1.5px dashed var(--border-rule);background:var(--bg-subtle);}" +
        ".empty-icon-wrap{width:44px;height:44px;border:1.5px solid var(--border-rule);background:#ffffff;color:var(--text-primary);display:flex;align-items:center;justify-content:center;margin:0 auto 12px;font-size:20px;border-radius:2px;}" +
        ".empty-state h4{font-size:14px;font-weight:900;color:var(--text-primary);margin-bottom:4px;letter-spacing:0.5px;}" +
        ".empty-state p{font-size:12px;color:var(--text-secondary);margin-bottom:16px;}" +

        // Transaction Card Items
        ".txn-card{background:#ffffff;border:1.5px solid var(--border-rule);border-radius:2px;padding:14px 16px;margin-bottom:10px;transition:all 0.1s;}" +
        ".txn-card:hover{background:var(--bg-subtle);transform:translate(-1px,-1px);}" +
        ".txn-top{display:flex;justify-content:space-between;align-items:flex-start;margin-bottom:4px;}" +
        ".txn-amount{font-size:16px;font-weight:900;color:var(--text-primary);letter-spacing:-0.3px;font-feature-settings:'tnum';font-variant-numeric:tabular-nums;}" +
        ".txn-desc{font-size:13px;font-weight:600;color:var(--text-primary);margin-bottom:4px;}" +
        ".txn-bottom{display:flex;justify-content:space-between;align-items:center;margin-top:6px;border-top:1px solid var(--border-light);padding-top:6px;}" +
        ".txn-meta{font-size:11px;color:var(--text-muted);font-weight:600;}" +
        ".txn-actions{display:flex;gap:6px;}" +

        // Category Filter Tabs
        ".cat-tabs{display:flex;gap:6px;overflow-x:auto;margin-bottom:20px;padding-bottom:2px;-webkit-overflow-scrolling:touch;}" +
        ".cat-tab{padding:8px 14px;border-radius:2px;font-size:11px;font-weight:800;text-decoration:none;color:var(--text-primary);background:#ffffff;border:1.5px solid var(--border-rule);white-space:nowrap;transition:all 0.1s;text-transform:uppercase;letter-spacing:0.5px;}" +
        ".cat-tab:hover{background:var(--bg-subtle);}" +
        ".cat-tab.active{background:var(--text-primary);color:#ffffff;border-color:var(--text-primary);}" +
        ".cat-tab.active.t-SALE{background:var(--sales-val);border-color:var(--sales-val);color:#fff;}" +
        ".cat-tab.active.t-EXPENSE{background:var(--expense-val);border-color:var(--expense-val);color:#fff;}" +
        ".cat-tab.active.t-SUPPLY{background:var(--supply-val);border-color:var(--supply-val);color:#fff;}" +
        ".cat-tab.active.t-DEBT{background:var(--debt-val);border-color:var(--debt-val);color:#fff;}" +
        ".cat-tab.active.t-PAYMENT{background:var(--payment-val);border-color:var(--payment-val);color:#fff;}" +

        // Buttons
        ".btn{display:inline-flex;align-items:center;justify-content:center;gap:6px;padding:9px 18px;border:1.5px solid var(--border-rule);border-radius:2px;font-size:12px;cursor:pointer;font-weight:800;text-transform:uppercase;letter-spacing:0.5px;transition:all 0.1s;text-decoration:none;}" +
        ".btn:hover{transform:translate(-1px,-1px);}" +
        ".btn:active{transform:translate(1px,1px);}" +
        ".btn-primary{background:var(--brand-primary);color:#ffffff;border-color:var(--border-rule);}" +
        ".btn-primary:hover{background:var(--brand-dark);}" +
        ".btn-danger{background:#fee2e2;color:var(--expense-val);border-color:var(--expense-val);}" +
        ".btn-danger:hover{background:#fecaca;}" +

        // Debts
        ".debt-card{background:#ffffff;border:1.5px solid var(--border-rule);border-radius:2px;padding:16px;margin-bottom:12px;transition:all 0.1s;}" +
        ".debt-card:hover{background:var(--bg-subtle);}" +
        ".debt-card h3{margin-bottom:8px;color:var(--text-primary);font-size:14px;font-weight:900;text-transform:uppercase;letter-spacing:0.5px;}" +
        ".progress-bar{height:8px;background:var(--bg-subtle);border:1px solid var(--border-rule);border-radius:0;overflow:hidden;margin:12px 0 8px;}" +
        ".progress-animate{height:100%;border-radius:0;width:0;background:var(--brand-primary);transition:width 0.8s ease;}" +
        ".debt-amounts{display:flex;gap:16px;font-size:12px;flex-wrap:wrap;color:var(--text-secondary);font-weight:600;}" +
        ".status-badge{padding:2px 6px;border:1px solid var(--border-rule);border-radius:2px;font-size:10px;font-weight:800;text-transform:uppercase;letter-spacing:0.5px;}" +
        ".status-unpaid{background:#ffebee;color:var(--expense-val);border-color:var(--expense-val);}" +
        ".status-partial{background:#fff3e0;color:var(--supply-val);border-color:var(--supply-val);}" +
        ".status-paid{background:var(--brand-light);color:var(--sales-val);border-color:var(--sales-val);}" +

        // Chat UI
        ".chat-container{padding:16px 0;}" +
        ".chat-messages{min-height:360px;max-height:480px;overflow-y:auto;padding:12px 4px;margin-bottom:14px;display:flex;flex-direction:column;gap:10px;}" +
        ".chat-msg{padding:12px 16px;border-radius:2px;font-size:13px;line-height:1.5;max-width:85%;border:1.5px solid var(--border-rule);}" +
        ".chat-msg.user{background:var(--brand-primary);color:#ffffff;align-self:flex-end;border-color:var(--border-rule);}" +
        ".chat-msg.system{background:var(--bg-subtle);border:1.5px solid var(--border-rule);color:var(--text-primary);align-self:flex-start;}" +
        ".typing{display:flex;gap:5px;padding:12px 16px;align-self:flex-start;background:var(--bg-subtle);border:1.5px solid var(--border-rule);border-radius:2px;}" +
        ".typing span{width:6px;height:6px;background:var(--text-primary);border-radius:0;animation:typingBounce 1.2s infinite ease-in-out;}" +
        ".typing span:nth-child(2){animation-delay:0.15s;}" +
        ".typing span:nth-child(3){animation-delay:0.3s;}" +
        "@keyframes typingBounce{0%,80%,100%{transform:scale(0.8);opacity:0.4;}40%{transform:scale(1.2);opacity:1;}}" +
        ".chat-input-bar{display:flex;gap:8px;background:#ffffff;padding:8px;border:1.5px solid var(--border-rule);border-radius:2px;}" +
        ".chat-input-bar input{flex:1;padding:10px 14px;border:1.5px solid var(--border-rule);border-radius:2px;font-size:13px;font-weight:600;color:var(--text-primary);background:#ffffff;outline:none;}" +
        ".chat-input-bar input:focus{border-color:var(--brand-primary);}" +
        ".chat-input-bar button{padding:10px 18px;border-radius:2px;}" +
        ".quick-chips{display:flex;gap:6px;overflow-x:auto;padding-bottom:8px;margin-bottom:8px;}" +
        ".quick-chip{padding:6px 12px;border-radius:2px;background:#ffffff;border:1.5px solid var(--border-rule);color:var(--text-primary);font-size:11px;font-weight:800;cursor:pointer;white-space:nowrap;transition:all 0.1s;text-transform:uppercase;letter-spacing:0.5px;}" +
        ".quick-chip:hover{background:var(--brand-light);border-color:var(--brand-primary);color:var(--brand-primary);}" +

        // Help & Confirmation
        ".help-fab{position:fixed;bottom:80px;left:24px;width:38px;height:38px;border-radius:2px;background:#ffffff;color:var(--text-primary);border:1.5px solid var(--border-rule);display:flex;align-items:center;justify-content:center;cursor:pointer;z-index:50;transition:all 0.1s;}" +
        ".help-fab:hover{background:var(--bg-subtle);transform:translate(-1px,-1px);}" +
        ".help-panel{position:fixed;right:-340px;top:0;width:320px;height:100vh;background:#ffffff;z-index:201;transition:right 0.25s ease;overflow-y:auto;padding:24px;border-left:2px solid var(--border-rule);}" +
        ".help-panel.open{right:0;}" +
        ".help-example{padding:10px 12px;margin:6px 0;background:var(--bg-subtle);border:1px solid var(--border-rule);border-radius:2px;font-size:12px;font-weight:600;cursor:pointer;transition:all 0.1s;}" +
        ".help-example:hover{background:var(--brand-light);border-color:var(--brand-primary);color:var(--brand-primary);}" +
        ".confirm-card{background:var(--bg-subtle);border:2px solid var(--border-rule);border-radius:2px;padding:14px 16px;margin:8px 0;}" +
        ".confirm-card .actions{display:flex;gap:8px;margin-top:12px;flex-wrap:wrap;}" +
        ".confirm-btn{background:var(--brand-primary);color:#fff;border:1.5px solid var(--border-rule);padding:8px 14px;border-radius:2px;font-weight:800;font-size:11px;text-transform:uppercase;letter-spacing:0.5px;cursor:pointer;}" +
        ".change-btn{background:#ffffff;color:var(--text-primary);border:1.5px solid var(--border-rule);padding:8px 14px;border-radius:2px;font-weight:800;font-size:11px;text-transform:uppercase;letter-spacing:0.5px;cursor:pointer;}" +
        ".cancel-btn{background:#fee2e2;color:var(--expense-val);border:1.5px solid var(--expense-val);padding:8px 14px;border-radius:2px;font-weight:800;font-size:11px;text-transform:uppercase;letter-spacing:0.5px;cursor:pointer;}" +
        ".category-select{padding:6px 10px;border:1.5px solid var(--border-rule);border-radius:2px;font-size:12px;font-weight:700;margin-top:6px;background:#ffffff;color:var(--text-primary);}" +
        ".toast{position:fixed;top:20px;right:20px;padding:12px 20px;border-radius:2px;color:#fff;font-size:12px;font-weight:800;letter-spacing:0.5px;text-transform:uppercase;z-index:300;opacity:0;transform:translateY(-10px);transition:all 0.2s ease;border:1.5px solid var(--border-rule);background:var(--text-primary);}" +
        ".toast.show{opacity:1;transform:translateY(0);}" +
        ".toast.success{background:var(--brand-primary);}" +
        ".fab{position:fixed;bottom:24px;right:24px;width:48px;height:48px;border-radius:2px;background:var(--brand-primary);color:#fff;font-size:20px;border:2px solid var(--border-rule);cursor:pointer;display:flex;align-items:center;justify-content:center;text-decoration:none;z-index:50;transition:all 0.1s;}" +
        ".fab:hover{background:var(--brand-dark);transform:translate(-1px,-1px);}" +

        // Chart & Insights Styles
        ".donut-container{display:flex;flex-direction:column;align-items:center;padding:12px 0;}" +
        ".donut-svg{margin-bottom:12px;}" +
        ".chart-legend{display:flex;justify-content:center;gap:14px;flex-wrap:wrap;}" +
        ".legend-item{display:flex;align-items:center;gap:6px;font-size:11px;font-weight:800;color:var(--text-primary);letter-spacing:0.5px;}" +
        ".legend-dot{width:10px;height:10px;border:1px solid var(--border-rule);border-radius:0;}" +
        ".filter-bar{display:flex;gap:8px;flex-wrap:wrap;align-items:center;margin-bottom:18px;}" +
        ".filter-bar select,.filter-bar input{padding:8px 12px;border:1.5px solid var(--border-rule);border-radius:2px;font-size:12px;font-weight:700;background:#ffffff;color:var(--text-primary);}" +
        ".filter-bar select:focus,.filter-bar input:focus{border-color:var(--brand-primary);outline:none;}" +
        ".advice-card{background:#ffffff;border-radius:2px;padding:14px 16px;margin-bottom:12px;border:1.5px solid var(--border-rule);border-left:5px solid var(--brand-primary);}" +
        ".advice-card.warning{border-left-color:var(--supply-val);}" +
        ".advice-card.danger{border-left-color:var(--expense-val);}" +
        ".advice-card h4{margin-bottom:4px;color:var(--text-primary);font-size:12px;font-weight:900;text-transform:uppercase;letter-spacing:0.5px;}" +
        ".advice-card p{color:var(--text-secondary);font-size:12px;line-height:1.5;font-weight:500;}" +
        ".period-bar{display:flex;gap:6px;flex-wrap:wrap;margin-bottom:20px;}" +
        ".period-btn{padding:8px 16px;border-radius:2px;text-decoration:none;font-size:11px;font-weight:800;letter-spacing:0.5px;text-transform:uppercase;border:1.5px solid var(--border-rule);color:var(--text-primary);background:#ffffff;transition:all 0.1s;}" +
        ".period-btn:hover{background:var(--bg-subtle);}" +
        ".period-btn.active{background:var(--text-primary);color:#ffffff;border-color:var(--text-primary);}" +
        ".chart-container{background:#ffffff;border-radius:2px;padding:18px;margin-bottom:20px;border:1.5px solid var(--border-rule);}" +
        ".chart-container h3{color:var(--text-primary);font-size:13px;font-weight:900;margin-bottom:14px;text-transform:uppercase;letter-spacing:0.8px;border-bottom:1px solid var(--border-light);padding-bottom:6px;}" +
        ".carousel{position:relative;overflow:hidden;border-radius:2px;margin-bottom:22px;background:var(--bg-subtle);border:1.5px solid var(--border-rule);}" +
        ".carousel-track{display:flex;transition:transform 0.4s ease;}" +
        ".carousel-slide{min-width:100%;padding:24px 20px;text-align:center;}" +
        ".carousel-slide h3{font-size:11px;color:var(--text-primary);text-transform:uppercase;letter-spacing:1px;font-weight:900;margin-bottom:6px;}" +
        ".carousel-slide .big-num{font-size:32px;font-weight:900;color:var(--brand-primary);letter-spacing:-0.5px;font-feature-settings:'tnum';font-variant-numeric:tabular-nums;}" +
        ".carousel-dots{display:flex;justify-content:center;gap:6px;padding:8px;border-top:1px solid var(--border-light);}" +
        ".carousel-dots span{width:8px;height:8px;border-radius:0;border:1px solid var(--border-rule);background:#ffffff;cursor:pointer;transition:all 0.1s;}" +
        ".carousel-dots span.active{background:var(--text-primary);width:20px;}" +
        ".app-footer{text-align:center;padding:24px 16px;color:var(--text-muted);font-size:11px;font-weight:700;letter-spacing:0.8px;text-transform:uppercase;border-top:2px solid var(--border-rule);background:var(--bg-subtle);}" +
        ".footer-inner{max-width:980px;margin:0 auto;display:flex;flex-direction:column;gap:4px;}" +
        ".footer-sub{font-size:10px;color:var(--text-muted);font-weight:600;}" +
        "@media print{.app-header,.sidebar,.sidebar-overlay,.fab,.help-fab,.no-print,.app-footer{display:none!important;}.device-frame{max-width:100%;margin:0;box-shadow:none;border:none;border-radius:0;}.app-shell{min-height:auto;background:#fff;}}" +
        "";
}