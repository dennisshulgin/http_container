package http.config;

import http.Router;
import http.RouterImpl;
import http.Servlet;
import http.service.SessionService;
import http.service.SessionServiceImpl;
import http.service.UserService;
import http.service.UserServiceImpl;
import servlets.AddServlet;
import servlets.AuthServlet;
import servlets.MainServlet;

import java.util.Properties;

public class AppConfiguration implements Configuration{
    @Override
    public Router routerConfig() {
        Router router = new RouterImpl();
        Servlet addServlet = new AddServlet();
        Servlet mainServlet = new MainServlet();
        mainServlet.addRole("ADMIN");
        router.addServlet("/main", mainServlet);
        router.addServlet("/add", addServlet);
        return router;
    }

    @Override
    public Properties propertiesConfig() {
        Properties properties = new Properties();
        properties.put("port", 8080);
        properties.put("serverName", "denis");
        properties.put("sessionService", new SessionServiceImpl());
        properties.put("userService", new UserServiceImpl());
        return properties;
    }
}
