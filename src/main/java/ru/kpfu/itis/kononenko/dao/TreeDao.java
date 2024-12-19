package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.dao.inter.ITreeDao;
import ru.kpfu.itis.kononenko.entity.Tree;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreeDao extends AbstractDao<Tree> implements ITreeDao {

    //language=sql
    private static final String SQL_GET_ALL_FOR_USER = """
           SELECT *
           FROM trees
           WHERE user_id = ?;
        """;

    private static final String SQL_SAVE = """
            INSERT
            INTO trees(user_id, name, is_private, created_at)
            values (?, ?, ?, ?);
        """;

    private static final String SQL_DELETE_BY_USER = """
            DELETE
            FROM trees
            WHERE user_id = ?;
        """;
    private static final String SQL_PUBLIC = """
           SELECT *
           FROM trees
           WHERE is_private = false;
        """;


    public TreeDao(RowMapper<Tree> mapper) {
        this.mapper = mapper;
        this.tableName = "trees";
    }


    //все для одного пользователя
    public List<Tree> getAllForOneUser(Long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_FOR_USER);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Tree> treesForUser = new ArrayList<>();
            while (resultSet.next()) {
                treesForUser.add(
                        mapper.mapRow(resultSet)
                );
            }
            return treesForUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override

    public long save(Tree tree) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, tree.userId());
            preparedStatement.setString(2, tree.name());
            preparedStatement.setBoolean(3, tree.isPrivate());
            preparedStatement.setTimestamp(4, tree.createdAt());

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

    public void deleteByUserId(Long userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_USER);
            preparedStatement.setLong(1, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tree> getPublic() {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_PUBLIC);
            ResultSet resultSet = statement.executeQuery();
            List<Tree> publicTrees = new ArrayList<>();
            while (resultSet.next()) {
                publicTrees.add(
                        mapper.mapRow(resultSet)
                );
            }
            return publicTrees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
