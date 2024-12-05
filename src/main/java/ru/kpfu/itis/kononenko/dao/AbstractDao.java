package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.util.Configuration;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao <T> {

    RowMapper<T> mapper;
    String tableName;

    protected final Connection connection = Configuration.getConnection();

    //language=sql
    private final String SQL_FIND_BY_ID = """
            SELECT *
            FROM %s
            WHERE id = ?
        """.formatted(tableName);
    private final String SQL_GET_ALL = """
           SELECT *
           FROM %s;
        """.formatted(tableName);
    private final String SQL_DELETE_BY_ID = """
            DELETE
            FROM %s
            WHERE id = ?
        """.formatted(tableName);


    public T findById(Long id){
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            //TODO тут похоже везде нужно проверять на resultSet == null
            return resultSet.next() ? mapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException("Не можем найти. С таким ИД нет");
        }
    };

    public List<T> getAll(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL);
            List<T> result = new ArrayList<>();
            //TODO тут похоже везде нужно проверять на resultSet == null
            while (resultSet.next()) {
                result.add(
                        mapper.mapRow(resultSet)
                );
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Пустой resultSet?");
        }
    };

    public boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Нечего удалять. Нет с таким ИД");
        }
    }

    public abstract long save(T entity);

//    //TODO может мне и не нужен этот метод,
//    // елси у меня единственная переменная connection и она protected
//    // - значит, в других классах просто не будет этого cjnnection создаваться
//    public Connection getConnection() {
//        return Configuration.getConnection();
//    }
}
