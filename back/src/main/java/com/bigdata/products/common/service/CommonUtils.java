package com.bigdata.products.common.service;

import com.bigdata.products.common.model.CommonEntity;
import com.bigdata.products.common.model.CommonProduct;

public interface CommonUtils<A extends CommonProduct, B extends CommonEntity> {
    default void mapToEntity(A a, B b) {
        b.setName(a.getName());
        b.setMinLoanAmount(a.getMinAmount());
        b.setMaxLoanAmount(a.getMaxAmount());
        b.setMinLoanTerm(a.getMinTerm());
        b.setMaxLoanTerm(a.getMaxTerm());
        b.setMinLoanRate(a.getMinRate());
        b.setUrl(a.getUrl());
        b.setComment(a.getComment());
        b.setStartDate(a.getStartDate());
        b.setFinishDate(a.getFinishDate());
        b.setActive(false);
    }

    default void mapToDto(A a, B b) {
        a.setName(b.getName());
        a.setMinAmount(b.getMinLoanAmount());
        a.setMaxAmount(b.getMaxLoanAmount());
        a.setMinTerm(b.getMinLoanTerm());
        a.setMaxTerm(b.getMaxLoanTerm());
        a.setMinRate(b.getMinLoanRate());
        a.setUrl(b.getUrl());
        a.setComment(b.getComment());
        a.setStartDate(b.getStartDate());
        a.setFinishDate(b.getFinishDate());
        a.setActive(false);
    }

    default void update(B b1, B b2) {
        b1.setName(b2.getName());
        b1.setMinLoanAmount(b2.getMinLoanAmount());
        b1.setMaxLoanAmount(b2.getMaxLoanAmount());
        b1.setMinLoanTerm(b2.getMinLoanTerm());
        b1.setMaxLoanTerm(b2.getMaxLoanTerm());
        b1.setMinLoanRate(b2.getMinLoanRate());
        b1.setUrl(b2.getUrl());
        b1.setComment(b2.getComment());
        b1.setStartDate(b2.getStartDate());
        b1.setFinishDate(b2.getFinishDate());
        b1.setActive(b2.isActive());
    }
}
