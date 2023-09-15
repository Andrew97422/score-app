package com.bigdata.application.model.entity;

import com.bigdata.application.model.enums.WorkExperience;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_experience")
@Entity
public class WorkExperienceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private WorkExperience name;

    @OneToOne
    @JoinColumn(name = "loan_application")
    private LoanApplicationEntity loanApplication;
}
