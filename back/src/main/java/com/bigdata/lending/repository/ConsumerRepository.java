package com.bigdata.lending.repository;

import com.bigdata.lending.model.entity.ConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerRepository extends GuideRepository<ConsumerEntity, Integer> {
    List<ConsumerEntity> findByMinLoanAmountLessThan(float creditAmount);
}
