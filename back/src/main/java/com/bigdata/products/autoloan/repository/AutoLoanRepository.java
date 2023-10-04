package com.bigdata.products.autoloan.repository;

import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.common.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoLoanRepository extends CommonRepository<AutoLoanEntity> {
}
