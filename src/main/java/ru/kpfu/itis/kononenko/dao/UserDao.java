package ru.kpfu.itis.kononenko.dao;


import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User> {

    private final Connection connection = getConnection();

    //language=sql
    private static final String SQL_FIND_BY_ID = """
            SELECT *
            FROM users
            WHERE id = ?
        """;
    private static final String SQL_GET_ALL = """
           SELECT *
           FROM users;
        """;
    private static final String SQL_DELETE_BY_ID = """
            DELETE
            FROM users
            WHERE id = ?
        """;
    private static final String SQL_SAVE = """
            INSERT
            INTO users(username, email, password_hash, created_at)
            values (?, ?, ?, ?);
        """;
    private static final String SQL_FIND_BY_LOGIN = """
            SELECT *
            FROM users
            WHERE username = ?
        """;
    private static final String SQL_DELETE_BY_LOGIN = """
            SELECT *
            FROM users
            WHERE username = ?
        """;

    public UserDao(RowMapper<User> mapper) {
        this.mapper = mapper;
    }

    public User findByLogin(String login) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            //TODO тут похоже везде нужно проверять на resultSet == null
            return resultSet.next() ? mapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException("Юзера с таким логином нет");
        }
    }

    @Override
    public User findById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            //TODO тут похоже везде нужно проверять на resultSet == null
            return resultSet.next() ? mapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException("С таким ИД нет пользователя");
        }
    }

    @Override
    public List<User> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL);
            List<User> users = new ArrayList<>();
            //TODO тут похоже везде нужно проверять на resultSet == null
            while (resultSet.next()) {
                users.add(
                        mapper.mapRow(resultSet)
                );
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Пустой resultSet?");
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Нет с таким ИД");
        }
    }

    public boolean deleteByLogin(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_LOGIN);
            preparedStatement.setString(1, username);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Нет с таким логином");
        }
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE);
            preparedStatement.setString(1, user.username());
            preparedStatement.setString(2, user.email());
            preparedStatement.setString(3, user.passwordHash());
            preparedStatement.setTimestamp(4, user.createdAt());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Такой Юзер уже есть");
        }
    }

}
