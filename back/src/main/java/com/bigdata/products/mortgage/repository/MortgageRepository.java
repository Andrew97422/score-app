package com.bigdata.products.mortgage.repository;

import com.bigdata.products.common.repository.CommonRepository;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MortgageRepository extends CommonRepository<MortgageEntity> {
    @Query(
            value = "SELECT * FROM mortgage WHERE (now() - start_date) < '24 hours' and is_perpetual = false",
            nativeQuery = true
    )
    List<MortgageEntity> findAllSoonActive();

    @Query(
            value = "SELECT * FROM mortgage WHERE (now() - finish_date) < '24 hours' and is_perpetual = false",
            nativeQuery = true
    )
    List<MortgageEntity> findAllSoonNotActive();
}
