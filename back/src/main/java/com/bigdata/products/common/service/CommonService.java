package com.bigdata.products.common.service;

import com.bigdata.products.common.model.CommonProduct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@EnableAsync
public interface CommonService<A extends CommonProduct> {
    Integer registerNewLending(A a);

    void deleteById(Integer id);

    A getById(Integer id);

    void updateById(Integer id, A a);

    @Scheduled(cron = "0 0 * * * ?")
    @Async
    void addProductToSchedulingToSetActive();

    @Scheduled(cron = "0 0 * * * ?")
    @Async
    void addProductToSchedulingToSetNotActive();

    void changeActive(List<Integer> ids, boolean active);

    List<A> getAllProducts();
}
