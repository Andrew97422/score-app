package com.bigdata.lending.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mortgage")
public class MortgageEntity extends GuideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "addition_to_interest")
    private String addToInterest;

    @Column(name = "down_payment")
    private String downPayment;

    public String toMailString() {
        return super.toMailString() + "\nПредлагаемая надбавка к % - " + addToInterest
                + "\nПервый взнос - " + downPayment;
    }
}
