package ru.kpfu.itis.kononenko.dao.inter;

import ru.kpfu.itis.kononenko.entity.Tree;

import java.util.List;

public interface ITreeDao extends ICrud<Tree>{

    List<Tree> getAllForOneUser(Long userId);
    void deleteByUserId(Long userId);
    List<Tree> getPublic();

}
