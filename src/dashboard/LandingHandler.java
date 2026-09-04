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
        "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800;900&family=JetBrains+Mono:wght@500;700;800&display=swap' rel='stylesheet'>" +
        "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css'>" +
        "<style>" +
        ":root{" +
        "--bg-canvas:#ffffff;" +
        "--bg-subtle:#f4f4f5;" +
        "--border-rule:#111827;" +
        "--border-light:#e5e7eb;" +
        "--text-primary:#111827;" +
        "--text-secondary:#4b5563;" +
        "--text-muted:#6b7280;" +
        "--brand-primary:#2e7d32;" +
        "--brand-dark:#1b5e20;" +
        "--brand-light:#e8f5e9;" +
        "}" +
        "*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',-apple-system,BlinkMacSystemFont,sans-serif;}" +
        "body{background:var(--bg-canvas);color:var(--text-primary);-webkit-font-smoothing:antialiased;line-height:1.45;}" +
        
        // Navbar
        ".navbar{background:#ffffff;padding:16px 28px;display:flex;justify-content:space-between;align-items:center;border-bottom:2px solid var(--border-rule);position:sticky;top:0;z-index:100;}" +
        ".nav-brand{display:flex;align-items:center;gap:12px;text-decoration:none;}" +
        ".nav-logo{display:flex;align-items:center;justify-content:center;}" +
        ".navbar h1{color:var(--text-primary);font-size:18px;font-weight:900;letter-spacing:0.8px;text-transform:uppercase;}" +
        ".nav-actions{display:flex;align-items:center;gap:10px;}" +
        ".nav-link{text-decoration:none;padding:8px 18px;border-radius:2px;font-weight:800;font-size:12px;text-transform:uppercase;letter-spacing:0.5px;transition:all 0.1s;border:1.5px solid var(--border-rule);}" +
        ".nav-login{color:var(--text-primary);background:#ffffff;}" +
        ".nav-login:hover{background:var(--bg-subtle);}" +
        ".nav-signup{background:var(--brand-primary);color:#ffffff;border-color:var(--border-rule);}" +
        ".nav-signup:hover{background:var(--brand-dark);}" +

        // Hero (Swiss Poster Structure)
        ".hero{background:#ffffff;color:var(--text-primary);padding:80px 24px 70px;text-align:left;max-width:980px;margin:0 auto;border-bottom:2px solid var(--border-rule);}" +
        ".hero-tag{display:inline-block;background:var(--text-primary);color:#fff;padding:4px 10px;font-size:11px;font-weight:900;letter-spacing:1px;text-transform:uppercase;margin-bottom:20px;}" +
        ".hero h2{font-size:52px;font-weight:900;max-width:880px;margin-bottom:20px;line-height:1.08;letter-spacing:-1.5px;text-transform:uppercase;}" +
        ".hero h2 span{color:var(--brand-primary);}" +
        ".hero p{font-size:16px;color:var(--text-secondary);max-width:640px;margin-bottom:32px;line-height:1.6;font-weight:500;}" +
        ".hero-cta-box{display:flex;gap:12px;flex-wrap:wrap;align-items:center;}" +
        ".hero-cta{display:inline-flex;align-items:center;gap:8px;background:var(--brand-primary);color:#fff;padding:12px 28px;border-radius:2px;text-decoration:none;font-size:13px;font-weight:800;letter-spacing:0.5px;text-transform:uppercase;border:1.5px solid var(--border-rule);transition:all 0.1s;}" +
        ".hero-cta:hover{background:var(--brand-dark);transform:translate(-1px,-1px);}" +
        ".hero-sec{display:inline-flex;align-items:center;gap:6px;background:#ffffff;color:var(--text-primary);border:1.5px solid var(--border-rule);padding:12px 24px;border-radius:2px;text-decoration:none;font-size:13px;font-weight:800;letter-spacing:0.5px;text-transform:uppercase;}" +
        ".hero-sec:hover{background:var(--bg-subtle);}" +
        ".hero-sub{margin-top:20px;color:var(--text-muted);font-size:11px;font-weight:700;letter-spacing:0.5px;text-transform:uppercase;}" +
        ".sdg{display:flex;gap:8px;margin-top:24px;flex-wrap:wrap;}" +
        ".sdg-badge{padding:3px 8px;border-radius:2px;font-size:10px;font-weight:800;letter-spacing:0.5px;text-transform:uppercase;border:1px solid var(--border-rule);}" +
        ".sdg8{background:#dcfce7;color:#166534;}" +
        ".sdg9{background:#ffedd5;color:#9a3412;}" +

        // Demo Card
        ".demo-section{max-width:980px;margin:0 auto;padding:32px 24px;border-bottom:2px solid var(--border-rule);}" +
        ".demo-card{background:#ffffff;border:2px solid var(--border-rule);border-radius:2px;}" +
        ".demo-header{background:var(--bg-subtle);border-bottom:1.5px solid var(--border-rule);padding:12px 18px;display:flex;align-items:center;justify-content:space-between;}" +
        ".demo-title{font-size:11px;font-weight:900;color:var(--text-primary);text-transform:uppercase;letter-spacing:1px;}" +
        ".demo-body{padding:20px;background:#ffffff;display:flex;flex-direction:column;gap:10px;}" +
        ".msg-bubble{padding:12px 16px;border-radius:2px;font-size:13px;max-width:85%;line-height:1.4;border:1.5px solid var(--border-rule);}" +
        ".user-bubble{background:var(--brand-primary);color:#ffffff;align-self:flex-end;}" +
        ".sys-bubble{background:var(--bg-subtle);color:var(--text-primary);align-self:flex-start;}" +
        ".sys-bubble strong{color:var(--brand-primary);}" +

        // How it works (Grid Section)
        ".how{padding:60px 24px;background:#ffffff;max-width:980px;margin:0 auto;border-bottom:2px solid var(--border-rule);}" +
        ".section-tag{font-size:11px;font-weight:900;color:var(--text-primary);text-transform:uppercase;letter-spacing:1px;margin-bottom:8px;border-bottom:1.5px solid var(--border-rule);padding-bottom:4px;display:inline-block;}" +
        ".how h3{font-size:32px;font-weight:900;letter-spacing:-1px;color:var(--text-primary);margin-bottom:8px;text-transform:uppercase;}" +
        ".how .sub{color:var(--text-secondary);margin-bottom:36px;font-size:14px;font-weight:500;}" +
        ".steps{display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:0;border:1.5px solid var(--border-rule);}" +
        ".step-card{background:#ffffff;padding:24px;border-right:1.5px solid var(--border-rule);}" +
        ".step-card:last-child{border-right:none;}" +
        ".step-badge{width:32px;height:32px;background:var(--text-primary);color:#ffffff;display:flex;align-items:center;justify-content:center;font-size:14px;font-weight:900;margin-bottom:14px;border-radius:0;}" +
        ".step-card h4{font-size:14px;font-weight:900;margin-bottom:6px;color:var(--text-primary);text-transform:uppercase;letter-spacing:0.5px;}" +
        ".step-card p{color:var(--text-secondary);font-size:12px;line-height:1.6;font-weight:500;}" +

        // Features (Swiss Matrix)
        ".features{padding:60px 24px;background:var(--bg-subtle);border-bottom:2px solid var(--border-rule);}" +
        ".features-inner{max-width:980px;margin:0 auto;}" +
        ".features h3{font-size:32px;font-weight:900;letter-spacing:-1px;color:var(--text-primary);margin-bottom:32px;text-transform:uppercase;}" +
        ".feature-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(280px,1fr));gap:12px;}" +
        ".feature-card{background:#ffffff;padding:20px;border:1.5px solid var(--border-rule);border-radius:2px;}" +
        ".feature-icon{width:36px;height:36px;background:var(--brand-light);color:var(--brand-primary);border:1.5px solid var(--border-rule);display:flex;align-items:center;justify-content:center;font-size:18px;margin-bottom:12px;border-radius:0;}" +
        ".feature-card h4{font-size:13px;font-weight:900;margin-bottom:4px;color:var(--text-primary);text-transform:uppercase;letter-spacing:0.5px;}" +
        ".feature-card p{color:var(--text-secondary);font-size:12px;line-height:1.5;font-weight:500;}" +

        // CTA
        ".cta{padding:60px 24px;background:#ffffff;text-align:left;max-width:980px;margin:0 auto;border-bottom:2px solid var(--border-rule);}" +
        ".cta h3{font-size:36px;font-weight:900;letter-spacing:-1px;margin-bottom:8px;text-transform:uppercase;}" +
        ".cta p{font-size:14px;color:var(--text-secondary);max-width:560px;margin-bottom:24px;line-height:1.5;font-weight:500;}" +
        ".cta a{display:inline-flex;align-items:center;gap:8px;background:var(--brand-primary);color:#ffffff;padding:12px 28px;border-radius:2px;text-decoration:none;font-size:13px;font-weight:800;letter-spacing:0.5px;text-transform:uppercase;border:1.5px solid var(--border-rule);}" +
        ".cta a:hover{background:var(--brand-dark);}" +

        // Footer
        ".footer{padding:28px 24px;background:#ffffff;text-align:center;color:var(--text-muted);font-size:11px;font-weight:700;letter-spacing:0.8px;text-transform:uppercase;}" +
        ".footer a{color:var(--brand-primary);text-decoration:none;font-weight:800;}" +

        // Mobile Responsive
        "@media(max-width:640px){.hero h2{font-size:36px;}.hero{padding:48px 16px;}.navbar{padding:12px 16px;}.step-card{border-right:none;border-bottom:1.5px solid var(--border-rule);}}" +
        "</style></head><body>" +

        // Navbar
        "<nav class='navbar'>" +
        "<a href='/' class='nav-brand'>" +
        "<div class='nav-logo'><img src='" + HtmlTemplates.LOGO_DATA + "' style='width:24px;height:24px;' alt='Logo'></div>" +
        "<h1>SmartLedger</h1></a>" +
        "<div class='nav-actions'>" +
        "<a href='/auth/login' class='nav-link nav-login'>Login</a>" +
        "<a href='/auth/signup' class='nav-link nav-signup'>Register</a>" +
        "</div></nav>" +

        // Hero
        "<header class='hero'>" +
        "<div class='hero-tag'>COS 202 CLASS PROJECT &middot; SMART FINANCIAL ACCOUNTING</div>" +
        "<h2>Type what you sold.<br><span>We balance the ledger.</span></h2>" +
        "<p>SmartLedger empowers small business owners and merchants to record sales, expenses, and debtor balances through natural language conversations. Zero manual bookkeeping friction.</p>" +
        "<div class='hero-cta-box'>" +
        "<a href='/auth/signup' class='hero-cta'><i class='ti ti-arrow-right'></i> Start Recording Free</a>" +
        "<a href='/auth/login' class='hero-sec'><i class='ti ti-login'></i> Existing Merchant</a>" +
        "</div>" +
        "<p class='hero-sub'>Zero installation required &middot; Works in any modern browser</p>" +
        "<div class='sdg'><span class='sdg-badge sdg8'>UN SDG 8 &middot; Decent Work</span><span class='sdg-badge sdg9'>UN SDG 9 &middot; Innovation</span></div>" +
        "</header>" +

        // Interactive Demo Preview
        "<div class='demo-section'>" +
        "<div class='demo-card'>" +
        "<div class='demo-header'>" +
        "<div class='demo-title'>Live Conversational Parsing Stream</div>" +
        "<div><span class='sdg-badge' style='background:#dcfce7;color:#166534;'>Real-Time NLP</span></div>" +
        "</div>" +
        "<div class='demo-body'>" +
        "<div class='msg-bubble user-bubble'>Sold 5 bags of rice for &#8358;100,000</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-check' style='color:#2e7d32;'></i> <strong>SALE</strong> recorded &middot; &#8358;100,000.00 (5 bags of rice)</div>" +
        "<div class='msg-bubble user-bubble'>Oga Musa owes me &#8358;12,000</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-scale' style='color:#6a1b9a;'></i> <strong>DEBT</strong> tracked &middot; &#8358;12,000.00 (Counterparty: Oga Musa)</div>" +
        "<div class='msg-bubble user-bubble'>Paid &#8358;5,000 for transport</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-receipt' style='color:#c62828;'></i> <strong>EXPENSE</strong> logged &middot; &#8358;5,000.00</div>" +
        "</div></div></div>" +

        // How it works
        "<section class='how'>" +
        "<div class='section-tag'>Architecture Workflow</div>" +
        "<h3>How SmartLedger Operates</h3>" +
        "<p class='sub'>Three streamlined steps to continuous financial accounting.</p>" +
        "<div class='steps'>" +
        "<div class='step-card'><div class='step-badge'>01</div><h4>Natural Input</h4><p>Type \"Sold 5 bags for &#8358;100k\" or \"Mama Tope paid &#8358;20,000\" in conversational syntax.</p></div>" +
        "<div class='step-card'><div class='step-badge'>02</div><h4>Rule-Based NLP</h4><p>Classification engine resolves sales, expenses, supplies, debts, and payments with typo tolerance.</p></div>" +
        "<div class='step-card'><div class='step-badge'>03</div><h4>Ledger Analytics</h4><p>Maintains live profit margin health, recording streaks, debtor netting, and printable audits.</p></div>" +
        "</div></section>" +

        // Features
        "<section class='features'>" +
        "<div class='features-inner'>" +
        "<div class='section-tag'>Core Capabilities</div>" +
        "<h3>Built for Merchant Precision</h3>" +
        "<div class='feature-grid'>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-messages'></i></div><h4>Conversational Input</h4><p>Understands regional trading terms, shorthand numbers, and currency representations.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-chart-bar'></i></div><h4>Swiss Grid Dashboards</h4><p>High-contrast visual charts showing revenue velocity, outflows, and margin health.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-scale'></i></div><h4>Debtor Balance Netting</h4><p>Tracks credit positions, partial payments, and debtor balances automatically.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-shield-check'></i></div><h4>Interactive Verification</h4><p>Confirmation cards permit single-tap category overrides prior to persistence.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-device-mobile'></i></div><h4>Universal Browser Support</h4><p>Lightweight zero-framework architecture designed for instant mobile load times.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-lock'></i></div><h4>Isolated User DAO</h4><p>Salted SHA-256 password hashing with segregated merchant session tokens.</p></div>" +
        "</div></div></section>" +

        // CTA
        "<section class='cta'>" +
        "<h3>Eliminate Accounting Discrepancies</h3>" +
        "<p>Transition from manual paper notebooks to structured conversational bookkeeping.</p>" +
        "<a href='/auth/signup'><i class='ti ti-user-plus'></i> Register Merchant Workspace</a>" +
        "</section>" +

        // Footer
        "<footer class='footer'>" +
        "<p>SmartLedger System &#169; 2026 &middot; COS 202 Group 22 Class Project</p>" +
        "<p style='margin-top:8px;'><a href='/auth/login'>Login</a> &middot; <a href='/auth/signup'>Register</a> &middot; <a href='/'>Home</a></p>" +
        "</footer>" +

        "</body></html>";
    }
}