package com.bigdata.application.repository;

import com.bigdata.application.model.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<LoanApplicationEntity,Integer> {
}
