package ru.itis.backend.repository;

import ru.itis.backend.entity.Session;

import java.util.List;

public interface SessionRepository {
    List<Session> findAllByToken(String token);
    void addSession(Session session);
    void deleteByToken(String token);

}
