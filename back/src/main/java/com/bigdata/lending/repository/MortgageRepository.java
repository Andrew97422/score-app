package com.bigdata.lending.repository;

import com.bigdata.lending.model.entity.MortgageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MortgageRepository extends JpaRepository<MortgageEntity, Integer> {
    List<MortgageEntity> findByMinLoanAmountLessThan(float creditAmount);
}
