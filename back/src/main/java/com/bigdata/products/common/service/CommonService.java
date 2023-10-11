package com.bigdata.products.common.service;

import com.bigdata.products.common.model.CommonProduct;

import java.util.List;

public interface CommonService<A extends CommonProduct> {
    Integer registerNewLending(A a);

    void deleteById(Integer id);

    A getById(Integer id);

    void updateById(Integer id, A a);

    void addProductToSchedulingToSetActive();

    void addProductToSchedulingToSetNotActive();

    void changeActive(List<Integer> ids, boolean active);

    List<A> getAllProducts();
}
