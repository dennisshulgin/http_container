package servlets;

import http.HttpRequest;
import http.HttpResponse;
import http.Servlet;

public class MainServlet extends Servlet {

    @Override
    public void get(HttpRequest request, HttpResponse response) {
        String body = "{\"ddd\": 1 }";
        response.setCode("OK");
        response.setStatusCode(200);
        response.setBody(body);
        response.addHeader("Server", "denis");
        response.addHeader("Content-Type", "text/html");
        System.out.println(request.getBody());
    }

    @Override
    public void post(HttpRequest request, HttpResponse response) {
        String body = "{\"ddd\": 1 }";
        response.setCode("OK");
        response.setStatusCode(200);
        response.setBody(body);
        response.addHeader("Server", "denis");
        response.addHeader("Content-Type", "text/html");
        //System.out.println(request.getHeaders());
        //System.out.println(request.getBody());
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
