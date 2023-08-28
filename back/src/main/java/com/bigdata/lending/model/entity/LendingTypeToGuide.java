package com.bigdata.lending.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class LendingTypeToGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "lending_type")
    private LendingTypeEntity lendingType;

    @OneToOne
    @JoinColumn(name = "mortgage")
    private MortgageEntity mortgage;

    @OneToOne
    @JoinColumn(name = "consumer")
    private ConsumerEntity consumer;

    @OneToOne
    @JoinColumn(name = "auto_loan")
    private AutoLoanEntity autoLoan;
}
