package com.bigdata.lending.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mortgage")
public class MortgageEntity extends GuideEntity {
    @Column(name = "addition_to_interest")
    private String addToInterest;

    @Column(name = "down_payment")
    private String downPayment;
}
