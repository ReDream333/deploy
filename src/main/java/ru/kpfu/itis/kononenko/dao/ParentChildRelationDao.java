package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.entity.ParentChildRelation;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParentChildRelationDao extends AbstractDao<ParentChildRelation>{

    //language=sql
    /*Все дети одного родителя*/
    private static final String SQL_GET_ALL_FOR_USER = """
           SELECT *
           FROM parent_child_relations
           WHERE parent_id = ?;
        """;

    private static final String SQL_SAVE = """
            INSERT
            INTO parent_child_relations(parent_id, child_id)
            values (?, ?);
        """;

    private static final String SQL_GET_ALL_BY_TREE_ID = """
            SELECT parent_child_relations.*
            FROM parent_child_relations
            JOIN nodes ON parent_child_relations.parent_id = nodes.id
            WHERE nodes.tree_id = ?;
        """;

    public ParentChildRelationDao(RowMapper<ParentChildRelation> mapper) {
        this.mapper = mapper;
        this.tableName = "parent_child_relations";
    }


//    List<ParentChildRelation> getAllForOneUser(Long userId) {
//        try {
//            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_FOR_USER);
//            statement.setLong(1, userId);
//            ResultSet resultSet = statement.executeQuery();
//            List<ParentChildRelation> childrenForUser = new ArrayList<>();
//            while (resultSet.next()) {
//                childrenForUser.add(
//                        mapper.mapRow(resultSet)
//                );
//            }
//            return childrenForUser;
//        } catch (SQLException e) {
//            throw new RuntimeException("Пустой resultSet?");
//        }
//    }
    @Override
    public long save(ParentChildRelation parentChildRelation) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, parentChildRelation.parentId());
            preparedStatement.setLong(2, parentChildRelation.childId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Не удалось получить сгенерированный ключ.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Такая связь родитель->ребенок уже есть");
        }
    }

    public List<ParentChildRelation> getRelationsByTreeId(Long treeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_TREE_ID);
            preparedStatement.setLong(1, treeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ParentChildRelation> relations = new ArrayList<>();
            while (resultSet.next()) {
                relations.add(mapper.mapRow(resultSet));
            }
            return relations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
