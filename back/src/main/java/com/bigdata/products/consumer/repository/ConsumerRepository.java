package com.bigdata.products.consumer.repository;

import com.bigdata.products.common.repository.CommonRepository;
import com.bigdata.products.consumer.model.entity.ConsumerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerRepository extends CommonRepository<ConsumerEntity> {
    @Query(
            value = "SELECT * FROM consumer WHERE (now() - start_date) < '24 hours'",
            nativeQuery = true
    )
    List<ConsumerEntity> findAllSoonActive();

    @Query(
            value = "SELECT * FROM consumer WHERE (now() - finish_date) < '24 hours'",
            nativeQuery = true
    )
    List<ConsumerEntity> findAllSoonNotActive();
}
