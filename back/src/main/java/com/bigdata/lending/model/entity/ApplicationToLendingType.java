package com.bigdata.lending.model.entity;

import com.bigdata.application.model.entity.LoanApplicationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ApplicationToLendingType {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "application_id")
    private LoanApplicationEntity loanProductApplication;

    @OneToOne
    @JoinColumn(name = "lending_type_id")
    private LendingTypeEntity lendingType;
}
