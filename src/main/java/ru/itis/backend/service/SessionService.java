package ru.itis.backend.service;

import jakarta.servlet.http.Cookie;

public interface SessionService {

    boolean existSessionByCookie(Cookie[] cookies);

    boolean existByToken(String token);

    void addSession(String username, String token);

    void deleteSessionByToken(String token);

}
