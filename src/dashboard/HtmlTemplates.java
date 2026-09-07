package dashboard;

public class HtmlTemplates {

    public static final String LOGO_DATA = "data:image/svg+xml;utf8,%3Csvg%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%20viewBox%3D%220%200%2036%2036%22%20width%3D%2232%22%20height%3D%2232%22%20fill%3D%22none%22%3E%3Cpath%20d%3D%22M22%2010h-8a4%204%200%200%200-4%204v0a4%204%200%200%200%204%204h8a4%204%200%200%201%204%204v0a4%204%200%200%201-4%204h-9%22%20stroke%3D%22%2310b981%22%20stroke-width%3D%223.2%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22/%3E%3Cpath%20d%3D%22M22%2016l8-8m0%200h-5.5m5.5%200v5.5%22%20stroke%3D%22%230f172a%22%20stroke-width%3D%223%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22/%3E%3Ccircle%20cx%3D%2230%22%20cy%3D%228%22%20r%3D%221.5%22%20fill%3D%22%2310b981%22/%3E%3C/svg%3E";

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
            "<div><div class='sidebar-brand'>SmartLedger</div><div class='sidebar-tagline'>Bookkeeping for small traders</div></div>" +
            "</div>" +
            "<button onclick='toggleSidebar()' class='sidebar-close' aria-label='Close Navigation'><i class='ti ti-x'></i></button>" +
            "</div>" +
            "<nav class='sidebar-nav'>" +
            "<div class='nav-section-label'>Main</div>" +
            sideLink("/dashboard/" + token, "Overview", "ti-layout-grid", active.equals("overview")) +
            sideLink("/chat/" + token, "Chat", "ti-message-circle", active.equals("chat")) +
            sideLink("/dashboard/" + token + "/transactions", "Transactions", "ti-receipt", active.equals("transactions")) +
            sideLink("/dashboard/" + token + "/debts", "Debts", "ti-scale", active.equals("debts")) +
            "<div class='nav-section-label'>Reports</div>" +
            sideLink("/analysis/" + token, "Analysis", "ti-chart-pie", active.equals("analysis")) +
            sideLink("/report/" + token, "Report", "ti-file-analytics", active.equals("report")) +
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
            "<div>SmartLedger &#169; 2026 &#8212; COS 202 Group 22</div>" +
            "<div class='footer-sub'>Aligned with UN SDG 8 (Decent Work) and SDG 9 (Industry, Innovation)</div>" +
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
        String timeGreet = hour >= 5 && hour < 12 ? "Good morning" : hour < 17 ? "Good afternoon" : hour < 21 ? "Good evening" : "Hello";
        String salesMsg = todaySales > 0 ? "You've made &#8358;" + formatAmount(todaySales) + " in sales today." : "No sales recorded yet today.";
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
            color = "var(--brand-primary)"; label = "Healthy"; tip = "Business is in good shape!"; statusClass = "healthy";
        } else if (profit > 0) {
            color = "var(--supply-val)"; label = "Okay"; tip = "Expenses are creeping up."; statusClass = "moderate";
        } else {
            color = "var(--expense-val)"; label = "Needs Attention"; tip = "Spending exceeds earnings."; statusClass = "warning";
        }
        return "<div class='health-card " + statusClass + " anim-on-scroll'>" +
            "<div class='health-dot' style='background:" + color + ";'></div>" +
            "<div class='health-content'><strong>" + label + "</strong><p>" + tip + "</p></div>" +
            "</div>";
    }

    public static String streakBanner(int streak) {
        if (streak < 2) return "";
        return "<div class='streak-banner anim-on-scroll'>" +
            "<span class='streak-tag'>Streak</span>" +
            "<span><strong>" + streak + "-day recording streak! Keep it going.</span></div>";
    }

    public static String pieChart(double sales, double expenses, double supplies) {
        double total = sales + expenses + supplies;
        if (total == 0) return "<p class='empty'>No data yet.</p>";
        double s1 = sales/total*360, s2 = expenses/total*360, s3 = supplies/total*360;
        double a1 = 0, a2 = s1, a3 = s1+s2;
        return "<div class='donut-container anim-on-scroll'>" +
            "<svg width='180' height='180' viewBox='0 0 180 180' class='donut-svg'>" +
            pieSlice(90,90,75,a1,a1+s1,"#2e7d32") + pieSlice(90,90,75,a2,a2+s2,"#c62828") + pieSlice(90,90,75,a3,a3+s3,"#e65100") +
            "<circle cx='90' cy='90' r='50' fill='#ffffff' stroke='#f0f0f0' stroke-width='1'/>" +
            "<text x='90' y='82' text-anchor='middle' font-size='9' font-weight='800' fill='#666' letter-spacing='1'>Total</text>" +
            "<text x='90' y='102' text-anchor='middle' font-size='12' font-weight='800' fill='#111827'>&#8358;" + formatAmount(total) + "</text>" +
            "</svg>" +
            "<div class='chart-legend'>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#2e7d32;'></span><span class='legend-text'>Sales (" + (int)(sales/total*100) + "%)</span></div>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#c62828;'></span><span class='legend-text'>Expenses (" + (int)(expenses/total*100) + "%)</span></div>" +
            "<div class='legend-item'><span class='legend-dot' style='background:#e65100;'></span><span class='legend-text'>Supplies (" + (int)(supplies/total*100) + "%)</span></div>" +
            "</div></div>";
    }

    private static String pieSlice(int cx, int cy, int r, double startAngle, double endAngle, String color) {
        if (endAngle - startAngle >= 360) endAngle = startAngle + 359.99;
        if (endAngle - startAngle < 0.5) return "";
        double sr = Math.toRadians(startAngle - 90), er = Math.toRadians(endAngle - 90);
        int x1 = (int)(cx + r * Math.cos(sr)), y1 = (int)(cy + r * Math.sin(sr));
        int x2 = (int)(cx + r * Math.cos(er)), y2 = (int)(cy + r * Math.sin(er));
        int large = (endAngle - startAngle) > 180 ? 1 : 0;
        return "<path d='M" + cx + "," + cy + " L" + x1 + "," + y1 + " A" + r + "," + r + " 0 " + large + ",1 " + x2 + "," + y2 + " Z' fill='" + color + "' stroke='#fff' stroke-width='2'/>";
    }

    public static String barChart(double sales, double expenses, double supplies, double debts, double payments) {
        double max = Math.max(1, Math.max(sales, Math.max(expenses, Math.max(supplies, Math.max(debts, payments)))));
        StringBuilder svg = new StringBuilder();
        svg.append("<div class='anim-on-scroll chart-wrapper'><svg width='100%' viewBox='0 0 360 200' xmlns='http://www.w3.org/2000/svg'>");
        svg.append("<line x1='30' y1='155' x2='340' y2='155' stroke='#ccc' stroke-width='1'/>");
        svg.append("<line x1='30' y1='85' x2='340' y2='85' stroke='#e5e7eb' stroke-dasharray='2 2' stroke-width='1'/>");
        svg.append("<line x1='30' y1='15' x2='340' y2='15' stroke='#e5e7eb' stroke-dasharray='2 2' stroke-width='1'/>");

        String[][] bars = {
            {"Sales", String.valueOf(sales), "#2e7d32"},
            {"Expense", String.valueOf(expenses), "#c62828"},
            {"Supply", String.valueOf(supplies), "#e65100"},
            {"Debt", String.valueOf(debts), "#6a1b9a"},
            {"Paid", String.valueOf(payments), "#1565c0"}
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
               .append("' fill='").append(bars[i][2]).append("' class='bar-el'/>");
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
            "<h4>No transactions yet</h4>" +
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
        "--bg-canvas:#f9fafb;" + // Softer off-white for depth
        "--bg-surface:#ffffff;" +
        "--bg-subtle:#f3f4f6;" +
        "--border-rule:#e5e7eb;" + // Standard 1px human border
        "--border-light:#f3f4f6;" +
        "--text-primary:#1f2937;" + // Dark gray, softer than pitch black
        "--text-secondary:#4b5563;" +
        "--text-muted:#9ca3af;" +
        "--brand-primary:#166534;" + // Professional Pine Green
        "--brand-dark:#14532d;" +
        "--brand-light:#f0fdf4;" +
        "--sales-val:#15803d;" +
        "--expense-val:#b91c1c;" +
        "--supply-val:#c2410c;" +
        "--debt-val:#6d28d9;" +
        "--payment-val:#1d4ed8;" +
        "--shadow-sm:0 1px 2px 0 rgba(0,0,0,0.05);" + // Realistic depth, no AI glow
        "--shadow-md:0 4px 6px -1px rgba(0,0,0,0.05),0 2px 4px -1px rgba(0,0,0,0.03);" +
        "}" +
        "*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;}" +
        "html{scroll-behavior:smooth;background-color:var(--bg-canvas);}" +
        "body{background:var(--bg-canvas);min-height:100vh;color:var(--text-primary);-webkit-font-smoothing:antialiased;line-height:1.5;}" +
        ".device-frame{max-width:980px;margin:0 auto;min-height:100vh;background:#ffffff;box-shadow:var(--shadow-sm);}" + 
        ".app-shell{background:transparent;min-height:100vh;display:flex;flex-direction:column;}" +

        // Header
        ".app-header{background:#ffffff;border-bottom:1px solid var(--border-rule);padding:0 24px;display:flex;justify-content:space-between;align-items:center;height:64px;position:sticky;top:0;z-index:100;}" +
        ".hdr-left{display:flex;align-items:center;gap:14px;}" +
        ".logo-link{display:flex;align-items:center;text-decoration:none;gap:12px;}" +
        ".logo-badge{display:flex;align-items:center;justify-content:center;transition:transform 0.15s;}" +
        ".logo-link:hover .logo-badge{}" +
        ".logo-img{width:28px;height:28px;object-fit:contain;}" + 
        ".logo-meta{display:flex;align-items:center;gap:10px;}" +
        ".logo-text{font-size:16px;font-weight:700;color:var(--text-primary);}" + 
        ".biz-badge{font-size:11px;font-weight:600;color:var(--brand-primary);background:var(--brand-light);padding:4px 10px;border-radius:12px;max-width:180px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;}" +
        ".hdr-right{display:flex;align-items:center;gap:10px;}" +
        ".nav-action-btn{display:inline-flex;align-items:center;gap:6px;background:var(--brand-primary);color:#fff;text-decoration:none;font-size:13px;font-weight:600;padding:8px 16px;border-radius:6px;transition:background 0.15s;box-shadow:var(--shadow-sm);}" +
        ".nav-action-btn:hover{background:var(--brand-dark);}" +
        ".hamburger{background:transparent;border:1px solid var(--border-rule);font-size:18px;cursor:pointer;color:var(--text-secondary);width:36px;height:36px;display:flex;align-items:center;justify-content:center;border-radius:6px;transition:background 0.15s;}" +
        ".hamburger:hover{background:var(--bg-subtle);color:var(--text-primary);}" +

        // Sidebar
        ".sidebar-overlay{display:none;position:fixed;inset:0;background:rgba(17,24,39,0.4);z-index:199;backdrop-filter:blur(2px);}" +
        ".sidebar-overlay.open{display:block;}" +
        ".sidebar{position:fixed;left:-320px;top:0;width:280px;height:100vh;background:#ffffff;border-right:1px solid var(--border-rule);z-index:200;transition:left 0.3s cubic-bezier(0.4, 0, 0.2, 1);overflow-y:auto;}" +
        ".sidebar.open{left:0;}" +
        ".sidebar-hdr{padding:24px 20px;display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid var(--border-rule);background:#ffffff;}" + 
        ".sidebar-brand-box{display:flex;align-items:center;gap:12px;}" +
        ".sidebar-logo-badge{display:flex;align-items:center;justify-content:center;}" +
        ".sidebar-brand{font-size:16px;font-weight:700;color:var(--text-primary);}" +
        ".sidebar-tagline{font-size:11px;color:var(--text-muted);font-weight:500;margin-top:2px;}" +
        ".sidebar-close{background:none;border:none;font-size:20px;color:var(--text-muted);cursor:pointer;width:32px;height:32px;display:flex;align-items:center;justify-content:center;border-radius:6px;transition:background 0.15s;}" +
        ".sidebar-close:hover{background:var(--bg-subtle);color:var(--text-primary);}" +
        ".sidebar-nav{padding:16px 14px;}" +
        ".nav-section-label{font-size:11px;font-weight:600;color:var(--text-muted);padding:12px 10px 8px;text-transform:uppercase;letter-spacing:0.5px;}" + 
        ".side-link{display:flex;align-items:center;gap:12px;padding:10px 12px;color:var(--text-secondary);text-decoration:none;font-size:14px;font-weight:500;border-radius:6px;transition:all 0.15s;margin-bottom:2px;}" +
        ".side-link:hover{color:var(--text-primary);background:var(--bg-subtle);}" +
        ".side-link.active{background:var(--brand-light);color:var(--brand-primary);font-weight:600;}" + 
        ".side-link i{font-size:18px;}" +
        ".sidebar-divider{border-top:1px solid var(--border-rule);margin:16px 14px;}" +
        ".logout-link{color:var(--expense-val)!important;}" +
        ".logout-link:hover{background:#fef2f2!important;}" +

        // Container
        ".container{padding:32px 24px;flex:1;}" +

        // Animations (Untouched function, tweaked curves for realism)
        ".anim-on-scroll{opacity:0;transform:translateY(8px);transition:opacity 0.4s ease-out,transform 0.4s ease-out;}" +
        ".anim-on-scroll.in-view{opacity:1;transform:translateY(0);}" +
        ".chart-wrapper .bar-el{transform:scaleY(0);transform-origin:bottom;transition:transform 0.6s cubic-bezier(0.4, 0, 0.2, 1);}" +
        ".chart-wrapper.in-view .bar-el{transform:scaleY(1);}" +

        // Greeting & Status Cards
        ".greeting{margin-bottom:32px;padding-bottom:24px;border-bottom:1px solid var(--border-rule);display:flex;justify-content:space-between;align-items:flex-end;flex-wrap:wrap;gap:16px;}" +
        ".greeting h2{font-size:24px;font-weight:700;color:var(--text-primary);line-height:1.2;}" +
        ".greeting-sub{color:var(--text-secondary);font-size:14px;font-weight:400;margin-top:6px;}" +
        ".health-card{display:flex;align-items:center;gap:16px;background:#ffffff;border:1px solid var(--border-rule);border-radius:8px;padding:16px 20px;margin-bottom:24px;box-shadow:var(--shadow-sm);}" +
        ".health-dot{width:10px;height:10px;border-radius:50%;flex-shrink:0;}" + 
        ".health-content strong{font-size:14px;font-weight:600;color:var(--text-primary);}" +
        ".health-content p{margin:4px 0 0;color:var(--text-secondary);font-size:13px;line-height:1.4;}" +
        ".streak-banner{background:var(--brand-light);border:1px solid #dcfce7;border-radius:8px;padding:14px 20px;margin-bottom:24px;font-size:13px;color:var(--brand-dark);display:flex;align-items:center;gap:12px;}" +
        ".streak-tag{background:var(--brand-primary);color:#fff;font-size:11px;font-weight:600;padding:4px 8px;border-radius:4px;text-transform:uppercase;letter-spacing:0.5px;}" +

        // Stat Cards
        ".cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(180px,1fr));gap:16px;margin-bottom:32px;}" +
        ".card{background:#ffffff;border:1px solid var(--border-rule);border-radius:10px;padding:20px;transition:box-shadow 0.2s, transform 0.2s;position:relative;box-shadow:var(--shadow-sm);}" +
        ".card:hover{box-shadow:var(--shadow-md);}" +
        ".card-header{display:flex;justify-content:space-between;align-items:center;margin-bottom:12px;}" + 
        ".card h3,.card-label{font-size:13px;font-weight:600;color:var(--text-secondary);}" +
        ".card .value{font-size:24px;font-weight:700;color:var(--text-primary);font-feature-settings:'tnum';font-variant-numeric:tabular-nums;}" +
        ".card.sales{border-left:3px solid var(--sales-val);}" +
        ".card.sales .value{color:var(--sales-val);}" +
        ".card.expenses{border-left:3px solid var(--expense-val);}" +
        ".card.expenses .value{color:var(--expense-val);}" +
        ".card.supplies{border-left:3px solid var(--supply-val);}" +
        ".card.supplies .value{color:var(--supply-val);}" +
        ".card.debts{border-left:3px solid var(--debt-val);}" +
        ".card.debts .value{color:var(--debt-val);}" +
        ".card.payments{border-left:3px solid var(--payment-val);}" +
        ".card.payments .value{color:var(--payment-val);}" +
        ".card.deliveries{border-left:3px solid #0f766e;}" +
        ".card.deliveries .value{color:#0f766e;}" +
        ".card.profit{border-left:3px solid var(--sales-val);}" +
        ".card.profit .value{color:var(--sales-val);}" +
        ".card.profit.negative{border-left:3px solid var(--expense-val);}" +
        ".card.profit.negative .value{color:var(--expense-val);}" +

        // Section Containers
        ".section{background:#ffffff;border:1px solid var(--border-rule);border-radius:10px;padding:24px;margin-bottom:32px;box-shadow:var(--shadow-sm);}" +
        ".section.alt{background:var(--bg-canvas);box-shadow:none;border:none;padding:0;}" + 
        ".section h2{font-size:15px;font-weight:600;color:var(--text-primary);margin-bottom:24px;display:flex;align-items:center;gap:10px;border-bottom:1px solid var(--border-light);padding-bottom:12px;}" +

        // Tables 
        "table{width:100%;border-collapse:collapse;font-size:14px;}" + 
        "th{background:var(--bg-canvas);color:var(--text-secondary);padding:12px 16px;text-align:left;font-size:12px;font-weight:600;border-top:1px solid var(--border-rule);border-bottom:1px solid var(--border-rule);}" +
        "td{padding:16px;border-bottom:1px solid var(--border-light);color:var(--text-primary);font-weight:400;}" + 
        "tr{transition:background-color 0.15s;}" +
        "tr:hover{background:var(--bg-canvas);}" +
        ".badge{padding:4px 8px;border-radius:6px;font-size:11px;font-weight:600;display:inline-block;letter-spacing:0.3px;}" +
        ".badge-SALE{background:var(--brand-light);color:var(--sales-val);}" +
        ".badge-EXPENSE{background:#fef2f2;color:var(--expense-val);}" +
        ".badge-SUPPLY{background:#fff7ed;color:var(--supply-val);}" +
        ".badge-DEBT{background:#f5f3ff;color:var(--debt-val);}" +
        ".badge-PAYMENT{background:#eff6ff;color:var(--payment-val);}" +
        ".badge-DELIVERY{background:#f0fdfa;color:#0f766e;}" +
        ".empty{color:var(--text-muted);padding:40px 0;font-size:14px;text-align:center;font-weight:500;}" +
        ".empty-state{text-align:center;padding:48px 24px;border:1px dashed #d1d5db;border-radius:10px;background:var(--bg-canvas);}" + 
        ".empty-icon-wrap{width:48px;height:48px;background:#ffffff;border:1px solid var(--border-rule);box-shadow:var(--shadow-sm);color:var(--text-muted);display:flex;align-items:center;justify-content:center;margin:0 auto 16px;font-size:24px;border-radius:50%;}" +
        ".empty-state h4{font-size:16px;font-weight:600;color:var(--text-primary);margin-bottom:8px;}" +
        ".empty-state p{font-size:14px;color:var(--text-secondary);margin-bottom:24px;}" +

        // Transaction Card Items
        ".txn-card{background:#ffffff;border:1px solid var(--border-rule);border-radius:8px;padding:16px 20px;margin-bottom:12px;transition:box-shadow 0.2s;box-shadow:var(--shadow-sm);}" +
        ".txn-card:hover{}" +
        ".txn-top{display:flex;justify-content:space-between;align-items:flex-start;margin-bottom:8px;}" +
        ".txn-amount{font-size:16px;font-weight:700;color:var(--text-primary);font-feature-settings:'tnum';font-variant-numeric:tabular-nums;}" +
        ".txn-desc{font-size:14px;font-weight:500;color:var(--text-primary);margin-bottom:8px;}" +
        ".txn-bottom{display:flex;justify-content:space-between;align-items:center;margin-top:12px;border-top:1px solid var(--border-light);padding-top:12px;}" +
        ".txn-meta{font-size:12px;color:var(--text-muted);font-weight:400;}" +
        ".txn-actions{display:flex;gap:8px;}" +

        // Category Filter Tabs (Pills)
        ".cat-tabs{display:flex;gap:8px;overflow-x:auto;margin-bottom:24px;padding-bottom:4px;-webkit-overflow-scrolling:touch;scrollbar-width:none;}" +
        ".cat-tabs::-webkit-scrollbar{display:none;}" +
        ".cat-tab{padding:8px 16px;border-radius:20px;font-size:13px;font-weight:500;text-decoration:none;color:var(--text-secondary);background:#ffffff;border:1px solid var(--border-rule);white-space:nowrap;transition:all 0.15s;}" + 
        ".cat-tab:hover{background:var(--bg-canvas);color:var(--text-primary);}" +
        ".cat-tab.active{background:var(--text-primary);color:#ffffff;border-color:var(--text-primary);font-weight:600;}" +
        ".cat-tab.active.t-SALE{background:var(--sales-val);border-color:var(--sales-val);}" +
        ".cat-tab.active.t-EXPENSE{background:var(--expense-val);border-color:var(--expense-val);}" +
        ".cat-tab.active.t-SUPPLY{background:var(--supply-val);border-color:var(--supply-val);}" +
        ".cat-tab.active.t-DEBT{background:var(--debt-val);border-color:var(--debt-val);}" +
        ".cat-tab.active.t-PAYMENT{background:var(--payment-val);border-color:var(--payment-val);}" +

        // Buttons
        ".btn{display:inline-flex;align-items:center;justify-content:center;gap:8px;padding:10px 20px;border:1px solid var(--border-rule);border-radius:6px;font-size:13px;cursor:pointer;font-weight:600;transition:all 0.15s;text-decoration:none;box-shadow:var(--shadow-sm);}" +
        ".btn:active{transform:scale(0.98);}" +
        ".btn-primary{background:var(--brand-primary);color:#ffffff;border-color:var(--brand-primary);}" +
        ".btn-primary:hover{background:var(--brand-dark);border-color:var(--brand-dark);}" +
        ".btn-danger{background:#ffffff;color:var(--expense-val);border-color:var(--border-rule);}" + 
        ".btn-danger:hover{background:#fef2f2;border-color:#fca5a5;}" +

        // Debts
        ".debt-card{background:#ffffff;border:1px solid var(--border-rule);border-radius:10px;padding:20px;margin-bottom:16px;box-shadow:var(--shadow-sm);}" +
        ".debt-card h3{margin-bottom:12px;color:var(--text-primary);font-size:15px;font-weight:600;}" +
        ".progress-bar{height:6px;background:var(--bg-canvas);border-radius:3px;overflow:hidden;margin:16px 0 12px;}" +
        ".progress-animate{height:100%;border-radius:3px;width:0;background:var(--brand-primary);transition:width 1s cubic-bezier(0.4, 0, 0.2, 1);}" +
        ".debt-amounts{display:flex;gap:24px;font-size:13px;flex-wrap:wrap;color:var(--text-secondary);font-weight:500;}" +
        ".status-badge{padding:4px 10px;border-radius:12px;font-size:11px;font-weight:600;}" + 
        ".status-unpaid{background:#fef2f2;color:var(--expense-val);}" +
        ".status-partial{background:#fff7ed;color:var(--supply-val);}" +
        ".status-paid{background:var(--brand-light);color:var(--sales-val);}" +

        // Chat UI
        ".chat-container{padding:16px 0;max-width:768px;margin:0 auto;}" +
        ".chat-messages{min-height:400px;max-height:550px;overflow-y:auto;padding:16px 8px;margin-bottom:20px;display:flex;flex-direction:column;gap:16px;scrollbar-width:thin;}" +
        ".chat-msg{padding:14px 18px;border-radius:12px;font-size:14px;line-height:1.5;max-width:80%;box-shadow:var(--shadow-sm);}" +
        ".chat-msg.user{background:var(--brand-primary);color:#ffffff;align-self:flex-end;border-bottom-right-radius:4px;}" +
        ".chat-msg.system{background:#ffffff;border:1px solid var(--border-rule);color:var(--text-primary);align-self:flex-start;border-bottom-left-radius:4px;}" +
        ".chat-msg.system.welcome{background:var(--brand-light);border:1px solid #dcfce7;}" +
        ".typing{display:flex;gap:6px;padding:16px 20px;align-self:flex-start;background:#ffffff;border:1px solid var(--border-rule);border-radius:12px;border-bottom-left-radius:4px;box-shadow:var(--shadow-sm);}" +
        ".typing span{width:6px;height:6px;background:var(--text-muted);border-radius:50%;animation:typingBounce 1.4s infinite ease-in-out both;}" +
        ".typing span:nth-child(1){animation-delay:-0.32s;}" +
        ".typing span:nth-child(2){animation-delay:-0.16s;}" +
        "@keyframes typingBounce{0%,80%,100%{transform:scale(0);opacity:0.4;}40%{transform:scale(1);opacity:1;}}" +
        ".chat-input-bar{display:flex;gap:12px;background:#ffffff;padding:12px;border:1px solid var(--border-rule);border-radius:10px;box-shadow:var(--shadow-sm);}" +
        ".chat-input-bar input{flex:1;padding:12px 16px;border:1px solid var(--border-light);border-radius:6px;font-size:14px;font-weight:400;color:var(--text-primary);background:var(--bg-canvas);outline:none;transition:border-color 0.2s;}" +
        ".chat-input-bar input:focus{border-color:var(--brand-primary);background:#ffffff;}" +
        ".chat-input-bar button{padding:12px 24px;border-radius:6px;font-weight:600;}" +
        ".quick-chips{display:flex;gap:8px;overflow-x:auto;padding-bottom:12px;margin-bottom:12px;scrollbar-width:none;}" +
        ".quick-chips::-webkit-scrollbar{display:none;}" +
        ".quick-chip{padding:8px 14px;border-radius:20px;background:#ffffff;border:1px solid var(--border-rule);color:var(--text-secondary);font-size:12px;font-weight:500;cursor:pointer;white-space:nowrap;transition:all 0.15s;}" +
        ".quick-chip:hover{background:var(--bg-canvas);color:var(--text-primary);border-color:#d1d5db;}" +
        ".quick-chip.chip-sale{color:var(--sales-val);border-color:var(--sales-val);background:var(--brand-light);}" +
        ".quick-chip.chip-expense{color:var(--expense-val);border-color:var(--expense-val);background:#fef2f2;}" +
        ".quick-chip.chip-debt{color:var(--debt-val);border-color:var(--debt-val);background:#f5f3ff;}" +
        ".quick-chip.chip-payment{color:var(--payment-val);border-color:var(--payment-val);background:#eff6ff;}" +
        ".quick-chip.chip-profit{color:var(--sales-val);border-color:var(--sales-val);background:var(--brand-light);}" +
        ".quick-chip.chip-debtors{color:var(--debt-val);border-color:var(--debt-val);background:#f5f3ff;}" +

        // Help & Confirmation
        ".help-fab{position:fixed;bottom:90px;left:24px;width:44px;height:44px;border-radius:50%;background:var(--brand-primary);color:#ffffff;border:none;display:flex;align-items:center;justify-content:center;cursor:pointer;z-index:50;transition:all 0.2s;box-shadow:var(--shadow-md);}" +
        ".help-fab:hover{background:var(--brand-dark);}" +
        ".help-panel{position:fixed;right:-360px;top:0;width:340px;height:100vh;background:#ffffff;z-index:201;transition:right 0.3s cubic-bezier(0.4, 0, 0.2, 1);overflow-y:auto;padding:32px 24px;border-left:1px solid var(--border-rule);box-shadow:-4px 0 15px rgba(0,0,0,0.03);}" +
        ".help-panel.open{right:0;}" +
        ".help-example{padding:12px 16px;margin:8px 0;background:var(--bg-canvas);border:1px solid var(--border-light);border-radius:8px;font-size:13px;font-weight:500;cursor:pointer;transition:all 0.15s;color:var(--text-secondary);}" +
        ".help-example:hover{background:#ffffff;border-color:var(--brand-primary);color:var(--brand-primary);box-shadow:var(--shadow-sm);}" +
        ".confirm-card{background:#ffffff;border:1px solid var(--border-rule);border-radius:10px;padding:20px;margin:12px 0;box-shadow:var(--shadow-md);align-self:flex-start;max-width:85%;}" +
        ".confirm-card .actions{display:flex;gap:10px;margin-top:16px;flex-wrap:wrap;}" +
        ".confirm-btn{background:var(--brand-primary);color:#fff;border:none;padding:10px 18px;border-radius:6px;font-weight:600;font-size:13px;cursor:pointer;transition:background 0.15s;}" +
        ".confirm-btn:hover{background:var(--brand-dark);}" +
        ".change-btn{background:#ffffff;color:var(--text-primary);border:1px solid var(--border-rule);padding:10px 18px;border-radius:6px;font-weight:600;font-size:13px;cursor:pointer;}" +
        ".cancel-btn{background:#ffffff;color:var(--text-secondary);border:1px solid var(--border-rule);padding:10px 18px;border-radius:6px;font-weight:500;font-size:13px;cursor:pointer;transition:background 0.15s;}" +
        ".cancel-btn:hover{background:var(--bg-canvas);color:var(--text-primary);}" +
        ".category-select{padding:8px 12px;border:1px solid var(--border-rule);border-radius:6px;font-size:13px;font-weight:500;margin-top:10px;background:#ffffff;color:var(--text-primary);outline:none;}" +
        ".category-select:focus{border-color:var(--brand-primary);}" +
        ".toast{position:fixed;top:24px;left:50%;transform:translateX(-50%) translateY(-20px);padding:14px 24px;border-radius:8px;color:#fff;font-size:14px;font-weight:500;z-index:9999;opacity:0;transition:all 0.3s cubic-bezier(0.4, 0, 0.2, 1);background:#1f2937;box-shadow:var(--shadow-md);display:flex;align-items:center;gap:10px;}" + 
        ".toast.show{opacity:1;transform:translateX(-50%) translateY(0);}" +
        ".toast.success{background:#1f2937; border-left:4px solid var(--brand-primary);}" + 
        ".fab{position:fixed;bottom:32px;right:32px;width:56px;height:56px;border-radius:28px;background:var(--brand-primary);color:#fff;font-size:24px;border:none;cursor:pointer;display:flex;align-items:center;justify-content:center;text-decoration:none;z-index:50;transition:box-shadow 0.2s;box-shadow:var(--shadow-md);}" + 
        ".fab:hover{box-shadow:0 6px 16px rgba(0,0,0,0.1);}" +

        // Chart & Insights Styles
        ".donut-container{display:flex;flex-direction:column;align-items:center;padding:16px 0;}" +
        ".donut-svg{margin-bottom:20px;}" +
        ".chart-legend{display:flex;justify-content:center;gap:20px;flex-wrap:wrap;}" +
        ".legend-item{display:flex;align-items:center;gap:8px;font-size:13px;font-weight:500;color:var(--text-secondary);}" +
        ".legend-dot{width:12px;height:12px;border-radius:50%;}" + 
        ".filter-bar{display:flex;gap:10px;flex-wrap:wrap;align-items:center;margin-bottom:24px;}" +
        ".filter-bar select,.filter-bar input{padding:10px 14px;border:1px solid var(--border-rule);border-radius:6px;font-size:13px;font-weight:500;background:#ffffff;color:var(--text-primary);}" +
        ".filter-bar select:focus,.filter-bar input:focus{border-color:var(--brand-primary);outline:none;}" +
        ".advice-card{background:#ffffff;border-radius:10px;padding:18px 20px;margin-bottom:16px;border:1px solid var(--border-rule);border-left:4px solid var(--brand-primary);box-shadow:var(--shadow-sm);}" +
        ".advice-card.warning{border-left-color:var(--supply-val);}" +
        ".advice-card.danger{border-left-color:var(--expense-val);}" +
        ".advice-card h4{margin-bottom:6px;color:var(--text-primary);font-size:14px;font-weight:600;}" +
        ".advice-card p{color:var(--text-secondary);font-size:13px;line-height:1.6;font-weight:400;}" +
        ".period-bar{display:flex;gap:8px;flex-wrap:wrap;margin-bottom:24px;}" +
        ".period-btn{padding:10px 20px;border-radius:20px;text-decoration:none;font-size:13px;font-weight:500;border:1px solid var(--border-rule);color:var(--text-secondary);background:#ffffff;transition:all 0.15s;}" + 
        ".period-btn:hover{background:var(--bg-canvas);color:var(--text-primary);}" +
        ".period-btn.active{background:var(--text-primary);color:#ffffff;border-color:var(--text-primary);font-weight:600;}" +
        ".chart-container{background:#ffffff;border-radius:10px;padding:24px;margin-bottom:24px;border:1px solid var(--border-rule);box-shadow:var(--shadow-sm);}" +
        ".chart-container h3{color:var(--text-primary);font-size:14px;font-weight:600;margin-bottom:20px;border-bottom:1px solid var(--border-light);padding-bottom:12px;}" +
        ".carousel{position:relative;overflow:hidden;border-radius:10px;margin-bottom:32px;background:#ffffff;border:1px solid var(--border-rule);box-shadow:var(--shadow-sm);}" +
        ".carousel-track{display:flex;transition:transform 0.5s cubic-bezier(0.4, 0, 0.2, 1);}" +
        ".carousel-slide{min-width:100%;padding:32px 24px;text-align:center;}" +
        ".carousel-slide h3{font-size:13px;color:var(--text-secondary);font-weight:500;margin-bottom:8px;}" +
        ".carousel-slide .big-num{font-size:36px;font-weight:700;color:var(--text-primary);font-feature-settings:'tnum';font-variant-numeric:tabular-nums;}" +
        ".carousel-dots{display:flex;justify-content:center;gap:8px;padding:16px;border-top:1px solid var(--border-light);}" +
        ".carousel-dots span{width:8px;height:8px;border-radius:50%;background:#d1d5db;cursor:pointer;transition:all 0.2s;}" +
        ".carousel-dots span.active{background:var(--brand-primary);width:24px;border-radius:4px;}" + 
        ".app-footer{text-align:center;padding:32px 24px;color:var(--text-muted);font-size:12px;font-weight:400;border-top:1px solid var(--border-rule);background:#ffffff;}" + 
        ".footer-inner{max-width:980px;margin:0 auto;display:flex;flex-direction:column;gap:8px;}" +
        ".footer-sub{font-size:11px;color:var(--text-muted);font-weight:400;}" +
        "@media print{.app-header,.sidebar,.sidebar-overlay,.fab,.help-fab,.no-print,.app-footer{display:none!important;}.device-frame{max-width:100%;margin:0;box-shadow:none;border:none;border-radius:0;}.app-shell{min-height:auto;background:#fff;}}" +
        "";
}