package ru.kpfu.itis.kononenko.mapper;

import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NodeMapper implements RowMapper<Node> {
    @Override
    public Node mapRow(ResultSet resultSet) throws SQLException {
        return new Node(
                resultSet.getLong("id"),
                resultSet.getLong("tree_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("gender").charAt(0),
                resultSet.getDate("birth_date"),
                resultSet.getDate("death_date"),
                resultSet.getString("comment"),
                resultSet.getString("photo")
        );
    }
}
