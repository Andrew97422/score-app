package com.bigdata.products.autoloan.service;

import com.bigdata.products.autoloan.model.dto.AutoLoanProduct;
import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.common.CommonUtils;
import org.springframework.stereotype.Service;

@Service
public class AutoLoanUtils implements CommonUtils<AutoLoanProduct, AutoLoanEntity> {

    @Override
    public void mapToEntity(AutoLoanProduct request, AutoLoanEntity entity) {
        CommonUtils.super.mapToEntity(request, entity);
        entity.setMileage(request.getMileage());
    }

    @Override
    public void mapToDto(AutoLoanProduct product, AutoLoanEntity autoLoan) {
        CommonUtils.super.mapToDto(product, autoLoan);
        autoLoan.setMileage(product.getMileage());
    }

    @Override
    public void update(AutoLoanEntity oldEntity, AutoLoanEntity newEntity) {
        CommonUtils.super.update(oldEntity, newEntity);
        oldEntity.setMileage(newEntity.getMileage());
    }
}
