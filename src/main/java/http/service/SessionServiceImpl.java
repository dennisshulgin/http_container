package http.service;

import http.entity.Session;

import java.util.*;

public class SessionServiceImpl implements SessionService{

    private final HashMap<UUID, Session> sessions = new HashMap<>();

    @Override
    public synchronized Optional<Session> findSessionByUserId(UUID userId) {
        return Optional.of(sessions.get(userId));
    }

    @Override
    public synchronized void addSession(UUID userId) {
        Optional<Session> oldSession = Optional.ofNullable(sessions.get(userId));
        if(oldSession.isPresent()) {
            sessions.remove(userId);
        }
        Session session = new Session(UUID.randomUUID());
        sessions.put(userId, session);
    }

    @Override
    public synchronized void deleteSessionByUserId(UUID userId) {
        sessions.remove(userId);
    }
}
