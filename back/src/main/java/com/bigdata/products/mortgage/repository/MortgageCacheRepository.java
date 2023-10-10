package com.bigdata.products.mortgage.repository;

import com.bigdata.products.common.repository.CommonCacheRepository;
import com.bigdata.products.mortgage.model.entity.MortgageCacheEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface MortgageCacheRepository extends CommonCacheRepository<MortgageCacheEntity> {
}
