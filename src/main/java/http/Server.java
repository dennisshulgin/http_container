package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


public class Server {
    private final ServerSocket socketServer;

    private final ExecutorService executorService;

    private final Router router;

    private boolean running = false;

    public Server(int port, Router router) throws IOException {
        this.socketServer = new ServerSocket(port);
        this.router = router;
        this.executorService = Executors.newFixedThreadPool(100);
    }

    public void start() throws IOException {
        running = true;
        while(running) {
            Socket clientSocket = socketServer.accept();
            Client client = new Client(clientSocket, router);
            executorService.submit(client);
        }
    }

    public void stop() throws IOException{
        running = false;
        socketServer.close();
    }
}
