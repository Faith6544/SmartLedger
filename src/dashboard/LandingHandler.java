package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class LandingHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        // Only handle exact root path - let other handlers handle their paths
        if (!path.equals("/")) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        String html = buildLanding();
        byte[] bytes = html.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private String buildLanding() {
        return "<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'>" +
        "<meta name='viewport' content='width=device-width,initial-scale=1.0'>" +
        "<meta property='og:title' content='SmartLedger — Financial Bookkeeping for Modern Traders'>" +
        "<meta property='og:description' content='Record-keeping made effortless. Chat your transactions in plain language and view them on a live financial dashboard.'>" +
        "<meta property='og:image' content='https://raw.githubusercontent.com/Faith6544/SmartLedger/main/logo.png'>" +
        "<meta property='og:url' content='https://smartledger-m28i.onrender.com'>" +
        "<meta property='og:type' content='website'>" +
        "<title>SmartLedger - Financial Bookkeeping for Modern Traders</title>" +
        "<link rel='icon' type='image/png' href='" + HtmlTemplates.LOGO_DATA + "'>" +
        "<link rel='shortcut icon' type='image/png' href='" + HtmlTemplates.LOGO_DATA + "'>" +
        "<link rel='preconnect' href='https://fonts.googleapis.com'>" +
        "<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>" +
        "<link href='https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap' rel='stylesheet'>" +
        "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css'>" +
        "<style>" +
        ":root{" +
        "--bg-canvas:#ffffff;" +
        "--bg-subtle:#f8fafc;" +
        "--border-default:#e2e8f0;" +
        "--text-primary:#0f172a;" +
        "--text-secondary:#475569;" +
        "--text-muted:#94a3b8;" +
        "--brand-primary:#059669;" +
        "--brand-dark:#047857;" +
        "--brand-light:#ecfdf5;" +
        "}" +
        "*{margin:0;padding:0;box-sizing:border-box;font-family:'Plus Jakarta Sans',-apple-system,BlinkMacSystemFont,sans-serif;}" +
        "body{background:var(--bg-canvas);color:var(--text-primary);-webkit-font-smoothing:antialiased;line-height:1.5;}" +
        
        // Navbar
        ".navbar{background:rgba(255,255,255,0.9);backdrop-filter:blur(12px);padding:14px 28px;display:flex;justify-content:space-between;align-items:center;border-bottom:1px solid var(--border-default);position:sticky;top:0;z-index:100;}" +
        ".nav-brand{display:flex;align-items:center;gap:10px;text-decoration:none;}" +
        ".nav-logo{width:34px;height:34px;background:#ffffff;border:1px solid var(--border-default);border-radius:8px;display:flex;align-items:center;justify-content:center;box-shadow:0 1px 2px rgba(0,0,0,0.05);}" +
        ".navbar h1{color:var(--text-primary);font-size:18px;font-weight:700;letter-spacing:-0.3px;}" +
        ".nav-actions{display:flex;align-items:center;gap:12px;}" +
        ".nav-link{text-decoration:none;padding:8px 18px;border-radius:9999px;font-weight:600;font-size:13px;transition:all 0.15s;}" +
        ".nav-login{color:var(--text-secondary);border:1px solid var(--border-default);}" +
        ".nav-login:hover{background:#f1f5f9;color:var(--text-primary);}" +
        ".nav-signup{background:var(--brand-primary);color:#ffffff;box-shadow:0 1px 3px rgba(5,150,105,0.25);}" +
        ".nav-signup:hover{background:var(--brand-dark);}" +

        // Hero
        ".hero{background:radial-gradient(circle at 50% 0%,#0f172a 0%,#090d16 100%);color:#fff;padding:90px 24px 80px;text-align:center;position:relative;overflow:hidden;}" +
        ".hero-pill{display:inline-flex;align-items:center;gap:8px;background:rgba(255,255,255,0.08);border:1px solid rgba(255,255,255,0.15);padding:6px 14px;border-radius:9999px;font-size:12px;font-weight:600;color:#34d399;margin-bottom:24px;}" +
        ".hero h2{font-size:48px;font-weight:800;max-width:760px;margin:0 auto 20px;line-height:1.15;letter-spacing:-1px;}" +
        ".hero h2 span{background:linear-gradient(135deg,#34d399,#10b981);-webkit-background-clip:text;-webkit-text-fill-color:transparent;}" +
        ".hero p{font-size:17px;color:#94a3b8;max-width:580px;margin:0 auto 36px;line-height:1.6;}" +
        ".hero-cta-box{display:flex;justify-content:center;gap:14px;flex-wrap:wrap;align-items:center;}" +
        ".hero-cta{display:inline-flex;align-items:center;gap:8px;background:var(--brand-primary);color:#fff;padding:14px 32px;border-radius:9999px;text-decoration:none;font-size:15px;font-weight:700;transition:all 0.15s;box-shadow:0 4px 20px rgba(5,150,105,0.35);}" +
        ".hero-cta:hover{background:var(--brand-dark);transform:translateY(-1px);}" +
        ".hero-sec{display:inline-flex;align-items:center;gap:6px;background:rgba(255,255,255,0.08);color:#ffffff;border:1px solid rgba(255,255,255,0.15);padding:14px 24px;border-radius:9999px;text-decoration:none;font-size:14px;font-weight:600;}" +
        ".hero-sec:hover{background:rgba(255,255,255,0.15);}" +
        ".hero-sub{margin-top:20px;color:#64748b;font-size:13px;}" +
        ".sdg{display:flex;justify-content:center;gap:10px;margin-top:28px;flex-wrap:wrap;}" +
        ".sdg-badge{padding:4px 12px;border-radius:9999px;font-size:11px;font-weight:600;}" +
        ".sdg8{background:rgba(147,19,42,0.8);color:#fff;border:1px solid rgba(255,255,255,0.2);}" +
        ".sdg9{background:rgba(243,109,37,0.8);color:#fff;border:1px solid rgba(255,255,255,0.2);}" +

        // Interactive Preview Card
        ".demo-section{padding:0 20px;margin-top:-40px;position:relative;z-index:2;max-width:760px;margin-left:auto;margin-right:auto;}" +
        ".demo-card{background:#ffffff;border:1px solid var(--border-default);border-radius:18px;box-shadow:0 20px 40px -15px rgba(15,23,42,0.15);overflow:hidden;}" +
        ".demo-header{background:#f8fafc;border-bottom:1px solid var(--border-default);padding:12px 18px;display:flex;align-items:center;justify-content:space-between;}" +
        ".demo-dots{display:flex;gap:6px;}" +
        ".demo-dot{width:10px;height:10px;border-radius:50%;background:#e2e8f0;}" +
        ".demo-title{font-size:12px;font-weight:600;color:var(--text-secondary);}" +
        ".demo-body{padding:20px;background:#ffffff;display:flex;flex-direction:column;gap:12px;}" +
        ".msg-bubble{padding:12px 16px;border-radius:12px;font-size:13px;max-width:85%;line-height:1.4;box-shadow:0 1px 2px rgba(0,0,0,0.04);}" +
        ".user-bubble{background:var(--brand-primary);color:#ffffff;align-self:flex-end;border-bottom-right-radius:2px;}" +
        ".sys-bubble{background:#f8fafc;border:1px solid var(--border-default);color:var(--text-primary);align-self:flex-start;border-bottom-left-radius:2px;}" +
        ".sys-bubble strong{color:var(--brand-dark);}" +

        // How it works
        ".how{padding:80px 24px;background:#ffffff;text-align:center;max-width:960px;margin:0 auto;}" +
        ".section-tag{font-size:11px;font-weight:700;color:var(--brand-primary);text-transform:uppercase;letter-spacing:1px;margin-bottom:8px;}" +
        ".how h3{font-size:32px;font-weight:800;letter-spacing:-0.5px;color:var(--text-primary);margin-bottom:8px;}" +
        ".how .sub{color:var(--text-secondary);margin-bottom:48px;font-size:15px;}" +
        ".steps{display:grid;grid-template-columns:repeat(auto-fit,minmax(240px,1fr));gap:24px;text-align:left;}" +
        ".step-card{background:#f8fafc;border:1px solid var(--border-default);border-radius:14px;padding:24px;transition:all 0.2s;}" +
        ".step-card:hover{border-color:#cbd5e1;transform:translateY(-2px);box-shadow:0 6px 18px rgba(0,0,0,0.04);}" +
        ".step-badge{width:36px;height:36px;background:var(--brand-light);color:var(--brand-dark);border:1px solid #a7f3d0;border-radius:10px;display:flex;align-items:center;justify-content:center;font-size:15px;font-weight:800;margin-bottom:16px;}" +
        ".step-card h4{font-size:16px;font-weight:700;margin-bottom:8px;color:var(--text-primary);}" +
        ".step-card p{color:var(--text-secondary);font-size:13px;line-height:1.6;}" +

        // Features
        ".features{padding:80px 24px;background:#f8fafc;border-top:1px solid var(--border-default);border-bottom:1px solid var(--border-default);text-align:center;}" +
        ".features-inner{max-width:960px;margin:0 auto;}" +
        ".features h3{font-size:32px;font-weight:800;letter-spacing:-0.5px;color:var(--text-primary);margin-bottom:40px;}" +
        ".feature-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:20px;text-align:left;}" +
        ".feature-card{background:#ffffff;border-radius:14px;padding:24px;border:1px solid var(--border-default);box-shadow:0 1px 3px rgba(0,0,0,0.04);transition:all 0.15s;}" +
        ".feature-card:hover{border-color:#cbd5e1;transform:translateY(-2px);box-shadow:0 10px 20px -5px rgba(0,0,0,0.06);}" +
        ".feature-icon{width:42px;height:42px;background:var(--brand-light);color:var(--brand-dark);border:1px solid #a7f3d0;border-radius:10px;display:flex;align-items:center;justify-content:center;font-size:20px;margin-bottom:16px;}" +
        ".feature-card h4{font-size:15px;font-weight:700;margin-bottom:6px;color:var(--text-primary);}" +
        ".feature-card p{color:var(--text-secondary);font-size:13px;line-height:1.5;}" +

        // CTA
        ".cta{padding:80px 24px;background:#0f172a;text-align:center;color:#ffffff;}" +
        ".cta h3{font-size:32px;font-weight:800;letter-spacing:-0.5px;margin-bottom:12px;}" +
        ".cta p{font-size:15px;color:#94a3b8;max-width:500px;margin:0 auto 30px;line-height:1.6;}" +
        ".cta a{display:inline-flex;align-items:center;gap:8px;background:var(--brand-primary);color:#ffffff;padding:14px 36px;border-radius:9999px;text-decoration:none;font-size:15px;font-weight:700;transition:all 0.15s;}" +
        ".cta a:hover{background:var(--brand-dark);transform:translateY(-1px);}" +

        // Footer
        ".footer{padding:30px 24px;background:#ffffff;border-top:1px solid var(--border-default);text-align:center;color:var(--text-muted);font-size:12px;}" +
        ".footer a{color:var(--brand-dark);text-decoration:none;font-weight:600;}" +

        // Mobile Responsive
        "@media(max-width:640px){.hero h2{font-size:32px;}.hero{padding:60px 16px 60px;}.navbar{padding:12px 16px;}.nav-link{padding:6px 14px;font-size:12px;}.demo-section{margin-top:-20px;}}" +
        "</style></head><body>" +

        // Navbar
        "<nav class='navbar'>" +
        "<a href='/' class='nav-brand'>" +
        "<div class='nav-logo'><img src='" + HtmlTemplates.LOGO_DATA + "' style='width:22px;height:22px;' alt='Logo'></div>" +
        "<h1>SmartLedger</h1></a>" +
        "<div class='nav-actions'>" +
        "<a href='/auth/login' class='nav-link nav-login'>Login</a>" +
        "<a href='/auth/signup' class='nav-link nav-signup'>Get Started</a>" +
        "</div></nav>" +

        // Hero
        "<header class='hero'>" +
        "<div class='hero-pill'><i class='ti ti-sparkles'></i> Chat-Based Financial Accounting</div>" +
        "<h2>Type what you sold.<br><span>We handle the rest.</span></h2>" +
        "<p>SmartLedger lets small business owners and merchants record sales, expenses, and debts by typing naturally — like sending a message. No complicated spreadsheets. No accounting jargon.</p>" +
        "<div class='hero-cta-box'>" +
        "<a href='/auth/signup' class='hero-cta'><i class='ti ti-arrow-right'></i> Start Recording Free</a>" +
        "<a href='/auth/login' class='hero-sec'><i class='ti ti-login'></i> Existing Merchant</a>" +
        "</div>" +
        "<p class='hero-sub'>Zero installation required &middot; Works instantly in any browser</p>" +
        "<div class='sdg'><span class='sdg-badge sdg8'>UN SDG 8 &middot; Decent Work & Economic Growth</span><span class='sdg-badge sdg9'>UN SDG 9 &middot; Industry & Innovation</span></div>" +
        "</header>" +

        // Interactive Demo Preview
        "<div class='demo-section'>" +
        "<div class='demo-card'>" +
        "<div class='demo-header'>" +
        "<div class='demo-dots'><div class='demo-dot'></div><div class='demo-dot'></div><div class='demo-dot'></div></div>" +
        "<div class='demo-title'>Live Conversational Ledger Preview</div>" +
        "<div><span class='sdg-badge' style='background:#dcfce7;color:#15803d;'>Real-Time NLP</span></div>" +
        "</div>" +
        "<div class='demo-body'>" +
        "<div class='msg-bubble user-bubble'>Sold 5 bags of rice for &#8358;100,000</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-check' style='color:#10b981;'></i> <strong>SALE</strong> recorded &middot; &#8358;100,000.00 (5 bags of rice)</div>" +
        "<div class='msg-bubble user-bubble'>Oga Musa owes me &#8358;12,000</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-scale' style='color:#6366f1;'></i> <strong>DEBT</strong> tracked &middot; &#8358;12,000.00 (Counterparty: Oga Musa)</div>" +
        "<div class='msg-bubble user-bubble'>Paid &#8358;5,000 for transport</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-receipt' style='color:#f43f5e;'></i> <strong>EXPENSE</strong> logged &middot; &#8358;5,000.00</div>" +
        "</div></div></div>" +

        // How it works
        "<section class='how'>" +
        "<div class='section-tag'>Simple Workflow</div>" +
        "<h3>How SmartLedger Works</h3>" +
        "<p class='sub'>Three effortless steps to complete financial clarity.</p>" +
        "<div class='steps'>" +
        "<div class='step-card'><div class='step-badge'>1</div><h4>Chat Naturally</h4><p>Type \"Sold 5 bags for &#8358;100k\" or \"Mama Tope paid &#8358;20,000\" exactly as you speak.</p></div>" +
        "<div class='step-card'><div class='step-badge'>2</div><h4>Automatic Categorization</h4><p>Our NLP parser classifies sales, expenses, supplies, debts, and payments with typo tolerance.</p></div>" +
        "<div class='step-card'><div class='step-badge'>3</div><h4>Instant Analytics</h4><p>Track live profit margins, daily revenue streaks, debtor balances, and export PDF reports.</p></div>" +
        "</div></section>" +

        // Features
        "<section class='features'>" +
        "<div class='features-inner'>" +
        "<div class='section-tag'>Capabilities</div>" +
        "<h3>Tailored for Emerging Market Traders</h3>" +
        "<div class='feature-grid'>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-messages'></i></div><h4>Conversational Input</h4><p>Understands Pidgin, shorthand, numbers, and unit words effortlessly.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-chart-bar'></i></div><h4>Live Dashboards</h4><p>High-contrast visual charts showing real-time revenue, outflows, and margin health.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-scale'></i></div><h4>Debtor Balance Netting</h4><p>Track credits, partial payments, and net debtor positions automatically.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-shield-check'></i></div><h4>Smart Confirmations</h4><p>Interactive confirmation cards allow instant one-tap category edits before persisting.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-device-mobile'></i></div><h4>Mobile Optimized</h4><p>Fast, lightweight, and responsive across all smartphones and network tiers.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-lock'></i></div><h4>Secure & Private</h4><p>Salted SHA-256 encrypted authentication and isolated merchant sessions.</p></div>" +
        "</div></div></section>" +

        // CTA
        "<section class='cta'>" +
        "<h3>Stop Losing Track of Your Money</h3>" +
        "<p>Join merchants who are switching from messy paper notebooks to conversational bookkeeping.</p>" +
        "<a href='/auth/signup'><i class='ti ti-user-plus'></i> Create Free Merchant Account</a>" +
        "</section>" +

        // Footer
        "<footer class='footer'>" +
        "<p>SmartLedger &#169; 2026 &middot; COS 202 Group 22 Class Project</p>" +
        "<p style='margin-top:8px;'><a href='/auth/login'>Login</a> &middot; <a href='/auth/signup'>Register</a> &middot; <a href='/'>Home</a></p>" +
        "</footer>" +

        "</body></html>";
    }
}