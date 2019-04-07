package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class ShortenedLecture {

    @Id
    private long id;
    private String name;
    private Integer dayOfTheWeek;
    private String mandatory;

    public ShortenedLecture(String name, Integer dayOfTheWeek, String mandatory) {
        this.name = name;
        this.dayOfTheWeek = dayOfTheWeek;
        this.mandatory = mandatory;
    }
}

