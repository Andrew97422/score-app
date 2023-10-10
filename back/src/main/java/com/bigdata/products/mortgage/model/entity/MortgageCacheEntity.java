package com.bigdata.products.mortgage.model.entity;

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
@Table(name = "mortgage_cache")
public class MortgageCacheEntity extends CommonCacheProduct {

    @ManyToOne
    @JoinColumn(name = "mortgage_id")
    private MortgageEntity mortgage;
}
