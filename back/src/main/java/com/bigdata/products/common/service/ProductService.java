package com.bigdata.products.common.service;

import com.bigdata.products.common.model.CommonEntity;
import com.bigdata.products.common.model.LendingType;

import java.util.List;

public interface ProductService {
    List<CommonEntity> guides(float creditAmount, LendingType lendingType);
}
