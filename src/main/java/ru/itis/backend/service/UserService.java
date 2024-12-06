package ru.itis.backend.service;

import ru.itis.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    boolean addUser(String username, String password);

    boolean isAuthorized(String username, String password);
}
