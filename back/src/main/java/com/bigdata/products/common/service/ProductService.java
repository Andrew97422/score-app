package com.bigdata.products.common.service;

import com.bigdata.products.common.model.CommonEntity;

import java.util.List;

public interface ProductService {
    List<CommonEntity> guides(float creditAmount);
}
