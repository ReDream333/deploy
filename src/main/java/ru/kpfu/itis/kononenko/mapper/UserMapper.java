package ru.kpfu.itis.kononenko.mapper;

import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password_hash"),
                resultSet.getTimestamp("created_at"),
                resultSet.getString("photo")
        );
    }
}
