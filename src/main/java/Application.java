import http.*;
import http.config.AppConfiguration;
import http.config.Configuration;
import http.entity.Role;
import http.service.SessionServiceImpl;
import http.service.UserServiceImpl;
import servlets.AddServlet;
import servlets.AuthServlet;
import servlets.MainServlet;

import java.io.IOException;
import java.util.HashSet;

public class Application {
    public static void main(String[] args) throws IOException {
        Configuration config = new AppConfiguration();
        Server server = new Server(config);
        server.start();
    }
}
