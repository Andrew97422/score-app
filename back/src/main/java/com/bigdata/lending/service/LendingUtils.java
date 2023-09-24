package com.bigdata.lending.service;

import com.bigdata.lending.model.dto.AutoLoanProduct;
import com.bigdata.lending.model.dto.ConsumerProduct;
import com.bigdata.lending.model.dto.MortgageProduct;
import com.bigdata.lending.model.entity.AutoLoanEntity;
import com.bigdata.lending.model.entity.ConsumerEntity;
import com.bigdata.lending.model.entity.MortgageEntity;
import org.springframework.stereotype.Service;

@Service
public class LendingUtils {
    public ConsumerEntity mapToEntity(ConsumerProduct request) {
        return ConsumerEntity.builder()
                .name(request.getName())
                .minLoanAmount(request.getMinAmount())
                .maxLoanAmount(request.getMaxAmount())
                .minLoanTerm(request.getMinTerm())
                .maxLoanTerm(request.getMaxTerm())
                .minLoanRate(request.getMinRate())
                .url(request.getUrl())
                .comment(request.getComment())
                .discount(request.getDiscount())
                .build();
    }

    public MortgageEntity mapToEntity(MortgageProduct request) {
        return MortgageEntity.builder()
                .name(request.getName())
                .minLoanAmount(request.getMinLoanAmount())
                .maxLoanAmount(request.getMaxLoanAmount())
                .minLoanTerm(request.getMinLoanTerm())
                .maxLoanTerm(request.getMaxLoanTerm())
                .minLoanRate(request.getMinLoanRate())
                .url(request.getUrl())
                .comment(request.getComment())
                .addToInterest(request.getAddToInterest())
                .downPayment(request.getDownPayment())
                .build();
    }

    public AutoLoanEntity mapToEntity(AutoLoanProduct request) {
        return AutoLoanEntity.builder()
                .name(request.getName())
                .minLoanAmount(request.getMinLoanAmount())
                .maxLoanAmount(request.getMaxLoanAmount())
                .minLoanTerm(request.getMinLoanTerm())
                .maxLoanTerm(request.getMaxLoanTerm())
                .minLoanRate(request.getMinLoanRate())
                .url(request.getUrl())
                .comment(request.getComment())
                .mileage(request.getMileage())
                .build();
    }

    public void update(ConsumerEntity oldEntity, ConsumerEntity newEntity) {
        oldEntity.setName(newEntity.getName());
        oldEntity.setMinLoanAmount(newEntity.getMinLoanAmount());
        oldEntity.setMaxLoanAmount(newEntity.getMaxLoanAmount());
        oldEntity.setMinLoanTerm(newEntity.getMinLoanTerm());
        oldEntity.setMaxLoanTerm(newEntity.getMaxLoanTerm());
        oldEntity.setMinLoanRate(newEntity.getMinLoanRate());
        oldEntity.setUrl(newEntity.getUrl());
        oldEntity.setComment(newEntity.getComment());
        oldEntity.setDiscount(newEntity.getDiscount());
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
