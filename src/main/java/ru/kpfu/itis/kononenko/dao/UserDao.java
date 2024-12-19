package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.dao.inter.IUserDao;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.*;

public class UserDao extends AbstractDao<User> implements IUserDao {

    //language=sql
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

    private static final String SQL_FIND_BY_LOGIN_AND_PASSWORD = """
            SELECT *
            FROM users
            WHERE username = ? AND password_hash = ?
        """;
    private static final String SQL_DELETE_BY_LOGIN = """
            SELECT *
            FROM users
            WHERE username = ?
        """;

    private static final String SQL_UPDATE_LOGIN = """
            UPDATE users
            SET username = ?
            WHERE username = ?
        """;

    private static final String SQL_UPDATE_PHOTO = """
            UPDATE users
            SET photo = ?
            WHERE id = ?
        """;



    public UserDao(RowMapper<User> mapper) {
        this.mapper = mapper;
        this.tableName = "users";
    }

    @Override
    public void updateUser(String newName, String tempName) {
        try (
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_LOGIN)) {
            statement.setString(1, newName);
            statement.setString(2, tempName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? mapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteByLogin(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_LOGIN);
            preparedStatement.setString(1, username);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.username());
            preparedStatement.setString(2, user.email());
            preparedStatement.setString(3, user.passwordHash());
            preparedStatement.setTimestamp(4, user.createdAt());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Не удалось получить сгенерированный ключ.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserPhoto(long userId, String photoUrl) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PHOTO)) {

            preparedStatement.setString(1, photoUrl);
            preparedStatement.setLong(2, userId);

            int success = preparedStatement.executeUpdate();
            if (success == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByLoginAndPassword(String login, String hashPassword) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, hashPassword);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? mapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
