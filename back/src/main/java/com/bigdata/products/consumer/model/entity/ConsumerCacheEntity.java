package com.bigdata.products.consumer.model.entity;

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
@Table(name = "consumer_cache")
public class ConsumerCacheEntity extends CommonCacheProduct {

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private ConsumerEntity consumer;
}
