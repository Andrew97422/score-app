package com.bigdata.products.consumer.service;

import com.bigdata.products.common.CommonUtils;
import com.bigdata.products.consumer.model.dto.ConsumerProduct;
import com.bigdata.products.consumer.model.entity.ConsumerEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsumerUtils implements CommonUtils<ConsumerProduct, ConsumerEntity> {
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
                .startDate(request.getStartDate())
                .build();
    }

    public ConsumerProduct mapToDto(ConsumerEntity consumerEntity) {
        return ConsumerProduct.builder()
                .name(consumerEntity.getName())
                .minAmount(consumerEntity.getMinLoanAmount())
                .maxAmount(consumerEntity.getMaxLoanAmount())
                .minTerm(consumerEntity.getMinLoanTerm())
                .maxTerm(consumerEntity.getMaxLoanTerm())
                .minRate(consumerEntity.getMinLoanRate())
                .url(consumerEntity.getUrl())
                .comment(consumerEntity.getComment())
                .discount(consumerEntity.getDiscount())
                .startDate(consumerEntity.getStartDate())
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
        oldEntity.setStartDate(newEntity.getStartDate());
    }
}
