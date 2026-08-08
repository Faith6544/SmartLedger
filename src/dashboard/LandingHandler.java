package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class LandingHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        // Only handle exact root path — let other handlers handle their paths
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
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
        "<meta name='viewport' content='width=device-width,initial-scale=1.0'>" +
        "<title>SmartLedger — Record-keeping made simple</title>" +
        "<link rel='icon' type='image/png' href='" + HtmlTemplates.LOGO_DATA + "'>" +
        "<link rel='shortcut icon' type='image/png' href='" + HtmlTemplates.LOGO_DATA + "'>" +
        "<style>" +
        "*{margin:0;padding:0;box-sizing:border-box;}" +
        "body{font-family:'Segoe UI',sans-serif;color:#333;}" +

        // Navbar
        ".navbar{background:#fff;padding:15px 30px;display:flex;justify-content:space-between;align-items:center;border-bottom:1px solid #eee;position:sticky;top:0;z-index:100;}" +
        ".navbar h1{color:#2e7d32;font-size:22px;}" +
        ".navbar a{text-decoration:none;padding:10px 24px;border-radius:8px;font-weight:600;font-size:14px;}" +
        ".nav-login{color:#2e7d32;border:2px solid #2e7d32;margin-right:10px;}" +
        ".nav-login:hover{background:#e8f5e9;}" +
        ".nav-signup{background:#4CAF50;color:#fff;}" +
        ".nav-signup:hover{background:#43A047;}" +

        // Hero
        ".hero{background:linear-gradient(135deg,#1a1a2e 0%,#16213e 50%,#0f3460 100%);color:#fff;padding:100px 30px;text-align:center;}" +
        ".hero h2{font-size:48px;font-weight:800;max-width:700px;margin:0 auto 20px;line-height:1.15;}" +
        ".hero h2 span{color:#4CAF50;}" +
        ".hero p{font-size:18px;color:#b0bec5;max-width:550px;margin:0 auto 40px;line-height:1.6;}" +
        ".hero-cta{display:inline-block;background:#4CAF50;color:#fff;padding:16px 40px;border-radius:10px;text-decoration:none;font-size:17px;font-weight:700;}" +
        ".hero-cta:hover{background:#43A047;}" +
        ".hero-sub{margin-top:15px;color:#78909c;font-size:13px;}" +

        // How it works
        ".how{padding:80px 30px;background:#fff;text-align:center;}" +
        ".how h3{font-size:32px;margin-bottom:10px;color:#1a1a2e;}" +
        ".how .sub{color:#888;margin-bottom:50px;font-size:16px;}" +
        ".steps{display:flex;justify-content:center;gap:40px;flex-wrap:wrap;max-width:900px;margin:0 auto;}" +
        ".step{flex:1;min-width:220px;max-width:280px;text-align:center;}" +
        ".step-num{width:50px;height:50px;background:#e8f5e9;color:#2e7d32;border-radius:50%;display:inline-flex;align-items:center;justify-content:center;font-size:22px;font-weight:700;margin-bottom:15px;}" +
        ".step h4{font-size:18px;margin-bottom:8px;color:#1a1a2e;}" +
        ".step p{color:#666;font-size:14px;line-height:1.5;}" +

        // Features
        ".features{padding:80px 30px;background:#f5f5f5;text-align:center;}" +
        ".features h3{font-size:32px;margin-bottom:50px;color:#1a1a2e;}" +
        ".feature-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(250px,1fr));gap:25px;max-width:900px;margin:0 auto;}" +
        ".feature-card{background:#fff;border-radius:12px;padding:30px;text-align:left;box-shadow:0 2px 12px rgba(0,0,0,0.06);}" +
        ".feature-card .icon{font-size:30px;margin-bottom:12px;}" +
        ".feature-card h4{font-size:17px;margin-bottom:8px;color:#1a1a2e;}" +
        ".feature-card p{color:#666;font-size:14px;line-height:1.5;}" +

        // Example
        ".example{padding:80px 30px;background:#fff;text-align:center;}" +
        ".example h3{font-size:32px;margin-bottom:15px;color:#1a1a2e;}" +
        ".example .sub{color:#888;margin-bottom:40px;font-size:16px;}" +
        ".chat-demo{max-width:500px;margin:0 auto;background:#1a1a2e;border-radius:16px;padding:25px;text-align:left;}" +
        ".chat-demo .msg{padding:10px 14px;margin:8px 0;border-radius:10px;font-size:14px;}" +
        ".chat-demo .user-msg{background:#2e7d32;color:#fff;margin-left:40px;}" +
        ".chat-demo .sys-msg{background:#16213e;color:#b0bec5;margin-right:40px;}" +
        ".chat-demo .sys-msg b{color:#4CAF50;}" +

        // CTA
        ".cta{padding:80px 30px;background:linear-gradient(135deg,#2e7d32,#4CAF50);text-align:center;color:#fff;}" +
        ".cta h3{font-size:32px;margin-bottom:15px;}" +
        ".cta p{font-size:16px;opacity:0.9;margin-bottom:30px;}" +
        ".cta a{display:inline-block;background:#fff;color:#2e7d32;padding:16px 40px;border-radius:10px;text-decoration:none;font-size:17px;font-weight:700;}" +
        ".cta a:hover{background:#f5f5f5;}" +

        // Footer
        ".footer{padding:30px;background:#1a1a2e;text-align:center;color:#666;font-size:13px;}" +
        ".footer a{color:#4CAF50;text-decoration:none;}" +

        // SDG badges
        ".sdg{display:flex;justify-content:center;gap:15px;margin-top:20px;}" +
        ".sdg-badge{padding:6px 14px;border-radius:20px;font-size:12px;font-weight:600;}" +
        ".sdg8{background:#93132a;color:#fff;}" +
        ".sdg9{background:#f36d25;color:#fff;}" +

        // Mobile
        "@media(max-width:600px){.hero h2{font-size:30px;}.hero{padding:60px 20px;}.steps{flex-direction:column;align-items:center;}.navbar{padding:10px 15px;}.navbar a{padding:8px 14px;font-size:12px;}}" +
        "</style></head><body>" +

        // Navbar
        "<div class='navbar'>" +
        "<div style='display:flex;align-items:center;gap:8px;'><div style='width:32px;height:32px;background:#c6edc3;border:2px solid #1a1a2e;border-radius:50%;display:flex;align-items:center;justify-content:center;'><img src='" + HtmlTemplates.LOGO_DATA + "' style='width:20px;height:20px;'></div><h1>SmartLedger</h1></div>" +
        "<div>" +
        "<a href='/auth/login' class='nav-login'>Login</a>" +
        "<a href='/auth/signup' class='nav-signup'>Sign Up</a>" +
        "</div></div>" +

        // Hero
        "<div class='hero'>" +
        "<h2>Type what you sold.<br><span>We handle the rest.</span></h2>" +
        "<p>SmartLedger lets small business owners record sales, expenses, and debts by typing naturally — like sending a message. No forms. No accounting knowledge needed.</p>" +
        "<a href='/auth/signup' class='hero-cta'>Start Recording Free</a>" +
        "<p class='hero-sub'>No downloads. No credit card. Just type and go.</p>" +
        "<div class='sdg'><span class='sdg-badge sdg8'>SDG 8 — Decent Work</span><span class='sdg-badge sdg9'>SDG 9 — Innovation</span></div>" +
        "</div>" +

        // How it works
        "<div class='how'>" +
        "<h3>How It Works</h3>" +
        "<p class='sub'>Three steps. That's all.</p>" +
        "<div class='steps'>" +
        "<div class='step'><div class='step-num'>1</div><h4>Type What Happened</h4><p>\"Sold 5 bags of rice for &#8358;100,000\" — type it exactly how you'd say it.</p></div>" +
        "<div class='step'><div class='step-num'>2</div><h4>We Categorize It</h4><p>SmartLedger reads your message and automatically sorts it as a sale, expense, debt, or payment.</p></div>" +
        "<div class='step'><div class='step-num'>3</div><h4>See Your Dashboard</h4><p>Everything organized — sales, expenses, profits, and who owes you — in one clean view.</p></div>" +
        "</div></div>" +

        // Chat demo
        "<div class='example'>" +
        "<h3>Just Like Chatting</h3>" +
        "<p class='sub'>No forms to fill. No fields to learn. Just type.</p>" +
        "<div class='chat-demo'>" +
        "<div class='msg user-msg'>Sold 5 bags of rice for &#8358;100,000</div>" +
        "<div class='msg sys-msg'><b>SALE</b> recorded — &#8358;100,000.00</div>" +
        "<div class='msg user-msg'>Oga Musa owes me &#8358;12,000</div>" +
        "<div class='msg sys-msg'><b>DEBT</b> recorded — &#8358;12,000.00 (Oga Musa)</div>" +
        "<div class='msg user-msg'>Paid &#8358;5,000 for transport</div>" +
        "<div class='msg sys-msg'><b>EXPENSE</b> recorded — &#8358;5,000.00</div>" +
        "<div class='msg user-msg'>What's my profit?</div>" +
        "<div class='msg sys-msg'>Profit: <b>&#8358;95,000.00</b></div>" +
        "</div></div>" +

        // Features
        "<div class='features'>" +
        "<h3>Built for Nigerian Traders</h3>" +
        "<div class='feature-grid'>" +
        "<div class='feature-card'><div class='icon'>&#128172;</div><h4>Chat-Style Input</h4><p>Type transactions like you're sending a message. Pidgin, English, shorthand — it understands.</p></div>" +
        "<div class='feature-card'><div class='icon'>&#128202;</div><h4>Live Dashboard</h4><p>See sales, expenses, debts, and profit at a glance. Filter by date, type, or person.</p></div>" +
        "<div class='feature-card'><div class='icon'>&#128176;</div><h4>Debt Tracking</h4><p>Know exactly who owes you and how much. Get notified when payments come in.</p></div>" +
        "<div class='feature-card'><div class='icon'>&#9989;</div><h4>Smart Confirmation</h4><p>Every entry gets confirmed before saving. Wrong category? Change it in one tap.</p></div>" +
        "<div class='feature-card'><div class='icon'>&#128241;</div><h4>Works on Phone</h4><p>No app to download. Open in any browser on your phone and start recording.</p></div>" +
        "<div class='feature-card'><div class='icon'>&#128274;</div><h4>Your Data, Private</h4><p>Each trader gets their own secure dashboard. Only you can see your records.</p></div>" +
        "</div></div>" +

        // CTA
        "<div class='cta'>" +
        "<h3>Stop Losing Track of Your Money</h3>" +
        "<p>Join traders who are ditching notebooks for something smarter.</p>" +
        "<a href='/auth/signup'>Create Your Free Account</a>" +
        "</div>" +

        // Footer
        "<div class='footer'>" +
        "<p>SmartLedger &#169; 2026 &#8212; COS 202 Group 22</p>" +
        "<p style='margin-top:8px;'><a href='/auth/login'>Login</a> &middot; <a href='/auth/signup'>Sign Up</a></p>" +
        "</div>" +

        "</body></html>";
    }
}