package http.config;

import http.Router;
import http.service.SessionService;
import http.service.UserService;

public abstract class Configuration {

    public abstract int serverPortConfig();

    public abstract Router routerConfig();

    public abstract SessionService sessionServiceConfig();

    public abstract UserService userServiceConfig();
}
