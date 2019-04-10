package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class ComplexSchedule implements Schedule {
    @Id
    private UUID id = UUID.randomUUID();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SimpleSchedule.class)
    private List<SimpleSchedule> groups;

    public ComplexSchedule(List<SimpleSchedule> groups) {
        this.groups = groups;
    }
}

