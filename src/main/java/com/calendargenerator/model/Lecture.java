package com.calendargenerator.model;

import lombok.Data;

import java.util.Calendar;

@Data
public class Lecture {
    private final Calendar startDateCalendar;
    private final Calendar endDateCalendar;
    private final String name;
    private final String instructor;
    private final String location;
}
