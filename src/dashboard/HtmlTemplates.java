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
            "<link href='https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap' rel='stylesheet'>" +
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
            "<div class='sidebar-logo-badge'><img src='" + LOGO_DATA + "' style='width:36px;height:36px;' alt='Logo'></div>" +
            "<div><div class='sidebar-brand'>SmartLedger</div><div class='sidebar-tagline'>Financial Workspace</div></div>" +
            "</div>" +
            "<button onclick='toggleSidebar()' class='sidebar-close' aria-label='Close Navigation'><i class='ti ti-x'></i></button>" +
            "</div>" +
            "<nav class='sidebar-nav'>" +
            "<div class='nav-section-label'>MAIN MENU</div>" +
            sideLink("/dashboard/" + token, "Overview", "ti-layout-grid", active.equals("overview")) +
            sideLink("/chat/" + token, "Chat & Record", "ti-message-circle", active.equals("chat")) +
            sideLink("/dashboard/" + token + "/transactions", "Transactions", "ti-receipt", active.equals("transactions")) +
            sideLink("/dashboard/" + token + "/debts", "Debts & Balances", "ti-scale", active.equals("debts")) +
            "<div class='nav-section-label'>INSIGHTS</div>" +
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
        return "</div></div>" +
            "<footer class='app-footer'>" +
            "<div>SmartLedger &#169; 2026 &#8212; Smart Financial Accounting System (COS 202)</div>" +
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
        String timeGreet = hour >= 5 && hour < 12 ? "Good morning" : hour < 17 ? "Good afternoon" : hour < 21 ? "Good evening" : "Hello";
        String salesMsg = todaySales > 0 ? "You've recorded <strong style='color:var(--sales-val);'>&#8358;" + formatAmount(todaySales) + "</strong> in sales today." : "No sales recorded yet today.";
        return "<div class='greeting anim-on-scroll'>" +
            "<div class='greeting-main'><h2>" + timeGreet + ", " + escapeHtml(username) + "</h2>" +
            "<p class='greeting-sub'>" + salesMsg + "</p></div>" +
            "</div>";
    }

    public static String healthIndicator(double sales, double expenses, double supplies) {
        double profit = sales - expenses - supplies;
        double ratio = sales > 0 ? (expenses / sales) * 100 : 0;
        String color, label, tip, statusClass;
        if (profit > 0 && ratio < 50) {
            color = "var(--brand-primary)"; label = "Strong Margin"; tip = "Healthy cashflow & spending under control."; statusClass = "healthy";
        } else if (profit > 0) {
            color = "var(--supply-val)"; label = "Moderate Health"; tip = "Operational expenses are climbing."; statusClass = "moderate";
        } else {
            color = "var(--expense-val)"; label = "Deficit Warning"; tip = "Outflows currently exceed revenues."; statusClass = "warning";
        }
        return "<div class='health-card " + statusClass + " anim-on-scroll'>" +
            "<div class='health-dot' style='background:" + color + ";'></div>" +
            "<div class='health-content'><strong>" + label + "</strong><p>" + tip + "</p></div>" +
            "</div>";
    }

    public static String streakBanner(int streak) {
        if (streak < 2) return "";
        String emoji = streak >= 7 ? "&#128293;" : "&#9889;";
        return "<div class='streak-banner anim-on-scroll'>" +
            "<span class='streak-icon'>" + emoji + "</span>" +
            "<span><strong>" + streak + "-Day Active Streak</strong> &middot; Daily bookkeeping consistency</span></div>";
    }

    public static String pieChart(double sales, double expenses, double supplies) {
        double total = sales + expenses + supplies;
        if (total == 0) return "<p class='empty'>No data available for breakdown.</p>";
        double s1 = sales/total*360, s2 = expenses/total*360, s3 = supplies/total*360;
        double a1 = 0, a2 = s1, a3 = s1+s2;
        return "<div class='donut-container anim-on-scroll'>" +
            "<svg width='180' height='180' viewBox='0 0 180 180' class='donut-svg'>" +
            pieSlice(90,90,75,a1,a1+s1,"#10b981") + pieSlice(90,90,75,a2,a2+s2,"#f43f5e") + pieSlice(90,90,75,a3,a3+s3,"#f59e0b") +
            "<circle cx='90' cy='90' r='48' fill='#ffffff'/>" +
            "<text x='90' y='82' text-anchor='middle' font-size='10' font-weight='600' fill='#64748b' text-transform='uppercase'>Total Flow</text>" +
            "<text x='90' y='102' text-anchor='middle' font-size='13' font-weight='700' fill='#0f172a'>&#8358;" + formatAmount(total) + "</text>" +
            "</svg>" +
            "<div class='chart-legend'>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#10b981;'></span><span class='legend-text'>Sales (" + (int)(sales/total*100) + "%)</span></div>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#f43f5e;'></span><span class='legend-text'>Expenses (" + (int)(expenses/total*100) + "%)</span></div>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#f59e0b;'></span><span class='legend-text'>Supplies (" + (int)(supplies/total*100) + "%)</span></div>" +
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
        svg.append("<div class='anim-on-scroll chart-wrapper'><svg width='100%' viewBox='0 0 360 200' xmlns='http://www.w3.org/2000/svg'>");
        svg.append("<line x1='30' y1='155' x2='340' y2='155' stroke='#e2e8f0' stroke-width='1'/>");
        svg.append("<line x1='30' y1='85' x2='340' y2='85' stroke='#f1f5f9' stroke-dasharray='4 4' stroke-width='1'/>");
        svg.append("<line x1='30' y1='15' x2='340' y2='15' stroke='#f1f5f9' stroke-dasharray='4 4' stroke-width='1'/>");

        String[][] bars = {
            {"Sales", String.valueOf(sales), "#10b981"},
            {"Expense", String.valueOf(expenses), "#f43f5e"},
            {"Supply", String.valueOf(supplies), "#f59e0b"},
            {"Debt", String.valueOf(debts), "#6366f1"},
            {"Paid", String.valueOf(payments), "#0ea5e9"}
        };

        for (int i = 0; i < bars.length; i++) {
            double val = Double.parseDouble(bars[i][1]);
            int h = (int)(val / max * 135);
            if (h < 4 && val > 0) h = 4;
            int x = 42 + i * 62, y = 155 - h;
            // Track bg
            svg.append("<rect x='").append(x).append("' y='20' width='36' height='135' fill='#f8fafc' rx='5'/>");
            // Value bar
            svg.append("<rect x='").append(x).append("' y='").append(y).append("' width='36' height='").append(h)
               .append("' fill='").append(bars[i][2]).append("' rx='5' class='bar-el'/>");
            if (val > 0) {
                svg.append("<text x='").append(x+18).append("' y='").append(Math.max(14, y-6))
                   .append("' text-anchor='middle' font-size='10' font-weight='600' fill='#475569'>")
                   .append(formatAmount(val)).append("</text>");
            }
            svg.append("<text x='").append(x+18).append("' y='175' text-anchor='middle' font-size='11' font-weight='500' fill='#64748b'>")
               .append(bars[i][0]).append("</text>");
        }
        svg.append("</svg></div>");
        return svg.toString();
    }

    public static String emptyState(String message, String ctaText, String ctaHref) {
        return "<div class='empty-state anim-on-scroll'>" +
            "<div class='empty-icon-wrap'><i class='ti ti-receipt-off'></i></div>" +
            "<h4>No Transactions Yet</h4>" +
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
        "function countUp(el){var target=parseFloat(el.getAttribute('data-target'))||0;if(target===0){el.innerHTML='\u20A60.00';return;}var dur=1400,startTime=null;function step(ts){if(!startTime)startTime=ts;var p=Math.min((ts-startTime)/dur,1);p=1-Math.pow(1-p,3);var val=p*target;el.innerHTML='\u20A6'+val.toLocaleString('en-US',{minimumFractionDigits:2,maximumFractionDigits:2});if(p<1)requestAnimationFrame(step);}requestAnimationFrame(step);}" +
        "document.querySelectorAll('.stagger-children').forEach(function(parent){var children=parent.querySelectorAll('.anim-on-scroll,.card');children.forEach(function(c,i){c.style.transitionDelay=(i*0.06)+'s';});});" +
        "});</script>";

    private static final String CSS =
        ":root{" +
        "--bg-canvas:#f8fafc;" +
        "--bg-surface:#ffffff;" +
        "--border-subtle:#f1f5f9;" +
        "--border-default:#e2e8f0;" +
        "--border-hover:#cbd5e1;" +
        "--text-primary:#0f172a;" +
        "--text-secondary:#475569;" +
        "--text-muted:#94a3b8;" +
        "--brand-primary:#059669;" +
        "--brand-dark:#047857;" +
        "--brand-light:#ecfdf5;" +
        "--sales-val:#047857;" +
        "--expense-val:#e11d48;" +
        "--supply-val:#d97706;" +
        "--debt-val:#4f46e5;" +
        "--payment-val:#0284c7;" +
        "--shadow-xs:0 1px 2px 0 rgba(0,0,0,0.05);" +
        "--shadow-card:0 1px 3px 0 rgba(15,23,42,0.06),0 1px 2px -1px rgba(15,23,42,0.04);" +
        "--shadow-hover:0 10px 15px -3px rgba(15,23,42,0.08),0 4px 6px -4px rgba(15,23,42,0.04);" +
        "--radius-sm:6px;" +
        "--radius-md:10px;" +
        "--radius-lg:14px;" +
        "--radius-pill:9999px;" +
        "}" +
        "*{margin:0;padding:0;box-sizing:border-box;font-family:'Plus Jakarta Sans',-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;}" +
        "html{scroll-behavior:smooth;background-color:var(--bg-canvas);}" +
        "body{background:radial-gradient(circle at 50% 0%,#ecfdf5 0%,var(--bg-canvas) 45%);min-height:100vh;color:var(--text-primary);-webkit-font-smoothing:antialiased;}" +
        ".device-frame{max-width:960px;margin:0 auto;min-height:100vh;}" +
        ".app-shell{background:transparent;min-height:100vh;display:flex;flex-direction:column;}" +
        
        // Header
        ".app-header{background:rgba(255,255,255,0.85);backdrop-filter:blur(12px);border-bottom:1px solid var(--border-default);padding:0 24px;display:flex;justify-content:space-between;align-items:center;height:60px;position:sticky;top:0;z-index:100;}" +
        ".hdr-left{display:flex;align-items:center;gap:12px;}" +
        ".logo-link{display:flex;align-items:center;text-decoration:none;gap:10px;}" +
        ".logo-badge{width:36px;height:36px;background:#ffffff;border:1px solid var(--border-default);border-radius:var(--radius-md);display:flex;align-items:center;justify-content:center;box-shadow:var(--shadow-xs);transition:transform 0.2s;}" +
        ".logo-link:hover .logo-badge{transform:scale(1.04);border-color:var(--brand-primary);}" +
        ".logo-img{width:22px;height:22px;}" +
        ".logo-meta{display:flex;align-items:center;gap:8px;}" +
        ".logo-text{font-size:16px;font-weight:700;color:var(--text-primary);letter-spacing:-0.3px;}" +
        ".biz-badge{font-size:11px;font-weight:600;color:var(--brand-dark);background:var(--brand-light);padding:3px 8px;border-radius:var(--radius-pill);border:1px solid #a7f3d0;max-width:160px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;}" +
        ".hdr-right{display:flex;align-items:center;gap:10px;}" +
        ".nav-action-btn{display:inline-flex;align-items:center;gap:6px;background:var(--brand-primary);color:#fff;text-decoration:none;font-size:12px;font-weight:600;padding:6px 14px;border-radius:var(--radius-pill);transition:all 0.2s;box-shadow:var(--shadow-xs);}" +
        ".nav-action-btn:hover{background:var(--brand-dark);transform:translateY(-1px);}" +
        ".hamburger{background:transparent;border:1px solid var(--border-default);font-size:18px;cursor:pointer;color:var(--text-secondary);width:34px;height:34px;display:flex;align-items:center;justify-content:center;border-radius:var(--radius-sm);transition:all 0.15s;}" +
        ".hamburger:hover{background:#f1f5f9;color:var(--text-primary);border-color:var(--border-hover);}" +
        
        // Sidebar
        ".sidebar-overlay{display:none;position:fixed;inset:0;background:rgba(15,23,42,0.4);z-index:199;backdrop-filter:blur(3px);}" +
        ".sidebar-overlay.open{display:block;}" +
        ".sidebar{position:fixed;left:-320px;top:0;width:280px;height:100vh;background:#0f172a;z-index:200;transition:left 0.3s cubic-bezier(0.4,0,0.2,1);overflow-y:auto;box-shadow:4px 0 24px rgba(0,0,0,0.2);}" +
        ".sidebar.open{left:0;}" +
        ".sidebar-hdr{padding:24px 20px 18px;display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid rgba(255,255,255,0.08);}" +
        ".sidebar-brand-box{display:flex;align-items:center;gap:12px;}" +
        ".sidebar-logo-badge{width:40px;height:40px;background:#ffffff;border-radius:var(--radius-md);display:flex;align-items:center;justify-content:center;}" +
        ".sidebar-brand{font-size:16px;font-weight:700;color:#ffffff;letter-spacing:-0.2px;}" +
        ".sidebar-tagline{font-size:11px;color:#94a3b8;font-weight:500;}" +
        ".sidebar-close{background:none;border:none;font-size:18px;color:#94a3b8;cursor:pointer;padding:4px;border-radius:var(--radius-sm);transition:all 0.2s;}" +
        ".sidebar-close:hover{color:#ffffff;background:rgba(255,255,255,0.1);}" +
        ".sidebar-nav{padding:16px 12px;}" +
        ".nav-section-label{font-size:10px;font-weight:700;color:#64748b;letter-spacing:0.8px;padding:10px 12px 6px;text-transform:uppercase;}" +
        ".side-link{display:flex;align-items:center;gap:12px;padding:10px 14px;color:#94a3b8;text-decoration:none;font-size:13px;font-weight:500;border-radius:var(--radius-md);transition:all 0.15s;margin-bottom:2px;}" +
        ".side-link:hover{color:#ffffff;background:rgba(255,255,255,0.06);}" +
        ".side-link.active{background:rgba(16,185,129,0.12);color:#34d399;font-weight:600;}" +
        ".side-link i{font-size:18px;}" +
        ".sidebar-divider{border-top:1px solid rgba(255,255,255,0.08);margin:12px 0;}" +
        ".logout-link{color:#f87171!important;}" +
        ".logout-link:hover{background:rgba(239,68,68,0.1)!important;color:#fca5a5!important;}" +
        
        // Container
        ".container{padding:24px 20px;flex:1;}" +

        // Animations
        ".anim-on-scroll{opacity:0;transform:translateY(12px);transition:opacity 0.4s cubic-bezier(0.16,1,0.3,1),transform 0.4s cubic-bezier(0.16,1,0.3,1);}" +
        ".anim-on-scroll.in-view{opacity:1;transform:translateY(0);}" +
        ".chart-wrapper .bar-el{transform:scaleY(0);transform-origin:bottom;transition:transform 0.8s cubic-bezier(0.16,1,0.3,1);}" +
        ".chart-wrapper.in-view .bar-el{transform:scaleY(1);}" +

        // Greeting & Status Cards
        ".greeting{margin-bottom:20px;display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap;gap:12px;}" +
        ".greeting h2{font-size:22px;font-weight:800;color:var(--text-primary);letter-spacing:-0.5px;}" +
        ".greeting-sub{color:var(--text-secondary);font-size:13px;margin-top:2px;}" +
        ".health-card{display:flex;align-items:center;gap:12px;background:var(--bg-surface);border-radius:var(--radius-lg);padding:12px 16px;margin-bottom:18px;border:1px solid var(--border-default);box-shadow:var(--shadow-card);}" +
        ".health-dot{width:10px;height:10px;border-radius:50%;flex-shrink:0;box-shadow:0 0 0 3px rgba(16,185,129,0.2);}" +
        ".health-content strong{font-size:13px;font-weight:700;color:var(--text-primary);}" +
        ".health-content p{margin:0;color:var(--text-secondary);font-size:12px;line-height:1.4;}" +
        ".streak-banner{background:#fffbeb;border:1px solid #fef3c7;border-radius:var(--radius-md);padding:10px 14px;margin-bottom:18px;font-size:12px;color:#92400e;display:flex;align-items:center;gap:8px;box-shadow:var(--shadow-xs);}" +

        // Stat Cards
        ".cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(140px,1fr));gap:12px;margin-bottom:22px;}" +
        ".card{background:var(--bg-surface);border-radius:var(--radius-lg);padding:16px 14px;border:1px solid var(--border-default);box-shadow:var(--shadow-card);transition:all 0.2s cubic-bezier(0.16,1,0.3,1);position:relative;overflow:hidden;}" +
        ".card:hover{transform:translateY(-2px);box-shadow:var(--shadow-hover);border-color:var(--border-hover);}" +
        ".card-header{display:flex;justify-content:space-between;align-items:center;margin-bottom:8px;}" +
        ".card h3,.card-label{font-size:11px;font-weight:600;color:var(--text-muted);text-transform:uppercase;letter-spacing:0.4px;}" +
        ".card .value{font-size:20px;font-weight:800;letter-spacing:-0.5px;color:var(--text-primary);font-feature-settings:'tnum';}" +
        ".card.sales .value{color:var(--sales-val);}" +
        ".card.expenses .value{color:var(--expense-val);}" +
        ".card.supplies .value{color:var(--supply-val);}" +
        ".card.debts .value{color:var(--debt-val);}" +
        ".card.payments .value{color:var(--payment-val);}" +
        ".card.deliveries .value{color:#0d9488;}" +
        ".card.profit .value{color:var(--sales-val);}" +
        ".card.profit.negative .value{color:var(--expense-val);}" +

        // Section Containers
        ".section{background:var(--bg-surface);border-radius:var(--radius-lg);padding:20px;margin-bottom:20px;border:1px solid var(--border-default);box-shadow:var(--shadow-card);}" +
        ".section.alt{background:#f8fafc;}" +
        ".section h2{font-size:14px;font-weight:700;color:var(--text-primary);margin-bottom:16px;display:flex;align-items:center;gap:8px;}" +

        // Tables
        "table{width:100%;border-collapse:collapse;}" +
        "th{background:#f8fafc;color:var(--text-muted);padding:10px 12px;text-align:left;font-size:11px;font-weight:600;text-transform:uppercase;letter-spacing:0.3px;border-bottom:1px solid var(--border-default);}" +
        "td{padding:12px;border-bottom:1px solid var(--border-subtle);font-size:13px;color:var(--text-secondary);}" +
        "tr{transition:background-color 0.15s;}" +
        "tr:hover{background:#f8fafc;}" +
        ".badge{padding:3px 8px;border-radius:var(--radius-pill);font-size:10px;font-weight:700;text-transform:uppercase;letter-spacing:0.3px;display:inline-block;}" +
        ".badge-SALE{background:#dcfce7;color:#15803d;}" +
        ".badge-EXPENSE{background:#ffe4e6;color:#be123c;}" +
        ".badge-SUPPLY{background:#fef3c7;color:#b45309;}" +
        ".badge-DEBT{background:#e0e7ff;color:#4338ca;}" +
        ".badge-PAYMENT{background:#e0f2fe;color:#0369a1;}" +
        ".badge-DELIVERY{background:#ccfbf1;color:#0f766e;}" +
        ".empty{color:var(--text-muted);padding:24px 0;font-size:13px;text-align:center;}" +
        ".empty-state{text-align:center;padding:40px 20px;}" +
        ".empty-icon-wrap{width:48px;height:48px;border-radius:50%;background:#f1f5f9;color:var(--text-muted);display:flex;align-items:center;justify-content:center;margin:0 auto 12px;font-size:22px;}" +
        ".empty-state h4{font-size:15px;font-weight:700;color:var(--text-primary);margin-bottom:4px;}" +
        ".empty-state p{font-size:13px;color:var(--text-secondary);margin-bottom:16px;}" +

        // Transaction Card Items
        ".txn-card{background:var(--bg-surface);border-radius:var(--radius-md);padding:14px 16px;margin-bottom:10px;border:1px solid var(--border-default);box-shadow:var(--shadow-card);transition:all 0.15s cubic-bezier(0.16,1,0.3,1);}" +
        ".txn-card:hover{border-color:var(--border-hover);box-shadow:var(--shadow-hover);transform:translateY(-1px);}" +
        ".txn-top{display:flex;justify-content:space-between;align-items:flex-start;margin-bottom:4px;}" +
        ".txn-amount{font-size:16px;font-weight:800;color:var(--text-primary);letter-spacing:-0.2px;font-feature-settings:'tnum';}" +
        ".txn-desc{font-size:13px;font-weight:500;color:var(--text-primary);margin-bottom:4px;}" +
        ".txn-bottom{display:flex;justify-content:space-between;align-items:center;margin-top:6px;}" +
        ".txn-meta{font-size:11px;color:var(--text-muted);}" +
        ".txn-actions{display:flex;gap:6px;}" +

        // Category Filter Tabs
        ".cat-tabs{display:flex;gap:6px;overflow-x:auto;margin-bottom:18px;padding-bottom:2px;-webkit-overflow-scrolling:touch;}" +
        ".cat-tab{padding:6px 12px;border-radius:var(--radius-pill);font-size:12px;font-weight:600;text-decoration:none;color:var(--text-secondary);background:var(--bg-surface);border:1px solid var(--border-default);white-space:nowrap;transition:all 0.15s;}" +
        ".cat-tab:hover{border-color:var(--border-hover);color:var(--text-primary);background:#f8fafc;}" +
        ".cat-tab.active{background:var(--text-primary);color:#ffffff;border-color:var(--text-primary);}" +
        ".cat-tab.active.t-SALE{background:#10b981;border-color:#10b981;}" +
        ".cat-tab.active.t-EXPENSE{background:#f43f5e;border-color:#f43f5e;}" +
        ".cat-tab.active.t-SUPPLY{background:#f59e0b;border-color:#f59e0b;}" +
        ".cat-tab.active.t-DEBT{background:#6366f1;border-color:#6366f1;}" +
        ".cat-tab.active.t-PAYMENT{background:#0ea5e9;border-color:#0ea5e9;}" +

        // Buttons
        ".btn{display:inline-flex;align-items:center;justify-content:center;gap:6px;padding:8px 16px;border:none;border-radius:var(--radius-md);font-size:12px;cursor:pointer;font-weight:600;transition:all 0.15s;text-decoration:none;}" +
        ".btn:hover{transform:translateY(-1px);box-shadow:var(--shadow-xs);}" +
        ".btn:active{transform:scale(0.98);}" +
        ".btn-primary{background:var(--brand-primary);color:#ffffff;}" +
        ".btn-primary:hover{background:var(--brand-dark);}" +
        ".btn-danger{background:#fee2e2;color:#b91c1c;border:1px solid #fecaca;}" +
        ".btn-danger:hover{background:#fecaca;}" +

        // Debts
        ".debt-card{background:var(--bg-surface);border-radius:var(--radius-lg);padding:16px;margin-bottom:12px;border:1px solid var(--border-default);box-shadow:var(--shadow-card);transition:all 0.15s;}" +
        ".debt-card:hover{border-color:var(--border-hover);box-shadow:var(--shadow-hover);}" +
        ".debt-card h3{margin-bottom:8px;color:var(--text-primary);font-size:14px;font-weight:700;}" +
        ".progress-bar{height:6px;background:#f1f5f9;border-radius:var(--radius-pill);overflow:hidden;margin:12px 0 8px;}" +
        ".progress-animate{height:100%;border-radius:var(--radius-pill);width:0;background:var(--brand-primary);transition:width 1s cubic-bezier(0.16,1,0.3,1);}" +
        ".debt-amounts{display:flex;gap:14px;font-size:12px;flex-wrap:wrap;color:var(--text-secondary);}" +
        ".status-badge{padding:2px 8px;border-radius:var(--radius-pill);font-size:10px;font-weight:700;}" +
        ".status-unpaid{background:#fee2e2;color:#b91c1c;}" +
        ".status-partial{background:#fef3c7;color:#b45309;}" +
        ".status-paid{background:#dcfce7;color:#15803d;}" +

        // Chat UI
        ".chat-container{padding:16px 0;}" +
        ".chat-messages{min-height:360px;max-height:480px;overflow-y:auto;padding:12px 4px;margin-bottom:12px;display:flex;flex-direction:column;gap:10px;}" +
        ".chat-msg{padding:12px 16px;border-radius:var(--radius-lg);font-size:13px;line-height:1.5;max-width:85%;box-shadow:var(--shadow-xs);}" +
        ".chat-msg.user{background:var(--brand-primary);color:#ffffff;align-self:flex-end;border-bottom-right-radius:2px;}" +
        ".chat-msg.system{background:var(--bg-surface);border:1px solid var(--border-default);color:var(--text-primary);align-self:flex-start;border-bottom-left-radius:2px;}" +
        ".typing{display:flex;gap:5px;padding:12px 16px;align-self:flex-start;background:var(--bg-surface);border:1px solid var(--border-default);border-radius:var(--radius-lg);}" +
        ".typing span{width:6px;height:6px;background:var(--text-muted);border-radius:50%;animation:typingBounce 1.2s infinite ease-in-out;}" +
        ".typing span:nth-child(2){animation-delay:0.15s;}" +
        ".typing span:nth-child(3){animation-delay:0.3s;}" +
        "@keyframes typingBounce{0%,80%,100%{transform:scale(0.8);opacity:0.4;}40%{transform:scale(1.2);opacity:1;}}" +
        ".chat-input-bar{display:flex;gap:8px;background:var(--bg-surface);padding:6px;border-radius:var(--radius-lg);border:1px solid var(--border-default);box-shadow:var(--shadow-card);}" +
        ".chat-input-bar input{flex:1;padding:10px 14px;border:none;background:transparent;font-size:14px;color:var(--text-primary);}" +
        ".chat-input-bar input:focus{outline:none;}" +
        ".chat-input-bar button{padding:10px 18px;border-radius:var(--radius-md);}" +
        ".quick-chips{display:flex;gap:6px;overflow-x:auto;padding-bottom:8px;margin-bottom:8px;}" +
        ".quick-chip{padding:5px 12px;border-radius:var(--radius-pill);background:var(--bg-surface);border:1px solid var(--border-default);color:var(--text-secondary);font-size:11px;font-weight:600;cursor:pointer;white-space:nowrap;transition:all 0.15s;}" +
        ".quick-chip:hover{border-color:var(--brand-primary);color:var(--brand-dark);background:var(--brand-light);}" +

        // Help & Confirmation
        ".help-fab{position:fixed;bottom:80px;left:24px;width:38px;height:38px;border-radius:50%;background:var(--bg-surface);color:var(--text-secondary);border:1px solid var(--border-default);display:flex;align-items:center;justify-content:center;cursor:pointer;box-shadow:var(--shadow-card);z-index:50;transition:all 0.2s;}" +
        ".help-fab:hover{background:#f8fafc;color:var(--text-primary);border-color:var(--border-hover);}" +
        ".help-panel{position:fixed;right:-340px;top:0;width:320px;height:100vh;background:#ffffff;z-index:201;transition:right 0.3s cubic-bezier(0.16,1,0.3,1);box-shadow:-4px 0 24px rgba(0,0,0,0.12);overflow-y:auto;padding:24px;border-left:1px solid var(--border-default);}" +
        ".help-panel.open{right:0;}" +
        ".help-example{padding:10px 12px;margin:6px 0;background:#f8fafc;border:1px solid var(--border-default);border-radius:var(--radius-md);font-size:12px;cursor:pointer;transition:all 0.15s;}" +
        ".help-example:hover{background:var(--brand-light);border-color:#a7f3d0;color:var(--brand-dark);}" +
        ".confirm-card{background:var(--bg-surface);border:1px solid #fde68a;border-radius:var(--radius-lg);padding:14px 16px;margin:8px 0;box-shadow:var(--shadow-card);}" +
        ".confirm-card .actions{display:flex;gap:8px;margin-top:12px;flex-wrap:wrap;}" +
        ".confirm-btn{background:var(--brand-primary);color:#fff;border:none;padding:8px 14px;border-radius:var(--radius-md);font-weight:600;cursor:pointer;}" +
        ".change-btn{background:#f1f5f9;color:var(--text-primary);border:1px solid var(--border-default);padding:8px 14px;border-radius:var(--radius-md);font-weight:600;cursor:pointer;}" +
        ".cancel-btn{background:#fee2e2;color:#b91c1c;border:none;padding:8px 14px;border-radius:var(--radius-md);font-weight:600;cursor:pointer;}" +
        ".category-select{padding:6px 10px;border:1px solid var(--border-default);border-radius:var(--radius-md);font-size:12px;margin-top:6px;background:var(--bg-surface);color:var(--text-primary);}" +
        ".toast{position:fixed;top:20px;right:20px;padding:12px 20px;border-radius:var(--radius-md);color:#fff;font-size:13px;font-weight:600;z-index:300;opacity:0;transform:translateY(-10px);transition:all 0.3s cubic-bezier(0.16,1,0.3,1);box-shadow:var(--shadow-hover);background:#0f172a;}" +
        ".toast.show{opacity:1;transform:translateY(0);}" +
        ".toast.success{background:var(--brand-primary);}" +
        ".fab{position:fixed;bottom:24px;right:24px;width:48px;height:48px;border-radius:50%;background:var(--brand-primary);color:#fff;font-size:20px;border:none;cursor:pointer;box-shadow:var(--shadow-hover);display:flex;align-items:center;justify-content:center;text-decoration:none;z-index:50;transition:all 0.2s;}" +
        ".fab:hover{background:var(--brand-dark);transform:scale(1.05);}" +

        // Chart & Insights Styles
        ".donut-container{display:flex;flex-direction:column;align-items:center;padding:12px 0;}" +
        ".donut-svg{margin-bottom:12px;}" +
        ".chart-legend{display:flex;justify-content:center;gap:14px;flex-wrap:wrap;}" +
        ".legend-item{display:flex;align-items:center;gap:6px;font-size:11px;color:var(--text-secondary);}" +
        ".legend-dot{width:8px;height:8px;border-radius:50%;}" +
        ".filter-bar{display:flex;gap:8px;flex-wrap:wrap;align-items:center;margin-bottom:18px;}" +
        ".filter-bar select,.filter-bar input{padding:8px 12px;border:1px solid var(--border-default);border-radius:var(--radius-md);font-size:12px;background:var(--bg-surface);color:var(--text-primary);}" +
        ".filter-bar select:focus,.filter-bar input:focus{border-color:var(--brand-primary);outline:none;box-shadow:0 0 0 2px rgba(16,185,129,0.15);}" +
        ".advice-card{background:var(--bg-surface);border-radius:var(--radius-lg);padding:14px 16px;margin-bottom:12px;border:1px solid var(--border-default);border-left:4px solid var(--brand-primary);box-shadow:var(--shadow-card);}" +
        ".advice-card.warning{border-left-color:var(--supply-val);}" +
        ".advice-card.danger{border-left-color:var(--expense-val);}" +
        ".advice-card h4{margin-bottom:4px;color:var(--text-primary);font-size:13px;font-weight:700;}" +
        ".advice-card p{color:var(--text-secondary);font-size:12px;line-height:1.5;}" +
        ".period-bar{display:flex;gap:6px;flex-wrap:wrap;margin-bottom:20px;}" +
        ".period-btn{padding:6px 14px;border-radius:var(--radius-pill);text-decoration:none;font-size:12px;font-weight:600;border:1px solid var(--border-default);color:var(--text-secondary);background:var(--bg-surface);transition:all 0.15s;}" +
        ".period-btn:hover{border-color:var(--border-hover);color:var(--text-primary);background:#f8fafc;}" +
        ".period-btn.active{background:var(--text-primary);color:#ffffff;border-color:var(--text-primary);}" +
        ".chart-container{background:var(--bg-surface);border-radius:var(--radius-lg);padding:18px;margin-bottom:20px;border:1px solid var(--border-default);box-shadow:var(--shadow-card);}" +
        ".chart-container h3{color:var(--text-primary);font-size:14px;font-weight:700;margin-bottom:12px;}" +
        ".carousel{position:relative;overflow:hidden;border-radius:var(--radius-lg);margin-bottom:20px;background:linear-gradient(135deg,#ecfdf5,#d1fae5);border:1px solid #a7f3d0;}" +
        ".carousel-track{display:flex;transition:transform 0.4s cubic-bezier(0.16,1,0.3,1);}" +
        ".carousel-slide{min-width:100%;padding:24px 20px;text-align:center;}" +
        ".carousel-slide h3{font-size:11px;color:var(--brand-dark);text-transform:uppercase;letter-spacing:0.5px;font-weight:700;margin-bottom:6px;}" +
        ".carousel-slide .big-num{font-size:32px;font-weight:800;color:var(--brand-dark);letter-spacing:-0.5px;font-feature-settings:'tnum';}" +
        ".carousel-dots{display:flex;justify-content:center;gap:6px;padding:8px;}" +
        ".carousel-dots span{width:6px;height:6px;border-radius:50%;background:rgba(4,120,87,0.25);cursor:pointer;transition:all 0.2s;}" +
        ".carousel-dots span.active{background:var(--brand-dark);width:16px;border-radius:var(--radius-pill);}" +
        ".app-footer{text-align:center;padding:24px 16px;color:var(--text-muted);font-size:12px;border-top:1px solid var(--border-default);background:transparent;}" +
        "@media print{.app-header,.sidebar,.sidebar-overlay,.fab,.help-fab,.no-print,.app-footer{display:none!important;}.device-frame{max-width:100%;margin:0;box-shadow:none;border:none;border-radius:0;}.app-shell{min-height:auto;background:#fff;}}" +
        "";
}