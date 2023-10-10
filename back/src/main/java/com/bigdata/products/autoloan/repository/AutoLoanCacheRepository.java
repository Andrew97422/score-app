package com.bigdata.products.autoloan.repository;

import com.bigdata.products.autoloan.model.entity.AutoLoanCacheEntity;
import com.bigdata.products.common.repository.CommonCacheRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoLoanCacheRepository extends CommonCacheRepository<AutoLoanCacheEntity> {
}
