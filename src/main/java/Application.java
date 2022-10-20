import http.*;
import http.config.AppConfiguration;
import http.config.Configuration;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Configuration config = new AppConfiguration();
        Server server = new Server(config);
        server.start();
    }
}
