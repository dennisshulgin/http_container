package http;

import exceptions.ForbiddenException;
import exceptions.MethodNotFoundException;
import exceptions.ServletNotFoundException;
import http.config.Configuration;
import http.entity.Session;
import http.entity.User;
import http.service.SessionService;
import http.service.SessionServiceImpl;
import http.service.UserService;
import http.service.UserServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Client implements Runnable{

    private int seconds = 180;
    private final Socket socket;
    private final InputStream is;
    private final OutputStream os;
    private final Router router;
    private UserService userService;

    private SessionService sessionService;
    private boolean connected;
    private final byte[] BUFFER = new byte[1024];

    public Client(Socket socket, Configuration configuration) throws IOException {
        this.socket = socket;
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
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
                System.out.println(sb);
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
                } catch (ForbiddenException e) {
                    response.setCode("Forbidden");
                    response.setStatusCode(403);
                    response.addHeader("server", "denis");
                    response.addHeader("Content-Type", "text/html; charset=utf-8");
                    response.setBody("<html><body><h2>403 Forbidden</h2></body></html>");
                }
                finally {
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
            MethodNotFoundException, ForbiddenException{
        executeHttpMethod(request, response);
    }

    public void executeHttpMethod(HttpRequest request, HttpResponse response) throws ServletNotFoundException,
            MethodNotFoundException, ForbiddenException{
        Servlet servlet = router.getServlet(request.getUrl());
        if(servlet == null) {
            throw new ServletNotFoundException("Servlet not found!");
        }

        if(!checkUserAllow(request, servlet)) {
            throw new ForbiddenException("Forbidden!");
        }

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

    public boolean checkUserAllow(HttpRequest request, Servlet servlet) throws ForbiddenException{
        Set<String> roles = servlet.getRoles();
        UUID userId = request.getHeaders().containsKey("userId") ?
                UUID.fromString(request.getHeaders().get("userId")) : null;
        UUID sessionId = request.getHeaders().containsKey("userId") ?
                UUID.fromString(request.getHeaders().get("sessionId")) : null;
        Optional<User> userOptional = getUser(userId, sessionId);
        if(roles.size() > 0 && userOptional.isEmpty()) {
            return false;
        }
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            roles.retainAll(user.getRoles());
            return !roles.isEmpty();
        }
        return true;
    }

    public Optional<User> getUser(UUID userId, UUID sessionId) {
        if(userId == null || sessionId == null) {
            return Optional.empty();
        }
        Optional<User> userOptional = userService.findUserById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Session> sessionOptional = sessionService.findSessionByUserId(user.getId());
            if(sessionOptional.isPresent()) {
                Session session = sessionOptional.get();
                if(!session.getSessionId().equals(sessionId)) {
                    return Optional.empty();
                }
                ZonedDateTime time = session.getStartDate();
                if(time.plusSeconds(seconds).isAfter(ZonedDateTime.now())) {
                    return userOptional;
                }
            }
        }
        return Optional.empty();
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
