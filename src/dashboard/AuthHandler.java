package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.TransactionDAO;
import database.UserDAO;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import model.User;

public class AuthHandler implements HttpHandler {

    private UserDAO userDAO = new UserDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();
        String method = exchange.getRequestMethod();

        if (path.equals("/auth/login")) {
            if ("GET".equals(method)) {
                boolean justRegistered = query != null && query.contains("registered=1");
                sendPage(exchange, loginPage(null, justRegistered));
            } else if ("POST".equals(method)) {
                processLogin(exchange);
            }
        } else if (path.equals("/auth/signup")) {
            if ("GET".equals(method)) {
                sendPage(exchange, signupPage(null));
            } else if ("POST".equals(method)) {
                processSignup(exchange);
            }
        } else {
            exchange.getResponseHeaders().set("Location", "/auth/login");
            exchange.sendResponseHeaders(302, -1);
        }
    }

    private void processLogin(HttpExchange exchange) throws IOException {
        Map<String, String> params = parseBody(exchange);
        String username = params.getOrDefault("username", "");
        String password = params.getOrDefault("password", "");

        User user = userDAO.login(username, password);
        if (user != null) {
            int txnCount = transactionDAO.getAllByUser(user.getId()).size();
            String welcomeFlag = txnCount == 0 ? "new" : "back";
            exchange.getResponseHeaders().set("Location", "/dashboard/" + user.getDashboardToken() + "?welcome=" + welcomeFlag);
            exchange.sendResponseHeaders(302, -1);
        } else {
            sendPage(exchange, loginPage("Invalid username or password.", false));
        }
    }

    private void processSignup(HttpExchange exchange) throws IOException {
        Map<String, String> params = parseBody(exchange);
        String username = params.getOrDefault("username", "");
        String password = params.getOrDefault("password", "");

        if (username.isEmpty() || password.length() < 4) {
            sendPage(exchange, signupPage("Username required. Password must be at least 4 characters."));
            return;
        }

        User user = new User(username, password, params.getOrDefault("business_name", ""));
        if (userDAO.createUser(user)) {
            exchange.getResponseHeaders().set("Location", "/auth/login?registered=1");
            exchange.sendResponseHeaders(302, -1);
        } else {
            sendPage(exchange, signupPage("Username already taken."));
        }
    }

    private String loginPage(String error, boolean justRegistered) {
        String page = authPage("Login", "/auth/login", "Login", "/auth/signup", "Don't have an account? Sign up", error);
        if (justRegistered) {
            String toastScript = "<script>document.addEventListener('DOMContentLoaded',function(){" +
                "var t=document.createElement('div');" +
                "t.style.cssText='position:fixed;top:20px;left:50%;transform:translateX(-50%) translateY(-20px);background:linear-gradient(135deg,#2563eb,#3b82f6);color:#fff;padding:14px 28px;border-radius:8px;font-size:14px;font-weight:600;z-index:999;opacity:0;transition:all 0.5s ease;box-shadow:0 6px 20px rgba(37,99,235,0.3);max-width:90%;text-align:center;';" +
                "t.textContent='Account created successfully! Please log in.';" +
                "document.body.appendChild(t);" +
                "setTimeout(function(){t.style.opacity=1;t.style.transform='translateX(-50%) translateY(0)';},100);" +
                "setTimeout(function(){t.style.opacity=0;t.style.transform='translateX(-50%) translateY(-20px)';setTimeout(function(){t.remove();},500);},4000);" +
                "});</script>";
            page = page.replace("</body>", toastScript + "</body>");
        }
        return page;
    }

    private String signupPage(String error) {
        return authPage("Sign Up", "/auth/signup", "Create Account", "/auth/login", "Already have an account? Login", error);
    }

    private String authPage(String title, String action, String btnText, String altLink, String altText, String error) {
        StringBuilder h = new StringBuilder();
        h.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        h.append("<meta name='viewport' content='width=device-width,initial-scale=1.0'>");
        h.append("<title>SmartLedger - " + title + "</title>");
        h.append("<link rel='icon' type='image/png' href='" + HtmlTemplates.LOGO_DATA + "'>");
        h.append("<link rel='preconnect' href='https://fonts.googleapis.com'>");
        h.append("<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>");
        h.append("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800;900&display=swap' rel='stylesheet'>");
        h.append("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@2.44.0/tabler-icons.min.css'>");
        h.append("<style>");
        h.append("*{margin:0;padding:0;box-sizing:border-box;}");
        h.append(":root{--bg-canvas:#e8ecf1;--border-rule:#c5cdd8;--border-light:#b0b8c4;--text-primary:#1a1a2e;--text-secondary:#2c3e50;--text-muted:#5b6f84;--brand-primary:#2563eb;--brand-dark:#1d4ed8;--brand-light:#dbeafe;--expense-val:#1d4ed8;}");
        h.append("body{font-family:'Inter',sans-serif;background:var(--bg-canvas);color:var(--text-primary);min-height:100vh;}");
        h.append(".auth-card{background:#ffffff;border:1px solid var(--border-rule);border-radius:8px;padding:36px 32px;width:100%;max-width:400px;}");
        h.append(".auth-card h1{font-size:18px;font-weight:800;color:var(--text-primary);letter-spacing:-0.3px;}");
        h.append(".auth-card .sub{font-size:11px;color:var(--text-muted);font-weight:600;text-transform:uppercase;letter-spacing:0.5px;}");
        h.append(".auth-input{width:100%;padding:10px 14px;border:1px solid var(--border-rule);border-radius:6px;font-size:13px;font-weight:500;background:#ffffff;color:var(--text-primary);outline:none;transition:border 0.2s;}");
        h.append(".auth-input:focus{border-color:var(--brand-primary);}");
        h.append(".auth-label{font-size:11px;color:var(--text-secondary);font-weight:600;display:block;margin-bottom:4px;text-transform:uppercase;letter-spacing:0.3px;}");
        h.append(".auth-btn{width:100%;padding:12px;background:var(--brand-primary);color:#fff;border:1px solid var(--brand-primary);border-radius:6px;font-size:12px;font-weight:700;cursor:pointer;transition:background 0.2s;}");
        h.append(".auth-btn:hover{background:var(--brand-dark);}");
        h.append(".auth-error{background:#fee2e2;color:#dc2626;border:1px solid #dc2626;border-radius:6px;padding:10px 14px;margin-bottom:16px;font-size:12px;font-weight:600;display:flex;align-items:center;gap:6px;}");
        h.append(".auth-alt{text-align:center;margin-top:20px;padding-top:16px;border-top:1px solid var(--border-rule);font-size:12px;font-weight:600;}");
        h.append(".auth-alt a{color:var(--brand-primary);text-decoration:none;}");
        h.append(".auth-alt a:hover{text-decoration:underline;}");
        h.append(".logo-wrap{display:inline-flex;align-items:center;justify-content:center;width:44px;height:44px;background:#ffffff;border:1px solid var(--border-rule);border-radius:50%;margin-bottom:10px;}");
        h.append(".logo-wrap img{width:26px;height:26px;}");
        h.append("</style></head><body>");
        
        h.append("<div style='min-height:100vh;display:flex;align-items:center;justify-content:center;padding:24px 16px;'>");
        h.append("<div class='auth-card'>");
        
        // Logo & Title
        h.append("<div style='text-align:center;margin-bottom:24px;border-bottom:1px solid var(--border-rule);padding-bottom:18px;'>");
        h.append("<div class='logo-wrap'><img src='").append(HtmlTemplates.LOGO_DATA).append("' alt='Logo'></div>");
        h.append("<h1>SmartLedger</h1>");
        h.append("<p class='sub'>Merchant Accounting · COS 202</p>");
        h.append("</div>");

        // Error
        if (error != null) {
            h.append("<div class='auth-error'><i class='ti ti-alert-circle'></i> ").append(HtmlTemplates.escapeHtml(error)).append("</div>");
        }

        // Form
        h.append("<form method='POST' action='").append(action).append("'>");
        h.append("<div style='margin-bottom:14px;'>");
        h.append("<label class='auth-label'>Username</label>");
        h.append("<input class='auth-input' name='username' type='text' required placeholder='Enter your username'>");
        h.append("</div>");
        
        h.append("<div style='margin-bottom:14px;'>");
        h.append("<label class='auth-label'>Password</label>");
        h.append("<input class='auth-input' name='password' type='password' required placeholder='••••••••'>");
        h.append("</div>");
        
        if (action.contains("signup")) {
            h.append("<div style='margin-bottom:16px;'>");
            h.append("<label class='auth-label'>Business Name <span style='color:var(--text-muted);font-weight:400;'>(Optional)</span></label>");
            h.append("<input class='auth-input' name='business_name' type='text' placeholder='e.g. Mama Tope Provisions'>");
            h.append("</div>");
        }
        
        h.append("<button class='auth-btn' type='submit'>").append(btnText).append("</button>");
        h.append("</form>");

        h.append("<div class='auth-alt'>");
        h.append("<a href='").append(altLink).append("'>").append(altText).append("</a>");
        h.append("</div>");

        h.append("</div></div>");
        h.append("</body></html>");
        return h.toString();
    }

    private Map<String, String> parseBody(HttpExchange exchange) throws IOException {
        Map<String, String> params = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        for (String pair : sb.toString().split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                params.put(URLDecoder.decode(kv[0], "UTF-8"), URLDecoder.decode(kv[1], "UTF-8"));
            }
        }
        return params;
    }

    private void sendPage(HttpExchange exchange, String html) throws IOException {
        byte[] bytes = html.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}