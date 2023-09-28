package com.bigdata.products.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonRepository<A extends CommonEntity> extends JpaRepository<A, Integer> {
    List<A> findByMinLoanAmountLessThan(float minAmount);
}
