package com.bigdata.products.autoloan.service;

import com.bigdata.products.autoloan.model.dto.AutoLoanProduct;
import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.common.CommonUtils;
import org.springframework.stereotype.Service;

@Service
public class AutoLoanUtils implements CommonUtils<AutoLoanProduct, AutoLoanEntity> {

    public AutoLoanEntity mapToEntity(AutoLoanProduct request) {
        return AutoLoanEntity.builder()
                .name(request.getName())
                .minLoanAmount(request.getMinAmount())
                .maxLoanAmount(request.getMaxAmount())
                .minLoanTerm(request.getMinTerm())
                .maxLoanTerm(request.getMaxTerm())
                .minLoanRate(request.getMinRate())
                .url(request.getUrl())
                .comment(request.getComment())
                .mileage(request.getMileage())
                .build();
    }

    public AutoLoanProduct mapToDto(AutoLoanEntity autoLoan) {
        return AutoLoanProduct.builder()
                .name(autoLoan.getName())
                .minAmount(autoLoan.getMinLoanAmount())
                .maxAmount(autoLoan.getMaxLoanAmount())
                .minTerm(autoLoan.getMinLoanTerm())
                .maxTerm(autoLoan.getMaxLoanTerm())
                .minRate(autoLoan.getMinLoanRate())
                .url(autoLoan.getUrl())
                .comment(autoLoan.getComment())
                .mileage(autoLoan.getMileage())
                .build();
    }

    public void update(AutoLoanEntity oldEntity, AutoLoanEntity newEntity) {
        oldEntity.setName(newEntity.getName());
        oldEntity.setMinLoanAmount(newEntity.getMinLoanAmount());
        oldEntity.setMaxLoanAmount(newEntity.getMaxLoanAmount());
        oldEntity.setMinLoanTerm(newEntity.getMinLoanTerm());
        oldEntity.setMaxLoanTerm(newEntity.getMaxLoanTerm());
        oldEntity.setMinLoanRate(newEntity.getMinLoanRate());
        oldEntity.setUrl(newEntity.getUrl());
        oldEntity.setComment(newEntity.getComment());
        oldEntity.setMileage(newEntity.getMileage());
    }
}
