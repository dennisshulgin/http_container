package http.config;

import http.Router;
import http.entity.User;
import http.service.SessionService;
import http.service.SessionServiceImpl;
import http.service.UserService;
import http.service.UserServiceImpl;

import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

public interface Configuration {
    Properties propertiesConfig();
    Router routerConfig();
}
