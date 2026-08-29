package dashboard;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class DashboardServer {

    private HttpServer server;
    private int port;

    public DashboardServer(int port) throws Exception {
        this.port = port;
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new LandingHandler());
        server.createContext("/dashboard/", new DashboardHandler());
        server.createContext("/chat/", new WebChatHandler());
        server.createContext("/api/", new ApiHandler());
        server.createContext("/auth/", new AuthHandler());
        server.createContext("/analysis/", new AnalysisHandler());
        server.createContext("/report/", new ReportHandler());
        server.setExecutor(Executors.newFixedThreadPool(10));
    }

    public void start() {
        server.start();
        System.out.println("✅ SmartLedger running at http://localhost:" + port + "/");
        System.out.println("📊 Dashboard: http://localhost:" + port + "/dashboard/test123-dashboard-token");
    }

    public void stop() { 
        server.stop(0); 
    }
}