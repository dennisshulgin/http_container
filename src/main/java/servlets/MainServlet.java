package servlets;

import http.HttpRequest;
import http.HttpResponse;
import http.Servlet;

public class MainServlet implements Servlet {

    @Override
    public void get(HttpRequest request, HttpResponse response) {
        String body = "<html><body><h1>Hello, Denis</h1></body></html>";
        response.setCode("OK");
        response.setStatusCode(200);
        response.setBody(body);
        response.addHeader("Server", "denis");
        response.addHeader("Content-Type", "text/html");
    }

    @Override
    public void post(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void delete(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void put(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void patch(HttpRequest request, HttpResponse response) {

    }
}
