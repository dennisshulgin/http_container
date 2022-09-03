package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements Runnable{

    private final Socket socket;
    private final InputStream is;
    private final OutputStream os;

    private final Router router;

    private boolean connected;

    private final byte[] BUFFER = new byte[1024];

    public Client(Socket socket, Router router) throws IOException {
        this.socket = socket;
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
        this.router = router;
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
                HttpRequest request = new HttpRequestImpl(sb.toString());
                HttpResponse response = new HttpResponseImpl();

                String url = request.getUrl();
                handle(request, response);
                os.write(response.message().getBytes());
                connected = false;
            }
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle(HttpRequest request, HttpResponse response) {
        Servlet servlet = router.getServlet(request.getUrl());
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
                break;
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
