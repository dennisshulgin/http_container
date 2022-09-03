package http;

public interface Servlet {
    void get(HttpRequest request, HttpResponse response);
    void post(HttpRequest request, HttpResponse response);
    void delete(HttpRequest request, HttpResponse response);
    void put(HttpRequest request, HttpResponse response);
    void patch(HttpRequest request, HttpResponse response);
}
