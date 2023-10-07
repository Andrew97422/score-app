package com.bigdata.products.common.repository;

import com.bigdata.products.common.model.CommonCacheProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonCacheRepository extends JpaRepository<CommonCacheProduct, Integer> {
}
