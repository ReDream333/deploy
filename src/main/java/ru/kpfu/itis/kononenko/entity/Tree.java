package ru.kpfu.itis.kononenko.entity;

import java.sql.Timestamp;

public record Tree(
        Long id,
        Long userId,
        String name,
        boolean isPrivate,
        Timestamp createdAt
) {}
