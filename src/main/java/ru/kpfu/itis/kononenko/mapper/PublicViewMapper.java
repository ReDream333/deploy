package ru.kpfu.itis.kononenko.mapper;

import ru.kpfu.itis.kononenko.entity.PublicView;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicViewMapper implements RowMapper<PublicView> {
    @Override
    public PublicView mapRow(ResultSet resultSet) throws SQLException {
        return new PublicView(
                resultSet.getInt("id"),
                resultSet.getInt("tree_id"),
                resultSet.getInt("viewer_user_id"),
                resultSet.getTimestamp("viewed_at")
        );
    }
}
