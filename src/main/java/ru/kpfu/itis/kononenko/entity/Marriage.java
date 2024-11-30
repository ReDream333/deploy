package ru.kpfu.itis.kononenko.entity;

import java.sql.Date;

public record Marriage(
        int id,
        int spouseMale,
        int spouseFemale,
        Date startDate,
        Date endDate
) { }
