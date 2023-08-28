package com.bigdata.lending.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "guide_products")
public abstract class GuideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "min_amount")
    private float minLoanAmount;

    @Column(name = "max_amount")
    private float maxLoanAmount;

    @Column(name = "min_term")
    private float minLoanTerm;

    @Column(name = "max_term")
    private float maxLoanTerm;

    @Column(name = "min_rate")
    private float minLoanRate;

    @Column(name = "url")
    private String url;

    @Column(name = "comment")
    private String comment;
}
