package ru.kpfu.itis.kononenko.service;

import ru.kpfu.itis.kononenko.dao.TreeDao;
import ru.kpfu.itis.kononenko.entity.Tree;
import ru.kpfu.itis.kononenko.util.Configuration;


import java.sql.Timestamp;

public class TreeService {
    private static final TreeDao treeDao = Configuration.getTreeDao();

    public long createTree(Long userId, String name, boolean isPrivate, Timestamp createdAt) {
        Tree tree = new Tree(
                null,
                userId,
                name,
                isPrivate,
                null,
                createdAt
        );
        return treeDao.save(tree);
    }
}
