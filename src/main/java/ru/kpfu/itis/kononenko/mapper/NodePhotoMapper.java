package ru.kpfu.itis.kononenko.mapper;

import ru.kpfu.itis.kononenko.entity.NodePhoto;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NodePhotoMapper implements RowMapper<NodePhoto> {
    @Override
    public NodePhoto mapRow(ResultSet resultSet) throws SQLException {
        return new NodePhoto(
                resultSet.getLong("id"),
                resultSet.getLong("node_id"),
                resultSet.getString("photo_url"),
                resultSet.getString("description")
        );
    }
}
