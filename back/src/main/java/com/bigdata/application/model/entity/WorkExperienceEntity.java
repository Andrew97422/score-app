package com.bigdata.application.model.entity;

import com.bigdata.application.model.enums.WorkExperience;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTable(name = "work_experience")
public class WorkExperienceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private WorkExperience name;
}
