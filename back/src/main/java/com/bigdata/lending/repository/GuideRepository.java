package com.bigdata.lending.repository;

import com.bigdata.lending.model.entity.GuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository<A extends GuideEntity, Integer>
        extends JpaRepository<GuideEntity, Integer> {
}
