import dashboard.DashboardServer;

public class WebApp {
    public static void main(String[] args) {
        try {
            int port = 8080;
            String envPort = System.getenv("PORT");
            if (envPort != null) {
                port = Integer.parseInt(envPort);
            }

            DashboardServer server = new DashboardServer(port);
            server.start();
            System.out.println("SmartLedger web server running on port " + port);
            System.out.println("Login: http://localhost:" + port + "/auth/login");

            // Keep the server running
            Thread.currentThread().join();
        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
