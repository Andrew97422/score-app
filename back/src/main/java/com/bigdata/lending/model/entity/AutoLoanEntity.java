package com.bigdata.lending.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "auto_loan")
public class AutoLoanEntity extends GuideEntity {

    @Column(name = "car_mileage")
    private String mileage;

    public String toMailString() {
        return super.toMailString() + "\nПредлагаемый пробег - " + mileage;
    }
}
