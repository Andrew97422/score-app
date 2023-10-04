package com.bigdata.products.consumer.model.entity;

import com.bigdata.products.common.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

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

}
