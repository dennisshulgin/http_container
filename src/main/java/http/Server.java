package http;

import http.config.Configuration;
import http.service.SessionService;
import http.service.UserService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {

    private int port = 8080;
    private final Configuration configuration;
    private final ServerSocket socketServer;
    private final ExecutorService executorService;
    private boolean running = false;

    public Server(Configuration configuration) throws IOException {
        this.configuration = configuration;
        this.executorService = Executors.newFixedThreadPool(100);
        if (configuration.propertiesConfig().contains("port")) {
            port = (int)configuration.propertiesConfig().get("port");
        }
        this.socketServer = new ServerSocket(port);
    }

    public void start() throws IOException {
        running = true;
        while(running) {
            Socket clientSocket = socketServer.accept();
            Client client = new Client(clientSocket, configuration);
            executorService.submit(client);
        }
    }

    public void stop() throws IOException{
        running = false;
        socketServer.close();
    }
}
