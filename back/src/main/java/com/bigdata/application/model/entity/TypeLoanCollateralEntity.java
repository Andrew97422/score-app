package com.bigdata.application.model.entity;

import com.bigdata.application.model.enums.LoanCollateralType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "type_loan_collateral")
@Entity
public class TypeLoanCollateralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private LoanCollateralType name;

    @OneToOne
    @JoinColumn(name = "loan_application")
    private LoanApplicationEntity loanApplication;
}
