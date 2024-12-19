package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.util.Configuration;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao <T>{

    RowMapper<T> mapper;
    String tableName;

    protected final Connection connection = Configuration.getConnection();

    //language=sql
    private String getSqlFindById() {
        String SQL_FIND_BY_ID = """
                    SELECT *
                    FROM %s
                    WHERE id = ?
                """;
        return SQL_FIND_BY_ID.formatted(tableName);
    }

    //language=sql
    private String getSqlGetAll() {
        String SQL_GET_ALL = """
                   SELECT *
                   FROM %s;
                """;
        return SQL_GET_ALL.formatted(tableName);
    }

    //language=sql
    private String getSqlDeleteById() {
        String SQL_DELETE_BY_ID = """
                    DELETE
                    FROM %s
                    WHERE id = ?
                """;
        return SQL_DELETE_BY_ID.formatted(tableName);
    }


    public T findById(Long id){
        try {
            PreparedStatement statement = connection.prepareStatement(getSqlFindById());
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? mapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getAll(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getSqlGetAll());
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(
                        mapper.mapRow(resultSet)
                );
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSqlDeleteById());
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract long save(T entity);
}
