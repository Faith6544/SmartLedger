import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.io.OutputStream;
import java.io.IOException;

public class StartServer {
    public static void main(String[] args) {
        try {
            int port = 8081;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            
            System.out.println("🚀 Starting SmartLedger Web Server on port " + port + "...");
            
            // Create HTTP server directly (bypass DashboardServer issues)
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            
            // Home page
            server.createContext("/", exchange -> {
                String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                    "<title>SmartLedger</title>" +
                    "<style>" +
                    "body { font-family: 'Segoe UI', sans-serif; margin: 0; padding: 0; background: #0f0f1a; color: #fff; display: flex; justify-content: center; align-items: center; min-height: 100vh; }" +
                    ".container { text-align: center; padding: 40px; }" +
                    "h1 { font-size: 48px; color: #00d4ff; }" +
                    ".subtitle { font-size: 20px; color: #888; margin: 20px 0; }" +
                    ".features { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin: 40px 0; }" +
                    ".feature { background: #1a1a2e; padding: 20px; border-radius: 10px; border: 1px solid #2a2a4e; }" +
                    ".feature h3 { color: #00d4ff; }" +
                    ".btn { display: inline-block; padding: 15px 30px; background: #00d4ff; color: #0f0f1a; text-decoration: none; border-radius: 5px; font-weight: bold; margin-top: 20px; }" +
                    ".btn:hover { background: #00b8d4; }" +
                    "</style></head><body>" +
                    "<div class='container'>" +
                    "<h1>📊 SmartLedger</h1>" +
                    "<p class='subtitle'>Conversational Record-Keeping for Small Traders</p>" +
                    "<div class='features'>" +
                    "<div class='feature'><h3>💬 Chat-Based</h3><p>Type transactions like you talk</p></div>" +
                    "<div class='feature'><h3>📊 Analytics</h3><p>See your profit and debts instantly</p></div>" +
                    "<div class='feature'><h3>🌐 Dashboard</h3><p>View all records in one place</p></div>" +
                    "</div>" +
                    "<a href='/dashboard/test123-dashboard-token' class='btn'>🚀 View Dashboard</a>" +
                    "</div></body></html>";
                
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                byte[] response = html.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, response.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                }
            });
            
            // Dashboard page
            server.createContext("/dashboard/", exchange -> {
                String path = exchange.getRequestURI().getPath();
                String token = path.substring(path.lastIndexOf('/') + 1);
                
                String html = generateDashboard(token);
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                byte[] response = html.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, response.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                }
            });
            
            server.setExecutor(null);
            server.start();
            
            System.out.println("✅ Server started successfully!");
            System.out.println("📊 Dashboard: http://localhost:" + port + "/dashboard/test123-dashboard-token");
            System.out.println("🏠 Home: http://localhost:" + port + "/");
            System.out.println();
            System.out.println("🔄 Press Ctrl+C to stop the server");
            
            // Keep server running
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String generateDashboard(String token) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        html.append("<title>SmartLedger Dashboard</title>");
        html.append("<style>");
        html.append("body { font-family: 'Segoe UI', sans-serif; margin: 0; padding: 20px; background: #0f0f1a; color: #fff; }");
        html.append(".container { max-width: 1200px; margin: 0 auto; }");
        html.append("h1 { color: #00d4ff; border-bottom: 2px solid #00d4ff; padding-bottom: 10px; }");
        html.append("h2 { color: #00d4ff; margin-top: 30px; }");
        html.append(".stats { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin: 20px 0; }");
        html.append(".stat-card { background: #1a1a2e; padding: 20px; border-radius: 10px; border: 1px solid #2a2a4e; }");
        html.append(".stat-card h3 { margin: 0 0 10px 0; color: #888; font-weight: normal; font-size: 14px; text-transform: uppercase; }");
        html.append(".stat-card .amount { font-size: 28px; font-weight: bold; color: #00d4ff; }");
        html.append(".stat-card .amount.sale { color: #4caf50; }");
        html.append(".stat-card .amount.expense { color: #f44336; }");
        html.append(".stat-card .amount.debt { color: #ff9800; }");
        html.append(".stat-card .amount.profit { color: #4caf50; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; background: #1a1a2e; border-radius: 10px; overflow: hidden; }");
        html.append("th { background: #0f3460; padding: 12px; text-align: left; font-weight: bold; color: #00d4ff; }");
        html.append("td { padding: 12px; border-bottom: 1px solid #1a1a2e; }");
        html.append("tr:hover { background: #2a2a4e; }");
        html.append(".SALE { color: #4caf50; font-weight: bold; }");
        html.append(".EXPENSE { color: #f44336; font-weight: bold; }");
        html.append(".DEBT { color: #ff9800; font-weight: bold; }");
        html.append(".PAYMENT { color: #2196f3; font-weight: bold; }");
        html.append(".SUPPLY { color: #9c27b0; font-weight: bold; }");
        html.append(".error { color: #f44336; padding: 20px; background: #1a1a2e; border-radius: 10px; }");
        html.append(".welcome { color: #888; }");
        html.append("</style></head><body>");
        html.append("<div class='container'>");
        html.append("<h1>📊 SmartLedger Dashboard</h1>");
        
        // Try to connect to database and show data
        try {
            // Try to load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            java.sql.Connection conn = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smartledger?useSSL=false&allowPublicKeyRetrieval=true", 
                "root", 
                "Skemzy@6544@"
            );
            
            // Get user
            java.sql.PreparedStatement pstmt = conn.prepareStatement("SELECT id, username FROM users WHERE dashboard_token = ?");
            pstmt.setString(1, token);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            int userId = -1;
            String username = "";
            if (rs.next()) {
                userId = rs.getInt("id");
                username = rs.getString("username");
                html.append("<p class='welcome'>Welcome, <strong>").append(username).append("</strong>! 👋</p>");
            } else {
                html.append("<div class='error'>❌ Invalid dashboard token! Please check your token.</div>");
                html.append("</div></body></html>");
                return html.toString();
            }
            rs.close();
            
            if (userId > 0) {
                // Get stats
                java.sql.PreparedStatement statsStmt = conn.prepareStatement(
                    "SELECT " +
                    "COALESCE(SUM(CASE WHEN type = 'SALE' THEN amount ELSE 0 END), 0) as sales, " +
                    "COALESCE(SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END), 0) as expenses, " +
                    "COALESCE(SUM(CASE WHEN type = 'SUPPLY' THEN amount ELSE 0 END), 0) as supplies, " +
                    "COALESCE(SUM(CASE WHEN type = 'DEBT' THEN amount ELSE 0 END), 0) as debts " +
                    "FROM transactions WHERE user_id = ?"
                );
                statsStmt.setInt(1, userId);
                java.sql.ResultSet statsRs = statsStmt.executeQuery();
                
                if (statsRs.next()) {
                    double sales = statsRs.getDouble("sales");
                    double expenses = statsRs.getDouble("expenses");
                    double supplies = statsRs.getDouble("supplies");
                    double debts = statsRs.getDouble("debts");
                    double profit = sales - expenses - supplies;
                    
                    html.append("<div class='stats'>");
                    html.append("<div class='stat-card'><h3>💰 Total Sales</h3><div class='amount sale'>₦").append(String.format("%,.2f", sales)).append("</div></div>");
                    html.append("<div class='stat-card'><h3>📦 Supplies</h3><div class='amount'>₦").append(String.format("%,.2f", supplies)).append("</div></div>");
                    html.append("<div class='stat-card'><h3>💸 Expenses</h3><div class='amount expense'>₦").append(String.format("%,.2f", expenses)).append("</div></div>");
                    html.append("<div class='stat-card'><h3>📋 Debts</h3><div class='amount debt'>₦").append(String.format("%,.2f", debts)).append("</div></div>");
                    html.append("<div class='stat-card'><h3>📈 Profit</h3><div class='amount profit'>₦").append(String.format("%,.2f", profit)).append("</div></div>");
                    html.append("</div>");
                }
                statsRs.close();
                
                // Get transactions
                java.sql.PreparedStatement txStmt = conn.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC LIMIT 50"
                );
                txStmt.setInt(1, userId);
                java.sql.ResultSet txRs = txStmt.executeQuery();
                
                html.append("<h2>Recent Transactions</h2>");
                html.append("<table>");
                html.append("<tr><th>Date</th><th>Type</th><th>Amount</th><th>Description</th></tr>");
                while (txRs.next()) {
                    String type = txRs.getString("type");
                    html.append("<tr>");
                    html.append("<td>").append(txRs.getTimestamp("transaction_date")).append("</td>");
                    html.append("<td class='").append(type).append("'>").append(type).append("</td>");
                    html.append("<td>₦").append(String.format("%,.2f", txRs.getDouble("amount"))).append("</td>");
                    html.append("<td>").append(txRs.getString("description")).append("</td>");
                    html.append("</tr>");
                }
                html.append("</table>");
                txRs.close();
            }
            
            conn.close();
            
        } catch (Exception e) {
            html.append("<div class='error'>❌ Database error: ").append(e.getMessage()).append("</div>");
            e.printStackTrace();
        }
        
        html.append("</div></body></html>");
        return html.toString();
    }
}