package ru.kpfu.itis.kononenko.dao.inter;

import java.util.List;

public interface ICrud<T> {
    T findById(Long id);
    List<T> getAll();
    boolean deleteById(Long id);
    long save(T entity);
}
