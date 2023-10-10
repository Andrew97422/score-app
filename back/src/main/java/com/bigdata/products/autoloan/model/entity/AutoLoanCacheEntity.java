package com.bigdata.products.autoloan.model.entity;

import com.bigdata.products.common.model.CommonCacheProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "auto_loan_cache")
public class AutoLoanCacheEntity extends CommonCacheProduct {

    @ManyToOne
    @JoinColumn(name = "auto_loan_id")
    private AutoLoanEntity autoLoan;
}
