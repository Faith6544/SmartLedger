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