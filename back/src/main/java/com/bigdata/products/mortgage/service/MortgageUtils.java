package com.bigdata.products.mortgage.service;

import com.bigdata.products.common.CommonUtils;
import com.bigdata.products.mortgage.model.dto.MortgageProduct;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import org.springframework.stereotype.Service;

@Service
public class MortgageUtils implements CommonUtils<MortgageProduct, MortgageEntity> {

    public MortgageEntity mapToEntity(MortgageProduct mortgageProduct) {
        return MortgageEntity.builder()
                .name(mortgageProduct.getName())
                .minLoanAmount(mortgageProduct.getMinAmount())
                .maxLoanAmount(mortgageProduct.getMaxAmount())
                .minLoanTerm(mortgageProduct.getMinTerm())
                .maxLoanTerm(mortgageProduct.getMaxTerm())
                .minLoanRate(mortgageProduct.getMinRate())
                .url(mortgageProduct.getUrl())
                .comment(mortgageProduct.getComment())
                .addToInterest(mortgageProduct.getAddToInterest())
                .downPayment(mortgageProduct.getDownPayment())
                .build();
    }

    public MortgageProduct mapToDto(MortgageEntity mortgageEntity) {
        return MortgageProduct.builder()
                .name(mortgageEntity.getName())
                .minAmount(mortgageEntity.getMinLoanAmount())
                .maxAmount(mortgageEntity.getMaxLoanAmount())
                .minTerm(mortgageEntity.getMinLoanTerm())
                .maxTerm(mortgageEntity.getMaxLoanTerm())
                .minRate(mortgageEntity.getMinLoanRate())
                .url(mortgageEntity.getUrl())
                .comment(mortgageEntity.getComment())
                .addToInterest(mortgageEntity.getAddToInterest())
                .downPayment(mortgageEntity.getDownPayment())
                .build();
    }

    public void update(MortgageEntity oldEntity, MortgageEntity newEntity) {
        oldEntity.setName(newEntity.getName());
        oldEntity.setMinLoanAmount(newEntity.getMinLoanAmount());
        oldEntity.setMaxLoanAmount(newEntity.getMaxLoanAmount());
        oldEntity.setMinLoanTerm(newEntity.getMinLoanTerm());
        oldEntity.setMaxLoanTerm(newEntity.getMaxLoanTerm());
        oldEntity.setMinLoanRate(newEntity.getMinLoanRate());
        oldEntity.setUrl(newEntity.getUrl());
        oldEntity.setComment(newEntity.getComment());
        oldEntity.setAddToInterest(newEntity.getAddToInterest());
        oldEntity.setDownPayment(newEntity.getDownPayment());
    }

}
