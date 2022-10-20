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

public class AppConfiguration extends Configuration{
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
    public int portConfig() {
        return 8080;
    }
}
