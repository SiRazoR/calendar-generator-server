package com.calendargenerator.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Data
public class Lecture {
    @Id
    private UUID id = UUID.randomUUID();
    private final Calendar startDateCalendar;
    private final Calendar endDateCalendar;
    private final String name;
    private final String instructor;
    private final String location;
}
