package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class ShortenedLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Integer dayOfTheWeek;
    private boolean mandatory;

    public ShortenedLecture(String name, Integer dayOfTheWeek, boolean mandatory) {
        this.name = name;
        this.dayOfTheWeek = dayOfTheWeek;
        this.mandatory = mandatory;
    }
}

