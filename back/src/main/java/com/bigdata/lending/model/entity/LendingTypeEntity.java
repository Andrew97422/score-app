package com.bigdata.lending.model.entity;

import com.bigdata.lending.model.enums.LendingType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "lending_type")
public class LendingTypeEntity {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(value = EnumType.STRING)
    private LendingType lendingType;
}
