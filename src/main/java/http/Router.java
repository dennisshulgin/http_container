package http;

import java.util.List;
import java.util.Map;

public interface Router {
    void addServlet(String path, Servlet servlet);
    void removeServlet(String path);
    Servlet getServlet(String path);
    List<Servlet> getAllServlets();
}
