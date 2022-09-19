package http;

import exceptions.MethodNotFoundException;
import exceptions.ServletNotFoundException;
import http.config.Configuration;
import http.entity.Role;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Set;

public class Client implements Runnable{

    private final Socket socket;
    private final InputStream is;
    private final OutputStream os;
    private final Configuration configuration;
    private final Router router;
    private boolean connected;
    private final byte[] BUFFER = new byte[1024];

    public Client(Socket socket, Configuration configuration) throws IOException {
        this.socket = socket;
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
        this.configuration = configuration;
        this.router = configuration.routerConfig();
        this.connected = true;
    }

    @Override
    public void run() {
        try {
            while(connected && socket != null && socket.isConnected()) {
                if(is.available() == 0) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                int available = is.available();
                while (available > 0) {
                    int read = is.read(BUFFER);
                    sb.append(new String(BUFFER, 0, read));
                    available -= read;
                }
                //System.out.println(sb);
                HttpRequest request = new HttpRequestImpl(sb.toString());
                HttpResponse response = new HttpResponseImpl();
                try{
                    handle(request, response);
                } catch (MethodNotFoundException | ServletNotFoundException e) {
                    response.setCode("Not Found");
                    response.setStatusCode(404);
                    response.addHeader("server", "denis");
                    response.addHeader("Content-Type", "text/html; charset=utf-8");
                    response.setBody("<html><body><h2>404 Page not found</h2></body></html>");
                } finally {
                    os.write(response.message().getBytes());
                    connected = false;
                }
            }
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle(HttpRequest request, HttpResponse response) throws ServletNotFoundException,
            MethodNotFoundException{
        executeHttpMethod(request, response);
    }

    public void executeHttpMethod(HttpRequest request, HttpResponse response) throws ServletNotFoundException,
            MethodNotFoundException {
        Servlet servlet = router.getServlet(request.getUrl());
        if(servlet == null) {
            throw new ServletNotFoundException("Servlet not found!");
        }
        Set<Role> roles = servlet.getRoles();

        switch (request.getMethod()) {
            case GET:
                servlet.get(request, response);
                break;
            case POST:
                servlet.post(request, response);
                break;
            case PUT:
                servlet.put(request, response);
                break;
            case PATCH:
                servlet.patch(request, response);
                break;
            case DELETE:
                servlet.delete(request, response);
                break;
            default:
                throw new MethodNotFoundException("Method not found!");
        }
    }

    public void close() {
        try {
            if (is != null) is.close();
            if (os != null) os.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
