package com.bigdata.lending.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auto_loan")
public class AutoLoanEntity extends GuideEntity {

    @Column(name = "car_mileage")
    private String mileage;

    public String toMailString() {
        return super.toMailString() + "\nПредлагаемый пробег - " + mileage;
    }
}
