package ru.kpfu.itis.kononenko.entity;

import java.sql.Date;

public record Node(
        int id,
        int treeId,
        String firstName,
        String lastName,
        String surname,
        char gender,
        Date birthDate,
        Date deathDate,
        String biography
) {}
