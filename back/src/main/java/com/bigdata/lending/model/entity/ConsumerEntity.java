package com.bigdata.lending.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "consumer")
public class ConsumerEntity extends GuideEntity {

    @Column(name = "discount")
    private String discount;

    public String toMailString() {
        return super.toMailString() + "\nПредлагаемая скидка - " + discount;
    }
}
