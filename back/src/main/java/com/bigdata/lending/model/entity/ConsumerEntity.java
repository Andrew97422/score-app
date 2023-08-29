package com.bigdata.lending.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "consumer")
public class ConsumerEntity extends GuideEntity {

    @Column(name = "discount")
    private String discount;
}
