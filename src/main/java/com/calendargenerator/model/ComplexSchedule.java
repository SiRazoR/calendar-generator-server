package com.calendargenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@ApiObject(description = "ComplexSchedule is an object that contains many groups. You can concatenate for example 3 groups into one schedule")
public class ComplexSchedule implements Schedule {
    @Id
    @ApiObjectField(description = "Randomly generated UUID that identifies complex schedule", required = true)
    private UUID id = UUID.randomUUID();
    @ApiObjectField(description = "List of SimpleSchedule", required = true)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SimpleSchedule.class)
    private List<SimpleSchedule> groups;

    public ComplexSchedule(List<SimpleSchedule> groups) {
        this.groups = groups;
    }
}

