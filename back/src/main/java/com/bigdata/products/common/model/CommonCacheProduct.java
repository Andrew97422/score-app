package com.bigdata.products.common.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CommonCacheProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private LendingType lendingType;

    private byte[] product;
}
