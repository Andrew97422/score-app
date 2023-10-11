package com.bigdata.products.common;

import com.bigdata.products.common.model.CommonProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommonController<A extends CommonProduct> {
    ResponseEntity<Integer> registerLending(A a);

    ResponseEntity<HttpStatus> deleteLending(Integer id);

    ResponseEntity<A> getLending(Integer id);

    ResponseEntity<Integer> updateLending(Integer id, A a);

    ResponseEntity<List<A>> getAllProducts();
}
