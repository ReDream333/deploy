package ru.kpfu.itis.kononenko.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public record Node(
        Long id,
        Long treeId,
        String firstName,
        String lastName,
        String surname,
        char gender,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date birthDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date deathDate,
        String comment,
        String photo
) {}
