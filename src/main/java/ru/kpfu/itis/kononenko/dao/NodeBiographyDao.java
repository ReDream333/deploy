package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.dao.inter.INodeBiographyDao;
import ru.kpfu.itis.kononenko.entity.NodeBiography;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NodeBiographyDao extends AbstractDao<NodeBiography> implements INodeBiographyDao {


    //language=sql

    private static final String SQL_SAVE = """
            INSERT
            INTO nodes_biography(node_id, biography)
            values (?, ?)
            ON CONFLICT (node_id)
            DO UPDATE SET biography = EXCLUDED.biography;
        """;

    private static final String SQL_GET_BY_NODE_ID = """
            SELECT biography
            FROM nodes_biography
            WHERE node_id = ?;
        """;

    private static final String SQL_DELETE_BY_NODE_ID = """
                    DELETE
                    FROM nodes_biography
                    WHERE node_id = ?
                """;

    public NodeBiographyDao(RowMapper<NodeBiography> mapper) {
        this.mapper = mapper;
        this.tableName = "nodes_biography";
    }

    @Override
    public long save(NodeBiography nodeBiography) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, nodeBiography.nodeId());
            preparedStatement.setString(2, nodeBiography.biography());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getByNodeId(Long nodeId) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_NODE_ID);
            statement.setLong(1, nodeId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getString("biography") : "";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByNodeId(Long nodeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_NODE_ID);
            preparedStatement.setLong(1, nodeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
