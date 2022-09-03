package http;

public interface Router {
    void addServlet(String path, Servlet servlet);
    void removeServlet(String path);
    Servlet getServlet(String path);
}
