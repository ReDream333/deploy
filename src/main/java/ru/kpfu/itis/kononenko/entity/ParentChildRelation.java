package ru.kpfu.itis.kononenko.entity;

public record ParentChildRelation(
        int id,
        int parentId,
        int childId
) {}
