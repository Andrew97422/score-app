package com.bigdata.products.consumer.model.entity;

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
@Table(name = "consumer")
public class ConsumerEntity extends CommonEntity {

    @Column(name = "discount")
    private String discount;

    @OneToMany
    private List<ConsumerCacheEntity> consumerCache;
}
