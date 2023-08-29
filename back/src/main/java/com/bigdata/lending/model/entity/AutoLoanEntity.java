package com.bigdata.lending.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "auto_loan")
public class AutoLoanEntity extends GuideEntity {

    @Column(name = "car_mileage")
    private String mileage;
}
