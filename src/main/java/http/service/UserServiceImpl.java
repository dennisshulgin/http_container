package http.service;

import http.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService{

    private final List<User> users = new ArrayList<>();

    @Override
    public synchronized Optional<User> findUserById(UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public synchronized void addUser(User user) {
        users.add(user);
    }

    @Override
    public synchronized void deleteUserByUserId(UUID id) {
        Optional<User> user = users.stream().filter(u -> u.getId().equals(id)).findFirst();
        user.ifPresent(users::remove);
    }
}
