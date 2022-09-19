package http.service;

import http.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> findUserById(UUID id);
    void addUser(User user);
    void deleteUserByUserId(UUID id);
}
