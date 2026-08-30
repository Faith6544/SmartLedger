package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.UserDAO;
import database.TransactionDAO;
import model.User;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

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
            }
            else if ("POST".equals(method)) { processLogin(exchange); }
        } else if (path.equals("/auth/signup")) {
            if ("GET".equals(method)) { sendPage(exchange, signupPage(null)); }
            else if ("POST".equals(method)) { processSignup(exchange); }
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
                "t.style.cssText='position:fixed;top:20px;left:50%;transform:translateX(-50%) translateY(-20px);background:linear-gradient(135deg,#4CAF50,#66BB6A);color:#fff;padding:14px 28px;border-radius:12px;font-size:14px;font-weight:600;z-index:999;opacity:0;transition:all 0.5s ease;box-shadow:0 6px 20px rgba(76,175,80,0.3);max-width:90%;text-align:center;';" +
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
        h.append(HtmlTemplates.head(title));
        h.append("<div style='min-height:100vh;display:flex;align-items:center;justify-content:center;'>");
        h.append("<div style='background:#fff;border-radius:12px;padding:40px;width:100%;max-width:380px;box-shadow:0 4px 20px rgba(0,0,0,0.08);'>");
        h.append("<div style='text-align:center;margin-bottom:10px;'><div style='display:inline-flex;align-items:center;justify-content:center;width:70px;height:70px;background:#c6edc3;border:2px solid #1a1a2e;border-radius:50%;'><img src='").append(HtmlTemplates.LOGO_DATA).append("' style='width:45px;height:45px;'></div></div>");
        h.append("<h1 style='color:#2e7d32;text-align:center;margin-bottom:5px;'>SmartLedger</h1>");
        h.append("<p style='color:#888;text-align:center;margin-bottom:30px;font-size:14px;'>Record-keeping made simple</p>");

        if (error != null) {
            h.append("<div style='background:#ffebee;color:#c62828;padding:10px 15px;border-radius:6px;margin-bottom:20px;font-size:13px;'>")
             .append(HtmlTemplates.escapeHtml(error)).append("</div>");
        }

        h.append("<form method='POST' action='").append(action).append("'>");
        h.append("<label style='font-size:13px;color:#666;font-weight:500;'>Username</label>");
        h.append("<input name='username' type='text' required style='width:100%;padding:10px 14px;border:1px solid #ddd;border-radius:6px;font-size:14px;margin:5px 0 15px;'>");
        h.append("<label style='font-size:13px;color:#666;font-weight:500;'>Password</label>");
        h.append("<input name='password' type='password' required style='width:100%;padding:10px 14px;border:1px solid #ddd;border-radius:6px;font-size:14px;margin:5px 0 15px;'>");
        if (action.contains("signup")) {
            h.append("<label style='font-size:13px;color:#666;font-weight:500;'>Business Name <span style='color:#aaa;'>(optional)</span></label>");
            h.append("<input name='business_name' type='text' placeholder='e.g. Mama Tope Provisions' style='width:100%;padding:10px 14px;border:1px solid #ddd;border-radius:6px;font-size:14px;margin:5px 0 15px;'>");
        }
        h.append("<div style='height:10px;'></div>");
        h.append("<button type='submit' style='width:100%;padding:12px;background:linear-gradient(135deg,#4CAF50,#66BB6A);color:#fff;border:none;border-radius:6px;font-size:15px;font-weight:600;cursor:pointer;transition:all 0.3s;'>")
         .append(btnText).append("</button>");
        h.append("</form>");

        h.append("<p style='text-align:center;margin-top:20px;font-size:13px;'>");
        h.append("<a href='").append(altLink).append("' style='color:#2196F3;text-decoration:none;'>").append(altText).append("</a></p>");

        h.append("</div></div>");
        h.append(HtmlTemplates.footer());
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
            if (kv.length == 2) params.put(URLDecoder.decode(kv[0], "UTF-8"), URLDecoder.decode(kv[1], "UTF-8"));
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