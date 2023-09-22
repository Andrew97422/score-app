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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consumer")
public class ConsumerEntity extends GuideEntity {

    @Column(name = "discount")
    private String discount;

    public String toMailString() {
        return super.toMailString() + "\nПредлагаемая скидка - " + discount;
    }
}
