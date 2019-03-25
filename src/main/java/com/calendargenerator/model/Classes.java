package com.calendargenerator.model;


import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.Calendar;
import java.util.UUID;

@Entity
public class Classes {
    @Id
    private UUID id;

    private Calendar startDateCalendar;
    private Calendar endDateCalendar;
    private String name;
    private String instructor;
    private String location;

    public Classes() {
        this.id = UUID.randomUUID();
    }

    public Classes(Calendar startDate, Calendar endDate, String name, String instructor, String location) {
        this();
        this.startDateCalendar = startDate;
        this.endDateCalendar = endDate;
        this.name = name;
        this.instructor = instructor;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public Calendar getStartDateCalendar() {
        return startDateCalendar;
    }

    public Calendar getEndDateCalendar() {
        return endDateCalendar;
    }

    public String getName() {
        return name;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "\nLecture{" +
                "startDate=" + startDateCalendar.getTime()+
                ", endDate=" + endDateCalendar.getTime() +
                ", name='" + name + '\'' +
                ", instructor='" + instructor + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
