package ru.itis.backend.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.backend.JdbcTemplate;
import ru.itis.backend.entity.User;
import ru.itis.backend.mapper.RowMapper;
import ru.itis.backend.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper;

    public UserRepositoryImpl(DataSource dataSource, RowMapper<User> rowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = rowMapper;
    }

    private static final String FIND_ALL = "select * from users";
    private static final String FIND_BY_USERNAME = "select * from users where username = ?";
    private static final String ADD_USER = "insert into users (username, password) values (?, ?)";
    @Override
    public List<User> findAll() {
        return jdbcTemplate.execute(FIND_ALL, rowMapper);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> users = jdbcTemplate.execute(FIND_BY_USERNAME, rowMapper, username);
        if (users.size() > 1) {
            throw new RuntimeException("Incorrect results size: " + users.size() + " for username: " + username);
        }
        return users.stream().findAny();
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update(ADD_USER, user.getUsername(), user.getPassword());
    }

}
