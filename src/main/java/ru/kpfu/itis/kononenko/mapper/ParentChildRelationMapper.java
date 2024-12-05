package ru.kpfu.itis.kononenko.mapper;

import ru.kpfu.itis.kononenko.entity.ParentChildRelation;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParentChildRelationMapper implements RowMapper<ParentChildRelation> {

    @Override
    public ParentChildRelation mapRow(ResultSet resultSet) throws SQLException {
        return new ParentChildRelation(
                resultSet.getLong("id"),
                resultSet.getLong("parent_id"),
                resultSet.getLong("child_id")
        );
    }
}
