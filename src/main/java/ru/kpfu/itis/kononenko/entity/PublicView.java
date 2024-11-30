package ru.kpfu.itis.kononenko.entity;

import java.sql.Timestamp;

public record PublicView(
        int id,
        int treeId,
        int viewerUserId,
        Timestamp viewedAt
) {}

