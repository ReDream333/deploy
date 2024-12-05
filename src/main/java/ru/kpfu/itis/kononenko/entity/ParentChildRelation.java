package ru.kpfu.itis.kononenko.entity;

public record ParentChildRelation(
        Long id,
        Long parentId,
        Long childId
) {}
