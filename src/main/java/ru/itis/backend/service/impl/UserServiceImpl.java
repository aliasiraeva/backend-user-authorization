package ru.itis.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.backend.entity.User;
import ru.itis.backend.repository.UserRepository;
import ru.itis.backend.service.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean addUser(String username, String password) {
        Optional<User> existUser = userRepository.findByUsername(username);
        if (existUser.isPresent()) {
            return false;
        }
        String passwordHash = bCryptPasswordEncoder.encode(password);
        userRepository.addUser(User.builder()
                .username(username)
                .password(passwordHash)
                .build());
        return true;
    }

    @Override
    public boolean isAuthorized(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            String passwordHash = user.get().getPassword();
            return bCryptPasswordEncoder.matches(password, passwordHash);
        }
        return false;
    }
}
