package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class UekGroup {
    @Id
    private UUID id = UUID.randomUUID();
    private String groupId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = ShortenedLecture.class)
    private List<ShortenedLecture> lecture;

    public UekGroup(String groupId, List<ShortenedLecture> lecture) {
        this.groupId = groupId;
        this.lecture = lecture;
    }
}

