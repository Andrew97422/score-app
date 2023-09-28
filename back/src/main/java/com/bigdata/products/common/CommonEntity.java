package com.bigdata.products.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class CommonEntity implements Comparable<CommonEntity> {

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

    @Override
    public int compareTo(@NotNull CommonEntity o) {
        int diff = (int) (getMinLoanRate() - o.getMinLoanRate());
        if (diff != 0) {
            return diff;
        } else {
            return (int) (o.getMinLoanTerm() - getMinLoanTerm());
        }
    }
}
