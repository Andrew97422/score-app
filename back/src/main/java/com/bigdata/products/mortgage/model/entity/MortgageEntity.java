package com.bigdata.products.mortgage.model.entity;

import com.bigdata.products.common.model.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mortgage")
public class MortgageEntity extends CommonEntity {

    @Column(name = "addition_to_interest")
    private String addToInterest;

    @Column(name = "down_payment")
    private String downPayment;

    @OneToMany
    private List<MortgageCacheEntity> mortgageCache;
}
