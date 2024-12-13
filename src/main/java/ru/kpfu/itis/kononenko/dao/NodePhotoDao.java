package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.entity.NodePhoto;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NodePhotoDao extends AbstractDao<NodePhoto>{

    //language=sql

    private static final String SQL_SAVE = """
            INSERT
            INTO node_photos(node_id, photo_url, description)
            values (?, ?, ?);
        """;

    private static final String SQL_GET_ALL_FOR_NODE = """
            SELECT *
            FROM node_photos
            WHERE node_id = ?;
        """;

    private static final String SQL_UPDATE = """
            UPDATE node_photos
            SET description= ?
            WHERE id = ?
        """;

    public NodePhotoDao(RowMapper<NodePhoto> mapper) {
        this.mapper = mapper;
        this.tableName = "node_photos";
    }

    @Override
    public long save(NodePhoto nodePhoto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, nodePhoto.nodeId());
            preparedStatement.setString(2, nodePhoto.photoUrl());
            preparedStatement.setString(3, nodePhoto.description());

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
//
//    public List<String> getAllForOneNode(Long nodeId) {
//        try {
//            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_FOR_NODE);
//            statement.setLong(1, nodeId);
//            ResultSet resultSet = statement.executeQuery();
//            List<String> photosForNode = new ArrayList<>();
//            while (resultSet.next()) {
//                photosForNode.add(
//                        resultSet.getString("photo_url")
//                );
//            }
//            return photosForNode;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public List<NodePhoto> getPhotosForOneNode(Long nodeId) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_FOR_NODE);
            statement.setLong(1, nodeId);
            ResultSet resultSet = statement.executeQuery();
            List<NodePhoto> photosForNode = new ArrayList<>();
            while (resultSet.next()) {
                photosForNode.add(mapper.mapRow(resultSet));
            }
            return photosForNode;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDescription(Long id, String description) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

            // Заполняем параметры запроса
            preparedStatement.setString(1, description);
            preparedStatement.setLong(2, id);


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
