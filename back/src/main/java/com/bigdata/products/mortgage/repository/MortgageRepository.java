package com.bigdata.products.mortgage.repository;

import com.bigdata.products.common.CommonRepository;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface MortgageRepository extends CommonRepository<MortgageEntity> {
}
