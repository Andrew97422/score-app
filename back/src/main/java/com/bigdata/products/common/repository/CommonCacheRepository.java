package com.bigdata.products.common.repository;

import com.bigdata.products.common.model.CommonCacheProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonCacheRepository<A extends CommonCacheProduct> extends JpaRepository<A, Integer> {
}
