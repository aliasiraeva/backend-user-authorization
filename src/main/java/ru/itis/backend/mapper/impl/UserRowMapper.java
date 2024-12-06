package ru.itis.backend.mapper.impl;

import ru.itis.backend.entity.User;
import ru.itis.backend.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public List<User> mapRow(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(User.builder()
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .build());
        }
        return users;
    }
}
