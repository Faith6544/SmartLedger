package dashboard;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class DashboardServer {

    private HttpServer server;

    public DashboardServer(int port) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/dashboard/", new DashboardHandler());
        server.setExecutor(null);
    }

    public void start() {
        server.start();
        System.out.println("Dashboard server running at http://localhost:8080/dashboard/");
    }

    public void stop() {
        server.stop(0);
    }
}
