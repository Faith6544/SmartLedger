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
            exchange.getResponseHeaders().set("Location",
                    "/dashboard/" + user.getDashboardToken() + "?welcome=" + welcomeFlag);
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
        String page = authPage("Login", "/auth/login", "Login", "/auth/signup", "Don't have an account? Sign up",
                error);
        if (justRegistered) {
            String toastScript = "<script>document.addEventListener('DOMContentLoaded',function(){" +
                    "var t=document.createElement('div');" +
                    "t.style.cssText='position:fixed;top:24px;left:50%;transform:translateX(-50%) translateY(-20px);background:#1f2937;border-left:4px solid #166534;color:#fff;padding:14px 24px;border-radius:8px;font-size:14px;font-weight:500;z-index:9999;opacity:0;transition:all 0.3s cubic-bezier(0.4, 0, 0.2, 1);box-shadow:0 4px 6px -1px rgba(0,0,0,0.1),0 2px 4px -1px rgba(0,0,0,0.06);max-width:90%;display:flex;align-items:center;text-align:left;';"
                    + "t.textContent='Account created successfully! Please log in.';" +
                    "document.body.appendChild(t);" +
                    "setTimeout(function(){t.style.opacity=1;t.style.transform='translateX(-50%) translateY(0)';},100);"
                    +
                    "setTimeout(function(){t.style.opacity=0;t.style.transform='translateX(-50%) translateY(-20px)';setTimeout(function(){t.remove();},500);},4000);"
                    +
                    "});</script>";
            page = page.replace("</body>", toastScript + "</body>");
        }
        return page;
    }

    private String signupPage(String error) {
        return authPage("Sign Up", "/auth/signup", "Create Account", "/auth/login", "Already have an account? Login",
                error);
    }

    private String authPage(String title, String action, String btnText, String altLink, String altText, String error) {
        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head(title));
        h.append(
                "<div style='min-height:100vh;display:flex;align-items:center;justify-content:center;padding:24px 16px;'>");
        h.append(
                "<div style='background:#ffffff;border:1px solid var(--border-rule);border-radius:10px;padding:36px 32px;width:100%;max-width:400px;box-shadow:0 1px 2px 0 rgba(0,0,0,0.05);'>");
        h.append(
                "<div style='text-align:center;margin-bottom:24px;border-bottom:1px solid var(--border-rule);padding-bottom:18px;'>");
        h.append(
                "<div style='display:inline-flex;align-items:center;justify-content:center;width:44px;height:44px;background:#ffffff;border:1px solid var(--border-rule);border-radius:10px;margin-bottom:10px;'><img src='")
                .append(HtmlTemplates.LOGO_DATA).append("' style='width:26px;height:26px;' alt='Logo'></div>");
        h.append(
                "<h1 style='color:var(--text-primary);font-size:18px;font-weight:700;'>SmartLedger</h1>");
        h.append(
                "<p style='color:var(--text-secondary);font-size:12px;font-weight:500;margin-top:4px;'>Merchant Accounting &middot; COS 202</p></div>");

        if (error != null) {
            h.append(
                    "<div style='background:#fee2e2;color:var(--expense-val);border:1px solid #fca5a5;border-radius:6px;padding:10px 14px;margin-bottom:20px;font-size:13px;font-weight:500;display:flex;align-items:center;gap:6px;'>")
                    .append("<i class='ti ti-alert-circle'></i> ").append(HtmlTemplates.escapeHtml(error))
                    .append("</div>");
        }

        h.append("<form method='POST' action='").append(action).append("'>");
        h.append(
                "<div style='margin-bottom:16px;'><label style='font-size:13px;color:var(--text-primary);font-weight:600;display:block;margin-bottom:6px;'>Username</label>");
        h.append(
                "<input name='username' type='text' required placeholder='Enter your username' style='width:100%;padding:10px 14px;border:1px solid var(--border-rule);border-radius:6px;font-size:14px;font-weight:400;background:#ffffff;color:var(--text-primary);outline:none;'></div>");

        h.append(
                "<div style='margin-bottom:16px;'><label style='font-size:13px;color:var(--text-primary);font-weight:600;display:block;margin-bottom:6px;'>Password</label>");
        h.append(
                "<input name='password' type='password' required placeholder='••••••••' style='width:100%;padding:10px 14px;border:1px solid var(--border-rule);border-radius:6px;font-size:14px;font-weight:400;background:#ffffff;color:var(--text-primary);outline:none;'></div>");

        if (action.contains("signup")) {
            h.append(
                    "<div style='margin-bottom:16px;'><label style='font-size:13px;color:var(--text-primary);font-weight:600;display:block;margin-bottom:6px;'>Business Name <span style='color:var(--text-muted);font-weight:400;'>(optional)</span></label>");
            h.append(
                    "<input name='business_name' type='text' placeholder='e.g. Mama Tope Provisions' style='width:100%;padding:10px 14px;border:1px solid var(--border-rule);border-radius:6px;font-size:14px;font-weight:400;background:#ffffff;color:var(--text-primary);outline:none;'></div>");
        }
        h.append("<div style='height:6px;'></div>");
        h.append(
                "<button type='submit' style='width:100%;padding:14px;background:#166534;color:#ffffff;border:none;border-radius:6px;font-size:14px;font-weight:600;cursor:pointer;letter-spacing:0.3px;'>")
                .append(btnText).append("</button>");
        h.append("</form>");

        h.append(
                "<div style='text-align:center;margin-top:24px;padding-top:16px;border-top:1px solid #f3f4f6;font-size:13px;font-weight:500;'>");
        h.append("<a href='").append(altLink).append("' style='color:#166534;text-decoration:none;font-weight:600;'>")
                .append(altText).append("</a></div>");

        h.append("</div></div>");
        h.append(HtmlTemplates.footer());
        return h.toString();
    }

    private Map<String, String> parseBody(HttpExchange exchange) throws IOException {
        Map<String, String> params = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line);
        for (String pair : sb.toString().split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2)
                params.put(URLDecoder.decode(kv[0], "UTF-8"), URLDecoder.decode(kv[1], "UTF-8"));
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