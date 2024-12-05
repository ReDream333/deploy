package ru.kpfu.itis.kononenko.entity;

import java.sql.Date;

public record Node(
        Long id,
        Long treeId,
        String firstName,
        String lastName,
        String surname,
        char gender,
        Date birthDate,
        Date deathDate,
        String biography
) {}
