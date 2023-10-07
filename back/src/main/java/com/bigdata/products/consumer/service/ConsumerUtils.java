package com.bigdata.products.consumer.service;

import com.bigdata.products.common.service.CommonUtils;
import com.bigdata.products.consumer.model.dto.ConsumerProduct;
import com.bigdata.products.consumer.model.entity.ConsumerEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsumerUtils implements CommonUtils<ConsumerProduct, ConsumerEntity> {

    @Override
    public void mapToEntity(ConsumerProduct request, ConsumerEntity entity) {
        CommonUtils.super.mapToEntity(request, entity);
        entity.setDiscount(request.getDiscount());
    }

    @Override
    public void mapToDto(ConsumerProduct product, ConsumerEntity consumerEntity) {
        CommonUtils.super.mapToDto(product, consumerEntity);
        product.setDiscount(consumerEntity.getDiscount());
    }

    @Override
    public void update(ConsumerEntity oldEntity, ConsumerEntity newEntity) {
        CommonUtils.super.update(oldEntity, newEntity);
        oldEntity.setDiscount(newEntity.getDiscount());
    }
}
