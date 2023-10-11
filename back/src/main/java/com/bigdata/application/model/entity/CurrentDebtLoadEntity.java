package com.bigdata.application.model.entity;

import com.bigdata.application.model.enums.CountActiveLoans;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "current_debt_load")
public class CurrentDebtLoadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "count_active_loans")
    @Enumerated(EnumType.STRING)
    private CountActiveLoans countActiveLoans;

    @OneToOne
    @JoinColumn(name = "loan_application")
    private LoanApplicationEntity loanApplication;

    @Column(name = "amount_of_monthly_loan_payments")
    private float amountLoanPayments;

    @Column(name = "monthly_income")
    private float monthlyIncome;
}
