package com.bigdata.application.model.entity;

import com.bigdata.application.model.enums.LoanCollateralType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTable(name = "type_loan_collateral")
public class TypeLoanCollateralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private LoanCollateralType name;
}
