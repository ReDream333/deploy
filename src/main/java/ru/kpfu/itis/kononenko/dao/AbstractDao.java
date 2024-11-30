package ru.kpfu.itis.kononenko.dao;

import ru.kpfu.itis.kononenko.util.Configuration;
import ru.kpfu.itis.kononenko.mapper.inter.RowMapper;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao <T> {

    RowMapper<T> mapper;

    abstract T findById(Long id);

    abstract List<T> getAll();

    abstract boolean deleteById(Long id);

    abstract boolean save(T entity);


    public Connection getConnection() {
        return Configuration.getConnection();
    }
}
