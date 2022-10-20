package http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouterImpl implements Router{

    private final Map<String, Servlet> servlets;

    public RouterImpl() {
        servlets = new HashMap<>();
    }

    public void addServlet(String path, Servlet servlet) {
        servlets.put(path, servlet);
    }

    public void removeServlet(String path) {
        servlets.remove(path);
    }

    public Servlet getServlet(String path) {
        return servlets.get(path);
    }

    public List<Servlet> getAllServlets() {
        return new ArrayList<>(servlets.values());
    }
}
