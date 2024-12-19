package ru.kpfu.itis.kononenko.dao.inter;

import ru.kpfu.itis.kononenko.entity.ParentChildRelation;

import java.util.List;

public interface IParentChildRelationDao extends ICrud<ParentChildRelation>{
    List<ParentChildRelation> getRelationsByTreeId(Long treeId);
}
