package ru.kpfu.itis.kononenko.mapper;

import ru.kpfu.itis.kononenko.entity.Marriage;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MarriageMapper implements RowMapper<Marriage> {
    @Override
    public Marriage mapRow(ResultSet resultSet) throws SQLException {
        return new Marriage(
                resultSet.getLong("id"),
                resultSet.getLong("spouse_male"),
                resultSet.getLong("spouse_female"),
                //FIXME тут либо так либо resultSet.getDate
                Date.valueOf(resultSet.getObject("start_date", LocalDate.class)),
                Date.valueOf(resultSet.getObject("end_date", LocalDate.class))
        );
    }
}
