package com.bigdata.products.autoloan.model.entity;

import com.bigdata.products.common.model.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auto_loan")
public class AutoLoanEntity extends CommonEntity {

    @Column(name = "car_mileage")
    private String mileage;

}
