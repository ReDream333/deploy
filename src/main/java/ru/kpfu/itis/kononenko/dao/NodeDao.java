package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NodeDao extends AbstractDao<Node>{

    //language=sql

    private static final String SQL_SAVE = """
            INSERT
            INTO nodes(tree_id, first_name, last_name, surname, gender, birth_date, death_date, biography)
            values (?, ?, ?, ?, ?, ?, ?, ?);
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
            preparedStatement.setLong(5, node.gender());
            preparedStatement.setDate(6, node.birthDate());
            preparedStatement.setDate(7, node.deathDate());
            preparedStatement.setString(8, node.biography());

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
}
