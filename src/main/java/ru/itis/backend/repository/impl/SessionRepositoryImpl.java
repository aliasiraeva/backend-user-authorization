package ru.itis.backend.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.backend.JdbcTemplate;
import ru.itis.backend.entity.Session;
import ru.itis.backend.mapper.RowMapper;
import ru.itis.backend.repository.SessionRepository;

import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Session> rowMapper;;

    public SessionRepositoryImpl(DataSource dataSource, RowMapper<Session> rowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = rowMapper;
    }

    private static final String FIND_ALL_BY_TOKEN = "select * from sessions where token = ?";
    private static final String ADD_SESSION = """
            insert into sessions (user_id, token)
            values (?, ?)
            """;
    private static final String DELETE_BY_TOKEN = "delete from sessions where token = ?";
    @Override
    public List<Session> findAllByToken(String token) {
        return jdbcTemplate.execute(FIND_ALL_BY_TOKEN, rowMapper, token);
    }

    @Override
    public void addSession(Session session) {
        jdbcTemplate.update(ADD_SESSION, session.getUserId(), session.getToken());
    }

    @Override
    public void deleteByToken(String token) {
        jdbcTemplate.update(DELETE_BY_TOKEN, token);
    }
}
