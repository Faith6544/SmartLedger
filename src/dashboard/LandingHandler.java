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
        "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap' rel='stylesheet'>" +
        "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css'>" +
        "<style>" +
        ":root{" +
        "--bg-canvas:#f9fafb;" +
        "--bg-surface:#ffffff;" +
        "--bg-subtle:#f3f4f6;" +
        "--border-rule:#e5e7eb;" +
        "--border-light:#f3f4f6;" +
        "--text-primary:#1f2937;" +
        "--text-secondary:#4b5563;" +
        "--text-muted:#9ca3af;" +
        "--brand-primary:#166534;" +
        "--brand-dark:#14532d;" +
        "--brand-light:#f0fdf4;" +
        "--shadow-sm:0 1px 2px 0 rgba(0,0,0,0.05);" +
        "--shadow-md:0 4px 6px -1px rgba(0,0,0,0.05),0 2px 4px -1px rgba(0,0,0,0.03);" +
        "}" +
        "*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',-apple-system,BlinkMacSystemFont,sans-serif;}" +
        "body{background:var(--bg-canvas);color:var(--text-primary);-webkit-font-smoothing:antialiased;line-height:1.5;}" +
        
        // Navbar
        ".navbar{background:#ffffff;padding:16px 28px;display:flex;justify-content:space-between;align-items:center;border-bottom:1px solid var(--border-rule);position:sticky;top:0;z-index:100;box-shadow:var(--shadow-sm);}" +
        ".nav-brand{display:flex;align-items:center;gap:12px;text-decoration:none;transition:transform 0.15s;}" +
        ".nav-brand:hover{opacity:0.9;}" +
        ".nav-logo{display:flex;align-items:center;justify-content:center;}" +
        ".navbar h1{color:var(--text-primary);font-size:18px;font-weight:700;}" +
        ".nav-actions{display:flex;align-items:center;gap:12px;}" +
        ".nav-link{text-decoration:none;padding:10px 20px;border-radius:6px;font-weight:600;font-size:13px;transition:all 0.15s;}" +
        ".nav-login{color:var(--text-secondary);background:transparent;}" +
        ".nav-login:hover{color:var(--text-primary);background:var(--bg-subtle);}" +
        ".nav-signup{background:var(--brand-primary);color:#ffffff;box-shadow:var(--shadow-sm);}" +
        ".nav-signup:hover{background:var(--brand-dark);transform:translateY(-1px);box-shadow:var(--shadow-md);}" +

        // Hero
        ".hero{background:#ffffff;color:var(--text-primary);padding:80px 24px 70px;text-align:left;max-width:980px;margin:0 auto;border-bottom:1px solid var(--border-rule);}" +
        ".hero-tag{display:inline-block;background:var(--brand-light);color:var(--brand-primary);padding:6px 12px;border-radius:20px;font-size:11px;font-weight:700;letter-spacing:0.5px;text-transform:uppercase;margin-bottom:24px;border:1px solid #dcfce7;}" +
        ".hero h2{font-size:48px;font-weight:800;max-width:880px;margin-bottom:20px;line-height:1.1;letter-spacing:-1px;}" +
        ".hero h2 span{color:var(--brand-primary);}" +
        ".hero p{font-size:16px;color:var(--text-secondary);max-width:640px;margin-bottom:32px;line-height:1.6;font-weight:400;}" +
        ".hero-cta-box{display:flex;gap:16px;flex-wrap:wrap;align-items:center;}" +
        ".hero-cta{display:inline-flex;align-items:center;gap:8px;background:var(--brand-primary);color:#fff;padding:14px 32px;border-radius:6px;text-decoration:none;font-size:14px;font-weight:600;transition:all 0.15s;box-shadow:var(--shadow-sm);}" +
        ".hero-cta:hover{background:var(--brand-dark);transform:translateY(-1px);box-shadow:var(--shadow-md);}" +
        ".hero-sec{display:inline-flex;align-items:center;gap:8px;background:#ffffff;color:var(--text-primary);border:1px solid var(--border-rule);padding:14px 32px;border-radius:6px;text-decoration:none;font-size:14px;font-weight:600;transition:all 0.15s;box-shadow:var(--shadow-sm);}" +
        ".hero-sec:hover{background:var(--bg-subtle);}" +
        ".hero-sub{margin-top:24px;color:var(--text-muted);font-size:12px;font-weight:500;}" +
        ".sdg{display:flex;gap:8px;margin-top:24px;flex-wrap:wrap;}" +
        ".sdg-badge{padding:4px 10px;border-radius:4px;font-size:11px;font-weight:600;letter-spacing:0.3px;text-transform:uppercase;}" +
        ".sdg8{background:#f0fdf4;color:#166534;border:1px solid #dcfce7;}" +
        ".sdg9{background:#fff7ed;color:#9a3412;border:1px solid #ffedd5;}" +

        // Demo Card
        ".demo-section{max-width:980px;margin:0 auto;padding:48px 24px;border-bottom:1px solid var(--border-rule);background:#ffffff;}" +
        ".demo-card{background:var(--bg-surface);border:1px solid var(--border-rule);border-radius:12px;box-shadow:var(--shadow-md);overflow:hidden;}" +
        ".demo-header{background:var(--bg-canvas);border-bottom:1px solid var(--border-rule);padding:16px 24px;display:flex;align-items:center;justify-content:space-between;}" +
        ".demo-title{font-size:12px;font-weight:600;color:var(--text-secondary);text-transform:uppercase;letter-spacing:0.5px;}" +
        ".demo-body{padding:24px;background:#ffffff;display:flex;flex-direction:column;gap:12px;}" +
        ".msg-bubble{padding:14px 18px;border-radius:12px;font-size:14px;max-width:85%;line-height:1.5;box-shadow:var(--shadow-sm);}" +
        ".user-bubble{background:var(--brand-primary);color:#ffffff;align-self:flex-end;border-bottom-right-radius:4px;}" +
        ".sys-bubble{background:var(--bg-canvas);color:var(--text-primary);align-self:flex-start;border-bottom-left-radius:4px;border:1px solid var(--border-rule);}" +
        ".sys-bubble strong{color:var(--brand-primary);font-weight:600;}" +

        // How it works
        ".how{padding:80px 24px;background:var(--bg-canvas);max-width:980px;margin:0 auto;border-bottom:1px solid var(--border-rule);}" +
        ".section-tag{font-size:12px;font-weight:700;color:var(--brand-primary);text-transform:uppercase;letter-spacing:0.5px;margin-bottom:12px;display:inline-block;}" +
        ".how h3{font-size:32px;font-weight:800;letter-spacing:-0.5px;color:var(--text-primary);margin-bottom:12px;}" +
        ".how .sub{color:var(--text-secondary);margin-bottom:48px;font-size:16px;font-weight:400;max-width:600px;}" +
        ".steps{display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:24px;}" +
        ".step-card{background:#ffffff;padding:32px;border:1px solid var(--border-rule);border-radius:12px;box-shadow:var(--shadow-sm);transition:transform 0.2s;}" +
        ".step-card:hover{box-shadow:var(--shadow-md);}" +
        ".step-badge{width:40px;height:40px;background:var(--brand-light);color:var(--brand-primary);display:flex;align-items:center;justify-content:center;font-size:14px;font-weight:700;margin-bottom:20px;border-radius:8px;border:1px solid #dcfce7;}" +
        ".step-card h4{font-size:16px;font-weight:700;margin-bottom:8px;color:var(--text-primary);}" +
        ".step-card p{color:var(--text-secondary);font-size:14px;line-height:1.6;font-weight:400;}" +

        // Features
        ".features{padding:80px 24px;background:#ffffff;border-bottom:1px solid var(--border-rule);}" +
        ".features-inner{max-width:980px;margin:0 auto;}" +
        ".features h3{font-size:32px;font-weight:800;letter-spacing:-0.5px;color:var(--text-primary);margin-bottom:48px;}" +
        ".feature-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(280px,1fr));gap:24px;}" +
        ".feature-card{background:#ffffff;padding:24px;border:1px solid var(--border-rule);border-radius:12px;transition:box-shadow 0.2s;box-shadow:var(--shadow-sm);}" +
        ".feature-card:hover{box-shadow:var(--shadow-md);}" +
        ".feature-icon{width:44px;height:44px;background:var(--bg-canvas);color:var(--brand-primary);border:1px solid var(--border-rule);display:flex;align-items:center;justify-content:center;font-size:20px;margin-bottom:16px;border-radius:10px;}" +
        ".feature-card h4{font-size:15px;font-weight:600;margin-bottom:8px;color:var(--text-primary);}" +
        ".feature-card p{color:var(--text-secondary);font-size:14px;line-height:1.6;font-weight:400;}" +

        // CTA
        ".cta{padding:80px 24px;background:var(--bg-canvas);text-align:left;max-width:980px;margin:0 auto;border-bottom:1px solid var(--border-rule);}" +
        ".cta h3{font-size:32px;font-weight:800;letter-spacing:-0.5px;margin-bottom:16px;}" +
        ".cta p{font-size:16px;color:var(--text-secondary);max-width:560px;margin-bottom:32px;line-height:1.6;font-weight:400;}" +
        ".cta a{display:inline-flex;align-items:center;gap:8px;background:var(--brand-primary);color:#ffffff;padding:14px 32px;border-radius:6px;text-decoration:none;font-size:14px;font-weight:600;box-shadow:var(--shadow-sm);transition:all 0.15s;}" +
        ".cta a:hover{background:var(--brand-dark);transform:translateY(-1px);box-shadow:var(--shadow-md);}" +

        // Footer
        ".footer{padding:32px 24px;background:#ffffff;text-align:center;color:var(--text-muted);font-size:13px;font-weight:400;}" +
        ".footer a{color:var(--text-secondary);text-decoration:none;font-weight:500;transition:color 0.15s;}" +
        ".footer a:hover{color:var(--brand-primary);}" +

        // Mobile Responsive
        "@media(max-width:640px){.hero h2{font-size:32px;}.hero{padding:48px 16px;}.navbar{padding:12px 16px;}.how{padding:48px 16px;}.features{padding:48px 16px;}.cta{padding:48px 16px;}}" +
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
        "<div class='hero-tag'>COS 202 Class Project &middot; Financial Accounting</div>" +
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
        "<div class='demo-title'>See how it works</div>" +
        "<div><span class='sdg-badge' style='background:#dcfce7;color:#166534;border:1px solid #bbf7d0;'>Live Demo</span></div>" +
        "</div>" +
        "<div class='demo-body'>" +
        "<div class='msg-bubble user-bubble'>Sold 5 bags of rice for &#8358;100,000</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-check' style='color:#166534;'></i> <strong>SALE</strong> recorded &middot; &#8358;100,000.00 (5 bags of rice)</div>" +
        "<div class='msg-bubble user-bubble'>Oga Musa owes me &#8358;12,000</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-scale' style='color:#6d28d9;'></i> <strong>DEBT</strong> tracked &middot; &#8358;12,000.00 (Counterparty: Oga Musa)</div>" +
        "<div class='msg-bubble user-bubble'>Paid &#8358;5,000 for transport</div>" +
        "<div class='msg-bubble sys-bubble'><i class='ti ti-receipt' style='color:#b91c1c;'></i> <strong>EXPENSE</strong> logged &middot; &#8358;5,000.00</div>" +
        "</div></div></div>" +

        // How it works
        "<section class='how'>" +
        "<div class='section-tag'>How it works</div>" +
        "<h3>Three Simple Steps</h3>" +
        "<p class='sub'>From plain language to a complete financial record.</p>" +
        "<div class='steps'>" +
        "<div class='step-card'><div class='step-badge'>01</div><h4>Type what happened</h4><p>Write \"Sold 5 bags for &#8358;100k\" or \"Mama Tope paid &#8358;20,000\" — just the way you'd say it.</p></div>" +
        "<div class='step-card'><div class='step-badge'>02</div><h4>We sort it out</h4><p>SmartLedger figures out if it's a sale, expense, supply, debt, or payment — even with typos.</p></div>" +
        "<div class='step-card'><div class='step-badge'>03</div><h4>See your numbers</h4><p>Track profit, monitor debts, spot trends, and print reports whenever you need them.</p></div>" +
        "</div></section>" +

        // Features
        "<section class='features'>" +
        "<div class='features-inner'>" +
        "<div class='section-tag'>Features</div>" +
        "<h3>Built for Small Traders</h3>" +
        "<div class='feature-grid'>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-messages'></i></div><h4>Talk the way you trade</h4><p>Use shorthand, local terms, and the way you naturally describe transactions — SmartLedger keeps up.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-chart-bar'></i></div><h4>Charts that make sense</h4><p>See where your money goes with clear visuals for sales, expenses, and profit at a glance.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-scale'></i></div><h4>Debt tracking</h4><p>Keep tabs on who owes you, how much they've paid, and what's still outstanding.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-shield-check'></i></div><h4>Confirm before saving</h4><p>Every entry shows you a preview first — change the category or cancel before it hits your books.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-device-mobile'></i></div><h4>Works on any device</h4><p>Open it in your phone browser, your tablet, or your laptop — no app download needed.</p></div>" +
        "<div class='feature-card'><div class='feature-icon'><i class='ti ti-lock'></i></div><h4>Your data stays private</h4><p>Each merchant account is separate and password-protected. Only you see your books.</p></div>" +
        "</div></div></section>" +

        // CTA
        "<section class='cta'>" +
        "<h3>Ditch the paper notebook</h3>" +
        "<p>Start recording your sales, expenses, and debts in a way that actually keeps up with your business.</p>" +
        "<a href='/auth/signup'><i class='ti ti-user-plus'></i> Create Free Account</a>" +
        "</section>" +

        // Footer
        "<footer class='footer'>" +
        "<p>SmartLedger System &#169; 2026 &middot; COS 202 Group 22 Class Project</p>" +
        "<p style='margin-top:12px;'><a href='/auth/login'>Login</a> &middot; <a href='/auth/signup'>Register</a> &middot; <a href='/'>Home</a></p>" +
        "</footer>" +

        "</body></html>";
    }
}