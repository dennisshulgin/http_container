package http.config;

import http.Router;
import http.RouterImpl;
import http.service.SessionService;
import http.service.SessionServiceImpl;
import http.service.UserService;
import http.service.UserServiceImpl;
import servlets.AddServlet;
import servlets.AuthServlet;
import servlets.MainServlet;

public class AppConfiguration extends Configuration{
    @Override
    public Router routerConfig() {
        Router router = new RouterImpl();
        router.addServlet("/main", new MainServlet());
        router.addServlet("/add", new AddServlet());
        router.addServlet("/auth", new AuthServlet());
        return router;
    }

    @Override
    public SessionService sessionServiceConfig() {
        return new SessionServiceImpl();
    }

    @Override
    public UserService userServiceConfig() {
        return new UserServiceImpl();
    }

    @Override
    public int serverPortConfig() {
        return 8080;
    }
}
