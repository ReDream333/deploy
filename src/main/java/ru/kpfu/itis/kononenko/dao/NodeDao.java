package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NodeDao extends AbstractDao<Node>{

    //language=sql

    private static final String SQL_SAVE = """
            INSERT
            INTO nodes(tree_id, first_name, last_name, surname, gender, birth_date, death_date, biography, photo)
            values (?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;
    private static final String SQL_GET_ALL_BY_TREE_ID = """
            SELECT *
            FROM nodes
            WHERE tree_id = ?;
        """;
    private static final String SQL_GET_NAME = """
            SELECT first_name, last_name
            FROM nodes
            WHERE id = ?;
        """;
    private static final String SQL_UPDATE = """
            UPDATE nodes
            SET first_name = ?,last_name = ?, birth_date = ?, death_date = ?, photo = ?
            WHERE id = ?
        """;



    public NodeDao(RowMapper<Node> mapper) {
        this.mapper = mapper;
        this.tableName = "nodes";
    }
    @Override
    public long save(Node node) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, node.treeId());
            preparedStatement.setString(2, node.firstName());
            preparedStatement.setString(3, node.lastName());
            preparedStatement.setString(4, node.surname());
            preparedStatement.setString(5, String.valueOf(node.gender()));
            preparedStatement.setDate(6, node.birthDate());
            preparedStatement.setDate(7, node.deathDate());
            preparedStatement.setString(8, node.biography());
            preparedStatement.setString(9, node.photo());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Не удалось получить сгенерированный ключ.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Такая нода уже есть");
        }
    }


    public List<Node> getNodesByTreeId(Long treeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_TREE_ID);
            preparedStatement.setLong(1, treeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Node> nodes = new ArrayList<>();
            while (resultSet.next()) {
                nodes.add(mapper.mapRow(resultSet));
            }
            return nodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNameById(Long nodeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_NAME);
            preparedStatement.setLong(1, nodeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next()?
                    "%s %s".formatted(
                            resultSet.getString(1),
                            resultSet.getString(2)):
                    null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Node newNode) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

            // Заполняем параметры запроса
            preparedStatement.setString(1, newNode.firstName()); // имя
            preparedStatement.setString(2, newNode.lastName());  // фамилия
            preparedStatement.setDate(3, newNode.birthDate()); // дата рождения
            preparedStatement.setDate(4, newNode.deathDate()); // дата смерти
            preparedStatement.setString(5, newNode.photo()); // ссылка на фото
            preparedStatement.setLong(6, newNode.id());      // ID ноды, по которой делаем обновление

            // Выполняем обновление
            int success = preparedStatement.executeUpdate();
            if (success == 0) {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update node", e);
        }
    }
}
