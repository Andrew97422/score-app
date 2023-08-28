package com.bigdata.application.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "current_debt_load")
public class CurrentDebtLoadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "count_active_loans")
    private int countActiveLoans;

    @Column(name = "amount_of_monthly_loan_payments")
    private int amountLoanPayments;

    @Column(name = "monthly_income")
    private int monthlyIncome;
}
