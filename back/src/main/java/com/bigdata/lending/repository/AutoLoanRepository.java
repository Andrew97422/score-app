package com.bigdata.lending.repository;

import com.bigdata.lending.model.entity.AutoLoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoLoanRepository extends JpaRepository<AutoLoanEntity, Integer> {
    List<AutoLoanEntity> findByMinLoanAmountLessThan(float creditAmount);
}
