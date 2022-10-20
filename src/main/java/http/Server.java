package http;

import http.config.Configuration;
import http.service.SessionService;
import http.service.UserService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {

    private final Configuration configuration;
    private final ServerSocket socketServer;
    private final ExecutorService executorService;
    private boolean running = false;

    public Server(Configuration configuration) throws IOException {
        this.configuration = configuration;
        this.socketServer = new ServerSocket(configuration.portConfig());
        this.executorService = Executors.newFixedThreadPool(100);
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
