package ru.itis.backend.service.impl;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import ru.itis.backend.entity.Session;
import ru.itis.backend.entity.User;
import ru.itis.backend.repository.SessionRepository;
import ru.itis.backend.service.SessionService;
import ru.itis.backend.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.itis.backend.constants.Constants.SESSION_TOKEN_ATTR;

@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final UserService userService;
    @Override
    public boolean existSessionByCookie(Cookie[] cookies) {
        Optional<String> sessionToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SESSION_TOKEN_ATTR))
                .map(Cookie::getValue)
                .findAny();
        return sessionToken.isPresent() && existByToken(sessionToken.get());
    }

    @Override
    public boolean existByToken(String token) {
        List<Session> sessions = sessionRepository.findAllByToken(token);
        return !sessions.isEmpty();
    }

    @Override
    public void addSession(String username, String token) {
        if (userService.findByUsername(username).isEmpty()) {
            throw new IllegalArgumentException("Некорректное имя пользователя");
        }
        User user = userService.findByUsername(username).get();
        sessionRepository.addSession(Session.builder()
                .userId(user.getId())
                .token(token)
                .build());
    }

    @Override
    public void deleteSessionByToken(String token) {
        sessionRepository.deleteByToken(token);
    }
}
