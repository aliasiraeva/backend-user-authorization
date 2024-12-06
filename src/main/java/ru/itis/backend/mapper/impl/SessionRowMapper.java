package ru.itis.backend.mapper.impl;

import ru.itis.backend.entity.Session;
import ru.itis.backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionRowMapper implements RowMapper<Session> {
    @Override
    public List<Session> mapRow(ResultSet resultSet) throws SQLException {
        List<Session> sessions = new ArrayList<>();
        while (resultSet.next()) {
            sessions.add(Session.builder()
                    .userId(resultSet.getLong("user_id"))
                    .token(resultSet.getString("token"))
                    .build());
        }
        return sessions;
    }
}
