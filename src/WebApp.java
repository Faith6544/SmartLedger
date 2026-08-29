import dashboard.DashboardServer;

public class WebApp {
    public static void main(String[] args) {
        try {
            int port = 8081;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            
            System.out.println("🚀 Starting SmartLedger WebApp on port " + port + "...");
            DashboardServer server = new DashboardServer(port);
            server.start();
            
            System.out.println("✅ Server started on port " + port);
            System.out.println("📊 Dashboard: http://localhost:" + port + "/dashboard/test123-dashboard-token");
            System.out.println("🏠 Home: http://localhost:" + port + "/");
            System.out.println("🔄 Press Ctrl+C to stop");
            
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}