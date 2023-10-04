package com.bigdata.products.common;

public interface CommonService<A extends CommonProduct> {
    Integer registerNewLending(A a);

    void deleteById(Integer id);

    A getById(Integer id);

    void updateById(Integer id, A a);
}
