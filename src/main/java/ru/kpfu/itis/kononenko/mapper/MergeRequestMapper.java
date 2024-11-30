package ru.kpfu.itis.kononenko.mapper;

import ru.kpfu.itis.kononenko.entity.MergeRequest;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MergeRequestMapper implements RowMapper<MergeRequest> {


    @Override
    public MergeRequest mapRow(ResultSet resultSet) throws SQLException {
        return new MergeRequest(
                resultSet.getInt("id"),
                resultSet.getInt("requester_tree_id"),
                resultSet.getInt("target_tree_id"),
                resultSet.getInt("common_ancestor_id"),
                resultSet.getString("status"),
                resultSet.getTimestamp("created_at")
        );
    }
}
