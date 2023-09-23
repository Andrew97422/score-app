package com.bigdata.lending.service;

import com.bigdata.lending.model.dto.AutoLoanProduct;
import com.bigdata.lending.model.dto.ConsumerProduct;
import com.bigdata.lending.model.dto.MortgageProduct;
import com.bigdata.lending.model.entity.AutoLoanEntity;
import com.bigdata.lending.model.entity.ConsumerEntity;
import com.bigdata.lending.model.entity.MortgageEntity;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
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
}
