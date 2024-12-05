package ru.kpfu.itis.kononenko.entity;

import java.sql.Date;

public record Marriage(
        Long id,
        Long spouseMale,
        Long spouseFemale,
        Date startDate,
        Date endDate
) { }
