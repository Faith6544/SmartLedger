package dashboard;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class DashboardServer {

    private HttpServer server;

    public DashboardServer(int port) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new LandingHandler());
        server.createContext("/dashboard/", new DashboardHandler());
        server.createContext("/chat/", new WebChatHandler());
        server.createContext("/api/", new ApiHandler());
        server.createContext("/auth/", new AuthHandler());
        server.createContext("/analysis/", new AnalysisHandler());
        server.createContext("/report/", new ReportHandler());
        server.setExecutor(Executors.newFixedThreadPool(10)); // was null — one request at a time before this
    }

    public void start() {
        server.start();
        System.out.println("SmartLedger running at http://localhost:8080/");
    }

    public void stop() { server.stop(0); }
}