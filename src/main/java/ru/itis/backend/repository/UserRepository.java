package ru.itis.backend.repository;

import ru.itis.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findByUsername(String username);
    void addUser(User user);
}
