package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import commands.CommandHandler;
import database.*;
import model.*;
import parser.MessageParser;
import parser.ParseResult;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ApiHandler implements HttpHandler {

    private MessageParser parser = new MessageParser();
    private CommandHandler commandHandler = new CommandHandler();
    private UserDAO userDAO = new UserDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();
    private ChatMessageDAO chatMessageDAO = new ChatMessageDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        String path = exchange.getRequestURI().getPath();
        Map<String, String> params = parseBody(exchange);

        String token = params.getOrDefault("token", "");
        User user = userDAO.getUserByToken(token);

        if (path.equals("/api/login")) { handleLogin(exchange, params); return; }
        if (path.equals("/api/signup")) { handleSignup(exchange, params); return; }

        if (user == null) { sendJson(exchange, 401, "{\"error\":\"Invalid token\"}"); return; }

        switch (path) {
            case "/api/send": handleSend(exchange, params, user); break;
            case "/api/confirm": handleConfirm(exchange, params, user); break;
            case "/api/delete": handleDelete(exchange, params, user); break;
            case "/api/edit": handleEdit(exchange, params, user); break;
            default: sendJson(exchange, 404, "{\"error\":\"Not found\"}");
        }
    }

    private void handleSend(HttpExchange exchange, Map<String, String> params, User user) throws IOException {
        String message = params.getOrDefault("message", "").trim();
        if (message.isEmpty()) { sendJson(exchange, 400, "{\"error\":\"Empty message\"}"); return; }

        // Save as chat message
        chatMessageDAO.save(new ChatMessage(user.getId(), message, false));

        // Check if command
        if (parser.isCommand(message)) {
            String response = commandHandler.handle(message, user.getId(), user.getDashboardToken());
            sendJson(exchange, 200, "{\"isCommand\":true,\"response\":\"" + escapeJson(response) + "\"}");
            return;
        }

        // Parse
        ParseResult result = parser.parse(message);

        if (!result.isTransaction()) {
            sendJson(exchange, 200, "{\"isTransaction\":false}");
            return;
        }

        String json = "{\"isTransaction\":true," +
            "\"isCommand\":false," +
            "\"type\":\"" + result.getType().name() + "\"," +
            "\"amount\":" + result.getAmount() + "," +
            "\"amountFormatted\":\"" + HtmlTemplates.formatAmount(result.getAmount()) + "\"," +
            "\"description\":\"" + escapeJson(message) + "\"," +
            "\"counterparty\":" + (result.getCounterparty() != null ? "\"" + escapeJson(result.getCounterparty()) + "\"" : "null") + "," +
            "\"confidence\":\"" + result.getConfidence().name() + "\"}";

        sendJson(exchange, 200, json);
    }

    private void handleConfirm(HttpExchange exchange, Map<String, String> params, User user) throws IOException {
        String typeStr = params.getOrDefault("type", "");
        String amountStr = params.getOrDefault("amount", "0");
        String description = params.getOrDefault("description", "");
        String counterparty = params.getOrDefault("counterparty", "");
        if (counterparty.isEmpty()) counterparty = null;

        try {
            TransactionType type = TransactionType.valueOf(typeStr);
            double amount = Double.parseDouble(amountStr);

            Transaction txn = new Transaction(user.getId(), type, amount, description, counterparty);
            transactionDAO.save(txn);

            // Update the chat message to mark as transaction
            chatMessageDAO.save(new ChatMessage(user.getId(), description, true));

            sendJson(exchange, 200, "{\"success\":true,\"amountFormatted\":\"" + HtmlTemplates.formatAmount(amount) + "\"}");
        } catch (Exception e) {
            sendJson(exchange, 400, "{\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private void handleDelete(HttpExchange exchange, Map<String, String> params, User user) throws IOException {
        try {
            int id = Integer.parseInt(params.getOrDefault("id", "0"));
            boolean success = transactionDAO.deleteTransaction(id, user.getId());
            sendJson(exchange, 200, "{\"success\":" + success + "}");
        } catch (Exception e) {
            sendJson(exchange, 400, "{\"error\":\"Invalid ID\"}");
        }
    }

    private void handleEdit(HttpExchange exchange, Map<String, String> params, User user) throws IOException {
        try {
            int id = Integer.parseInt(params.getOrDefault("id", "0"));
            TransactionType newType = TransactionType.valueOf(params.getOrDefault("type", "SALE"));
            boolean success = transactionDAO.updateType(id, newType, user.getId());
            sendJson(exchange, 200, "{\"success\":" + success + "}");
        } catch (Exception e) {
            sendJson(exchange, 400, "{\"error\":\"Invalid request\"}");
        }
    }

    private void handleLogin(HttpExchange exchange, Map<String, String> params) throws IOException {
        String username = params.getOrDefault("username", "");
        String password = params.getOrDefault("password", "");
        User user = userDAO.login(username, password);
        if (user != null) {
            sendJson(exchange, 200, "{\"success\":true,\"token\":\"" + user.getDashboardToken() + "\"}");
        } else {
            sendJson(exchange, 401, "{\"success\":false,\"error\":\"Invalid credentials\"}");
        }
    }

    private void handleSignup(HttpExchange exchange, Map<String, String> params) throws IOException {
        String username = params.getOrDefault("username", "");
        String password = params.getOrDefault("password", "");
        if (username.isEmpty() || password.length() < 4) {
            sendJson(exchange, 400, "{\"success\":false,\"error\":\"Username required, password min 4 chars\"}");
            return;
        }
        User user = new User(username, password);
        if (userDAO.createUser(user)) {
            sendJson(exchange, 200, "{\"success\":true,\"token\":\"" + user.getDashboardToken() + "\"}");
        } else {
            sendJson(exchange, 400, "{\"success\":false,\"error\":\"Username already taken\"}");
        }
    }

    // ===== Helpers =====

    private Map<String, String> parseBody(HttpExchange exchange) throws IOException {
        Map<String, String> params = new HashMap<>();
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        String body = sb.toString();

        if (!body.isEmpty()) {
            for (String pair : body.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    params.put(URLDecoder.decode(kv[0], "UTF-8"), URLDecoder.decode(kv[1], "UTF-8"));
                }
            }
        }
        return params;
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    private void sendJson(HttpExchange exchange, int code, String json) throws IOException {
        byte[] bytes = json.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
