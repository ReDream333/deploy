package ru.kpfu.itis.kononenko.entity;

import java.sql.Timestamp;

public record Tree(
        int id,
        int userId,
        String name,
        boolean isPrivate,
        int mergedTreeId,
        Timestamp createdAt
) {}
