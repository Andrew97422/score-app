package com.bigdata.products.consumer.repository;

import com.bigdata.products.common.repository.CommonCacheRepository;
import com.bigdata.products.consumer.model.entity.ConsumerCacheEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerCacheRepository extends CommonCacheRepository<ConsumerCacheEntity> {
}
