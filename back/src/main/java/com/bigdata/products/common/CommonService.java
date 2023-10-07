package com.bigdata.products.common;

import java.util.List;

public interface CommonService<A extends CommonProduct> {
    Integer registerNewLending(A a);

    void deleteById(Integer id);

    A getById(Integer id);

    void updateById(Integer id, A a);

    void addProductToSchedulingToSetActive();

    void addProductToSchedulingToSetNotActive();

    void makeActive(List<Integer> ids);

    void makeNotActive(List<Integer> ids);
}
