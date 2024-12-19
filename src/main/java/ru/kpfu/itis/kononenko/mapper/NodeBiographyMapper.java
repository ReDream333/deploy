package ru.kpfu.itis.kononenko.mapper;



import ru.kpfu.itis.kononenko.entity.NodeBiography;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NodeBiographyMapper implements RowMapper<NodeBiography> {
    @Override
    public NodeBiography mapRow(ResultSet resultSet) throws SQLException {
        return new NodeBiography(
                resultSet.getLong("id"),
                resultSet.getLong("node_id"),
                resultSet.getString("biography")
        );
    }
}
