package http;

import http.config.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class Server {
    public int port;
    public int threadCount;
    private final Configuration configuration;
    private final ServerSocket socketServer;
    private final ExecutorService executorService;
    private boolean running = false;
    private Router router;
    private Properties servicesConfig;

    private Properties propertiesConfig;

    public Server(Configuration configuration) throws IOException {
        loadServerConfiguration(configuration);
        this.configuration = configuration;
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.socketServer = new ServerSocket(port);
    }

    public void start() throws IOException {
        running = true;
        while(running) {
            Socket clientSocket = socketServer.accept();
            Client client = new Client(clientSocket, router, propertiesConfig, servicesConfig);
            executorService.submit(client);
        }
    }

    public void stop() throws IOException{
        running = false;
        socketServer.close();
    }

    public void loadServerConfiguration(Configuration configuration) {
        this.router = configuration.routerConfig();
        this.servicesConfig = configuration.servicesConfig();
        this.propertiesConfig = configuration.propertiesConfig();
        this.port = this.propertiesConfig
                .contains("port") ?
                (int)configuration.propertiesConfig().get("port") : 8080;
        this.threadCount = this.propertiesConfig
                .contains("threadCount") ?
                (int)configuration.propertiesConfig().get("threadCount") : 100;
        setServicesConfigInServlets(this.router, this.servicesConfig);
    }

    public void setServicesConfigInServlets(Router router, Properties servicesConfig) {
        List<Servlet> servletsList = router.getAllServlets();
        for(Servlet servlet : servletsList) {
            servlet.setServices(servicesConfig);
        }
    }
}
