import dashboard.DashboardServer;
import gui.ChatWindow;
import javax.swing.*;

public class WebApp {
    public static void main(String[] args) {
        try {
            // Use port 8081 (8080 is already in use)
            int port = 8081;
            if (args.length > 0) {
                try {
                    port = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid port, using default 8081");
                }
            }
            
            System.out.println("🚀 Starting SmartLedger WebApp on port " + port + "...");
            
            // Start the web server
            DashboardServer server = new DashboardServer(port);
            server.start();
            
            System.out.println("📊 Dashboard: http://localhost:" + port + "/dashboard/test123-dashboard-token");
            System.out.println("💬 Chat: http://localhost:" + port + "/chat/");
            System.out.println("🔄 Press Ctrl+C to stop");
            
            // Open chat window (without User parameter)
            SwingUtilities.invokeLater(() -> {
                try {
                    // Try creating ChatWindow without parameters first
                    try {
                        new ChatWindow().setVisible(true);
                    } catch (Exception e2) {
                        // If that fails, try with null
                        try {
                            new ChatWindow(null).setVisible(true);
                        } catch (Exception e3) {
                            System.err.println("❌ Could not open chat window: " + e3.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error opening chat: " + e.getMessage());
                }
            });
            
            // Keep server running
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}