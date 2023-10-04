package com.bigdata.products.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommonController<A extends CommonProduct> {
    ResponseEntity<Integer> registerLending(A a);

    ResponseEntity<HttpStatus> deleteLending(Integer id);

    ResponseEntity<A> getLending(Integer id);

    ResponseEntity<Integer> updateLending(Integer id, A a);
}
