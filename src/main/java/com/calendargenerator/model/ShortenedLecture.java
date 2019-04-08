package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class ShortenedLecture {

    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private Integer dayOfTheWeek;
    private boolean mandatory;

    public ShortenedLecture(String name, Integer dayOfTheWeek, boolean mandatory) {
        this.name = name;
        this.dayOfTheWeek = dayOfTheWeek;
        this.mandatory = mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortenedLecture that = (ShortenedLecture) o;
        return name.equals(that.name) &&
                dayOfTheWeek.equals(that.dayOfTheWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dayOfTheWeek);
    }
}

