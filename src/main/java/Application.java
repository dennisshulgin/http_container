import http.Router;
import http.RouterImpl;
import http.Server;
import servlets.AddServlet;
import servlets.MainServlet;

import java.io.IOException;
import java.util.Arrays;

public class Application {
    public static void main(String[] args) throws IOException {
        Router router = new RouterImpl();
        router.addServlet("/main", new MainServlet());
        router.addServlet("/add", new AddServlet());
        Server server = new Server(8080, router);
        server.start();
    }
}
