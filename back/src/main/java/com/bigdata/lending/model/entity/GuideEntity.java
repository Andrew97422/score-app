package com.bigdata.lending.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@MappedSuperclass
public class GuideEntity implements Comparable<GuideEntity> {

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
    public int compareTo(@NotNull GuideEntity o) {
        int diff = (int) (getMinLoanRate() - o.getMinLoanRate());
        if (diff != 0) {
            return diff;
        } else {
            return (int) (o.getMinLoanTerm() - getMinLoanTerm());
        }
    }

    public String toMailString() {
        return "\nНазвание продукта - " + getName() +
                "\nМинимальная сумма кредита - " + getMinLoanAmount() +
                "\nМаксимальная сумма кредита - " + getMaxLoanAmount() +
                "\nМинимальный срок кредита - " + getMinLoanTerm() +
                "\nМаксимальный срок кредита - " + getMaxLoanTerm() +
                "\nМинимальная ставка кредита - " + getMinLoanRate() +
                "\nСсылка на продукт - " + getUrl() +
                "\nКомментарий - " + getComment();
    }
}
