package servlets;

import http.HttpRequest;
import http.HttpResponse;
import http.Servlet;

import java.util.Base64;

public class AuthServlet extends Servlet {

    @Override
    public void get(HttpRequest request, HttpResponse response) {
        String authInfo = request.getHeaders()
                .get("Authorization")
                .replace("Basic", "")
                .trim();
        String decodeString = new String(Base64.getDecoder().decode(authInfo));
        String[] params = decodeString.split(":");
        String username = params[0];
        String password = params[1];

        String body = "{\"ddd\": 1 }";
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
