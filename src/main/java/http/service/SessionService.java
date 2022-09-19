package http.service;

import http.entity.Session;
import http.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionService {
    Optional<Session> findSessionByUserId(UUID userId);
    void addSession(UUID userId);
    void deleteSessionByUserId(UUID userId);
}
