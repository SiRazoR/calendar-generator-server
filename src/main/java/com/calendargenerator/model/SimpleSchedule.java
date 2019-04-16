package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@ApiObject(description = "SimpleSchedule is basically schedule for one group")
public class SimpleSchedule implements Schedule {

    @Id
    @ApiObjectField(description = "Randomly generated UUID that identifies simple schedule", required = true)
    private UUID id = UUID.randomUUID();
    @ApiObjectField(description = "UEK group if, for example 140781", required = true)
    private String groupId;
    @ApiObjectField(description = "List of ShortenedLecture that is basically normal lecture but without unnecessary data.", required = true)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = ShortenedLecture.class)
    private List<ShortenedLecture> lecture;

    public SimpleSchedule(String groupId, List<ShortenedLecture> lecture) {
        this.groupId = groupId;
        this.lecture = lecture;
    }
}

