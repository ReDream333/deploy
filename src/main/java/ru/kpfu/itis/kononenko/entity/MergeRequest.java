package ru.kpfu.itis.kononenko.entity;

import java.sql.Timestamp;

public record MergeRequest(
        Long id,
        Long requesterTreeId,
        Long targetTreeId,
        Long commonAncestorId,
        String status,
        Timestamp createdAt
) {}

