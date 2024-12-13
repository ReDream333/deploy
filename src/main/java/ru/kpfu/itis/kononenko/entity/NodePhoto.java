package ru.kpfu.itis.kononenko.entity;

public record NodePhoto(
        Long id,
        Long nodeId,
        String photoUrl,
        String description
){}
