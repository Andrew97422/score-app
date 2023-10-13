package com.bigdata.products.autoloan.repository;

import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.common.repository.CommonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoLoanRepository extends CommonRepository<AutoLoanEntity> {
    @Query(
            value = "SELECT * FROM auto_loan WHERE (now() - start_date) < '24 hours' and is_perpetual = false",
            nativeQuery = true
    )
    List<AutoLoanEntity> findAllSoonActive();

    @Query(
            value = "SELECT * FROM auto_loan WHERE (now() - finish_date) < '24 hours' and is_perpetual = false",
            nativeQuery = true
    )
    List<AutoLoanEntity> findAllSoonNotActive();
}
