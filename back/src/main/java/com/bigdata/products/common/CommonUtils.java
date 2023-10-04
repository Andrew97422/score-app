package com.bigdata.products.common;

public interface CommonUtils<A extends CommonProduct, B extends CommonEntity> {
    B mapToEntity(A a);

    A mapToDto(B b);

    void update(B b1, B b2);
}
