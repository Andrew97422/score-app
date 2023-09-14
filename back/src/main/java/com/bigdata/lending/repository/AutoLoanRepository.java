package com.bigdata.lending.repository;

import com.bigdata.lending.model.entity.AutoLoanEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoLoanRepository extends GuideRepository<AutoLoanEntity, Integer> {
    List<AutoLoanEntity> findByMinLoanAmountLessThan(float creditAmount);
}
