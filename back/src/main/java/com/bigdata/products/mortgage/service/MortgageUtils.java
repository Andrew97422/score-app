package com.bigdata.products.mortgage.service;

import com.bigdata.products.common.service.CommonUtils;
import com.bigdata.products.mortgage.model.dto.MortgageProduct;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import org.springframework.stereotype.Service;

@Service
public class MortgageUtils implements CommonUtils<MortgageProduct, MortgageEntity> {

    @Override
    public void mapToEntity(MortgageProduct mortgageProduct, MortgageEntity mortgageEntity) {
        CommonUtils.super.mapToEntity(mortgageProduct, mortgageEntity);
        mortgageEntity.setDownPayment(mortgageProduct.getDownPayment());
        mortgageEntity.setAddToInterest(mortgageProduct.getAddToInterest());
    }

    @Override
    public void mapToDto(MortgageProduct mortgageProduct, MortgageEntity mortgageEntity) {
        CommonUtils.super.mapToDto(mortgageProduct, mortgageEntity);
        mortgageProduct.setAddToInterest(mortgageEntity.getAddToInterest());
        mortgageProduct.setDownPayment(mortgageEntity.getDownPayment());
    }

    @Override
    public void update(MortgageEntity oldEntity, MortgageEntity newEntity) {
        CommonUtils.super.update(oldEntity, newEntity);
        oldEntity.setAddToInterest(newEntity.getAddToInterest());
        oldEntity.setDownPayment(newEntity.getDownPayment());
    }
}
