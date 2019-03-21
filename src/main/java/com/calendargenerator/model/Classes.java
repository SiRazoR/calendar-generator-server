package com.calendargenerator.model;


import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;

import java.util.Calendar;
import java.util.UUID;

@Entity
public class Classes {
    @Id
    private UUID id;

    private Calendar startDate;
    private Calendar endDate;
    private String name;
    private String instructor;
    private String location;

    protected Classes() {
        this.id = UUID.randomUUID();
    }

    public Classes(Calendar startDate, Calendar endDate, String name, String instructor, String location) {
        this();
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.instructor = instructor;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
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
                "startDate=" + startDate.getTime()+
                ", endDate=" + endDate.getTime() +
                ", name='" + name + '\'' +
                ", instructor='" + instructor + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
