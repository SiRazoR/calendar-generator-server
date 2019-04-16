package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@ApiObject(description = "ShortenedLecture represents one lecture. It contains only necessary for LectureScraper informations.")
public class ShortenedLecture {

    @Id
    @ApiObjectField(description = "Randomly generated UUID that identifies shortened schedule", required = true)
    private UUID id = UUID.randomUUID();
    @ApiObjectField(description = "Lecture name, for example Angielski", required = true)
    private String name;
    @ApiObjectField(description = "Lecture day of the week, for example 2(Monday)", required = true)
    private Integer dayOfTheWeek;
    @ApiObjectField(description = "Describes if current lecture is mandatory. If set as false, calendar will be generated without this",
            required = true)
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

