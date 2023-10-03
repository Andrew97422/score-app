package com.bigdata.products.common;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<CommonEntity> guides(float creditAmount);
}
