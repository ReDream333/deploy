package ru.kpfu.itis.kononenko.entity;

import java.sql.Timestamp;

public record MergeRequest(
        int id,
        int requesterTreeId,
        int targetTreeId,
        int commonAncestorId,
        String status,
        Timestamp createdAt
) {}

