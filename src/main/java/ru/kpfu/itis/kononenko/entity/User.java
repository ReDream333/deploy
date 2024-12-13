package ru.kpfu.itis.kononenko.entity;

import java.sql.Timestamp;

public record User (
        Long id,
        String username,
        String email,
        String passwordHash,
        Timestamp createdAt,
        String photo
) { }
