package ru.kpfu.itis.kononenko.mapper;
import ru.kpfu.itis.kononenko.entity.Tree;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TreeMapper implements RowMapper<Tree> {

    @Override
    public Tree mapRow(ResultSet resultSet) throws SQLException {
        return new Tree(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getString("name"),
                resultSet.getBoolean("is_private"),
                resultSet.getTimestamp("created_at")
        );
    }
}


